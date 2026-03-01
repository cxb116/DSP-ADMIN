package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DataDspSlot;

/**
 * 预算报表Service接口
 * 
 * @author ruoyi
 * @date 2026-03-01
 */
public interface IDataDspSlotService 
{
    /**
     * 查询预算报表
     * 
     * @param id 预算报表主键
     * @return 预算报表
     */
    public DataDspSlot selectDataDspSlotById(Long id);

    /**
     * 查询预算报表列表
     * 
     * @param dataDspSlot 预算报表
     * @return 预算报表集合
     */
    public List<DataDspSlot> selectDataDspSlotList(DataDspSlot dataDspSlot);

    /**
     * 新增预算报表
     * 
     * @param dataDspSlot 预算报表
     * @return 结果
     */
    public int insertDataDspSlot(DataDspSlot dataDspSlot);

    /**
     * 修改预算报表
     * 
     * @param dataDspSlot 预算报表
     * @return 结果
     */
    public int updateDataDspSlot(DataDspSlot dataDspSlot);

    /**
     * 批量删除预算报表
     * 
     * @param ids 需要删除的预算报表主键集合
     * @return 结果
     */
    public int deleteDataDspSlotByIds(Long[] ids);

    /**
     * 删除预算报表信息
     * 
     * @param id 预算报表主键
     * @return 结果
     */
    public int deleteDataDspSlotById(Long id);
}
