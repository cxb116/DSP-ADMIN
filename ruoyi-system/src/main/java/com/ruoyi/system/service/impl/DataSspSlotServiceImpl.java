package com.ruoyi.system.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
     * 生成当前月份的表名
     * 格式：data_ssp_slot_hour_YYYYMM
     *
     * @return 表名
     */
    private String generateCurrentTableName()
    {
        String currentMonth = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        return "data_ssp_slot_hour_" + currentMonth;
    }

    /**
     * 查询媒体数据报表
     *
     * @param dataSspSlot 包含 id 和动态表名 tableName
     * @return 媒体数据报表
     */
    @Override
    public DataSspSlot selectDataSspSlotById(DataSspSlot dataSspSlot)
    {
        // 如果没有设置表名，使用当前月份的表名
        if (dataSspSlot.getTableName() == null || dataSspSlot.getTableName().trim().isEmpty())
        {
            dataSspSlot.setTableName(generateCurrentTableName());
        }
        return dataSspSlotMapper.selectDataSspSlotById(dataSspSlot);
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
        // 如果没有设置表名，使用当前月份的表名
        if (dataSspSlot.getTableName() == null || dataSspSlot.getTableName().trim().isEmpty())
        {
            dataSspSlot.setTableName(generateCurrentTableName());
        }
        return dataSspSlotMapper.selectDataSspSlotList(dataSspSlot);
    }
}
