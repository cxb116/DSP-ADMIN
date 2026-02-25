package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 广告生成对象 dsp_launch
 * 
 * @author ruoyi
 * @date 2026-02-24
 */
public class DspLaunch extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 流量广告位Id */
    @Excel(name = "流量广告位Id")
    private Long sspSlotId;

    /** 预算广告位id */
    @Excel(name = "预算广告位id")
    private Long dspSlotId;

    /** 流量权重（最大值100） */
    @Excel(name = "流量权重", readConverterExp = "最=大值100")
    private Long trafficWeight;

    /** 投放策略: 1对接第三方，2：自主投放 */
    @Excel(name = "投放策略: 1对接第三方，2：自主投放")
    private Long launchStrategy;

    /** 底价 (给上游媒体底价,预算是rtb的时候就需要这个值) */
    @Excel(name = "底价 (给上游媒体底价,预算是rtb的时候就需要这个值)")
    private Long floorPrice;

    /** ip限流次数 */
    @Excel(name = "ip限流次数")
    private Long ipLimit;

    /** 捕获日志时长 */
    @Excel(name = "捕获日志时长")
    private Long logCaptureAt;

    /** 上报黑名单 */
    @Excel(name = "上报黑名单")
    private String trackSchwarz;

    /** 请求次数 */
    @Excel(name = "请求次数")
    private Long req;

    /** 展现次数 */
    @Excel(name = "展现次数")
    private Long ims;

    /** 点击次数 */
    @Excel(name = "点击次数")
    private Long clk;

    /** 投放时段 1全时段 2 自定义 */
    @Excel(name = "投放时段 1全时段 2 自定义")
    private Long launchTime;

    /** 地域定向 1 不限制，2 定向，3排除 */
    @Excel(name = "地域定向 1 不限制，2 定向，3排除")
    private Long regionDirection;

    /** 品牌定向 1 不限制，2 定向，3排除 */
    @Excel(name = "品牌定向 1 不限制，2 定向，3排除")
    private Long brandDirection;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setSspSlotId(Long sspSlotId) 
    {
        this.sspSlotId = sspSlotId;
    }

    public Long getSspSlotId() 
    {
        return sspSlotId;
    }

    public void setDspSlotId(Long dspSlotId) 
    {
        this.dspSlotId = dspSlotId;
    }

    public Long getDspSlotId() 
    {
        return dspSlotId;
    }

    public void setTrafficWeight(Long trafficWeight) 
    {
        this.trafficWeight = trafficWeight;
    }

    public Long getTrafficWeight() 
    {
        return trafficWeight;
    }

    public void setLaunchStrategy(Long launchStrategy) 
    {
        this.launchStrategy = launchStrategy;
    }

    public Long getLaunchStrategy() 
    {
        return launchStrategy;
    }

    public void setFloorPrice(Long floorPrice) 
    {
        this.floorPrice = floorPrice;
    }

    public Long getFloorPrice() 
    {
        return floorPrice;
    }

    public void setIpLimit(Long ipLimit) 
    {
        this.ipLimit = ipLimit;
    }

    public Long getIpLimit() 
    {
        return ipLimit;
    }

    public void setLogCaptureAt(Long logCaptureAt) 
    {
        this.logCaptureAt = logCaptureAt;
    }

    public Long getLogCaptureAt() 
    {
        return logCaptureAt;
    }

    public void setTrackSchwarz(String trackSchwarz) 
    {
        this.trackSchwarz = trackSchwarz;
    }

    public String getTrackSchwarz() 
    {
        return trackSchwarz;
    }

    public void setReq(Long req) 
    {
        this.req = req;
    }

    public Long getReq() 
    {
        return req;
    }

    public void setIms(Long ims) 
    {
        this.ims = ims;
    }

    public Long getIms() 
    {
        return ims;
    }

    public void setClk(Long clk) 
    {
        this.clk = clk;
    }

    public Long getClk() 
    {
        return clk;
    }

    public void setLaunchTime(Long launchTime) 
    {
        this.launchTime = launchTime;
    }

    public Long getLaunchTime() 
    {
        return launchTime;
    }

    public void setRegionDirection(Long regionDirection) 
    {
        this.regionDirection = regionDirection;
    }

    public Long getRegionDirection() 
    {
        return regionDirection;
    }

    public void setBrandDirection(Long brandDirection) 
    {
        this.brandDirection = brandDirection;
    }

    public Long getBrandDirection() 
    {
        return brandDirection;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sspSlotId", getSspSlotId())
            .append("dspSlotId", getDspSlotId())
            .append("trafficWeight", getTrafficWeight())
            .append("launchStrategy", getLaunchStrategy())
            .append("floorPrice", getFloorPrice())
            .append("ipLimit", getIpLimit())
            .append("logCaptureAt", getLogCaptureAt())
            .append("trackSchwarz", getTrackSchwarz())
            .append("req", getReq())
            .append("ims", getIms())
            .append("clk", getClk())
            .append("launchTime", getLaunchTime())
            .append("regionDirection", getRegionDirection())
            .append("brandDirection", getBrandDirection())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
