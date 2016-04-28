package com.kindlepocket.cms.pojo;

import java.util.List;

public class SearchResult {

    // total counts
    private Long total;

    // search data
    private List<Item> rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<Item> getRows() {
        return rows;
    }

    public void setRows(List<Item> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "SearchResult [total=" + total + ", rows=" + rows + "]";
    }

}
