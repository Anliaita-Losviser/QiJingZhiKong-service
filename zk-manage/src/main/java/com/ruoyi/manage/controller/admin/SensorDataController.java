package com.ruoyi.manage.controller.admin;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.manage.domain.po.SensorData;
import com.ruoyi.manage.domain.vo.SensorHourlyData;
import com.ruoyi.manage.service.ISensorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/manage/sensorData")
public class SensorDataController {
    
    @Autowired
    private ISensorDataService sensorDataService;
    
    @GetMapping("/list")
    public AjaxResult getSensorDataList(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
                                        @RequestParam String roomNumber) {
//        System.out.println(startTime);
//        System.out.println(endTime);
//        System.out.println(roomNumber);
        //从mongodb查询
        List<SensorHourlyData> sensorDatas = sensorDataService.getSensorDataList(startTime, endTime, roomNumber);
        return AjaxResult.success(sensorDatas);
    }
}
