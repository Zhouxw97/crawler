package com.wantwant.entity.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 折扣说明
 *
 * @author zhouxiaowen
 * @date 2021-04-23 12:10
 * @version 1.0
 */
public class JdDiscountdescDto implements Serializable {

    private static final long serialVersionUID = -284831259355969688L;

    private String high;

    private List<Map<String, Object>> info;

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public List<Map<String, Object>> getInfo() {
        return info;
    }

    public void setInfo(List<Map<String, Object>> info) {
        this.info = info;
    }

    @Override
    public String toString() {
        Map<String, Object> map = info.get(0);
        return "满"+map.get("quota")+"享"+map.get("discount")+"折扣";
    }
}
