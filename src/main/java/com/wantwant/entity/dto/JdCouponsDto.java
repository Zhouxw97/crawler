package com.wantwant.entity.dto;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * jd优惠劵
 *
 * @author zhouxiaowen
 * @date 2021-04-23 11:50
 * @version 1.0
 */
public class JdCouponsDto implements Serializable {

    private static final long serialVersionUID = 2164151481803726878L;

    //名称
    private String name;

    private String hourcoupon;

    //时间段
    private String timeDesc;

    private String couponType;

    //满quota减discount
    private String quota;

    //满quota减discount
    private String discount;

    private String couponstyle;

    private String couponKind;

    private String roleId;

    private String batchId;

    private String userLabel;

    private String businessLabel;

    private String key;

    private String token;

    //折扣说明
    private JdDiscountdescDto discountdesc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHourcoupon() {
        return hourcoupon;
    }

    public void setHourcoupon(String hourcoupon) {
        this.hourcoupon = hourcoupon;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCouponstyle() {
        return couponstyle;
    }

    public void setCouponstyle(String couponstyle) {
        this.couponstyle = couponstyle;
    }

    public String getCouponKind() {
        return couponKind;
    }

    public void setCouponKind(String couponKind) {
        this.couponKind = couponKind;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
    }

    public String getBusinessLabel() {
        return businessLabel;
    }

    public void setBusinessLabel(String businessLabel) {
        this.businessLabel = businessLabel;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JdDiscountdescDto getDiscountdesc() {
        return discountdesc;
    }

    public void setDiscountdesc(JdDiscountdescDto discountdesc) {
        this.discountdesc = discountdesc;
    }

    @Override
    public String toString() {
        return discountdesc.toString() +" " + name + " 有效期:"+timeDesc;
    }

    public static void main(String[] args) {
        String jsonStr = "{\n" +
                "    \"coupons\": [\n" +
                "        {\n" +
                "            \"didget\": \"0\",\n" +
                "            \"name\": \"仅可购买食品饮料部分商品\",\n" +
                "            \"hourcoupon\": 1,\n" +
                "            \"timeDesc\": \"2021.04.23 - 2021.04.23\",\n" +
                "            \"couponType\": 1,\n" +
                "            \"quota\": 199,\n" +
                "            \"discount\": 30,\n" +
                "            \"couponstyle\": 3,\n" +
                "            \"couponKind\": 1,\n" +
                "            \"roleId\": 50043947,\n" +
                "            \"batchId\": 781627458,\n" +
                "            \"userLabel\": \"0\",\n" +
                "            \"businessLabel\": \"0\",\n" +
                "            \"key\": \"ad291fd6a1004801a168cf191deb9f95\",\n" +
                "            \"token\": \"0c2a96f0d35609504da8f917d6ecd263\",\n" +
                "            \"discountdesc\": {\n" +
                "                \"high\": \"100\",\n" +
                "                \"info\": [\n" +
                "                    {\n" +
                "                        \"quota\": \"199\",\n" +
                "                        \"discount\": \"0.85\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"didget\": \"0\",\n" +
                "            \"name\": \"仅可购买牛奶乳品部分商品\",\n" +
                "            \"hourcoupon\": 1,\n" +
                "            \"timeDesc\": \"2021.04.23 - 2021.04.23\",\n" +
                "            \"couponType\": 1,\n" +
                "            \"quota\": 199,\n" +
                "            \"discount\": 30,\n" +
                "            \"couponstyle\": 3,\n" +
                "            \"couponKind\": 1,\n" +
                "            \"roleId\": 50098444,\n" +
                "            \"batchId\": 782701102,\n" +
                "            \"userLabel\": \"0\",\n" +
                "            \"businessLabel\": \"0\",\n" +
                "            \"key\": \"e9b8dc7ae9a749b18510300a018e4632\",\n" +
                "            \"token\": \"e4e9162d9d15d48abd3622efb5f39572\",\n" +
                "            \"discountdesc\": {\n" +
                "                \"high\": \"100\",\n" +
                "                \"info\": [\n" +
                "                    {\n" +
                "                        \"quota\": \"199\",\n" +
                "                        \"discount\": \"0.85\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"sku_info\": {\n" +
                "        \"sku\": \"5255424\",\n" +
                "        \"useJing\": \"1\",\n" +
                "        \"useDong\": \"1\",\n" +
                "        \"global\": \"0\",\n" +
                "        \"limitCouponDesc\": \"不可使用全品类券\",\n" +
                "        \"limitCouponType\": [\n" +
                "            \"limit_1\"\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        List<JdCouponsDto> coupons = JSONObject.parseArray(jsonObject.get("coupons").toString(), JdCouponsDto.class);
        System.out.println(coupons);
    }
}
