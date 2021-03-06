package cuifua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author cuifua
 * @Date 2021/7/25 10:37
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceCmnApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ServiceCmnApplication.class, args);
    }
}
