package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DspAdSceneMapper;
import com.ruoyi.system.domain.DspAdScene;
import com.ruoyi.system.service.IDspAdSceneService;

/**
 * 广告管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-24
 */
@Service
public class DspAdSceneServiceImpl implements IDspAdSceneService 
{
    @Autowired
    private DspAdSceneMapper dspAdSceneMapper;

    /**
     * 查询广告管理
     * 
     * @param id 广告管理主键
     * @return 广告管理
     */
    @Override
    public DspAdScene selectDspAdSceneById(Long id)
    {
        return dspAdSceneMapper.selectDspAdSceneById(id);
    }

    /**
     * 查询广告管理列表
     * 
     * @param dspAdScene 广告管理
     * @return 广告管理
     */
    @Override
    public List<DspAdScene> selectDspAdSceneList(DspAdScene dspAdScene)
    {
        return dspAdSceneMapper.selectDspAdSceneList(dspAdScene);
    }

    /**
     * 新增广告管理
     * 
     * @param dspAdScene 广告管理
     * @return 结果
     */
    @Override
    public int insertDspAdScene(DspAdScene dspAdScene)
    {
        dspAdScene.setCreateTime(DateUtils.getNowDate());
        return dspAdSceneMapper.insertDspAdScene(dspAdScene);
    }

    /**
     * 修改广告管理
     * 
     * @param dspAdScene 广告管理
     * @return 结果
     */
    @Override
    public int updateDspAdScene(DspAdScene dspAdScene)
    {
        dspAdScene.setUpdateTime(DateUtils.getNowDate());
        return dspAdSceneMapper.updateDspAdScene(dspAdScene);
    }

    /**
     * 批量删除广告管理
     * 
     * @param ids 需要删除的广告管理主键
     * @return 结果
     */
    @Override
    public int deleteDspAdSceneByIds(Long[] ids)
    {
        return dspAdSceneMapper.deleteDspAdSceneByIds(ids);
    }

    /**
     * 删除广告管理信息
     * 
     * @param id 广告管理主键
     * @return 结果
     */
    @Override
    public int deleteDspAdSceneById(Long id)
    {
        return dspAdSceneMapper.deleteDspAdSceneById(id);
    }
}
