package com.ruoyi.manage.service;

import java.util.List;
import com.ruoyi.manage.domain.po.Rooms;
import com.ruoyi.manage.domain.vo.RoomsVO;

/**
 * 房间Service接口
 * 
 * @author mzy
 * @date 2025-04-08
 */
public interface IRoomsService 
{
    /**
     * 查询房间
     * 
     * @param id 房间主键
     * @return 房间
     */
    public Rooms selectRoomsById(Long id);

    /**
     * 查询房间列表
     *
     * @param rooms 房间
     * @return 房间集合
     */
    public List<Rooms> selectRoomsList(Rooms rooms);

    /**
     * 新增房间
     * 
     * @param rooms 房间
     * @return 结果
     */
    public int insertRooms(Rooms rooms);

    /**
     * 修改房间
     * 
     * @param rooms 房间
     * @return 结果
     */
    public int updateRooms(Rooms rooms);

    /**
     * 批量删除房间
     * 
     * @param ids 需要删除的房间主键集合
     * @return 结果
     */
    public int deleteRoomsByIds(Long[] ids);

    /**
     * 删除房间信息
     * 
     * @param id 房间主键
     * @return 结果
     */
    public int deleteRoomsById(Long id);
    
    List<Rooms> selectEnabledRoomsList(Rooms rooms);
}
