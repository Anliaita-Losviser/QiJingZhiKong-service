package com.ruoyi.manage.controller.user;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.constant.MqttConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.manage.domain.dto.MqttCommandDTO;
import com.ruoyi.manage.listener.MqttListener;
import com.ruoyi.manage.service.IMqttService;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

@RestController
@RequestMapping("/user/mqtt")
public class MqttCommandController extends BaseController {
    
    @Autowired
    private IMqttService mqttService;
    
    @Autowired
    private MqttListener mqttListener;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private RabbitAdmin rabbitAdmin;
    
    /**
     * 发送指令
     *
     * @param mqttCommandDTO
     * @return
     */
    @Anonymous
    @PostMapping("/pubcommand")
    public AjaxResult PublishCommand(@RequestBody MqttCommandDTO mqttCommandDTO) {
        //构造主题，将房间号拼接进去
        String topic = MqttConstants.MQTT_TOPIC_HEADER + mqttCommandDTO.getRoomNumber() + MqttConstants.MQTT_TOPIC_PUBLISH;
        //发布消息
        try {
            mqttService.publishMessage(topic, mqttCommandDTO.getCommand().toJSONString());
            return AjaxResult.success();
        } catch (Exception e) {
            System.out.println("发送消息失败");
            System.out.println(e.getMessage());
            return AjaxResult.error();
        }
    }
    
    @Anonymous
    @GetMapping("/revmsg/{roomNumber}")
    public AjaxResult ReceiveMessage(@PathVariable String roomNumber) {
        //System.out.println("查询消息:"+roomNumber);
        try {
            //从队列获取消息
//            Message message = rabbitTemplate.receive("smc.device."+roomNumber);
//            JSONObject messageData = JSONObject.parseObject(new String(message.getBody()));
//            System.out.println("接收到消息:"+ messageData);
            QueueInformation queueInformation = rabbitAdmin.getQueueInfo("smc.device." + roomNumber);
            //System.out.println("队列信息:"+queueInformation);
            //设备离线，返回默认值
            if (queueInformation == null) {
                JSONObject messageData = new JSONObject();
                messageData.put("Temperature", "NaN");
                messageData.put("Humidity", "NaN");
                messageData.put("time", LocalDateTime.now());
                return AjaxResult.success(messageData);
            }
            
            JSONObject messageData = JSONObject.parseObject(
                    (String) rabbitTemplate.receiveAndConvert(
                            "smc.device." + roomNumber)
            );
            //System.out.println("接收到消息:" + messageData);
            //System.out.println(messageData.getDate("time"));
//            System.out.println(LocalDateTime.parse((CharSequence) messageData.get("time"), new DateTimeFormatterBuilder()
//                    .appendPattern("yyyy-MM-dd HH:mm:ss")
//                    .optionalStart()
//                    .appendFraction(ChronoField.NANO_OF_SECOND, 0, 6, true)
//                    .optionalEnd()
//                    .toFormatter()));
//            System.out.println("当前时间");
//            System.out.println(LocalDateTime.now());
            //System.out.println(messageData.getClass());
            // 队列为空，没有新消息，则返回默认值
            if (messageData == null) {
                messageData = new JSONObject();
                messageData.put("Temperature", "NaN");
                messageData.put("Humidity", "NaN");
                messageData.put("time", LocalDateTime.now());
                return AjaxResult.success(messageData);
            }
            // 构建支持0-6位小数秒的格式化器
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss")
            .optionalStart()
            .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
            .optionalEnd()
            .toFormatter();
            
            LocalDateTime messageTime = LocalDateTime.parse((CharSequence) messageData.get("time"), formatter);
            //TODO 如果当前时间与消息时间相差超过3秒，获取下一个消息
            Duration duration = Duration.between(messageTime, LocalDateTime.now());
            Duration absDuration = duration.abs();
            while (absDuration.getSeconds() > 3) {
                messageData = JSONObject.parseObject(
                        (String) rabbitTemplate.receiveAndConvert(
                                "smc.device." + roomNumber)
                );
                if(messageData == null){
                    break;
                }
                messageTime = LocalDateTime.parse((CharSequence) messageData.get("time"), formatter);
                duration = Duration.between(messageTime, LocalDateTime.now());
                absDuration = duration.abs();
            }
            
            return AjaxResult.success(messageData);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return AjaxResult.error();
        }
    }
}
