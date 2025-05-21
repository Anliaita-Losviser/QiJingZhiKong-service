package com.ruoyi.manage.listener;

import com.ruoyi.common.constant.StatusConstants;
import com.ruoyi.manage.domain.po.Devices;
import com.ruoyi.manage.service.impl.DevicesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceListener {
    
    @Autowired
    private DevicesServiceImpl devicesService;
    // 接收消息
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "smc.device.status.queue",durable = "true"),
            exchange = @Exchange(name = "device.status.direct"),
            key = "device.status.rev"))
    public void receiveMessage(String message) {
        //System.out.println("设备上下线:" + message);
        String roomNumber = message.split("/")[0];
        String status = message.split("/")[1];
        //调用服务，修改设备上下线状态
        Devices devices = devicesService.selectDevicesByRoomNumber(roomNumber);
        //System.out.println(devices.toString());
        devices.setIsOnline(status.equals(StatusConstants.CONNECTED) ? StatusConstants.ONLINE : StatusConstants.OFFLINE);
        devicesService.updateDevices(devices);
    }
}
