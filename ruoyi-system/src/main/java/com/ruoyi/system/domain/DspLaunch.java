package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 投放配置对象 dsp_launch
 *
 * @author ruoyi
 * @date 2026-02-25
 */
public class DspLaunch extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 媒体广告位ID */
    @Excel(name = "媒体广告位ID")
    private Long mediaAdId;

    /** 预算方广告位ID */
    @Excel(name = "预算方广告位ID")
    private Long dspSlotInfoId;

    /** 流量权重 */
    @Excel(name = "流量权重")
    private Integer trafficWeight;

    /** 投放策略：1对接第三方，2自主投放 */
    @Excel(name = "投放策略", readConverterExp = "1=对接第三方,2=自主投放")
    private Integer launchStrategy;

    /** 结算方式：1分成，2RTB */
    @Excel(name = "结算方式", readConverterExp = "1=分成,2=RTB")
    private Integer dspPayType;

    /** 分成比例(%) */
    @Excel(name = "分成比例")
    private Long dspDealRatio;

    /** 底价(分) */
    @Excel(name = "底价")
    private Long floorPrice;

    /** IP限流次数 */
    @Excel(name = "IP限流次数")
    private Integer ipLimit;

    /** 捕获日志时长(秒) */
    @Excel(name = "捕获日志时长")
    private Integer logCaptureAt;

    /** 上报黑名单 */
    @Excel(name = "上报黑名单")
    private String trackSchwarz;

    /** 请求次数限制 */
    @Excel(name = "请求次数限制")
    private Long req;

    /** 展现次数限制 */
    @Excel(name = "展现次数限制")
    private Long ims;

    /** 点击次数限制 */
    @Excel(name = "点击次数限制")
    private Long clk;

    /** 投放时段：1全时段，2自定义 */
    @Excel(name = "投放时段", readConverterExp = "1=全时段,2=自定义")
    private Integer launchTime;

    /** 地域定向：1不限制，2定向，3排除 */
    @Excel(name = "地域定向", readConverterExp = "1=不限制,2=定向,3=排除")
    private Integer regionDirection;

    /** 品牌定向：1不限制，2定向，3排除 */
    @Excel(name = "品牌定向", readConverterExp = "1=不限制,2=定向,3=排除")
    private Integer brandDirection;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setMediaAdId(Long mediaAdId)
    {
        this.mediaAdId = mediaAdId;
    }

    public Long getMediaAdId()
    {
        return mediaAdId;
    }

    public void setDspSlotInfoId(Long dspSlotInfoId)
    {
        this.dspSlotInfoId = dspSlotInfoId;
    }

    public Long getDspSlotInfoId()
    {
        return dspSlotInfoId;
    }

    public void setTrafficWeight(Integer trafficWeight)
    {
        this.trafficWeight = trafficWeight;
    }

    public Integer getTrafficWeight()
    {
        return trafficWeight;
    }

    public void setLaunchStrategy(Integer launchStrategy)
    {
        this.launchStrategy = launchStrategy;
    }

    public Integer getLaunchStrategy()
    {
        return launchStrategy;
    }

    public void setDspPayType(Integer dspPayType)
    {
        this.dspPayType = dspPayType;
    }

    public Integer getDspPayType()
    {
        return dspPayType;
    }

    public void setDspDealRatio(Long dspDealRatio)
    {
        this.dspDealRatio = dspDealRatio;
    }

    public Long getDspDealRatio()
    {
        return dspDealRatio;
    }

    public void setFloorPrice(Long floorPrice)
    {
        this.floorPrice = floorPrice;
    }

    public Long getFloorPrice()
    {
        return floorPrice;
    }

    public void setIpLimit(Integer ipLimit)
    {
        this.ipLimit = ipLimit;
    }

    public Integer getIpLimit()
    {
        return ipLimit;
    }

    public void setLogCaptureAt(Integer logCaptureAt)
    {
        this.logCaptureAt = logCaptureAt;
    }

    public Integer getLogCaptureAt()
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

    public void setLaunchTime(Integer launchTime)
    {
        this.launchTime = launchTime;
    }

    public Integer getLaunchTime()
    {
        return launchTime;
    }

    public void setRegionDirection(Integer regionDirection)
    {
        this.regionDirection = regionDirection;
    }

    public Integer getRegionDirection()
    {
        return regionDirection;
    }

    public void setBrandDirection(Integer brandDirection)
    {
        this.brandDirection = brandDirection;
    }

    public Integer getBrandDirection()
    {
        return brandDirection;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mediaAdId", getMediaAdId())
            .append("dspSlotInfoId", getDspSlotInfoId())
            .append("trafficWeight", getTrafficWeight())
            .append("launchStrategy", getLaunchStrategy())
            .append("dspPayType", getDspPayType())
            .append("dspDealRatio", getDspDealRatio())
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
