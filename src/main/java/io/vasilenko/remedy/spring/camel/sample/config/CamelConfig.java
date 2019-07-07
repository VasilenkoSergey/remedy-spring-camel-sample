package io.vasilenko.remedy.spring.camel.sample.config;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig extends CamelConfiguration {

    @Bean
    public CamelContext camelContext(ApplicationContext applicationContext) {
        SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
        camelContext.disableJMX();
        return camelContext;
    }
}
