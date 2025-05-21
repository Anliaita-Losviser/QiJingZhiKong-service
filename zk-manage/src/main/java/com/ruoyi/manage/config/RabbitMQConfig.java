package com.ruoyi.manage.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {
    //  创建RabbitAdmin对象
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);  // 服务启动时候开启自动启动
        return rabbitAdmin;
    }
    
    
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public RabbitTemplate.ReturnsCallback returnsCallback() {
        return new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                System.out.println("监听到消息return callback");
                System.out.println("交换机:" + returned.getExchange() +
                        "\n路由键:" + returned.getRoutingKey() +
                        "\n消息:" + returned.getMessage());
                System.out.println("原因代码:" + returned.getReplyCode() + "原因内容" + returned.getReplyText());
            }
        };
    }
}
