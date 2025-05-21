package com.ruoyi.manage.controller.admin;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.manage.domain.po.Rooms;
import com.ruoyi.manage.service.IRoomsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 房间Controller
 * 
 * @author mzy
 * @date 2025-04-08
 */
@RestController
@RequestMapping("/manage/rooms")
public class RoomsController extends BaseController
{
    @Autowired
    private IRoomsService roomsService;

    /**
     * 查询房间列表
     */
    @PreAuthorize("@ss.hasPermi('manage:rooms:list')")
    @GetMapping("/list")
    public TableDataInfo list(Rooms rooms)
    {
        startPage();
        List<Rooms> list = roomsService.selectRoomsList(rooms);
        //System.out.println(list);
        return getDataTable(list);
    }
    
    /**
     * 查询启用的房间列表
     * @param rooms
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:rooms:listEnable')")
    @GetMapping("/listEnable")
    public TableDataInfo listEnable(Rooms rooms)
    {
        List<Rooms> list = roomsService.selectEnabledRoomsList(rooms);
        //System.out.println(list);
        return getDataTable(list);
    }

    /**
     * 导出房间列表
     */
    @PreAuthorize("@ss.hasPermi('manage:rooms:export')")
    @Log(title = "房间", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Rooms rooms)
    {
        List<Rooms> list = roomsService.selectRoomsList(rooms);
        ExcelUtil<Rooms> util = new ExcelUtil<Rooms>(Rooms.class);
        util.exportExcel(response, list, "房间数据");
    }

    /**
     * 获取房间详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:rooms:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(roomsService.selectRoomsById(id));
    }

    /**
     * 新增房间
     */
    @PreAuthorize("@ss.hasPermi('manage:rooms:add')")
    @Log(title = "房间", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Rooms rooms)
    {
        return toAjax(roomsService.insertRooms(rooms));
    }

    /**
     * 修改房间
     */
    @PreAuthorize("@ss.hasPermi('manage:rooms:edit')")
    @Log(title = "房间", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Rooms rooms)
    {
        return toAjax(roomsService.updateRooms(rooms));
    }

    /**
     * 删除房间
     */
    @PreAuthorize("@ss.hasPermi('manage:rooms:remove')")
    @Log(title = "房间", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(roomsService.deleteRoomsByIds(ids));
    }
}
