package cuifua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author cuifua
 * @Date 2021/7/23 13:51
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cuifua")
public class ServiceHospApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ServiceHospApplication.class, args);
    }
}
