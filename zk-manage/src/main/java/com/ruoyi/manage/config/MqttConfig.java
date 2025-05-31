package com.ruoyi.manage.config;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.MqttConstants;
import com.ruoyi.common.constant.StatusConstants;
import com.ruoyi.manage.domain.po.SensorData;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MqttConfig {
    
    @Value("${mqtt.broker-url}")
    private String brokerUrl;
    
    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;
    
    @Value("${mqtt.timeout}")
    private int timeout;
    
    @Value("${mqtt.keepalive}")
    private int keepalive;
    
    @Value("${mqtt.qos}")
    private int qos;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private RabbitAdmin rabbitAdmin;
    
    @Autowired
    private DirectExchange mqttDirectExchange;
    
    @Bean
    public MqttAsyncClient mqttClient() throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setCleanSession(true);       // 是否清理会话
        options.setAutomaticReconnect(true); // 自动重连
        options.setConnectionTimeout(timeout);    // 连接超时（秒）
        options.setKeepAliveInterval(keepalive);    // 保活周期（秒）
        MemoryPersistence persistence = new MemoryPersistence();
        System.out.println("初始化MQTT客户端");
        System.out.println(brokerUrl);
        System.out.println(clientId);
        MqttAsyncClient client = new MqttAsyncClient(brokerUrl, clientId, persistence);
        
        client.setCallback(new MqttCallbackExtended(){
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                System.out.println("MQTT 连接成功（重连状态: " + reconnect + "）");
                // 重连后需要重新订阅主题
                try {
                    IMqttToken subscribeToken1 = client.subscribe(
                            MqttConstants.MQTT_TOPIC_HEADER + "+" + MqttConstants.MQTT_TOPIC_RECEIVE,
                            1);
                    subscribeToken1.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            System.out.println("订阅主题 " + MqttConstants.MQTT_TOPIC_HEADER + "+" + MqttConstants.MQTT_TOPIC_RECEIVE + " 成功");
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            System.err.println("订阅主题 " + MqttConstants.MQTT_TOPIC_HEADER + "+" + MqttConstants.MQTT_TOPIC_RECEIVE + " 失败: " + exception.getMessage());
                        }
                    });

                    IMqttToken subscribeToken2 = client.subscribe(
                            "$SYS/brokers/emqx@172.18.0.2/clients/+/connected",
                            1);
                    subscribeToken2.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            System.out.println("订阅设备上线主题成功");
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            System.err.println("订阅设备上线主题失败: " + exception.getMessage());
                        }
                    });

                    IMqttToken subscribeToken3 = client.subscribe(
                            "$SYS/brokers/emqx@172.18.0.2/clients/+/disconnected",
                            1);
                    subscribeToken3.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            System.out.println("订阅设备下线主题成功");
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            System.err.println("订阅设备下线主题失败: " + exception.getMessage());
                        }
                    });
                    } catch (MqttException e) {
                        System.err.println("重连后订阅失败: " + e.getMessage());
                    }
            }

            @Override
            public void connectionLost(Throwable cause) {
                System.err.println("MQTT 连接丢失: " + cause.getMessage());
                System.err.println(cause);
            }
            
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                // 消息处理逻辑，先判断是不是系统主题
                //System.out.println(topic);
                if(topic.startsWith("$SYS")){
                    //System.out.println("设备上下线消息：" + topic);
                    //解析出房间号
                    String roomNumber = topic.split("/")[4].split("-")[0];
                    String connectStatus = topic.split("/")[5];
                    if(roomNumber.matches("^[0-9]+$")){
                        //System.out.println(roomNumber);
                        //System.out.println(connectStatus);
                        //投递上下线消息给房间服务和设备服务
                        try {
                            rabbitTemplate.convertAndSend("device.status.direct",
                                "device.status.rev",
                                roomNumber+"/"+connectStatus);
                            //动态创建或销毁消息队列
                            if(connectStatus.equals(StatusConstants.CONNECTED)){
                                //为每个房间号创建消息队列
                                Map<String, Object> args = new HashMap<>();
                                args.put("x-message-ttl", 5000); // 队列级别 TTL 5秒过期
                                rabbitAdmin.declareQueue(new Queue(
                                        "smc.device."+roomNumber,
                                        true,
                                        false,
                                        false,
                                        args));
                                //绑定交换机
                                rabbitAdmin.declareBinding(new Binding("smc.device."+roomNumber,
                                        Binding.DestinationType.QUEUE,
                                        mqttDirectExchange.getName(),
                                        roomNumber+".rev",
                                        null));
                            }else if(connectStatus.equals(StatusConstants.DISCONNECTED)){
                                //删除消息队列
                                rabbitAdmin.deleteQueue("smc.device."+roomNumber);
                            }
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    //不是系统主题，则分割主题，提取房间号
                    String roomNumber = topic.split("/")[1];
                    //System.out.println("接收到消息，房间号：" + roomNumber);
                    //获取消息体
                    String payload = new String(message.getPayload());
                    JSONObject payloadJson = JSONObject.parseObject(payload);
                    SensorData sensorData = SensorData.builder()
                            .id(payloadJson.getString("id"))
                            .Temperature(payloadJson.getString("Temperature"))
                            .Humidity(payloadJson.getString("Humidity"))
                            .timeStamp(LocalDateTime.now())
                            .build();
                    //System.out.println(sensorData);
                    // 添加时间戳
                    payloadJson.put("time", LocalDateTime.now());
                    System.out.println(payloadJson);
                    //System.out.println("消息体"+payload);
                    //按房间号写入mongodb
                    mongoTemplate.save(sensorData, roomNumber);
                    // TODO: 处理接收到的消息，按房间号投递到对应的消息队列
                    try {
                        payload = payloadJson.toJSONString();
                        rabbitTemplate.convertAndSend(mqttDirectExchange.getName(),
                                roomNumber+".rev",
                                payload);
                    } catch (Exception e) {
                        System.out.println("投递消息失败:" + e.getMessage());
                    }
                }
            }
            
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                // 消息发布完成回调
            }
        }
        );
        
        try {
            client.connect(options);
            System.out.println("MQTT 连接成功");
            return client;
        } catch (MqttException e) {
            System.out.println("MQTT客户端错误");
            System.out.println(e.getReasonCode());
            System.out.println(e);
            throw e;
        }
    }
}
