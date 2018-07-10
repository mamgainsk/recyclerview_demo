package com.org.demowipro.request_pojo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class RowDescription implements Parcelable {

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

    @SerializedName("title")
    @Expose
    private String title;
    @PrimaryKey(autoGenerate = true)
    private int id;


    @SerializedName("imageHref")
    @Expose
    private String imageHref;

    public RowDescription() {
    }

    protected RowDescription(Parcel in) {
        id = in.readInt();
        title = in.readString();
        imageHref = in.readString();
        description = in.readString();
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(imageHref);
        dest.writeString(description);
    }
}
