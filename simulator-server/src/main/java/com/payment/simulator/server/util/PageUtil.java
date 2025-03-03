
package com.payment.simulator.server.util;

public class PageUtil {
    public PageUtil() {
    }

    public static int getStart(int pageNo, int pageSize) {
        if (pageNo < 1) {
            pageNo = 1;
        }

        if (pageSize < 1) {
            pageSize = 0;
        }

        return (pageNo - 1) * pageSize;
    }

    public static int[] transToStartEnd(int pageNo, int pageSize) {
        int start = getStart(pageNo, pageSize);
        if (pageSize < 1) {
            pageSize = 0;
        }

        int end = start + pageSize;
        return new int[]{start, end};
    }

    public static int totalPage(int totalCount, int pageSize) {
        if (pageSize == 0) {
            return 0;
        } else {
            return totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        }
    }

    public static int[] rainbow(int currentPage, int pageCount, int displayCount) {
        boolean isEven = displayCount % 2 == 0;
        int left = displayCount / 2;
        int right = displayCount / 2;
        int length = displayCount;
        if (isEven) {
            ++right;
        }

        if (pageCount < displayCount) {
            length = pageCount;
        }

        int[] result = new int[length];
        int i;
        if (pageCount >= displayCount) {
            if (currentPage <= left) {
                for (i = 0; i < result.length; ++i) {
                    result[i] = i + 1;
                }
            } else if (currentPage > pageCount - right) {
                for (i = 0; i < result.length; ++i) {
                    result[i] = i + pageCount - displayCount + 1;
                }
            } else {
                for (i = 0; i < result.length; ++i) {
                    result[i] = i + currentPage - left + (isEven ? 1 : 0);
                }
            }
        } else {
            for (i = 0; i < result.length; ++i) {
                result[i] = i + 1;
            }
        }

        return result;
    }

    public static int[] rainbow(int currentPage, int pageCount) {
        return rainbow(currentPage, pageCount, 10);
    }
}
