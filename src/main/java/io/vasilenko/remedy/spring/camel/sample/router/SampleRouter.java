package io.vasilenko.remedy.spring.camel.sample.router;

import org.apache.camel.LoggingLevel;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SampleRouter extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:sample")
                .log(LoggingLevel.INFO, this.getClass().getTypeName(), "body: ${body}")
                .process(exchange -> {
                    Message message = exchange.getMessage();
                    String name = message.getBody().toString();
                    String greeting = "Hello " + name;
                    message.setBody(greeting);
                });
    }
}
