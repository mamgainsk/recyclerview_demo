package com.org.demowipro.RequestPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RowContentInfo {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("rows")
    @Expose
    private List<RowDescription> rows = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RowDescription> getRows() {
        return rows;
    }

    public void setRows(List<RowDescription> rows) {
        this.rows = rows;
    }
}
