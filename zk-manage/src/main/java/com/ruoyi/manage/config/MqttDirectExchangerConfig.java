package com.ruoyi.manage.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttDirectExchangerConfig {
    //声明mqtt.direct交换机
    @Bean
    public DirectExchange mqttDirectExchange(){
        return new DirectExchange("mqtt.direct");
    }
}
