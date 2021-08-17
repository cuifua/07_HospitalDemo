package cuifua.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cuifua.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author cuifua
 * @Date 2021/7/23 14:39
 * @Version 1.0
 */
public interface DictService extends IService<Dict>
{
    //1、根据数据id查询子数据
    List<Dict> findChildData(Long id);

    //2、导出数据字典的接口
    void exportDictData(HttpServletResponse response);

    //3、导入数据字典的接口
    void importDictData(MultipartFile file);

    //4、根据dictcode和value查询
    String getDictName(String dictCode, String value);

    //根据dictCode获取下级节点
    List<Dict> findByDictCode(String dictCode);
}
