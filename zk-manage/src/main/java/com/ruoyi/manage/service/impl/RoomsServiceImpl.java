package com.ruoyi.manage.service.impl;

import com.ruoyi.common.constant.StatusConstants;
import com.ruoyi.manage.domain.po.Rooms;
import com.ruoyi.manage.mapper.RoomsMapper;
import com.ruoyi.manage.service.IRoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.timeseries.Granularity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 房间Service业务层处理
 * 
 * @author mzy
 * @date 2025-04-08
 */
@Service
public class RoomsServiceImpl implements IRoomsService 
{
    @Autowired
    private RoomsMapper roomsMapper;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询房间
     * 
     * @param id 房间主键
     * @return 房间
     */
    @Override
    public Rooms selectRoomsById(Long id)
    {
        return roomsMapper.selectRoomsById(id);
    }

    /**
     * 查询房间列表
     *
     * @param rooms 房间
     * @return 房间
     */
    @Override
    public List<Rooms> selectRoomsList(Rooms rooms)
    {
        List<Rooms> list = roomsMapper.selectRoomsList(rooms);
        //System.out.println(list);
        return list;
    }

    /**
     * 新增房间
     * 
     * @param rooms 房间
     * @return 结果
     */
    @Override
    public int insertRooms(Rooms rooms)
    {
        //创建mongodb时序集合
        // 检查集合是否存在，不存在则创建
        if (!mongoTemplate.collectionExists(rooms.getRoomNumber())) {
            mongoTemplate.createCollection(rooms.getRoomNumber(),
                    CollectionOptions.empty().timeSeries(
                            CollectionOptions.TimeSeriesOptions
                                    .timeSeries("timeStamp")
                                    .granularity(Granularity.HOURS)
                    )
            );
        }
        return roomsMapper.insertRooms(rooms);
    }

    /**
     * 修改房间
     * 
     * @param rooms 房间
     * @return 结果
     */
    @Override
    public int updateRooms(Rooms rooms)
    {
        return roomsMapper.updateRooms(rooms);
    }

    /**
     * 批量删除房间
     * 
     * @param ids 需要删除的房间主键
     * @return 结果
     */
    @Override
    public int deleteRoomsByIds(Long[] ids)
    {
        return roomsMapper.deleteRoomsByIds(ids);
    }

    /**
     * 删除房间信息
     * 
     * @param id 房间主键
     * @return 结果
     */
    @Override
    public int deleteRoomsById(Long id)
    {
        return roomsMapper.deleteRoomsById(id);
    }
    
    @Override
    public List<Rooms> selectEnabledRoomsList(Rooms rooms) {
        rooms.setStatus(StatusConstants.ENABLE);
        return roomsMapper.selectRoomsList(rooms);
    }
}
