package com.payment.simulator.common.dto.response;

import com.payment.simulator.common.dto.BaseDto;

import java.util.List;

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
