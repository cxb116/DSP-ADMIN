package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DataSspSlotMapper;
import com.ruoyi.system.domain.DataSspSlot;
import com.ruoyi.system.service.IDataSspSlotService;

/**
 * 媒体数据报表Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-01
 */
@Service
public class DataSspSlotServiceImpl implements IDataSspSlotService 
{
    @Autowired
    private DataSspSlotMapper dataSspSlotMapper;

    /**
     * 查询媒体数据报表
     * 
     * @param id 媒体数据报表主键
     * @return 媒体数据报表
     */
    @Override
    public DataSspSlot selectDataSspSlotById(Long id)
    {
        return dataSspSlotMapper.selectDataSspSlotById(id);
    }

    /**
     * 查询媒体数据报表列表
     * 
     * @param dataSspSlot 媒体数据报表
     * @return 媒体数据报表
     */
    @Override
    public List<DataSspSlot> selectDataSspSlotList(DataSspSlot dataSspSlot)
    {
        return dataSspSlotMapper.selectDataSspSlotList(dataSspSlot);
    }

    /**
     * 新增媒体数据报表
     * 
     * @param dataSspSlot 媒体数据报表
     * @return 结果
     */
    @Override
    public int insertDataSspSlot(DataSspSlot dataSspSlot)
    {
        return dataSspSlotMapper.insertDataSspSlot(dataSspSlot);
    }

    /**
     * 修改媒体数据报表
     * 
     * @param dataSspSlot 媒体数据报表
     * @return 结果
     */
    @Override
    public int updateDataSspSlot(DataSspSlot dataSspSlot)
    {
        return dataSspSlotMapper.updateDataSspSlot(dataSspSlot);
    }

    /**
     * 批量删除媒体数据报表
     * 
     * @param ids 需要删除的媒体数据报表主键
     * @return 结果
     */
    @Override
    public int deleteDataSspSlotByIds(Long[] ids)
    {
        return dataSspSlotMapper.deleteDataSspSlotByIds(ids);
    }

    /**
     * 删除媒体数据报表信息
     * 
     * @param id 媒体数据报表主键
     * @return 结果
     */
    @Override
    public int deleteDataSspSlotById(Long id)
    {
        return dataSspSlotMapper.deleteDataSspSlotById(id);
    }
}
