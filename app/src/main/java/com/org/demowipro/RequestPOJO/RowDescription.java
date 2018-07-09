package com.org.demowipro.RequestPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RowDescription {

    @SerializedName("title")
    @Expose
    private String title;

    @Override
    public String toString() {
        return "RowDescription{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageHref=" + imageHref +
                '}';
    }

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("imageHref")
    @Expose
    private Object imageHref;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getImageHref() {
        return imageHref;
    }

    public void setImageHref(Object imageHref) {
        this.imageHref = imageHref;
    }
}
