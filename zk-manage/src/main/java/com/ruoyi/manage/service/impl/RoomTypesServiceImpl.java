package com.ruoyi.manage.service.impl;

import java.util.List;

import com.ruoyi.common.constant.StatusConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.manage.mapper.RoomTypesMapper;
import com.ruoyi.manage.domain.po.RoomTypes;
import com.ruoyi.manage.service.IRoomTypesService;

/**
 * 房间类型Service业务层处理
 * 
 * @author mzy
 * @date 2025-04-08
 */
@Service
public class RoomTypesServiceImpl implements IRoomTypesService 
{
    @Autowired
    private RoomTypesMapper roomTypesMapper;

    /**
     * 查询房间类型
     * 
     * @param id 房间类型主键
     * @return 房间类型
     */
    @Override
    public RoomTypes selectRoomTypesById(Long id)
    {
        return roomTypesMapper.selectRoomTypesById(id);
    }

    /**
     * 查询房间类型列表
     * 
     * @param roomTypes 房间类型
     * @return 房间类型
     */
    @Override
    public List<RoomTypes> selectRoomTypesList(RoomTypes roomTypes)
    {
        return roomTypesMapper.selectRoomTypesList(roomTypes);
    }

    /**
     * 新增房间类型
     * 
     * @param roomTypes 房间类型
     * @return 结果
     */
    @Override
    public int insertRoomTypes(RoomTypes roomTypes)
    {
        return roomTypesMapper.insertRoomTypes(roomTypes);
    }

    /**
     * 修改房间类型
     * 
     * @param roomTypes 房间类型
     * @return 结果
     */
    @Override
    public int updateRoomTypes(RoomTypes roomTypes)
    {
        return roomTypesMapper.updateRoomTypes(roomTypes);
    }

    /**
     * 批量删除房间类型
     * 
     * @param ids 需要删除的房间类型主键
     * @return 结果
     */
    @Override
    public int deleteRoomTypesByIds(Long[] ids)
    {
        return roomTypesMapper.deleteRoomTypesByIds(ids);
    }

    /**
     * 删除房间类型信息
     * 
     * @param id 房间类型主键
     * @return 结果
     */
    @Override
    public int deleteRoomTypesById(Long id)
    {
        return roomTypesMapper.deleteRoomTypesById(id);
    }
    
    @Override
    public List<RoomTypes> selectEnabledRoomTypesList(RoomTypes roomTypes) {
        roomTypes.setStatus(StatusConstants.ENABLE);
        return roomTypesMapper.selectRoomTypesList(roomTypes);
    }
}
