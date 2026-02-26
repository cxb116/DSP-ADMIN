package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.etcd.EtcdTemplate;
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

    @Autowired(required = false)
    private EtcdTemplate etcdTemplate;

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
        int rows = dspLaunchMapper.insertDspLaunch(dspLaunch);

        // 数据库插入成功后同步到 etcd
        if (rows > 0 && etcdTemplate != null)
        {
            try
            {
                etcdTemplate.syncAdd("launch", dspLaunch.getId(), dspLaunch);
            }
            catch (Exception e)
            {
                // 仅记录日志，不影响返回结果
            }
        }

        return rows;
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
        // 1. 先查询该媒体广告位的所有旧配置，获取 ID 用于 etcd 删除
        List<DspLaunch> oldLaunchList = dspLaunchMapper.selectDspLaunchBySspSlotId(sspSlotId);

        // 2. 删除数据库中的旧配置
        dspLaunchMapper.deleteDspLaunchBySspSlotId(sspSlotId);

        // 3. 同步删除 etcd 中的旧数据
        if (etcdTemplate != null && oldLaunchList != null && !oldLaunchList.isEmpty())
        {
            for (DspLaunch oldLaunch : oldLaunchList)
            {
                try
                {
                    etcdTemplate.syncDelete("launch", oldLaunch.getId());
                }
                catch (Exception e)
                {
                    // 仅记录日志
                }
            }
        }

        int rows = 0;
        // 4. 批量插入新配置
        if (dspLaunchList != null && !dspLaunchList.isEmpty())
        {
            // 设置创建时间和更新时间
            java.util.Date nowDate = DateUtils.getNowDate();
            for (DspLaunch dspLaunch : dspLaunchList)
            {
                dspLaunch.setCreateTime(nowDate);
                dspLaunch.setUpdateTime(nowDate);
            }
            rows = dspLaunchMapper.batchInsertDspLaunch(dspLaunchList);

            // 5. 重新查询获取插入后的完整数据（包含自增 ID）
            List<DspLaunch> newLaunchList = dspLaunchMapper.selectDspLaunchBySspSlotId(sspSlotId);

            // 6. 批量同步到 etcd
            if (rows > 0 && etcdTemplate != null && newLaunchList != null && !newLaunchList.isEmpty())
            {
                for (DspLaunch newLaunch : newLaunchList)
                {
                    try
                    {
                        etcdTemplate.syncAdd("launch", newLaunch.getId(), newLaunch);
                    }
                    catch (Exception e)
                    {
                        // 仅记录日志
                    }
                }
            }
        }
        return rows;
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
        int rows = dspLaunchMapper.updateDspLaunch(dspLaunch);

        if (rows > 0 && etcdTemplate != null)
        {
            try
            {
                // 查询最新数据
                DspLaunch latest = dspLaunchMapper.selectDspLaunchById(dspLaunch.getId());
                etcdTemplate.syncUpdate("launch", latest.getId(), latest);
            }
            catch (Exception e)
            {
                // 仅记录日志
            }
        }

        return rows;
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
        int rows = dspLaunchMapper.deleteDspLaunchByIds(ids);

        if (rows > 0 && etcdTemplate != null)
        {
            for (Long id : ids)
            {
                try
                {
                    etcdTemplate.syncDelete("launch", id);
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
     * 删除投放配置信息
     *
     * @param id 投放配置主键
     * @return 结果
     */
    @Override
    public int deleteDspLaunchById(Long id)
    {
        int rows = dspLaunchMapper.deleteDspLaunchById(id);

        if (rows > 0 && etcdTemplate != null)
        {
            try
            {
                etcdTemplate.syncDelete("launch", id);
            }
            catch (Exception e)
            {
                // 仅记录日志
            }
        }

        return rows;
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
