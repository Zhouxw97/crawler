package com.wantwant.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 小商品vo
 *
 * @author zhouxiaowen
 * @date 2021-04-20 09:28
 * @version 1.0
 */
public class ItemVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 主键id
	*/
	private Long id;

	/**
	* 商品集合id
	*/
	private Long spu;

	/**
	* 商品最小品类单元id
	*/
	private Long sku;

	/**
	* 商品标题
	*/
	private String title;

	/**
	* 商品价格
	*/
	private Long price;

	/**
	* 店铺
	*/
	private String store;

	/**
	* 促销活动
	*/
	private String promotions;

	/**
	* 商品图片
	*/
	private String pic;

	/**
	* 商品详情地址
	*/
	private String url;

	/**
	* 创建时间
	*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private LocalDateTime created;

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

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
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
		sb.append("ItemDto{").append("id=").append(id).append(", spu=").append(spu).append(", sku=").append(sku)
				.append(", title=").append(title).append(", price=").append(price).append(", store=").append(store)
				.append(", promotions=").append(promotions).append(", pic=").append(pic).append(", url=").append(url)
				.append(", created=").append(created).append("}");
		return sb.toString();
	}

}