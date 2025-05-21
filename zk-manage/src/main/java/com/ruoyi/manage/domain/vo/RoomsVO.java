package com.ruoyi.manage.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

public class RoomsVO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 房间ID */
    private Long id;

    /** 房间号 */
    @Excel(name = "房间号")
    private String roomNumber;

    /** 关联的房间类型 */
    @Excel(name = "关联的房间类型")
    private String name;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

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

    public void setRoomNumber(String roomNumber)
    {
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber()
    {
        return roomNumber;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("roomNumber", getRoomNumber())
            .append("name", getName())
            .append("status", getStatus())
            .append("createdTime", getCreatedTime())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
