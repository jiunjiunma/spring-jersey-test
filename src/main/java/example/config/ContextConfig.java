package example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import example.service.SampleService;

/**
 * Date: 6/10/13
 * Time: 5:54 PM
 */
@Configuration
@ComponentScan(basePackages={"example.rest", "example.service"})
public class ContextConfig {
    @Bean
    static public SampleService sampleService() {
        return new SampleService();
    }
}
