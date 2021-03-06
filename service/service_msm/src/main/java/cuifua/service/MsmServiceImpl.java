package cuifua.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import cuifua.service.MsmService;
import cuifua.utils.ConstantPropertiesUtils;
import cuifua.vo.msm.MsmVo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author cuifua
 * @Date 2021/7/31 16:28
 * @Version 1.0
 */
@Service
public class MsmServiceImpl implements MsmService
{
    //发送手机验证码
    @Override
    public boolean send(String phone, String code)
    {
        //判断手机号是否为空
        if(ObjectUtils.isEmpty(phone))
            return false;

        //整合阿里云短信服务
        //设置相关参数
        DefaultProfile profile = DefaultProfile.
                getProfile(ConstantPropertiesUtils.REGION_Id,
                        ConstantPropertiesUtils.ACCESS_KEY_ID,
                        ConstantPropertiesUtils.SECRECT);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();

        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //手机号
        request.putQueryParameter("PhoneNumbers", phone);

        //签名名称
        request.putQueryParameter("SignName", "我的谷粒在线教育网站");

        //模板code
        request.putQueryParameter("TemplateCode", "SMS_180051135");

        //验证码  使用json格式   {"code":"123456"}
        Map<String,Object> param = new HashMap();
        param.put("code",code);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        //调用方法进行短信发送
        try
        {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        }
        catch (ServerException e)
        {
            e.printStackTrace();
        } catch (ClientException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    //mq发送短信封装
    @Override
    public boolean send(MsmVo msmVo)
    {
        if(!ObjectUtils.isEmpty(msmVo.getPhone()))
        {
            boolean isSend = this.send(msmVo.getPhone(), msmVo.getParam());
            return isSend;
        }
        return false;
    }

    //为OrderServiceImpl定做mq的发送短信的方法
    private boolean send(String phone, Map<String,Object> param)
    {
        //判断手机号是否为空
        if(ObjectUtils.isEmpty(phone))
            return false;

        //整合阿里云短信服务
        //设置相关参数
        DefaultProfile profile = DefaultProfile.
                getProfile(ConstantPropertiesUtils.REGION_Id,
                        ConstantPropertiesUtils.ACCESS_KEY_ID,
                        ConstantPropertiesUtils.SECRECT);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //手机号
        request.putQueryParameter("PhoneNumbers", phone);
        //签名名称
        request.putQueryParameter("SignName", "我的谷粒在线教育网站");
        //模板code
        request.putQueryParameter("TemplateCode", "SMS_180051135");

        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        //调用方法进行短信发送
        try
        {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        }
        catch (ServerException e)
        {
            e.printStackTrace();
        }

        catch (ClientException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
