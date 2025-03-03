package com.payment.simulator.common.dto.response;

public class GenericResponse<T> extends AbstractResponse {
    private static final long serialVersionUID = 1L;
    public static final GenericResponse<Void> SUCCESS = new GenericResponse<Void>() {
        private static final long serialVersionUID = -3815215167127265311L;

        public void setSuccess(boolean success) {
        }

        public void setCode(String code) {
        }

        public void setMsg(String msg) {
        }

        public void setRetry(boolean retry) {
        }

        public void setArgs(Object[] args) {
        }

        public void setData(Void data) {
        }
    };
    private T data;

    public GenericResponse() {
    }

    public GenericResponse(T data) {
        this.data = data;
        this.success = true;
        this.code = String.valueOf(SUCCESS.code);
        this.msg = SUCCESS.msg;
        this.retry = false;
    }

    public static <T> GenericResponse<T> failNoRetry(int code, String msg) {
        return fail(code, false, msg);
    }

    public static <T> GenericResponse<T> failNoRetry(String code, String msg) {
        return fail(code, false, msg);
    }

    public static <T> GenericResponse<T> fail(int code, String msg) {
        return fail(code, true, msg);
    }

    public static <T> GenericResponse<T> fail(int code, boolean retry, String msg) {
        GenericResponse<T> genericResult = new GenericResponse();
        genericResult.setSuccess(false);
        genericResult.setCode(String.valueOf(code));
        genericResult.setMsg(msg);
        genericResult.setRetry(retry);
        return genericResult;
    }

    public static <T> GenericResponse<T> fail(String code, String msg) {
        return fail(code, true, msg);
    }

    public static <T> GenericResponse<T> fail(String code, boolean retry, String msg) {
        GenericResponse<T> genericResult = new GenericResponse();
        genericResult.setSuccess(false);
        genericResult.setCode(code);
        genericResult.setRetry(retry);
        genericResult.setMsg(msg);
        return genericResult;
    }

    public static <T> GenericResponse<T> success() {
        return success(null);
    }

    public static <T> GenericResponse<T> success(T data) {
        GenericResponse<T> genericResult = new GenericResponse();
        genericResult.setSuccess(true);
        genericResult.setCode(SUCCESS.code);
        genericResult.setMsg(SUCCESS.msg);
        genericResult.setData(data);
        return genericResult;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GenericResult{");
        sb.append("data=").append(this.data);
        sb.append(", success=").append(this.success);
        sb.append(", code='").append(this.code).append('\'');
        sb.append(", msg='").append(this.msg).append('\'');
        sb.append('}');
        return sb.toString();
    }

    static {
        SUCCESS.success = true;
        SUCCESS.code = String.valueOf(200);
        SUCCESS.msg = "success";
    }
}
