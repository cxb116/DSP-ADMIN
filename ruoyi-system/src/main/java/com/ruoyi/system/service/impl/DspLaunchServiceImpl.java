package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.mapper.DspLaunchMapper;
import com.ruoyi.system.domain.DspLaunch;
import com.ruoyi.system.domain.DspLaunchDTO;
import com.ruoyi.system.service.IDspLaunchService;

/**
 * 投放配置Service业务层处理
 *
 * @author ruoyi
 * @date 2026-02-25
 */
@Service
public class DspLaunchServiceImpl implements IDspLaunchService
{
    @Autowired
    private DspLaunchMapper dspLaunchMapper;

    /**
     * 查询投放配置
     *
     * @param id 投放配置主键
     * @return 投放配置
     */
    @Override
    public DspLaunch selectDspLaunchById(Long id)
    {
        return dspLaunchMapper.selectDspLaunchById(id);
    }

    /**
     * 查询投放配置列表
     *
     * @param dspLaunch 投放配置
     * @return 投放配置
     */
    @Override
    public List<DspLaunch> selectDspLaunchList(DspLaunch dspLaunch)
    {
        return dspLaunchMapper.selectDspLaunchList(dspLaunch);
    }

    /**
     * 根据媒体广告位ID查询投放配置列表
     *
     * @param sspSlotId 媒体广告位ID
     * @return 投放配置集合
     */
    @Override
    public List<DspLaunch> selectDspLaunchBySspSlotId(Long sspSlotId)
    {
        return dspLaunchMapper.selectDspLaunchBySspSlotId(sspSlotId);
    }

    /**
     * 根据媒体广告位ID查询投放配置DTO列表（包含预算方广告位信息）
     *
     * @param sspSlotId 媒体广告位ID
     * @return 投放配置DTO集合
     */
    @Override
    public List<DspLaunchDTO> selectDspLaunchDTOBySspSlotId(Long sspSlotId)
    {
        return dspLaunchMapper.selectDspLaunchDTOBySspSlotId(sspSlotId);
    }

    /**
     * 新增投放配置
     *
     * @param dspLaunch 投放配置
     * @return 结果
     */
    @Override
    public int insertDspLaunch(DspLaunch dspLaunch)
    {
        dspLaunch.setCreateTime(DateUtils.getNowDate());
        return dspLaunchMapper.insertDspLaunch(dspLaunch);
    }

    /**
     * 批量保存投放配置（先删除旧的，再插入新的）
     *
     * @param sspSlotId 媒体广告位ID
     * @param dspLaunchList 投放配置集合
     * @return 结果
     */
    @Override
    @Transactional
    public int batchSaveDspLaunch(Long sspSlotId, List<DspLaunch> dspLaunchList)
    {
        // 先删除该媒体广告位的所有旧配置
        dspLaunchMapper.deleteDspLaunchBySspSlotId(sspSlotId);

        // 批量插入新配置
        if (dspLaunchList != null && !dspLaunchList.isEmpty())
        {
            // 设置创建时间和更新时间
            java.util.Date nowDate = DateUtils.getNowDate();
            for (DspLaunch dspLaunch : dspLaunchList)
            {
                dspLaunch.setCreateTime(nowDate);
                dspLaunch.setUpdateTime(nowDate);
            }
            return dspLaunchMapper.batchInsertDspLaunch(dspLaunchList);
        }
        return 0;
    }

    /**
     * 修改投放配置
     *
     * @param dspLaunch 投放配置
     * @return 结果
     */
    @Override
    public int updateDspLaunch(DspLaunch dspLaunch)
    {
        dspLaunch.setUpdateTime(DateUtils.getNowDate());
        return dspLaunchMapper.updateDspLaunch(dspLaunch);
    }

    /**
     * 批量删除投放配置
     *
     * @param ids 需要删除的投放配置主键集合
     * @return 结果
     */
    @Override
    public int deleteDspLaunchByIds(Long[] ids)
    {
        return dspLaunchMapper.deleteDspLaunchByIds(ids);
    }

    /**
     * 删除投放配置信息
     *
     * @param id 投放配置主键
     * @return 结果
     */
    @Override
    public int deleteDspLaunchById(Long id)
    {
        return dspLaunchMapper.deleteDspLaunchById(id);
    }

    /**
     * 根据媒体广告位ID删除投放配置
     *
     * @param sspSlotId 媒体广告位ID
     * @return 结果
     */
    @Override
    public int deleteDspLaunchBySspSlotId(Long sspSlotId)
    {
        return dspLaunchMapper.deleteDspLaunchBySspSlotId(sspSlotId);
    }
}
