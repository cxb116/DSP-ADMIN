package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SspSlotInfoMapper;
import com.ruoyi.system.domain.SspSlotInfo;
import com.ruoyi.system.service.ISspSlotInfoService;

/**
 * 媒体广告位Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-24
 */
@Service
public class SspSlotInfoServiceImpl implements ISspSlotInfoService 
{
    @Autowired
    private SspSlotInfoMapper sspSlotInfoMapper;

    /**
     * 查询媒体广告位
     * 
     * @param id 媒体广告位主键
     * @return 媒体广告位
     */
    @Override
    public SspSlotInfo selectSspSlotInfoById(Long id)
    {
        return sspSlotInfoMapper.selectSspSlotInfoById(id);
    }

    /**
     * 查询媒体广告位列表
     * 
     * @param sspSlotInfo 媒体广告位
     * @return 媒体广告位
     */
    @Override
    public List<SspSlotInfo> selectSspSlotInfoList(SspSlotInfo sspSlotInfo)
    {
        return sspSlotInfoMapper.selectSspSlotInfoList(sspSlotInfo);
    }

    /**
     * 新增媒体广告位
     * 
     * @param sspSlotInfo 媒体广告位
     * @return 结果
     */
    @Override
    public int insertSspSlotInfo(SspSlotInfo sspSlotInfo)
    {
        sspSlotInfo.setCreateTime(DateUtils.getNowDate());
        return sspSlotInfoMapper.insertSspSlotInfo(sspSlotInfo);
    }

    /**
     * 修改媒体广告位
     * 
     * @param sspSlotInfo 媒体广告位
     * @return 结果
     */
    @Override
    public int updateSspSlotInfo(SspSlotInfo sspSlotInfo)
    {
        sspSlotInfo.setUpdateTime(DateUtils.getNowDate());
        return sspSlotInfoMapper.updateSspSlotInfo(sspSlotInfo);
    }

    /**
     * 批量删除媒体广告位
     * 
     * @param ids 需要删除的媒体广告位主键
     * @return 结果
     */
    @Override
    public int deleteSspSlotInfoByIds(Long[] ids)
    {
        return sspSlotInfoMapper.deleteSspSlotInfoByIds(ids);
    }

    /**
     * 删除媒体广告位信息
     * 
     * @param id 媒体广告位主键
     * @return 结果
     */
    @Override
    public int deleteSspSlotInfoById(Long id)
    {
        return sspSlotInfoMapper.deleteSspSlotInfoById(id);
    }
}
