package com.ruoyi.manage.service;

import java.util.List;
import com.ruoyi.manage.domain.po.Devices;

/**
 * 设备信息Service接口
 * 
 * @author mzy
 * @date 2025-04-08
 */
public interface IDevicesService 
{
    /**
     * 查询设备信息
     * 
     * @param id 设备信息主键
     * @return 设备信息
     */
    public Devices selectDevicesById(Long id);

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
     * 批量删除设备信息
     * 
     * @param ids 需要删除的设备信息主键集合
     * @return 结果
     */
    public int deleteDevicesByIds(Long[] ids);

    /**
     * 删除设备信息信息
     * 
     * @param id 设备信息主键
     * @return 结果
     */
    public int deleteDevicesById(Long id);
}
