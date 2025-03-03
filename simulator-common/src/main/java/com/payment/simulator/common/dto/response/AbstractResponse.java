package com.payment.simulator.common.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

public abstract class AbstractResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    protected boolean success;
    protected String code;
    protected String msg;
    protected boolean retry;
    @JsonIgnore
    @JSONField(
            serialize = false,
            deserialize = false
    )
    private transient Object[] args;

    public AbstractResponse() {
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isRetry() {
        return this.retry;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
