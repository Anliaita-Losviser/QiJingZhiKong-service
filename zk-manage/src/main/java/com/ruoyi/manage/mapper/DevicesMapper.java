package com.ruoyi.manage.mapper;

import java.util.List;
import com.ruoyi.manage.domain.po.Devices;

/**
 * 设备信息Mapper接口
 * 
 * @author mzy
 * @date 2025-04-08
 */
public interface DevicesMapper 
{
    /**
     * 查询设备信息
     * 
     * @param id 设备信息主键
     * @return 设备信息
     */
    public Devices selectDevicesById(Long id);
    
    /**
     * 根据房间号查询设备信息
     * @param roomNumber
     * @return
     */
    public Devices selectDevicesByRoomNumber(String roomNumber);

    /**
     * 查询设备信息列表
     * 
     * @param devices 设备信息
     * @return 设备信息集合
     */
    public List<Devices> selectDevicesList(Devices devices);

    /**
     * 新增设备信息
     * 
     * @param devices 设备信息
     * @return 结果
     */
    public int insertDevices(Devices devices);

    /**
     * 修改设备信息
     * 
     * @param devices 设备信息
     * @return 结果
     */
    public int updateDevices(Devices devices);

    /**
     * 删除设备信息
     * 
     * @param id 设备信息主键
     * @return 结果
     */
    public int deleteDevicesById(Long id);

    /**
     * 批量删除设备信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDevicesByIds(Long[] ids);
}
