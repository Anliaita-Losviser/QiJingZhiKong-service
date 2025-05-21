package com.ruoyi.manage.domain.dto;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MqttCommandDTO {
    //房间号
    private String roomNumber;
    //指令消息体
    private JSONObject command;
}
