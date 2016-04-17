package com.kindlepocket.cms.pojo;

import java.util.List;

public class SearchResult {

    // total counts
    private Long total;

    // search data
    private List<?> rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}
