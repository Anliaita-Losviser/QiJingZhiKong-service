package com.ruoyi.manage.service;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface IMqttService {
    
    public void publishMessage(String topic, String message) throws MqttException;
}
