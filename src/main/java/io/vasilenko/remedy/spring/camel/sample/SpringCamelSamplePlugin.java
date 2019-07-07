package io.vasilenko.remedy.spring.camel.sample;

import com.bmc.arsys.api.Value;
import com.bmc.arsys.pluginsvr.plugins.ARFilterAPIPlugin;
import com.bmc.arsys.pluginsvr.plugins.ARPluginContext;
import com.bmc.thirdparty.org.slf4j.Logger;
import com.bmc.thirdparty.org.slf4j.LoggerFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan
public class SpringCamelSamplePlugin extends ARFilterAPIPlugin {

    private static final int INPUT_NAME_VALUE_INDEX = 0;

    private final Logger log = LoggerFactory.getLogger(SpringCamelSamplePlugin.class);

    private AnnotationConfigApplicationContext applicationContext;
    private CamelContext camelContext;

    @Autowired
    public void setCamelContext(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @Override
    public void initialize(ARPluginContext context) {
        applicationContext = new AnnotationConfigApplicationContext(SpringCamelSamplePlugin.class);
        applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
        log.info("initialized");
    }

    @Override
    public List<Value> filterAPICall(ARPluginContext context, List<Value> inputValues) {
        String name = String.valueOf(inputValues.get(INPUT_NAME_VALUE_INDEX));
        log.info("name: {}", name);

        String greeting = (String) camelContext.createProducerTemplate()
                .sendBody("direct:sample", ExchangePattern.InOut, name);

        log.info("greeting: {}", greeting);
        List<Value> outputList = new ArrayList<>();
        outputList.add(new Value(greeting));
        return outputList;
    }

    @Override
    public void terminate(ARPluginContext context) {
        applicationContext.close();
        try {
            camelContext.stop();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
