package com.escalant3.googleimagesearchapp.models;


import android.os.Parcel;
import android.os.Parcelable;

public class GoogleImage implements Parcelable {
    private String tbUrl;

    public String getThumb() {
        return tbUrl;
    }

    //region Parcelable implementation
    protected GoogleImage(Parcel in) {
        tbUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tbUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GoogleImage> CREATOR = new Creator<GoogleImage>() {
        @Override
        public GoogleImage createFromParcel(Parcel in) {
            return new GoogleImage(in);
        }

        @Override
        public GoogleImage[] newArray(int size) {
            return new GoogleImage[size];
        }
    };
    //endregion
}
