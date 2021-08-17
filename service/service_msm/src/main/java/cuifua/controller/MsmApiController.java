package cuifua.controller;

import cuifua.config.RedisConfig;
import cuifua.result.Result;
import cuifua.service.MsmService;
import cuifua.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author cuifua
 * @Date 2021/7/31 16:27
 * @Version 1.0
 */
@Api(tags = "短信验证码服务")
@RestController
@RequestMapping("/api/msm")
public class MsmApiController
{
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送手机验证码
    @ApiOperation(value = "发送手机验证码")
    @GetMapping("send/{phone}")
    public Result sendCode(@PathVariable String phone)
    {
        //先从redis获取验证码，如果获取得到，返回ok
        //key---手机号、value---验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (!ObjectUtils.isEmpty(code))
            return Result.ok();

        //如果从redis获取不到，生成验证码，通过整合短信服务进行发送
        code = RandomUtil.getSixBitRandom();

        //调用service 通过整合短信服务进行发送
        boolean isSend = msmService.send(phone, code);
        if (isSend)
        {
            redisTemplate.opsForValue().set(phone, code, 2, TimeUnit.MINUTES);
            return Result.ok();
        }
        else return Result.fail().message("发送短信失败");
    }
}
