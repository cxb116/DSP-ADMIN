package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 媒体广告位对象 ssp_slot_info
 * 
 * @author ruoyi
 * @date 2026-02-24
 */
public class SspSlotInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 媒体id */
    @Excel(name = "媒体id")
    private Long mediaId;

    /** 应用id */
    @Excel(name = "应用id")
    private Long appId;

    /** 广告位名称 */
    @Excel(name = "广告位名称")
    private String name;

    /** 内部广告位名称 */
    @Excel(name = "内部广告位名称")
    private String nameAlise;

    /** 广告类型 */
    @Excel(name = "广告类型")
    private Long adScene;

    /** 下游媒体结算方式，1=分成，2=RTB */
    @Excel(name = "下游媒体结算方式，1=分成，2=RTB")
    private Long sspPayType;

    /** 下游媒体分成系数 （0到100，单位%  只有分成有的系数） */
    @Excel(name = "下游媒体分成系数 ", readConverterExp = "0=到100，单位%,只=有分成有的系数")
    private Long sspDealRatio;

    /** 广告宽度 */
    @Excel(name = "广告宽度")
    private Long width;

    /** 广告高度 */
    @Excel(name = "广告高度")
    private Long height;

    /** 广告位图片 */
    @Excel(name = "广告位图片")
    private String adImage;

    /** 交互类型是否支持，(1：打开网页，2：deeplink，
3：直接下载应用；4: 广点通; 5 小程序跳转 6,应用商店下载，7 快应用 位掩码表示) */
    @Excel(name = "交互类型是否支持，(1：打开网页，2：deeplink， 3：直接下载应用；4: 广点通; 5 小程序跳转 6,应用商店下载，7 快应用 位掩码表示)")
    private Long interactionType;

    /** 状态：1正常 0禁用 2审核中 3拒绝 */
    @Excel(name = "状态：1正常 0禁用 2审核中 3拒绝")
    private Long enable;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setMediaId(Long mediaId) 
    {
        this.mediaId = mediaId;
    }

    public Long getMediaId() 
    {
        return mediaId;
    }

    public void setAppId(Long appId) 
    {
        this.appId = appId;
    }

    public Long getAppId() 
    {
        return appId;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setNameAlise(String nameAlise) 
    {
        this.nameAlise = nameAlise;
    }

    public String getNameAlise()
    {
        return nameAlise;
    }

    public void setAdScene(Long adScene)
    {
        this.adScene = adScene;
    }

    public Long getAdScene()
    {
        return adScene;
    }

    public void setSspPayType(Long sspPayType)
    {
        this.sspPayType = sspPayType;
    }

    public Long getSspPayType() 
    {
        return sspPayType;
    }

    public void setSspDealRatio(Long sspDealRatio) 
    {
        this.sspDealRatio = sspDealRatio;
    }

    public Long getSspDealRatio() 
    {
        return sspDealRatio;
    }

    public void setWidth(Long width) 
    {
        this.width = width;
    }

    public Long getWidth() 
    {
        return width;
    }

    public void setHeight(Long height) 
    {
        this.height = height;
    }

    public Long getHeight() 
    {
        return height;
    }

    public void setAdImage(String adImage) 
    {
        this.adImage = adImage;
    }

    public String getAdImage() 
    {
        return adImage;
    }

    public void setInteractionType(Long interactionType) 
    {
        this.interactionType = interactionType;
    }

    public Long getInteractionType() 
    {
        return interactionType;
    }

    public void setEnable(Long enable) 
    {
        this.enable = enable;
    }

    public Long getEnable() 
    {
        return enable;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mediaId", getMediaId())
            .append("appId", getAppId())
            .append("name", getName())
            .append("nameAlise", getNameAlise())
            .append("adScene", getAdScene())
            .append("sspPayType", getSspPayType())
            .append("sspDealRatio", getSspDealRatio())
            .append("width", getWidth())
            .append("height", getHeight())
            .append("adImage", getAdImage())
            .append("interactionType", getInteractionType())
            .append("enable", getEnable())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
