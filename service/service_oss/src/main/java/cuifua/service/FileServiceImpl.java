package cuifua.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import cuifua.util.ConstantOssPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author cuifua
 * @Date 2021/8/3 12:23
 * @Version 1.0
 */
public class FileServiceImpl implements FileService
{
    //上传文件到阿里云oss
    @Override
    public String upload(MultipartFile file)
    {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantOssPropertiesUtils.EDNPOINT;
        String accessKeyId = ConstantOssPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantOssPropertiesUtils.SECRECT;
        String bucketName = ConstantOssPropertiesUtils.BUCKET;

        try
        {
            // 创建OSSClient实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();

            //生成随机唯一值，使用uuid，添加到文件名称里面（防止上传的名字和内容一模一样）
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid+fileName;

            //按照当前日期，创建文件夹，上传到创建文件夹里面=====》2021/02/02/01.jpg
            String timeUrl = new DateTime().toString("yyyy/MM/dd");
            fileName = timeUrl+"/"+fileName;

            //调用方法实现上传
            // 1.jpg    /a/b/1.jpg
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //上传之后文件路径=====》https://yygh-atguigu.oss-cn-beijing.aliyuncs.com/01.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;

            return url;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

    }
}
