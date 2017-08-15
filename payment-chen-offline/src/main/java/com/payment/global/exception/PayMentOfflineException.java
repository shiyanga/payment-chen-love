package com.payment.global.exception;
import com.payment.entity.enums.ErrorEnum;
import payment.chen.service.common.util.StringUtils;

/**
 * @author <a href="jxdong@Ctrip.com">jeff</a>
 * @version 2015/11/25 15:46
 */
public class PayMentOfflineException extends Exception {
    private static final long serialVersionUID = 1L;
    protected String code;
    protected String message;

    public PayMentOfflineException() {
    }

    public PayMentOfflineException(String message) {
        super(message);
    }

    public PayMentOfflineException(Throwable cause) {
        super(cause);
    }

    public PayMentOfflineException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayMentOfflineException(ErrorEnum errorEnum, String messageDetail){
        super(errorEnum.getCode() + ":" +errorEnum.getMessage() + ". "+ StringUtils.safeString(messageDetail));
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage() + ". "+ messageDetail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}



