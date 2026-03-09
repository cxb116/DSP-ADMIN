package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("/export")
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
    public AjaxResult getInfo(@PathVariable("id") Long id, DataDspSlot dataDspSlot)
    {
        dataDspSlot.setId(id);
        return success(dataDspSlotService.selectDataDspSlotById(dataDspSlot));
    }
}
