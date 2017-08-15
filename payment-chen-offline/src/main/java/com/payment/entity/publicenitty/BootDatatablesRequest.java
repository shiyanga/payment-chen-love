package com.payment.entity.publicenitty;


/**
 * Created by shi_y on 2017/1/17.
 * bootstarp 分页
 */
public class BootDatatablesRequest {


    //每页显示多少
    private int offset = 0;
    //显示到多少
    private int limit = 10;
    //总记录数
    private int total = 1;

    //排序字段
    private String sort = "";
    private String order = "asc";

    public BootDatatablesRequest() {
    }


    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }


    private BootPage page;
    public BootPage getPage() {
        if (this.page == null) {
            this.page = new BootPage();
            page.setStartOffset(this.getOffset());
            page.setPageSize(this.getLimit());
        }
        return page;
    }

    public void setPage(BootPage page) {
        this.page = page;
    }


    //
//    public BootPage getPage() {
//        if (this.page == null) {
//            this.page = new BootPage();
//            page.setStartOffset(this.getOffset());
//            page.setPageSize(this.getLimit());
//        }
//        return page;
//    }
//
//
//    public void setPage(BootPage page) {
//        this.page = page;
//    }


    //    public Page getPage() {
//
//        if (this.page == null) {
//            this.page = new Page();
//            page.setStartOffset(this.getOffset());
//            page.setPageSize(this.getLimit());
//            //page.setDraw(this.getDraw());
//        }
//        return page;
//    }
//
//    public void setPage(Page page) {
//        this.page = page;
//    }
}
