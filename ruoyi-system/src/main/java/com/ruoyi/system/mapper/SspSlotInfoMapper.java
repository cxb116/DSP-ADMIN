package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SspSlotInfo;

/**
 * 媒体广告位Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-24
 */
public interface SspSlotInfoMapper 
{
    /**
     * 查询媒体广告位
     * 
     * @param id 媒体广告位主键
     * @return 媒体广告位
     */
    public SspSlotInfo selectSspSlotInfoById(Long id);

    /**
     * 查询媒体广告位列表
     * 
     * @param sspSlotInfo 媒体广告位
     * @return 媒体广告位集合
     */
    public List<SspSlotInfo> selectSspSlotInfoList(SspSlotInfo sspSlotInfo);

    /**
     * 新增媒体广告位
     * 
     * @param sspSlotInfo 媒体广告位
     * @return 结果
     */
    public int insertSspSlotInfo(SspSlotInfo sspSlotInfo);

    /**
     * 修改媒体广告位
     * 
     * @param sspSlotInfo 媒体广告位
     * @return 结果
     */
    public int updateSspSlotInfo(SspSlotInfo sspSlotInfo);

    /**
     * 删除媒体广告位
     * 
     * @param id 媒体广告位主键
     * @return 结果
     */
    public int deleteSspSlotInfoById(Long id);

    /**
     * 批量删除媒体广告位
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSspSlotInfoByIds(Long[] ids);
}
