package cuifua.service;

import cuifua.vo.msm.MsmVo;

/**
 * @Author cuifua
 * @Date 2021/7/31 16:28
 * @Version 1.0
 */
public interface MsmService
{
    //发送手机验证码
    boolean send(String phone, String code);

    //mq使用发送短信
    boolean send(MsmVo msmVo);
}
