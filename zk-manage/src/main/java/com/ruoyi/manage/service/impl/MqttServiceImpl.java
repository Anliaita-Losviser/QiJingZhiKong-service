package com.ruoyi.manage.service.impl;

import com.ruoyi.manage.service.IMqttService;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

@DependsOn("mqttClient")
@Service
public class MqttServiceImpl implements IMqttService {
    @Autowired
    private MqttAsyncClient mqttClient;
    
    @Value("${mqtt.qos}")
    private int qos;
    
    /**
     * 发布消息
     *
     * @param topic
     * @param message
     */
    @Override
    public void publishMessage(String topic, String message) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(false);  // 是否保留消息
        // 发布消息
        mqttClient.publish(topic, mqttMessage);
    }
    
    /**
     * 断开连接
     */
    @PreDestroy
    public void disconnect() {
        if (mqttClient != null && mqttClient.isConnected()) {
            try {
                mqttClient.disconnect();
            } catch (Exception e) {
                System.out.println("断开连接错误");
                System.out.println(e.getMessage());
            }
        }
    }
}
