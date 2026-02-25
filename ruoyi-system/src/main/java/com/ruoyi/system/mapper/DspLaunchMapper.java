package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DspLaunch;

/**
 * 广告生成Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-24
 */
public interface DspLaunchMapper 
{
    /**
     * 查询广告生成
     * 
     * @param id 广告生成主键
     * @return 广告生成
     */
    public DspLaunch selectDspLaunchById(Long id);

    /**
     * 查询广告生成列表
     * 
     * @param dspLaunch 广告生成
     * @return 广告生成集合
     */
    public List<DspLaunch> selectDspLaunchList(DspLaunch dspLaunch);

    /**
     * 新增广告生成
     * 
     * @param dspLaunch 广告生成
     * @return 结果
     */
    public int insertDspLaunch(DspLaunch dspLaunch);

    /**
     * 修改广告生成
     * 
     * @param dspLaunch 广告生成
     * @return 结果
     */
    public int updateDspLaunch(DspLaunch dspLaunch);

    /**
     * 删除广告生成
     * 
     * @param id 广告生成主键
     * @return 结果
     */
    public int deleteDspLaunchById(Long id);

    /**
     * 批量删除广告生成
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDspLaunchByIds(Long[] ids);
}
