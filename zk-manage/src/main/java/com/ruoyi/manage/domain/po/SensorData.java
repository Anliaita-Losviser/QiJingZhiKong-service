package com.ruoyi.manage.domain.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class SensorData implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    
    private String Temperature;
    
    private String Humidity;
    
    private LocalDateTime timeStamp;
}
