package com.rookiefly.open.dictionary.exception;

/**
 * 错误码接口
 */
public interface ErrorCode {

    /**
     * 获取错误码
     *
     * @return
     */
    String getCode();

    /**
     * 获取错误信息
     *
     * @return
     */
    String getDescription();
}
