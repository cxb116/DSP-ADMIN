package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DataSspSlot;

/**
 * 媒体数据报表Service接口
 * 
 * @author ruoyi
 * @date 2026-03-01
 */
public interface IDataSspSlotService 
{
    /**
     * 查询媒体数据报表
     * 
     * @param id 媒体数据报表主键
     * @return 媒体数据报表
     */
    public DataSspSlot selectDataSspSlotById(Long id);

    /**
     * 查询媒体数据报表列表
     * 
     * @param dataSspSlot 媒体数据报表
     * @return 媒体数据报表集合
     */
    public List<DataSspSlot> selectDataSspSlotList(DataSspSlot dataSspSlot);

    /**
     * 新增媒体数据报表
     * 
     * @param dataSspSlot 媒体数据报表
     * @return 结果
     */
    public int insertDataSspSlot(DataSspSlot dataSspSlot);

    /**
     * 修改媒体数据报表
     * 
     * @param dataSspSlot 媒体数据报表
     * @return 结果
     */
    public int updateDataSspSlot(DataSspSlot dataSspSlot);

    /**
     * 批量删除媒体数据报表
     * 
     * @param ids 需要删除的媒体数据报表主键集合
     * @return 结果
     */
    public int deleteDataSspSlotByIds(Long[] ids);

    /**
     * 删除媒体数据报表信息
     * 
     * @param id 媒体数据报表主键
     * @return 结果
     */
    public int deleteDataSspSlotById(Long id);
}
