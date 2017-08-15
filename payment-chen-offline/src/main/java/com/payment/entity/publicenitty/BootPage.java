package com.payment.entity.publicenitty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shi_y on 2017/1/18.
 */
public class BootPage<T> {


    private int currentPage = 1;// 页码，默认是第一页


    private int pageSize = 10;// 每页显示的记录数，默认是10
    private int startOffset = 0;//开始记录


    private long total;// 总记录数

    private List<T> row;// 对应的当前页记录
    private Map<String, Object> params = new HashMap<String, Object>();// 其他的参数我们把它分装成一个Map对象

    public long getTotal() {
        return total;
    }
    public void setTotal(List<T> list) {
        if (list != null) {
            if (list instanceof com.github.pagehelper.Page) {
                this.total = ((com.github.pagehelper.Page) list).getTotal();
            }
        } else {
            this.total = list.size();
        }
    }

    public List<T> getRow() {
        return row;
    }

    public void setRow(List<T> row) {
        this.row = row;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public int getCurrentPage() {
        if (this.pageSize > 0) {
            return startOffset < pageSize ? 1 : (startOffset / pageSize + 1);
        } else {
            return 1;
        }
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(int startOffset) {
        this.startOffset = startOffset;
    }


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
