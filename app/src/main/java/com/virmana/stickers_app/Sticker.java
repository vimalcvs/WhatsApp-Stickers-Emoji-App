/*
 * Copyright 2020  Vimal CVS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.virmana.stickers_app;

import android.os.Parcel;
        import android.os.Parcelable;

        import java.util.List;
/**
 * Created by Vimal-CVS on 04/11/2020.
 */

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
