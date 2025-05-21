package com.ruoyi.manage.mapper;

import java.util.List;
import com.ruoyi.manage.domain.po.RoomTypes;

/**
 * 房间类型Mapper接口
 * 
 * @author mzy
 * @date 2025-04-08
 */
public interface RoomTypesMapper 
{
    /**
     * 查询房间类型
     * 
     * @param id 房间类型主键
     * @return 房间类型
     */
    public RoomTypes selectRoomTypesById(Long id);

    /**
     * 查询房间类型列表
     * 
     * @param roomTypes 房间类型
     * @return 房间类型集合
     */
    public List<RoomTypes> selectRoomTypesList(RoomTypes roomTypes);

    /**
     * 新增房间类型
     * 
     * @param roomTypes 房间类型
     * @return 结果
     */
    public int insertRoomTypes(RoomTypes roomTypes);

    /**
     * 修改房间类型
     * 
     * @param roomTypes 房间类型
     * @return 结果
     */
    public int updateRoomTypes(RoomTypes roomTypes);

    /**
     * 删除房间类型
     * 
     * @param id 房间类型主键
     * @return 结果
     */
    public int deleteRoomTypesById(Long id);

    /**
     * 批量删除房间类型
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRoomTypesByIds(Long[] ids);
}
