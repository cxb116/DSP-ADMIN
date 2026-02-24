package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DspSlotInfoMapper;
import com.ruoyi.system.domain.DspSlotInfo;
import com.ruoyi.system.service.IDspSlotInfoService;

/**
 * 预算广告位Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-24
 */
@Service
public class DspSlotInfoServiceImpl implements IDspSlotInfoService 
{
    @Autowired
    private DspSlotInfoMapper dspSlotInfoMapper;

    /**
     * 查询预算广告位
     * 
     * @param id 预算广告位主键
     * @return 预算广告位
     */
    @Override
    public DspSlotInfo selectDspSlotInfoById(Long id)
    {
        return dspSlotInfoMapper.selectDspSlotInfoById(id);
    }

    /**
     * 查询预算广告位列表
     * 
     * @param dspSlotInfo 预算广告位
     * @return 预算广告位
     */
    @Override
    public List<DspSlotInfo> selectDspSlotInfoList(DspSlotInfo dspSlotInfo)
    {
        return dspSlotInfoMapper.selectDspSlotInfoList(dspSlotInfo);
    }

    /**
     * 新增预算广告位
     * 
     * @param dspSlotInfo 预算广告位
     * @return 结果
     */
    @Override
    public int insertDspSlotInfo(DspSlotInfo dspSlotInfo)
    {
        dspSlotInfo.setCreateTime(DateUtils.getNowDate());
        return dspSlotInfoMapper.insertDspSlotInfo(dspSlotInfo);
    }

    /**
     * 修改预算广告位
     * 
     * @param dspSlotInfo 预算广告位
     * @return 结果
     */
    @Override
    public int updateDspSlotInfo(DspSlotInfo dspSlotInfo)
    {
        dspSlotInfo.setUpdateTime(DateUtils.getNowDate());
        return dspSlotInfoMapper.updateDspSlotInfo(dspSlotInfo);
    }

    /**
     * 批量删除预算广告位
     * 
     * @param ids 需要删除的预算广告位主键
     * @return 结果
     */
    @Override
    public int deleteDspSlotInfoByIds(Long[] ids)
    {
        return dspSlotInfoMapper.deleteDspSlotInfoByIds(ids);
    }

    /**
     * 删除预算广告位信息
     * 
     * @param id 预算广告位主键
     * @return 结果
     */
    @Override
    public int deleteDspSlotInfoById(Long id)
    {
        return dspSlotInfoMapper.deleteDspSlotInfoById(id);
    }
}
