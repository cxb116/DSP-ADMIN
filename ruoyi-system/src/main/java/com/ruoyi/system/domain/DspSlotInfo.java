package com.ruoyi.system.domain;

import com.alibaba.fastjson2.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 预算广告位对象 dsp_slot_info
 * 
 * @author ruoyi
 * @date 2026-02-24
 */
public class DspSlotInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 广告位名称 */
    @Excel(name = "广告位名称")
    private String name;

    /** 操作系统类型，1=Android，2=iOS */
    @Excel(name = "操作系统类型，1=Android，2=iOS")
    private Long osType;

    /** 广告类型 */
    @Excel(name = "广告类型")
    private Long adScene;

    /** 预算方广告位 */
    @Excel(name = "预算方广告位")
    private String dspSlotCode;

    /** 预算方APPKEY */
    @Excel(name = "预算方APPKEY")
    private String dspAppKey;

    /** 预算方APPSECRET */
    @Excel(name = "预算方APPSECRET")
    private String dspAppSecret;

    /** 预算方AppId */
    @Excel(name = "预算方AppId")
    private String dspAppId;

    /** 预算方应用包名 */
    @Excel(name = "预算方应用包名")
    private String dspAppPkg;

    /** 应用版本号，支持多个，空格分割 */
    @Excel(name = "应用版本号，支持多个，空格分割")
    private String dspAppVer;

    /** 应用商店版本号，支持多个，空格分割 */
    @Excel(name = "应用商店版本号，支持多个，空格分割")
    private String dspAppStoreVer;

    /** 价格加密key */
    @Excel(name = "价格加密key")
    private String priceEncryptKey;

    /** 应用商店地址，支持多个，空格分割 */
    @Excel(name = "应用商店地址，支持多个，空格分割")
    private String dspAppStoreLink;

    /** 上游预算结算方式，1=分成，2=RTB */
    @Excel(name = "上游预算结算方式，1=分成，2=RTB")
    private String dspPayType;

    /** 成交系数，0到100，单位%，给上游预算出价打折扣  （rtb 时有这个） */
    @Excel(name = "成交系数，0到100，单位%，给上游预算出价打折扣  ", readConverterExp = "r=tb,时=有这个")
    private Long dspDealRatio;

    /** 公司ID */
    @Excel(name = "公司ID")
    private Long companyId;

    /** 产品ID */
    @Excel(name = "产品ID")
    private Long productId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    @JSONField(name = "id")
    public Long getId()
    {
        return id;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setOsType(Long osType) 
    {
        this.osType = osType;
    }

    public Long getOsType()
    {
        return osType;
    }

    public void setAdScene(Long adScene)
    {
        this.adScene = adScene;
    }

    public Long getAdScene()
    {
        return adScene;
    }

    public void setDspSlotCode(String dspSlotCode)
    {
        this.dspSlotCode = dspSlotCode;
    }

    public void setDspAppKey(String dspAppKey)
    {
        this.dspAppKey = dspAppKey;
    }

    public void setDspAppSecret(String dspAppSecret)
    {
        this.dspAppSecret = dspAppSecret;
    }

    public void setDspAppId(String dspAppId)
    {
        this.dspAppId = dspAppId;
    }

    public void setDspAppPkg(String dspAppPkg)
    {
        this.dspAppPkg = dspAppPkg;
    }

    public void setDspAppVer(String dspAppVer)
    {
        this.dspAppVer = dspAppVer;
    }

    public void setDspAppStoreVer(String dspAppStoreVer)
    {
        this.dspAppStoreVer = dspAppStoreVer;
    }

    public void setPriceEncryptKey(String priceEncryptKey)
    {
        this.priceEncryptKey = priceEncryptKey;
    }

    public void setDspAppStoreLink(String dspAppStoreLink)
    {
        this.dspAppStoreLink = dspAppStoreLink;
    }

    public void setDspPayType(String dspPayType)
    {
        this.dspPayType = dspPayType;
    }

    public void setDspDealRatio(Long dspDealRatio)
    {
        this.dspDealRatio = dspDealRatio;
    }

    public void setCompanyId(Long companyId) 
    {
        this.companyId = companyId;
    }

    @JSONField(name = "dsp_company_id")
    public Long getCompanyId()
    {
        return companyId;
    }

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    @JSONField(name = "dsp_product_id")
    public Long getProductId()
    {
        return productId;
    }

    @JSONField(name = "dsp_slot_code")
    public String getDspSlotCode()
    {
        return dspSlotCode;
    }

    @JSONField(name = "dsp_app_key")
    public String getDspAppKey()
    {
        return dspAppKey;
    }

    @JSONField(name = "dsp_app_secret")
    public String getDspAppSecret()
    {
        return dspAppSecret;
    }

    @JSONField(name = "dsp_app_id")
    public String getDspAppId()
    {
        return dspAppId;
    }

    @JSONField(name = "dsp_app_pkg")
    public String getDspAppPkg()
    {
        return dspAppPkg;
    }

    @JSONField(name = "dsp_app_ver")
    public String getDspAppVer()
    {
        return dspAppVer;
    }

    @JSONField(name = "dsp_app_store_ver")
    public String getDspAppStoreVer()
    {
        return dspAppStoreVer;
    }

    @JSONField(name = "price_encrypt_key")
    public String getPriceEncryptKey()
    {
        return priceEncryptKey;
    }

    @JSONField(name = "dsp_app_store_link")
    public String getDspAppStoreLink()
    {
        return dspAppStoreLink;
    }

    @JSONField(name = "dsp_pay_type")
    public String getDspPayType()
    {
        return dspPayType;
    }

    @JSONField(name = "dsp_deal_ratio")
    public Long getDspDealRatio()
    {
        return dspDealRatio;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("osType", getOsType())
            .append("dspSlotCode", getDspSlotCode())
            .append("dspAppKey", getDspAppKey())
            .append("dspAppSecret", getDspAppSecret())
            .append("dspAppId", getDspAppId())
            .append("dspAppPkg", getDspAppPkg())
            .append("dspAppVer", getDspAppVer())
            .append("dspAppStoreVer", getDspAppStoreVer())
            .append("priceEncryptKey", getPriceEncryptKey())
            .append("dspAppStoreLink", getDspAppStoreLink())
            .append("dspPayType", getDspPayType())
            .append("dspDealRatio", getDspDealRatio())
            .append("companyId", getCompanyId())
            .append("productId", getProductId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
