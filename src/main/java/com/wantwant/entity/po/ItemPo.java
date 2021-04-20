package com.wantwant.entity.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 小商品
 *
 * @author zhouxiaowen
 * @date 2021-04-20 09:23
 * @version 1.0
 */
@TableName("item")
public class ItemPo extends Model<ItemPo> {

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
	private Long spu;

	/**
	* 商品最小品类单元id
	*/
	@TableField("sku")
	private Long sku;

	/**
	* 商品标题
	*/
	@TableField("title")
	private String title;

	/**
	* 商品价格
	*/
	@TableField("price")
	private Double price;

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

	public ItemPo() {
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
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
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ItemPo{").append("id=").append(id).append(", spu=").append(spu).append(", sku=").append(sku)
				.append(", title=").append(title).append(", price=").append(price).append(", store=").append(store)
				.append(", promotions=").append(promotions).append(", pic=").append(pic).append(", url=").append(url)
				.append(", created=").append(created).append("}");
		return sb.toString();
	}

}
