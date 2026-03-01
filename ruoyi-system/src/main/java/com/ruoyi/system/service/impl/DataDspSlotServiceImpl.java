package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DataDspSlotMapper;
import com.ruoyi.system.domain.DataDspSlot;
import com.ruoyi.system.service.IDataDspSlotService;

/**
 * 预算报表Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-01
 */
@Service
public class DataDspSlotServiceImpl implements IDataDspSlotService 
{
    @Autowired
    private DataDspSlotMapper dataDspSlotMapper;

    /**
     * 查询预算报表
     * 
     * @param id 预算报表主键
     * @return 预算报表
     */
    @Override
    public DataDspSlot selectDataDspSlotById(Long id)
    {
        return dataDspSlotMapper.selectDataDspSlotById(id);
    }

    /**
     * 查询预算报表列表
     * 
     * @param dataDspSlot 预算报表
     * @return 预算报表
     */
    @Override
    public List<DataDspSlot> selectDataDspSlotList(DataDspSlot dataDspSlot)
    {
        return dataDspSlotMapper.selectDataDspSlotList(dataDspSlot);
    }

    /**
     * 新增预算报表
     * 
     * @param dataDspSlot 预算报表
     * @return 结果
     */
    @Override
    public int insertDataDspSlot(DataDspSlot dataDspSlot)
    {
        return dataDspSlotMapper.insertDataDspSlot(dataDspSlot);
    }

    /**
     * 修改预算报表
     * 
     * @param dataDspSlot 预算报表
     * @return 结果
     */
    @Override
    public int updateDataDspSlot(DataDspSlot dataDspSlot)
    {
        return dataDspSlotMapper.updateDataDspSlot(dataDspSlot);
    }

    /**
     * 批量删除预算报表
     * 
     * @param ids 需要删除的预算报表主键
     * @return 结果
     */
    @Override
    public int deleteDataDspSlotByIds(Long[] ids)
    {
        return dataDspSlotMapper.deleteDataDspSlotByIds(ids);
    }

    /**
     * 删除预算报表信息
     * 
     * @param id 预算报表主键
     * @return 结果
     */
    @Override
    public int deleteDataDspSlotById(Long id)
    {
        return dataDspSlotMapper.deleteDataDspSlotById(id);
    }
}
