package com.payment.simulator.common.event;

import lombok.Data;

@Data
public class IEvent<T> {

    /**
     * 采集主体标识
     */
    private String distinctId;
    /**
     * 消息时间
     */
    private long timestamp;
    /**
     * 事件ID，由是否系统提供？是否可以是traceId
     */
    private String eventId;
    /**
     * 标识事件的类型
     */
    private Integer eventType;
    /**
     * 系统id
     */
    private String systemId;

    /**
     * 采集项目名称
     */
    private String project;

    private T content;

    public IEvent() {
        this.timestamp = System.currentTimeMillis();
    }
}
