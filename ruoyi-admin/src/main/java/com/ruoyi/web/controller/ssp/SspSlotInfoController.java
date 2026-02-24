package com.ruoyi.web.controller.ssp;

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
import com.ruoyi.system.domain.SspSlotInfo;
import com.ruoyi.system.service.ISspSlotInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 媒体广告位Controller
 * 
 * @author ruoyi
 * @date 2026-02-24
 */
@RestController
@RequestMapping("/flow/mediaAd")
public class SspSlotInfoController extends BaseController
{
    @Autowired
    private ISspSlotInfoService sspSlotInfoService;

    /**
     * 查询媒体广告位列表
     */
    @PreAuthorize("@ss.hasPermi('flow:mediaAd:list')")
    @GetMapping("/list")
    public TableDataInfo list(SspSlotInfo sspSlotInfo)
    {
        startPage();
        List<SspSlotInfo> list = sspSlotInfoService.selectSspSlotInfoList(sspSlotInfo);
        return getDataTable(list);
    }

    /**
     * 导出媒体广告位列表
     */
    @PreAuthorize("@ss.hasPermi('flow:mediaAd:export')")
    @Log(title = "媒体广告位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SspSlotInfo sspSlotInfo)
    {
        List<SspSlotInfo> list = sspSlotInfoService.selectSspSlotInfoList(sspSlotInfo);
        ExcelUtil<SspSlotInfo> util = new ExcelUtil<SspSlotInfo>(SspSlotInfo.class);
        util.exportExcel(response, list, "媒体广告位数据");
    }

    /**
     * 获取媒体广告位详细信息
     */
    @PreAuthorize("@ss.hasPermi('flow:mediaAd:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sspSlotInfoService.selectSspSlotInfoById(id));
    }

    /**
     * 新增媒体广告位
     */
    @PreAuthorize("@ss.hasPermi('flow:mediaAd:add')")
    @Log(title = "媒体广告位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SspSlotInfo sspSlotInfo)
    {
        return toAjax(sspSlotInfoService.insertSspSlotInfo(sspSlotInfo));
    }

    /**
     * 修改媒体广告位
     */
    @PreAuthorize("@ss.hasPermi('flow:mediaAd:edit')")
    @Log(title = "媒体广告位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SspSlotInfo sspSlotInfo)
    {
        return toAjax(sspSlotInfoService.updateSspSlotInfo(sspSlotInfo));
    }

    /**
     * 删除媒体广告位
     */
    @PreAuthorize("@ss.hasPermi('flow:mediaAd:remove')")
    @Log(title = "媒体广告位", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sspSlotInfoService.deleteSspSlotInfoByIds(ids));
    }
}
