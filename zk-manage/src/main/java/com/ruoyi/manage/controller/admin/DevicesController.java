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
import com.ruoyi.manage.domain.po.Devices;
import com.ruoyi.manage.service.IDevicesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备信息Controller
 * 
 * @author mzy
 * @date 2025-04-08
 */
@RestController
@RequestMapping("/manage/devices")
public class DevicesController extends BaseController
{
    @Autowired
    private IDevicesService devicesService;

    /**
     * 查询设备信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:devices:list')")
    @GetMapping("/list")
    public TableDataInfo list(Devices devices)
    {
        startPage();
        List<Devices> list = devicesService.selectDevicesList(devices);
        return getDataTable(list);
    }

    /**
     * 导出设备信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:devices:export')")
    @Log(title = "设备信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Devices devices)
    {
        List<Devices> list = devicesService.selectDevicesList(devices);
        ExcelUtil<Devices> util = new ExcelUtil<Devices>(Devices.class);
        util.exportExcel(response, list, "设备信息数据");
    }

    /**
     * 获取设备信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:devices:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(devicesService.selectDevicesById(id));
    }

    /**
     * 新增设备信息
     */
    @PreAuthorize("@ss.hasPermi('manage:devices:add')")
    @Log(title = "设备信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Devices devices)
    {
        return toAjax(devicesService.insertDevices(devices));
    }

    /**
     * 修改设备信息
     */
    @PreAuthorize("@ss.hasPermi('manage:devices:edit')")
    @Log(title = "设备信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Devices devices)
    {
        return toAjax(devicesService.updateDevices(devices));
    }

    /**
     * 删除设备信息
     */
    @PreAuthorize("@ss.hasPermi('manage:devices:remove')")
    @Log(title = "设备信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(devicesService.deleteDevicesByIds(ids));
    }
}
