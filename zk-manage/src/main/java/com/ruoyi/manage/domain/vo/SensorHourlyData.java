package com.ruoyi.manage.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorHourlyData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private LocalDateTime hour;
    private Double averageTemperature;
    private Double averageHumidity;
}
