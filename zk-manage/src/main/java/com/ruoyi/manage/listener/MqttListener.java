package com.ruoyi.manage.listener;

import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MqttListener {
    
    // 消息数据
    private JSONObject messageData;
    
    // 接收消息
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "smc.mqtt.rev.queue",durable = "true"),
            exchange = @Exchange(name = "mqtt.direct"),
            key = "mqtt.rev"))
    public void receiveMessage(String message) {
        //System.out.println("message:" + message);
        //解析消息
        messageData = JSONObject.parseObject(message);
    }
    
    public JSONObject getMessageData() {
        return messageData;
    }
}
