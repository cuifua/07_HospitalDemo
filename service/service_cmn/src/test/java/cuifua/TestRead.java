package cuifua;

import com.alibaba.excel.EasyExcel;

/**
 * @Author cuifua
 * @Date 2021/7/25 15:47
 * @Version 1.0
 */
public class TestRead
{
    public static void main(String[] args)
    {
        //要读取的文件的路径
        String fileName = "D:\\1-GoalMenu\\06_Other\\excel\\02.xlsx";

        //调用方法实现读操作
        EasyExcel.read(fileName,UserData.class,new ExcelListener()).sheet().doRead();
    }
}
