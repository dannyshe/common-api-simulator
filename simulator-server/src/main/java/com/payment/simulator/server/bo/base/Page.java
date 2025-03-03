package com.payment.simulator.server.bo.base;

import com.payment.simulator.server.util.PageUtil;

import java.io.Serializable;

public class Page implements Serializable {
    private static final long serialVersionUID = 97792549823353462L;
    public static final int DEFAULT_PAGE_SIZE = 20;
    private int pageNumber;
    private int pageSize;

    public Page() {
        this(0, 20);
    }

    public Page(int pageNumber, int pageSize) {
        this.pageNumber = Math.max(pageNumber, 0);
        this.pageSize = pageSize <= 0 ? 20 : pageSize;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = Math.max(pageNumber, 0);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public int getNumPerPage() {
        return this.getPageSize();
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void setNumPerPage(int pageSize) {
        this.setPageSize(pageSize);
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 ? 20 : pageSize;
    }

    public int getStartPosition() {
        return this.getStartEnd()[0];
    }

    public int getEndPosition() {
        return this.getStartEnd()[1];
    }

    public int[] getStartEnd() {
        return PageUtil.transToStartEnd(this.pageNumber, this.pageSize);
    }

    public String toString() {
        return "Page [page=" + this.pageNumber + ", pageSize=" + this.pageSize + "]";
    }
}
