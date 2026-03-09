package com.ruoyi.system.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
     * 生成当前月份的表名
     * 格式：data_dsp_slot_day_YYYYMM
     *
     * @return 表名
     */
    private String generateCurrentTableName()
    {
        String currentMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        return "data_dsp_slot_day_" + currentMonth;
    }

    /**
     * 查询预算报表
     *
     * @param dataDspSlot 包含 id 和动态表名 tableName
     * @return 预算报表
     */
    @Override
    public DataDspSlot selectDataDspSlotById(DataDspSlot dataDspSlot)
    {
        // 如果没有设置表名，使用当前月份的表名
        if (dataDspSlot.getTableName() == null || dataDspSlot.getTableName().trim().isEmpty())
        {
            dataDspSlot.setTableName(generateCurrentTableName());
        }
        return dataDspSlotMapper.selectDataDspSlotById(dataDspSlot);
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
        // 如果没有设置表名，使用当前月份的表名
        if (dataDspSlot.getTableName() == null || dataDspSlot.getTableName().trim().isEmpty())
        {
            dataDspSlot.setTableName(generateCurrentTableName());
        }
        return dataDspSlotMapper.selectDataDspSlotList(dataDspSlot);
    }
}
