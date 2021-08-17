package cuifua;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @Author cuifua
 * @Date 2021/7/25 15:43
 * @Version 1.0
 */
public class ExcelListener extends AnalysisEventListener<UserData>
{
    //一行一行读取excel内容，从第二行读取
    @Override
    public void invoke(UserData userData, AnalysisContext analysisContext)
    {
        System.out.println(userData);
    }

    //读取表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context)
    {
        System.out.println("表头信息"+headMap);
    }

    //读取之后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext)
    {

    }
}
