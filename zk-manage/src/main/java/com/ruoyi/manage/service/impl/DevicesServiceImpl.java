package com.ruoyi.manage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.manage.mapper.DevicesMapper;
import com.ruoyi.manage.domain.po.Devices;
import com.ruoyi.manage.service.IDevicesService;

/**
 * 设备信息Service业务层处理
 * 
 * @author mzy
 * @date 2025-04-08
 */
@Service
public class DevicesServiceImpl implements IDevicesService 
{
    @Autowired
    private DevicesMapper devicesMapper;

    /**
     * 查询设备信息
     * 
     * @param id 设备信息主键
     * @return 设备信息
     */
    @Override
    public Devices selectDevicesById(Long id)
    {
        return devicesMapper.selectDevicesById(id);
    }
    
    @Override
    public Devices selectDevicesByRoomNumber(String roomNumber) {
        return devicesMapper.selectDevicesByRoomNumber(roomNumber);
    }
    
    /**
     * 查询设备信息列表
     * 
     * @param devices 设备信息
     * @return 设备信息
     */
    @Override
    public List<Devices> selectDevicesList(Devices devices)
    {
        return devicesMapper.selectDevicesList(devices);
    }

    /**
     * 新增设备信息
     * 
     * @param devices 设备信息
     * @return 结果
     */
    @Override
    public int insertDevices(Devices devices)
    {
        return devicesMapper.insertDevices(devices);
    }

    /**
     * 修改设备信息
     * 
     * @param devices 设备信息
     * @return 结果
     */
    @Override
    public int updateDevices(Devices devices)
    {
        return devicesMapper.updateDevices(devices);
    }

    /**
     * 批量删除设备信息
     * 
     * @param ids 需要删除的设备信息主键
     * @return 结果
     */
    @Override
    public int deleteDevicesByIds(Long[] ids)
    {
        return devicesMapper.deleteDevicesByIds(ids);
    }

    /**
     * 删除设备信息信息
     * 
     * @param id 设备信息主键
     * @return 结果
     */
    @Override
    public int deleteDevicesById(Long id)
    {
        return devicesMapper.deleteDevicesById(id);
    }
}
