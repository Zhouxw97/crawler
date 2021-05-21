package com.wantwant.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 主题门店业绩
 *
 * @author zhouxiaowen
 * @since 2021-05-21
 */

public class ThemestoreDataDto implements Serializable {

	private static final long serialVersionUID = 9170632713546003215L;

	/**
	* 今日销售额
	*/
	@ApiModelProperty(value = "今日销售额")
	private BigDecimal todayTransAmount;

	/**
	* 本月销售额
	*/
	@ApiModelProperty(value = "本月销售额")
	private BigDecimal monthTransAmount;

	/**
	* 本月团购销售额
	*/
	@ApiModelProperty(value = "本月团购销售额")
	private BigDecimal monthGroupAmount;

	/**
	* 本月目标金额
	*/
	@ApiModelProperty(value = "本月目标金额")
	private BigDecimal monthTargetAmount;

	/**
	* 本月目标达成率
	*/
	@ApiModelProperty(value = "本月目标达成率")
	private BigDecimal monthTargetRate;

	/**
	* 统计时间
	*/
	@NotNull(message = "请输入统计时间！")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(value = "统计时间yyyy-MM-dd HH:mm:ss")
	private LocalDateTime statisticsTime;

	public ThemestoreDataDto() {
		super();
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
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ThemestoreDataDto{").append(", todayTransAmount=").append(todayTransAmount)
				.append(", monthTransAmount=").append(monthTransAmount).append(", monthGroupAmount=")
				.append(monthGroupAmount).append(", monthTargetAmount=").append(monthTargetAmount)
				.append(", monthTargetRate=").append(monthTargetRate).append(", statisticsTime=").append(statisticsTime)
				.append("}");
		return sb.toString();
	}


}
