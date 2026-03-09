package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DataDspSlot;

/**
 * 预算报表Mapper接口
 *
 * @author ruoyi
 * @date 2026-03-01
 */
public interface DataDspSlotMapper
{
    /**
     * 查询预算报表
     *
     * @param dataDspSlot 包含 id 和动态表名 tableName
     * @return 预算报表
     */
    public DataDspSlot selectDataDspSlotById(DataDspSlot dataDspSlot);

    /**
     * 查询预算报表列表
     *
     * @param dataDspSlot 预算报表
     * @return 预算报表集合
     */
    public List<DataDspSlot> selectDataDspSlotList(DataDspSlot dataDspSlot);
}
