package com.vimalcvs.stickers_app;

        import android.os.Parcel;
        import android.os.Parcelable;

        import java.util.List;

public class Sticker implements Parcelable {
    public String imageFileUrl;
    public String imageFileName;
    public String imageFileUrlThum;
    public List<String> emojis;
    public long size;
    public Sticker(String imageFileUrlThum,String imageFileUrl,String imageFileName, List<String> emojis) {
        this.imageFileUrl = imageFileUrl;
        this.imageFileName = imageFileName;
        this.imageFileUrlThum = imageFileUrlThum;
        this.emojis = emojis;
    }
    protected Sticker(Parcel in) {
        imageFileUrl = in.readString();
        imageFileName = in.readString();
        imageFileUrlThum = in.readString();
        emojis = in.createStringArrayList();
        size = in.readLong();
    }
    public static final Creator<Sticker> CREATOR = new Creator<Sticker>() {
        @Override
        public Sticker createFromParcel(Parcel in) {
            return new Sticker(in);
        }

        @Override
        public Sticker[] newArray(int size) {
            return new Sticker[size];
        }
    };

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageFileUrl);
        dest.writeString(imageFileName);
        dest.writeString(imageFileUrlThum);
        dest.writeStringList(emojis);
        dest.writeLong(size);
    }
}
