package com.rookiefly.open.dictionary.exception;

import lombok.Data;

/**
 * Description:业务异常类信息
 */
@Data
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    protected final ErrorCode errorCode;

    /**
     * 无参默认构造UNSPECIFIED
     */
    public BizException() {
        super(BizErrorCodeEnum.SERVER_ERROR.getDescription());
        this.errorCode = BizErrorCodeEnum.SERVER_ERROR;
    }

    /**
     * 指定错误码构造通用异常
     *
     * @param errorCode 错误码
     */
    public BizException(final ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    /**
     * 指定详细描述构造通用异常
     *
     * @param detailedMessage 详细描述
     */
    public BizException(final String detailedMessage) {
        super(detailedMessage);
        this.errorCode = BizErrorCodeEnum.SERVER_ERROR;
    }

    /**
     * 指定导火索构造通用异常
     *
     * @param t 导火索
     */
    public BizException(final Throwable t) {
        super(t);
        this.errorCode = BizErrorCodeEnum.SERVER_ERROR;
    }

    /**
     * 构造通用异常
     *
     * @param errorCode       错误码
     * @param detailedMessage 详细描述
     */
    public BizException(final ErrorCode errorCode, final String detailedMessage) {
        super(detailedMessage);
        this.errorCode = errorCode;
    }

    /**
     * 构造通用异常
     *
     * @param errorCode 错误码
     * @param t         导火索
     */
    public BizException(final ErrorCode errorCode, final Throwable t) {
        super(errorCode.getDescription(), t);
        this.errorCode = errorCode;
    }

    /**
     * 构造通用异常
     *
     * @param detailedMessage 详细描述
     * @param t               导火索
     */
    public BizException(final String detailedMessage, final Throwable t) {
        super(detailedMessage, t);
        this.errorCode = BizErrorCodeEnum.SERVER_ERROR;
    }

    /**
     * 构造通用异常
     *
     * @param errorCode       错误码
     * @param detailedMessage 详细描述
     * @param t               导火索
     */
    public BizException(final ErrorCode errorCode, final String detailedMessage,
                        final Throwable t) {
        super(detailedMessage, t);
        this.errorCode = errorCode;
    }

    /**
     * Getter method for property <tt>errorCode</tt>.
     *
     * @return property value of errorCode
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
 

 

 

 

 

 

 

 

 

 

 

