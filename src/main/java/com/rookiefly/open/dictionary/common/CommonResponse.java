package com.rookiefly.open.dictionary.common;

import com.rookiefly.open.dictionary.exception.BizErrorCodeEnum;
import com.rookiefly.open.dictionary.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 接口响应数据
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommonResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SUCCESS_CODE = "200";

    public static final String SUCCESS_MSG = "success";

    private String code;

    private String msg;

    private Object data;

    public static CommonResponse newSuccessResponse() {
        return new CommonResponse(SUCCESS_CODE, SUCCESS_MSG, null);
    }

    public static CommonResponse newSuccessResponse(Object data) {
        return new CommonResponse(SUCCESS_CODE, SUCCESS_MSG, data);
    }

    public static CommonResponse newErrorResponse() {
        return newErrorResponse(BizErrorCodeEnum.SERVER_ERROR);
    }

    public static CommonResponse newErrorResponse(ErrorCode errorCode) {
        return new CommonResponse(errorCode.getCode(), errorCode.getDescription(), null);
    }

    public static CommonResponse newErrorResponse(ErrorCode errorCode, String errorMsg) {
        return new CommonResponse(errorCode.getCode(), errorMsg, null);
    }
}