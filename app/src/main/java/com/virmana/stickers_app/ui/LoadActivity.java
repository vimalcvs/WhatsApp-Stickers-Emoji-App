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
package com.virmana.stickers_app.ui;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.virmana.stickers_app.R;
import com.virmana.stickers_app.Sticker;
import com.virmana.stickers_app.StickerPack;
import com.virmana.stickers_app.api.apiClient;
import com.virmana.stickers_app.api.apiRest;
import com.virmana.stickers_app.entity.PackApi;
import com.virmana.stickers_app.entity.StickerApi;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.virmana.stickers_app.MainActivity.EXTRA_STICKERPACK;

public class LoadActivity extends AppCompatActivity {
    ArrayList<StickerPack> stickerPacks = new ArrayList<>();
    List<Sticker> mStickers;
    List<String> mEmojis,mDownloadFiles;
    private Integer position = 0;

    private  Integer id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Uri data = this.getIntent().getData();
        if (data==null){
            Bundle bundle = getIntent().getExtras() ;
            this.id =  bundle.getInt("id");
        }else{
            this.id=Integer.parseInt(data.getPath().replace("/share/","").replace(".html",""));
        }

        stickerPacks = new ArrayList<>();
        mStickers = new ArrayList<>();
        mEmojis = new ArrayList<>();
        mDownloadFiles = new ArrayList<>();
        mEmojis.add("");

        getArticle();
    }

    private static String getLastBitFromUrl(final String url) {
        return url.replaceFirst(".*/([^/?]+).*", "$1");
    }

    public void getArticle(){

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<PackApi> call = service.packById(id);
        call.enqueue(new retrofit2.Callback<PackApi>() {
            @Override
            public void onResponse(Call<PackApi> call, Response<PackApi> response) {
                if(response.isSuccessful()) {

                    position = 0 ;
                    stickerPacks.clear();
                    mStickers.clear();
                    mEmojis.clear();
                    mDownloadFiles.clear();
                    mEmojis.add("");
                    position = 0;

                    PackApi packApi= response.body();
                    stickerPacks.add(new StickerPack(
                            packApi.getIdentifier()+"",
                            packApi.getName(),
                            packApi.getPublisher(),
                            getLastBitFromUrl(packApi.getTrayImageFile()).replace(" ","_"),
                            packApi.getTrayImageFile(),
                            packApi.getSize(),
                            packApi.getDownloads(),
                            packApi.getPremium(),
                            packApi.getTrusted(),
                            packApi.getCreated(),
                            packApi.getUser(),
                            packApi.getUserimage(),
                            packApi.getUserid(),
                            packApi.getPublisherEmail(),
                            packApi.getPublisherWebsite(),
                            packApi.getPrivacyPolicyWebsite(),
                            packApi.getLicenseAgreementWebsite()
                    ));
                    List<StickerApi> stickerApiList =  packApi.getStickers();
                    for (int j = 0; j < stickerApiList.size(); j++) {
                        StickerApi stickerApi = stickerApiList.get(j);
                        mStickers.add(new Sticker(
                                stickerApi.getImageFileThum(),
                                stickerApi.getImageFile(),
                                getLastBitFromUrl(stickerApi.getImageFile()).replace(".png",".webp"),
                                mEmojis
                        ));
                        mDownloadFiles.add(stickerApi.getImageFile());
                    }
                    Hawk.put(packApi.getIdentifier()+"", mStickers);
                    stickerPacks.get(position).setStickers(Hawk.get(packApi.getIdentifier()+"",new ArrayList<Sticker>()));
                    stickerPacks.get(position).packApi = packApi;
                    mStickers.clear();


                       Intent intent =  new Intent((getApplicationContext()), StickerDetailsActivity.class);
                        intent.putExtra(EXTRA_STICKERPACK, stickerPacks.get(position));
                        intent.putExtra("from", true);
                       startActivity(intent);
                       overridePendingTransition(R.anim.enter, R.anim.exit);
                       finish();

                }else{
                    Toast.makeText(LoadActivity.this, "Pack Not exit", Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<PackApi> call, Throwable t) {
                Toast.makeText(LoadActivity.this, "Pack Not exit", Toast.LENGTH_SHORT).show();
                Intent intent  = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}



