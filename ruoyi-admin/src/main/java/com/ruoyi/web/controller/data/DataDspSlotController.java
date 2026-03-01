package com.ruoyi.system.controller;

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
import com.ruoyi.system.domain.DataDspSlot;
import com.ruoyi.system.service.IDataDspSlotService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 预算报表Controller
 * 
 * @author ruoyi
 * @date 2026-03-01
 */
@RestController
@RequestMapping("/system/dataDspSlot")
public class DataDspSlotController extends BaseController
{
    @Autowired
    private IDataDspSlotService dataDspSlotService;

    /**
     * 查询预算报表列表
     */
    @PreAuthorize("@ss.hasPermi('system:dataDspSlot:list')")
    @GetMapping("/list")
    public TableDataInfo list(DataDspSlot dataDspSlot)
    {
        startPage();
        List<DataDspSlot> list = dataDspSlotService.selectDataDspSlotList(dataDspSlot);
        return getDataTable(list);
    }

    /**
     * 导出预算报表列表
     */
    @PreAuthorize("@ss.hasPermi('system:dataDspSlot:export')")
    @Log(title = "预算报表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DataDspSlot dataDspSlot)
    {
        List<DataDspSlot> list = dataDspSlotService.selectDataDspSlotList(dataDspSlot);
        ExcelUtil<DataDspSlot> util = new ExcelUtil<DataDspSlot>(DataDspSlot.class);
        util.exportExcel(response, list, "预算报表数据");
    }

    /**
     * 获取预算报表详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:dataDspSlot:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(dataDspSlotService.selectDataDspSlotById(id));
    }

    /**
     * 新增预算报表
     */
    @PreAuthorize("@ss.hasPermi('system:dataDspSlot:add')")
    @Log(title = "预算报表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataDspSlot dataDspSlot)
    {
        return toAjax(dataDspSlotService.insertDataDspSlot(dataDspSlot));
    }

    /**
     * 修改预算报表
     */
    @PreAuthorize("@ss.hasPermi('system:dataDspSlot:edit')")
    @Log(title = "预算报表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataDspSlot dataDspSlot)
    {
        return toAjax(dataDspSlotService.updateDataDspSlot(dataDspSlot));
    }

    /**
     * 删除预算报表
     */
    @PreAuthorize("@ss.hasPermi('system:dataDspSlot:remove')")
    @Log(title = "预算报表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dataDspSlotService.deleteDataDspSlotByIds(ids));
    }
}
