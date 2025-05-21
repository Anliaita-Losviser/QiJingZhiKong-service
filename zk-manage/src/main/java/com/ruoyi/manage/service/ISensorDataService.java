package com.ruoyi.manage.service;

import com.ruoyi.manage.domain.po.SensorData;
import com.ruoyi.manage.domain.vo.SensorHourlyData;

import java.time.LocalDateTime;
import java.util.List;

public interface ISensorDataService {
    List<SensorHourlyData> getSensorDataList(LocalDateTime startTime, LocalDateTime endTime, String roomNumber);
}
