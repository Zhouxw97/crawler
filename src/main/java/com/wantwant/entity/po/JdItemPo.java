package com.wantwant.entity.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

/**
 * 京东商品品类信息
 *
 * @author zhouxiaowen
 * @date 2021-04-20 09:23
 * @version 1.0
 */
@TableName("jd_item")
public class JdItemPo extends Model<JdItemPo> {

	private static final long serialVersionUID = 1L;

	/**
	* 主键id
	*/
	@TableId(value = "id")
	private Long id;

	/**
	* 商品集合id
	*/
	@TableField("spu")
	private String spu;

	/**
	* 商品最小品类单元id
	*/
	@TableField("sku")
	private String sku;

	/**
	* 商品标题
	*/
	@TableField("title")
	private String title;

	/**
	* 商品当前价格
	*/
	@TableField("current_price")
	private String currentPrice;

	/**
	 * 商品指导价格
	 */
	@TableField("guide_price")
	private String guidePrice;

	/**
	* 店铺
	*/
	@TableField("store")
	private String store;

	/**
	* 促销活动
	*/
	@TableField("promotions")
	private String promotions;

	/**
	 * 优惠劵
	 */
	@TableField("coupon")
	private String coupon;

	/**
	* 商品图片
	*/
	@TableField("pic")
	private String pic;

	/**
	* 商品详情地址
	*/
	@TableField("url")
	private String url;

	/**
	* 创建时间
	*/
	@TableField("created")
	private LocalDateTime created;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpu() {
		return spu;
	}

	public void setSpu(String spu) {
		this.spu = spu;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getGuidePrice() {
		return guidePrice;
	}

	public void setGuidePrice(String guidePrice) {
		this.guidePrice = guidePrice;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getPromotions() {
		return promotions;
	}

	public void setPromotions(String promotions) {
		this.promotions = promotions;
	}

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "JdItemPo{" +
				"id=" + id +
				", spu='" + spu + '\'' +
				", sku='" + sku + '\'' +
				", title='" + title + '\'' +
				", currentPrice='" + currentPrice + '\'' +
				", guidePrice='" + guidePrice + '\'' +
				", store='" + store + '\'' +
				", promotions='" + promotions + '\'' +
				", coupon='" + coupon + '\'' +
				", pic='" + pic + '\'' +
				", url='" + url + '\'' +
				", created=" + created +
				'}';
	}
}
