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
import com.ruoyi.manage.domain.po.RoomTypes;
import com.ruoyi.manage.service.IRoomTypesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 房间类型Controller
 * 
 * @author mzy
 * @date 2025-04-08
 */
@RestController
@RequestMapping("/manage/roomTypes")
public class RoomTypesController extends BaseController
{
    @Autowired
    private IRoomTypesService roomTypesService;

    /**
     * 查询房间类型列表
     */
    @PreAuthorize("@ss.hasPermi('manage:roomTypes:list')")
    @GetMapping("/list")
    public TableDataInfo list(RoomTypes roomTypes)
    {
        startPage();
        List<RoomTypes> list = roomTypesService.selectRoomTypesList(roomTypes);
        return getDataTable(list);
    }
    
    /**
     * 查询启用的房间类型列表
     * @param roomTypes
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:roomTypes:listEnable')")
    @GetMapping("/listEnable")
    public TableDataInfo listEnable(RoomTypes roomTypes)
    {
        //startPage();
        List<RoomTypes> list = roomTypesService.selectEnabledRoomTypesList(roomTypes);
        return getDataTable(list);
    }

    /**
     * 导出房间类型列表
     */
    @PreAuthorize("@ss.hasPermi('manage:roomTypes:export')")
    @Log(title = "房间类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RoomTypes roomTypes)
    {
        List<RoomTypes> list = roomTypesService.selectRoomTypesList(roomTypes);
        ExcelUtil<RoomTypes> util = new ExcelUtil<RoomTypes>(RoomTypes.class);
        util.exportExcel(response, list, "房间类型数据");
    }

    /**
     * 获取房间类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:roomTypes:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(roomTypesService.selectRoomTypesById(id));
    }

    /**
     * 新增房间类型
     */
    @PreAuthorize("@ss.hasPermi('manage:roomTypes:add')")
    @Log(title = "房间类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RoomTypes roomTypes)
    {
        return toAjax(roomTypesService.insertRoomTypes(roomTypes));
    }

    /**
     * 修改房间类型
     */
    @PreAuthorize("@ss.hasPermi('manage:roomTypes:edit')")
    @Log(title = "房间类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RoomTypes roomTypes)
    {
        return toAjax(roomTypesService.updateRoomTypes(roomTypes));
    }

    /**
     * 删除房间类型
     */
    @PreAuthorize("@ss.hasPermi('manage:roomTypes:remove')")
    @Log(title = "房间类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(roomTypesService.deleteRoomTypesByIds(ids));
    }
}
