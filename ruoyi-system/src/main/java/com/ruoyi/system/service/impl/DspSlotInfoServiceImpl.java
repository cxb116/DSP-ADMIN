package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.etcd.EtcdTemplate;
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

    @Autowired(required = false)
    private EtcdTemplate etcdTemplate;

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

        // 设置默认值：如果结算方式为空，默认为分成模式（1）
        if (dspSlotInfo.getDspPayType() == null)
        {
            dspSlotInfo.setDspPayType(1L);
        }

        int rows = dspSlotInfoMapper.insertDspSlotInfo(dspSlotInfo);

        // 数据库插入成功后同步到 etcd
        if (rows > 0 && etcdTemplate != null)
        {
            try
            {
                etcdTemplate.syncAdd("dsp", dspSlotInfo.getId(), dspSlotInfo);
            }
            catch (Exception e)
            {
                // 仅记录日志，不影响返回结果
            }
        }

        return rows;
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
        int rows = dspSlotInfoMapper.updateDspSlotInfo(dspSlotInfo);

        if (rows > 0 && etcdTemplate != null)
        {
            try
            {
                // 查询最新数据
                DspSlotInfo latest = dspSlotInfoMapper.selectDspSlotInfoById(dspSlotInfo.getId());
                etcdTemplate.syncUpdate("dsp", latest.getId(), latest);
            }
            catch (Exception e)
            {
                // 仅记录日志
            }
        }

        return rows;
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
        int rows = dspSlotInfoMapper.deleteDspSlotInfoByIds(ids);

        if (rows > 0 && etcdTemplate != null)
        {
            for (Long id : ids)
            {
                try
                {
                    etcdTemplate.syncDelete("dsp", id);
                }
                catch (Exception e)
                {
                    // 仅记录日志
                }
            }
        }

        return rows;
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
        int rows = dspSlotInfoMapper.deleteDspSlotInfoById(id);

        if (rows > 0 && etcdTemplate != null)
        {
            try
            {
                etcdTemplate.syncDelete("dsp", id);
            }
            catch (Exception e)
            {
                // 仅记录日志
            }
        }

        return rows;
    }
}
