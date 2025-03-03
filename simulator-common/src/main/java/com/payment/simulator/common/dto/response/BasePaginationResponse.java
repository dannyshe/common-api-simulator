package com.payment.simulator.common.dto.response;

import java.util.List;

import com.payment.simulator.common.dto.BaseDto;
import lombok.Data;

/**
 * DTO基类
 *
 * 
 * @createTime 2021-11-01
 */
@Data
public class BasePaginationResponse<T> extends BaseDto {
    private List<T> items;
    private int currentPage;
    private int pageSize;
    private int totalPage;
    private long totalNum;

    public BasePaginationResponse() {
    }

    public BasePaginationResponse(int currentPage, int pageSize, long totalNum, List<T> items) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalNum = totalNum;
        this.items = items;
        this.totalPage =pageSize != 0 ? (int) ((totalNum + pageSize - 1) / pageSize) : 0;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}
