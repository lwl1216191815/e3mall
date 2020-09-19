package cn.e3mall.service.config.spring;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * dubbo配置
 */
@Configuration
@EnableDubbo(scanBasePackages = "cn.e3mall.service.impl")
@PropertySource("classpath:conf/dubbo-provider.properties")
public class DubboApplicationContextConfig {
}
