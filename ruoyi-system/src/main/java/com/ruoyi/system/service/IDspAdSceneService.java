package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DspAdScene;

/**
 * 广告类型管理Service接口
 * 
 * @author ruoyi
 * @date 2026-02-24
 */
public interface IDspAdSceneService 
{
    /**
     * 查询广告类型管理
     * 
     * @param id 广告类型管理主键
     * @return 广告类型管理
     */
    public DspAdScene selectDspAdSceneById(Long id);

    /**
     * 查询广告类型管理列表
     * 
     * @param dspAdScene 广告类型管理
     * @return 广告类型管理集合
     */
    public List<DspAdScene> selectDspAdSceneList(DspAdScene dspAdScene);

    /**
     * 新增广告类型管理
     * 
     * @param dspAdScene 广告类型管理
     * @return 结果
     */
    public int insertDspAdScene(DspAdScene dspAdScene);

    /**
     * 修改广告类型管理
     * 
     * @param dspAdScene 广告类型管理
     * @return 结果
     */
    public int updateDspAdScene(DspAdScene dspAdScene);

    /**
     * 批量删除广告类型管理
     * 
     * @param ids 需要删除的广告类型管理主键集合
     * @return 结果
     */
    public int deleteDspAdSceneByIds(Long[] ids);

    /**
     * 删除广告类型管理信息
     * 
     * @param id 广告类型管理主键
     * @return 结果
     */
    public int deleteDspAdSceneById(Long id);
}
