package cuifua.exception;

import cuifua.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author cuifua
 * @Date 2021/7/23 20:13
 * @Version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回结果可以用json结果输出
    public Result error(Exception e)
    {
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(YyghException.class)
    @ResponseBody//返回结果可以用json结果输出
    public Result error(YyghException e)
    {
        e.printStackTrace();
        return Result.fail();
    }
}
