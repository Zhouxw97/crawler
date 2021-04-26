package com.wantwant.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 京东商品品类信息查询条件
 *
 * @author zhouxiaowen
 * @date 2021-04-26 13:59
 * @version 1.0
 */
public class JdItemQueryDto implements Serializable {

	private static final long serialVersionUID = -5622637519842006868L;
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id", name = "id", example = "1")
	private Long id;

	/**
	 * 商品集合id
	 */
	@ApiModelProperty(value = "商品集合id", name = "spu", example = "1")
	private Long spu;

	/**
	 * 商品最小品类单元id
	 */
	@ApiModelProperty(value = "商品最小品类单元id", name = "sku", example = "1")
	private Long sku;

	/**
	 * 商品标题
	 */
	@ApiModelProperty(value = "商品标题", name = "title", example = "商品标题")
	private String title;

	/**
	 * 商品当前价格
	 */
	@ApiModelProperty(value = "商品当前价格", name = "currentPrice", example = "1")
	private Long currentPrice;

	/**
	 * 商品指导价格
	 */
	@ApiModelProperty(value = "商品指导价格", name = "guidePrice", example = "1")
	private Long guidePrice;

	/**
	 * 店铺
	 */
	@ApiModelProperty(value = "店铺", name = "store", example = "店铺")
	private String store;

	/**
	 * 促销活动
	 */
	@ApiModelProperty(value = "促销活动", name = "promotions", example = "促销活动")
	private String promotions;

	/**
	 * 优惠劵
	 */
	@ApiModelProperty(value = "优惠劵", name = "coupon", example = "优惠劵")
	private String coupon;

	/**
	 * 商品图片
	 */
	@ApiModelProperty(value = "商品图片", name = "pic", example = "商品图片")
	private String pic;

	/**
	 * 商品详情地址
	 */
	@ApiModelProperty(value = "商品详情地址", name = "url", example = "商品详情地址")
	private String url;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间", name = "created", example = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime created;

	public JdItemQueryDto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSpu() {
		return spu;
	}

	public void setSpu(Long spu) {
		this.spu = spu;
	}

	public Long getSku() {
		return sku;
	}

	public void setSku(Long sku) {
		this.sku = sku;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Long currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Long getGuidePrice() {
		return guidePrice;
	}

	public void setGuidePrice(Long guidePrice) {
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("JdItemQueryDto{").append("id=").append(id).append(", spu=").append(spu).append(", sku=").append(sku)
				.append(", title=").append(title).append(", currentPrice=").append(currentPrice).append(", guidePrice=")
				.append(guidePrice).append(", store=").append(store).append(", promotions=").append(promotions)
				.append(", coupon=").append(coupon).append(", pic=").append(pic).append(", url=").append(url)
				.append(", created=").append(created).append("}");
		return sb.toString();
	}

}
