package com.ruoyi.manage.domain.po;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备信息对象 devices
 * 
 * @author mzy
 * @date 2025-04-08
 */
public class Devices extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备ID */
    private Long id;

    /** 设备id */
    @Excel(name = "设备id")
    private String deviceId;

    /** 设备mqtt主题 */
    @Excel(name = "设备mqtt主题")
    private String topic;

    /** 关联的房间ID */
    @Excel(name = "关联的房间ID")
    private Long roomId;

    /** 状态（enabled-1/disabled-0） */
    @Excel(name = "状态", readConverterExp = "e=nabled-1/disabled-0")
    private Long status;

    /** 在线状态（online-1/offline-0） */
    @Excel(name = "在线状态", readConverterExp = "o=nline-1/offline-0")
    private Long isOnline;

    /** 创建时间 */
    private Date createdTime;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setDeviceId(String deviceId) 
    {
        this.deviceId = deviceId;
    }

    public String getDeviceId() 
    {
        return deviceId;
    }

    public void setTopic(String topic) 
    {
        this.topic = topic;
    }

    public String getTopic() 
    {
        return topic;
    }

    public void setRoomId(Long roomId) 
    {
        this.roomId = roomId;
    }

    public Long getRoomId() 
    {
        return roomId;
    }

    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    public void setIsOnline(Long isOnline) 
    {
        this.isOnline = isOnline;
    }

    public Long getIsOnline() 
    {
        return isOnline;
    }

    public void setCreatedTime(Date createdTime) 
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() 
    {
        return createdTime;
    }

    public void setUpdatedTime(Date updatedTime) 
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime() 
    {
        return updatedTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deviceId", getDeviceId())
            .append("topic", getTopic())
            .append("roomId", getRoomId())
            .append("status", getStatus())
            .append("isOnline", getIsOnline())
            .append("createdTime", getCreatedTime())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
