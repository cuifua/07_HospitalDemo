package cuifua.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author cuifua
 * @Date 2021/7/31 14:10
 * @Version 1.0
 */
@Configuration
@MapperScan("cuifua.mapper")
public class UserConfig
{

}
