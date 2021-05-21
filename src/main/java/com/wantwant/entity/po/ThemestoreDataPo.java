package com.wantwant.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 主题门店业绩
 *
 * @author zhouxiaowen
 * @since 2021-05-21
 */
@TableName("sfa_themeStore_data")
public class ThemestoreDataPo extends Model<ThemestoreDataPo> {

	private static final long serialVersionUID = -8062732863145975232L;

	/**
	* 主键ID
	*/
	@TableId("ID")
	private Long id;

	/**
	* 今日销售额
	*/
	@TableField("TODAY_TRANS_AMOUNT")
	private BigDecimal todayTransAmount;

	/**
	* 本月销售额
	*/
	@TableField("MONTH_TRANS_AMOUNT")
	private BigDecimal monthTransAmount;

	/**
	* 本月团购销售额
	*/
	@TableField("MONTH_GROUP_AMOUNT")
	private BigDecimal monthGroupAmount;

	/**
	* 本月目标金额
	*/
	@TableField("MONTH_TARGET_AMOUNT")
	private BigDecimal monthTargetAmount;

	/**
	* 本月目标达成率
	*/
	@TableField("MONTH_TARGET_RATE")
	private BigDecimal monthTargetRate;

	/**
	* 统计时间
	*/
	@TableField("STATISTICS_TIME")
	private LocalDateTime statisticsTime;

	public ThemestoreDataPo() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTodayTransAmount() {
		return todayTransAmount;
	}

	public void setTodayTransAmount(BigDecimal todayTransAmount) {
		this.todayTransAmount = todayTransAmount;
	}

	public BigDecimal getMonthTransAmount() {
		return monthTransAmount;
	}

	public void setMonthTransAmount(BigDecimal monthTransAmount) {
		this.monthTransAmount = monthTransAmount;
	}

	public BigDecimal getMonthGroupAmount() {
		return monthGroupAmount;
	}

	public void setMonthGroupAmount(BigDecimal monthGroupAmount) {
		this.monthGroupAmount = monthGroupAmount;
	}

	public BigDecimal getMonthTargetAmount() {
		return monthTargetAmount;
	}

	public void setMonthTargetAmount(BigDecimal monthTargetAmount) {
		this.monthTargetAmount = monthTargetAmount;
	}

	public BigDecimal getMonthTargetRate() {
		return monthTargetRate;
	}

	public void setMonthTargetRate(BigDecimal monthTargetRate) {
		this.monthTargetRate = monthTargetRate;
	}

	public LocalDateTime getStatisticsTime() {
		return statisticsTime;
	}

	public void setStatisticsTime(LocalDateTime statisticsTime) {
		this.statisticsTime = statisticsTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ThemestoreDataPo{").append("id=").append(id).append(", todayTransAmount=").append(todayTransAmount)
				.append(", monthTransAmount=").append(monthTransAmount).append(", monthGroupAmount=")
				.append(monthGroupAmount).append(", monthTargetAmount=").append(monthTargetAmount)
				.append(", monthTargetRate=").append(monthTargetRate).append(", statisticsTime=").append(statisticsTime)
				.append("}");
		return sb.toString();
	}

}
