package com.wantwant.enums;
import java.util.Map;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

/**
 * 状态枚举
 *
 * @author zhouxiaowen
 * @date 2021-04-19 11:36
 * @version 1.0
 */
public enum  StatusEnum {
    NOT_EXIST(-1L,"商品spu为空");

    private Long type;
    private String statusName;

    public static final Map<Long, String> MAP = new CaseInsensitiveMap<>();

    static {
        for (StatusEnum item : StatusEnum.values()) {
            MAP.put(item.type, item.statusName);
        }
    }
    StatusEnum(Long type, String statusName) {
        this.type = type;
        this.statusName = statusName;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
