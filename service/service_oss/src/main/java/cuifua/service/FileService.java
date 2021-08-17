package cuifua.service;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author cuifua
 * @Date 2021/8/3 12:22
 * @Version 1.0
 */
@Repository
public interface FileService
{
    //上传文件到阿里云oss
    String upload(MultipartFile file);
}
