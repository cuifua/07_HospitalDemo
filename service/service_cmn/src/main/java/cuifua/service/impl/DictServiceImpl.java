package cuifua.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cuifua.listener.DictListener;
import cuifua.mapper.DictMapper;
import cuifua.model.cmn.Dict;
import cuifua.service.DictService;
import cuifua.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author cuifua
 * @Date 2021/7/23 14:41
 * @Version 1.0
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService
{
    //1、根据数据id查询子数据
    @Override
    @Cacheable(value = "dict",keyGenerator = "keyGenerator")
    public List<Dict> findChildData(Long id)
    {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<Dict> dictList = baseMapper.selectList(wrapper);
        //向List里的每个dict对象设置hasChilden
        for(Dict dict : dictList)
        {
            Long dictId = dict.getId();
            boolean hasChild = this.hasChilden(dictId);
            dict.setHasChildren(hasChild);
        }
        return dictList;
    }
    //1.1、判断id下面是否有子数据
    private boolean hasChilden(Long id)
    {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);

        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }

    //2、导出数据字典的接口
    @Override
    public void exportDictData(HttpServletResponse response)
    {
        //1、设置下载信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        //String fileName = URLEncoder.encode("数据字典", "UTF-8");
        String fileName = "dict";
        response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");

        //2、查询数据库
        List<Dict> dictList = baseMapper.selectList(null);
        //将dict转换DictEeVo(做法就是遍历dictList，将里面的Dict对象复制到dictEeVo类型的)
        List<DictEeVo> dictVoList = new ArrayList<>();
        {
            for(Dict dict : dictList)
            {
                DictEeVo dictEeVo = new DictEeVo();
                BeanUtils.copyProperties(dict,dictEeVo);
                dictVoList.add(dictEeVo);
            }
        }

        //3、调用方法进行写操作
        try
        {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict")
                    .doWrite(dictVoList);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    //3、导入数据字典的接口
    @Override
    @CacheEvict(value = "dict", allEntries=true)//把dict下的所有缓存清空
    public void importDictData(MultipartFile file)
    {
        try
        {
            EasyExcel.read(file.getInputStream(),DictEeVo.class,new DictListener(baseMapper)).sheet().doRead();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //4、根据dictcode和value查询
    @Override
    public String getDictName(String dictCode, String value)
    {
        //如果dictCode为空，直接根据value查询
        if(ObjectUtils.isEmpty(dictCode))
        {
            //直接根据value查询
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("value",value);
            Dict dict = baseMapper.selectOne(wrapper);
            return dict.getName();
        }
        else
        {
            //如果dictCode不为空，根据dictCode和value查询
            //根据dictcode查询dict对象，得到dict的id值
            Dict codeDict = this.getDictByDictCode(dictCode);
            Long parent_id = codeDict.getId();

            //根据parent_id和value进行查询
            Dict finalDict = baseMapper.selectOne(new QueryWrapper<Dict>()
                    .eq("parent_id", parent_id)
                    .eq("value", value));
            return finalDict.getName();
        }
    }

    //5、根据dictCode获取下级节点
    @Override
    public List<Dict> findByDictCode(String dictCode)
    {
        //根据dictcode获取对应dict对象
        Dict dict = this.getDictByDictCode(dictCode);

        //根据dict对象getId(),通过id获取子节点
        List<Dict> chlidData = this.findChildData(dict.getId());
        return chlidData;
    }

    //（工具方法）根据dictCode获取下级节点
    private Dict getDictByDictCode(String dictCode)
    {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_code",dictCode);
        Dict codeDict = baseMapper.selectOne(wrapper);
        return codeDict;
    }
}
