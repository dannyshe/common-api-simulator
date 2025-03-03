package com.payment.simulator.common.dto.response;

import com.payment.simulator.common.dto.BaseDto;

import java.util.List;

/**
 * DTO基类
 *
 * @author Danny She
 * @createTime 2021-11-01
 */
public class BaseListReponse<T> extends BaseDto {
    private boolean hasNext;
    private List<T> items;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
