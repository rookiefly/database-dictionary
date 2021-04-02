package com.rookiefly.open.dictionary.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * 业务错误码
 */
public enum BizErrorCodeEnum implements ErrorCode {

    /**
     * 未指明的异常
     */
    SERVER_ERROR("500", "服务器异常，请稍后再试"),

    /**
     * 通用异常
     */
    REQUEST_ERROR("400", "入参异常,请检查入参后再次调用"),

    /**
     * 数据库异常
     */
    DATABASE_CONNECTION_ERROR("10001", "数据库连接异常"),
    ;

    /**
     * 错误码
     */
    private final String code;

    /**
     * 描述
     */
    private final String description;

    /**
     * @param code        错误码
     * @param description 描述
     */
    BizErrorCodeEnum(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据编码查询枚举。
     *
     * @param code 编码。
     * @return 枚举。
     */
    public static BizErrorCodeEnum getByCode(String code) {
        for (BizErrorCodeEnum value : BizErrorCodeEnum.values()) {
            if (StringUtils.equals(code, value.getCode())) {
                return value;
            }
        }
        return SERVER_ERROR;
    }

    /**
     * 枚举是否包含此code
     *
     * @param code 枚举code
     * @return 结果
     */
    public static Boolean contains(String code) {
        for (BizErrorCodeEnum value : BizErrorCodeEnum.values()) {
            if (StringUtils.equals(code, value.getCode())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
