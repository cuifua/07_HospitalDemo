package cuifua.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import cuifua.mapper.DictMapper;
import cuifua.model.cmn.Dict;
import cuifua.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

/**
 * @Author cuifua
 * @Date 2021/7/25 16:59
 * @Version 1.0
 */
public class DictListener extends AnalysisEventListener<DictEeVo>
{
    private DictMapper dictMapper;
    public DictListener(DictMapper dictMapper)
    {
        this.dictMapper = dictMapper;
    }

    //一行一行读取，从第二行开始
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext)
    {
        //调用方法添加数据库
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo,dict);
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext)
    {

    }
}
