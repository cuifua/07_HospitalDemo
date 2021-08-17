package cuifua;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author cuifua
 * @Date 2021/7/25 12:47
 * @Version 1.0
 */
@Data
public class UserData
{
    @ExcelProperty(value = "用户姓名",index = 0)
    private String username;

    @ExcelProperty(value = "用户编号",index = 1)
    private  Integer uid;
}
