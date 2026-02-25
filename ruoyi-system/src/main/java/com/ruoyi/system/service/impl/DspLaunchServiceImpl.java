package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DspLaunchMapper;
import com.ruoyi.system.domain.DspLaunch;
import com.ruoyi.system.service.IDspLaunchService;

/**
 * 广告生成Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-24
 */
@Service
public class DspLaunchServiceImpl implements IDspLaunchService 
{
    @Autowired
    private DspLaunchMapper dspLaunchMapper;

    /**
     * 查询广告生成
     * 
     * @param id 广告生成主键
     * @return 广告生成
     */
    @Override
    public DspLaunch selectDspLaunchById(Long id)
    {
        return dspLaunchMapper.selectDspLaunchById(id);
    }

    /**
     * 查询广告生成列表
     * 
     * @param dspLaunch 广告生成
     * @return 广告生成
     */
    @Override
    public List<DspLaunch> selectDspLaunchList(DspLaunch dspLaunch)
    {
        return dspLaunchMapper.selectDspLaunchList(dspLaunch);
    }

    /**
     * 新增广告生成
     * 
     * @param dspLaunch 广告生成
     * @return 结果
     */
    @Override
    public int insertDspLaunch(DspLaunch dspLaunch)
    {
        dspLaunch.setCreateTime(DateUtils.getNowDate());
        return dspLaunchMapper.insertDspLaunch(dspLaunch);
    }

    /**
     * 修改广告生成
     * 
     * @param dspLaunch 广告生成
     * @return 结果
     */
    @Override
    public int updateDspLaunch(DspLaunch dspLaunch)
    {
        dspLaunch.setUpdateTime(DateUtils.getNowDate());
        return dspLaunchMapper.updateDspLaunch(dspLaunch);
    }

    /**
     * 批量删除广告生成
     * 
     * @param ids 需要删除的广告生成主键
     * @return 结果
     */
    @Override
    public int deleteDspLaunchByIds(Long[] ids)
    {
        return dspLaunchMapper.deleteDspLaunchByIds(ids);
    }

    /**
     * 删除广告生成信息
     * 
     * @param id 广告生成主键
     * @return 结果
     */
    @Override
    public int deleteDspLaunchById(Long id)
    {
        return dspLaunchMapper.deleteDspLaunchById(id);
    }
}
