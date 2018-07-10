package com.org.demowipro.request_pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RowContentInfo implements Parcelable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("rows")
    @Expose
    private List<RowDescription> rows = null;

    public static final Creator<RowContentInfo> CREATOR = new Creator<RowContentInfo>() {
        @Override
        public RowContentInfo createFromParcel(Parcel in) {
            return new RowContentInfo(in);
        }

        @Override
        public RowContentInfo[] newArray(int size) {
            return new RowContentInfo[size];
        }
    };

    protected RowContentInfo(Parcel in) {
        title = in.readString();
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }
}
