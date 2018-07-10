package com.org.demowipro.request_pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RowDescription implements Parcelable {

    @SerializedName("title")
    @Expose
    private String title;

    public static final Creator<RowDescription> CREATOR = new Creator<RowDescription>() {
        @Override
        public RowDescription createFromParcel(Parcel in) {
            return new RowDescription(in);
        }

        @Override
        public RowDescription[] newArray(int size) {
            return new RowDescription[size];
        }
    };
    @SerializedName("imageHref")
    @Expose
    private String imageHref;

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

    protected RowDescription(Parcel in) {
        title = in.readString();
        description = in.readString();
        imageHref = in.readString();
    }

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

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(imageHref);
    }
}
