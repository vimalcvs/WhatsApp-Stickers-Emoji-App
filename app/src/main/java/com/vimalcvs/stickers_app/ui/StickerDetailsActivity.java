package com.vimalcvs.stickers_app.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.Constants;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.vimalcvs.stickers_app.BuildConfig;
import com.vimalcvs.stickers_app.MainActivity;
import com.vimalcvs.stickers_app.Manager.PrefManager;
import com.vimalcvs.stickers_app.R;
import com.vimalcvs.stickers_app.Sticker;
import com.vimalcvs.stickers_app.StickerPack;
import com.vimalcvs.stickers_app.adapter.StickerDetailsAdapter;
import com.vimalcvs.stickers_app.config.Config;
import com.orhanobut.hawk.Hawk;
import com.vimalcvs.stickers_app.api.apiClient;
import com.vimalcvs.stickers_app.api.apiRest;
import com.vimalcvs.stickers_app.entity.ApiResponse;
import com.squareup.picasso.Picasso;
import com.vimalcvs.stickers_app.entity.PackApi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.vimalcvs.stickers_app.MainActivity.EXTRA_STICKER_PACK_AUTHORITY;
import static com.vimalcvs.stickers_app.MainActivity.EXTRA_STICKER_PACK_ID;
import static com.vimalcvs.stickers_app.MainActivity.EXTRA_STICKER_PACK_NAME;

public class StickerDetailsActivity extends AppCompatActivity {



    private RewardedVideoAd mRewardedVideoAd;

    private Boolean autoDisplay =  false;


    private static final int ADD_PACK = 200;
    private static final String TAG = StickerDetailsActivity.class.getSimpleName();
    StickerPack stickerPack;
    StickerDetailsAdapter adapter;
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<Sticker> stickers;
    ArrayList<String> strings;

    private PackApi packApi;


    public static String path;
    public static String mainpath;
    private ImageView pack_try_image;
    private TextView item_pack_name;
    private TextView item_pack_publisher;
    private TextView text_view_create_pack;
    private TextView text_view_downloads_pack;
    private TextView text_view_size_pack;
    private LinearLayout linear_layout_add_to_whatsapp;
    private LinearLayout linear_layout_progress;
    private CircularImageView circle_image_view_user_image;
    private ImageView image_view_trusted_user;
    private TextView text_view_user_name;
    private LinearLayout linear_layout_ads;


    private AppCompatRatingBar rating_bar_guide_main_pack_activity;
    private AppCompatRatingBar rating_bar_guide_value_pack_activity;
    private RatingBar rating_bar_guide_1_pack_activity;
    private RatingBar rating_bar_guide_2_pack_activity;
    private RatingBar rating_bar_guide_3_pack_activity;
    private RatingBar rating_bar_guide_4_pack_activity;
    private RatingBar rating_bar_guide_5_pack_activity;
    private TextView text_view_rate_1_pack_activity;
    private TextView text_view_rate_2_pack_activity;
    private TextView text_view_rate_3_pack_activity;
    private TextView text_view_rate_4_pack_activity;
    private TextView text_view_rate_5_pack_activity;
    private ProgressBar progress_bar_rate_1_pack_activity;
    private ProgressBar progress_bar_rate_2_pack_activity;
    private ProgressBar progress_bar_rate_3_pack_activity;
    private ProgressBar progress_bar_rate_4_pack_activity;
    private ProgressBar progress_bar_rate_5_pack_activity;
    private TextView text_view_rate_main_pack_activity;
    private Button button_follow_user;
    private RelativeLayout linear_layout_pack_screen_shot;
    private LinearLayout linear_layout_share;
    private ImageView image_view_fav;
    private ProgressBar progress_bar_pack;
    private ProgressBar progress_bar_sticker;



    private Dialog dialog;

    private  Boolean DialogOpened = false;
    private  Boolean fromLoad = false;

    IInAppBillingService mService;

    private static final String LOG_TAG = "iabv3";
    // put your Google merchant id here (as stated in public profile of your Payments Merchant Center)
    // if filled library will provide protection against Freedom alike Play Market simulators
    private static final String MERCHANT_ID=null;

    private BillingProcessor bp;
    private boolean readyToPurchase = false;

    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_details);
        // added from main Activity
        mainpath = getFilesDir() + "/" + "stickers_asset";


        stickerPack = getIntent().getParcelableExtra(MainActivity.EXTRA_STICKERPACK);
        fromLoad =  getIntent().getBooleanExtra("from",false);
        toolbar = findViewById(R.id.toolbar);
        pack_try_image = findViewById(R.id.pack_try_image);
        item_pack_name = findViewById(R.id.item_pack_name);
        item_pack_publisher = findViewById(R.id.item_pack_publisher);
        text_view_create_pack = findViewById(R.id.text_view_create_pack);
        text_view_downloads_pack = findViewById(R.id.text_view_downloads_pack);
        text_view_size_pack = findViewById(R.id.text_view_size_pack);
        linear_layout_add_to_whatsapp = findViewById(R.id.linear_layout_add_to_whatsapp);
        linear_layout_progress = findViewById(R.id.linear_layout_progress);
        Picasso.with(this).load(stickerPack.trayImageUrl).placeholder(getResources().getDrawable(R.drawable.sticker_error)).error(getResources().getDrawable(R.drawable.sticker_error)).into(pack_try_image);
        item_pack_name.setText(stickerPack.name);
        item_pack_publisher.setText(stickerPack.publisher);
        text_view_create_pack.setText(stickerPack.created);
        text_view_downloads_pack.setText(stickerPack.downloads);
        text_view_size_pack.setText(stickerPack.size);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(stickerPack.name);
        getSupportActionBar().setSubtitle(stickerPack.publisher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerView);
        stickers = stickerPack.getStickers();
        strings = new ArrayList<>();
        path = getFilesDir() + "/" + "stickers_asset" + "/" + stickerPack.identifier + "/";
        File file = new File(path + stickers.get(0).imageFileName);
        Log.d(TAG, "onCreate: " +path + stickers.get(0).imageFileName);
        for (Sticker s : stickers) {
            strings.add(s.imageFileUrlThum);
        }
        adapter = new StickerDetailsAdapter(strings, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        initView();

        initAction();
        showAdsBanner();
        setUser();
        getRate();
        getUser();
        packApi = new PackApi(stickerPack);
        checkFavorite();
        initRewarded();
        loadRewardedVideoAd();
        initBuy();
        initAds();

    }
    private void initBuy() {
        Intent serviceIntent =
                new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);


        if(!BillingProcessor.isIabServiceAvailable(this)) {
            //  showToast("In-app billing service is unavailable, please upgrade Android Market/Play to version >= 3.9.16");
        }

        bp = new BillingProcessor(this, Config.LICENSE_KEY, MERCHANT_ID, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
                //  showToast("onProductPurchased: " + productId);
                Intent intent= new Intent(StickerDetailsActivity.this,SplashActivity.class);
                startActivity(intent);
                finish();
                updateTextViews();
            }
            @Override
            public void onBillingError(int errorCode, @Nullable Throwable error) {
                // showToast("onBillingError: " + Integer.toString(errorCode));
            }
            @Override
            public void onBillingInitialized() {
                //  showToast("onBillingInitialized");
                readyToPurchase = true;
                updateTextViews();
            }
            @Override
            public void onPurchaseHistoryRestored() {
                // showToast("onPurchaseHistoryRestored");
                for(String sku : bp.listOwnedProducts())
                    Log.d(LOG_TAG, "Owned Managed Product: " + sku);
                for(String sku : bp.listOwnedSubscriptions())
                    Log.d(LOG_TAG, "Owned Subscription: " + sku);
                updateTextViews();
            }
        });
        bp.loadOwnedPurchasesFromGoogle();
    }

    private void updateTextViews() {
        PrefManager prf= new PrefManager(getApplicationContext());
        bp.loadOwnedPurchasesFromGoogle();

    }
    public Bundle getPurchases(){
        if (!bp.isInitialized()) {


            //  Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            return null;
        }
        try{
            // Toast.makeText(this, "good", Toast.LENGTH_SHORT).show();

            return  mService.getPurchases(Constants.GOOGLE_API_VERSION, getApplicationContext().getPackageName(), Constants.PRODUCT_TYPE_SUBSCRIPTION, null);
        }catch (Exception e) {
            //  Toast.makeText(this, "ex", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }
        return null;
    }

    private void checkFavorite() {
        List<PackApi> favorites_list =Hawk.get("favorite");
        Boolean exist = false;
        if (favorites_list == null) {
            favorites_list = new ArrayList<>();
        }

        for (int i = 0; i < favorites_list.size(); i++) {
            if (favorites_list.get(i).getIdentifier().equals(packApi.getIdentifier())) {
                exist = true;
            }
        }
        if (exist){
            image_view_fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black));
        }else{
            image_view_fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border));

        }
    }

    public void initView(){
        this.linear_layout_ads = findViewById(R.id.linear_layout_ads);
        this.circle_image_view_user_image = findViewById(R.id.circle_image_view_user_image);
        this.image_view_trusted_user = findViewById(R.id.image_view_trusted_user);
        this.text_view_user_name = findViewById(R.id.text_view_user_name);
        this.progress_bar_sticker =  findViewById(R.id.progress_bar_sticker);
        this.progress_bar_pack =  findViewById(R.id.progress_bar_pack);

        this.linear_layout_share =(LinearLayout) findViewById(R.id.linear_layout_share);
        this.linear_layout_pack_screen_shot =(RelativeLayout) findViewById(R.id.linear_layout_pack_screen_shot);
        this.rating_bar_guide_main_pack_activity=(AppCompatRatingBar) findViewById(R.id.rating_bar_guide_main_pack_activity);
        this.rating_bar_guide_value_pack_activity=(AppCompatRatingBar) findViewById(R.id.rating_bar_guide_value_pack_activity);
        this.rating_bar_guide_1_pack_activity=(RatingBar) findViewById(R.id.rating_bar_guide_1_pack_activity);
        this.rating_bar_guide_2_pack_activity=(RatingBar) findViewById(R.id.rating_bar_guide_2_pack_activity);
        this.rating_bar_guide_3_pack_activity=(RatingBar) findViewById(R.id.rating_bar_guide_3_pack_activity);
        this.rating_bar_guide_4_pack_activity=(RatingBar) findViewById(R.id.rating_bar_guide_4_pack_activity);
        this.rating_bar_guide_5_pack_activity=(RatingBar) findViewById(R.id.rating_bar_guide_5_pack_activity);

        this.text_view_rate_1_pack_activity=(TextView) findViewById(R.id.text_view_rate_1_pack_activity);
        this.text_view_rate_2_pack_activity=(TextView) findViewById(R.id.text_view_rate_2_pack_activity);
        this.text_view_rate_3_pack_activity=(TextView) findViewById(R.id.text_view_rate_3_pack_activity);
        this.text_view_rate_4_pack_activity=(TextView) findViewById(R.id.text_view_rate_4_pack_activity);
        this.text_view_rate_5_pack_activity=(TextView) findViewById(R.id.text_view_rate_5_pack_activity);
        this.text_view_rate_main_pack_activity=(TextView) findViewById(R.id.text_view_rate_main_pack_activity);
        this.progress_bar_rate_1_pack_activity=(ProgressBar) findViewById(R.id.progress_bar_rate_1_pack_activity);
        this.progress_bar_rate_2_pack_activity=(ProgressBar) findViewById(R.id.progress_bar_rate_2_pack_activity);
        this.progress_bar_rate_3_pack_activity=(ProgressBar) findViewById(R.id.progress_bar_rate_3_pack_activity);
        this.progress_bar_rate_4_pack_activity=(ProgressBar) findViewById(R.id.progress_bar_rate_4_pack_activity);
        this.progress_bar_rate_5_pack_activity=(ProgressBar) findViewById(R.id.progress_bar_rate_5_pack_activity);

        this.image_view_fav  = (ImageView) findViewById(R.id.image_view_fav);
        this.button_follow_user  = (Button) findViewById(R.id.button_follow_user);
    }
    public void initAction(){
        this.linear_layout_add_to_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!stickerPack.premium.equals("true")){
                    NewAddPack();
                }else{
                    if (checkSUBSCRIBED()) {
                        NewAddPack();
                    }else{
                        showDialog();
                    }
                }
            }
        });

        this.image_view_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFavotite();
            }
        });
        this.linear_layout_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });
        this.button_follow_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                follow();
            }
        });
        this.rating_bar_guide_main_pack_activity.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser){
                    addRate(rating);
                }

            }
        });
        this.text_view_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =  new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("id",Integer.parseInt(stickerPack.userid));
                intent.putExtra("image",stickerPack.userimage);
                intent.putExtra("name",stickerPack.username);
                if (stickerPack.trused.equals("true"))
                    intent.putExtra("trusted",true);
                else
                    intent.putExtra("trusted",false);
                startActivity(intent,
                        ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth(),
                                view.getHeight()).toBundle());
            }
        });
        this.circle_image_view_user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =  new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("id",Integer.parseInt(stickerPack.userid));
                intent.putExtra("image",stickerPack.userimage);
                intent.putExtra("name",stickerPack.username);

                if (stickerPack.trused.equals("true"))
                    intent.putExtra("trusted",true);
                else
                    intent.putExtra("trusted",false);

                startActivity(intent,
                        ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth(),
                                view.getHeight()).toBundle());
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pack, menu);
        return true;
    }


    private void addFavotite() {


        List<PackApi> favorites_list =Hawk.get("favorite");
        Boolean exist = false;
        if (favorites_list == null) {
            favorites_list = new ArrayList<>();
        }
        int fav_position = -1;
        for (int i = 0; i < favorites_list.size(); i++) {
            if (favorites_list.get(i).getIdentifier().equals(packApi.getIdentifier())) {
                exist = true;
                fav_position = i;
            }
        }
        if (exist == false) {
            favorites_list.add(packApi);
            Hawk.put("favorite",favorites_list);
            image_view_fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black));

        }else{
            favorites_list.remove(fav_position);
            Hawk.put("favorite",favorites_list);
            image_view_fav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border));
        }
    }

    public void follow(){

        PrefManager prf= new PrefManager(getApplicationContext());
        if (prf.getString("LOGGED").toString().equals("TRUE")) {
            button_follow_user.setText(getResources().getString(R.string.loading));
            button_follow_user.setEnabled(false);
            String follower = prf.getString("ID_USER");
            String key = prf.getString("TOKEN_USER");
            Retrofit retrofit = apiClient.getClient();
            apiRest service = retrofit.create(apiRest.class);
            Call<ApiResponse> call = service.follow(Integer.parseInt(stickerPack.userid), Integer.parseInt(follower), key);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getCode().equals(200)){
                            button_follow_user.setText("UnFollow");
                        }else if (response.body().getCode().equals(202)) {
                            button_follow_user.setText("Follow");

                        }
                    }
                    button_follow_user.setEnabled(true);

                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    button_follow_user.setEnabled(true);
                }
            });
        }else{
            Intent intent = new Intent(StickerDetailsActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }
    private void getUser() {
        PrefManager prf= new PrefManager(getApplicationContext());
        if (prf.getString("LOGGED").toString().equals("TRUE")) {
            button_follow_user.setEnabled(false);
            Integer follower= -1;
            follower = Integer.parseInt(prf.getString("ID_USER"));
            if (follower!=Integer.parseInt(stickerPack.userid)){
                button_follow_user.setVisibility(View.VISIBLE);
            }
            Retrofit retrofit = apiClient.getClient();
            apiRest service = retrofit.create(apiRest.class);
            Call<ApiResponse> call = service.getUser(Integer.parseInt(stickerPack.userid),follower);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()){

                        for (int i=0;i<response.body().getValues().size();i++){

                            if (response.body().getValues().get(i).getName().equals("follow")){
                                if (response.body().getValues().get(i).getValue().equals("true"))
                                    button_follow_user.setText("UnFollow");
                                else
                                    button_follow_user.setText("Follow");
                            }
                        }

                    }else{


                    }
                    button_follow_user.setEnabled(true);
                }
                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    button_follow_user.setEnabled(true);
                }
            });
        }
    }

    public void addRate(final float value) {
        PrefManager prf = new PrefManager(getApplicationContext());
        if (prf.getString("LOGGED").toString().equals("TRUE")) {
            Retrofit retrofit = apiClient.getClient();
            apiRest service = retrofit.create(apiRest.class);
            Call<ApiResponse> call = service.addRate(prf.getString("ID_USER").toString(),Integer.parseInt(stickerPack.identifier), value);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getCode() == 200) {
                            Toasty.success(StickerDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toasty.success(StickerDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        getRate();
                    } else {

                    }

                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {


                }
            });
        } else {
            Intent intent = new Intent(StickerDetailsActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }


    public void getRate() {
        PrefManager prf = new PrefManager(getApplicationContext());
        String user_id = "0";
        if (prf.getString("LOGGED").toString().equals("TRUE")) {
            user_id=prf.getString("ID_USER").toString();
        }
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<ApiResponse> call = service.getRate(user_id,Integer.parseInt(stickerPack.identifier));
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == 200) {
                        rating_bar_guide_main_pack_activity.setRating(Integer.parseInt(response.body().getMessage()));
                    } else if (response.body().getCode() == 202){
                        rating_bar_guide_main_pack_activity.setRating(0);
                    }else{
                        rating_bar_guide_main_pack_activity.setRating(0);
                    }
                    if(response.body().getCode() != 500){
                        Integer rate_1=0;
                        Integer rate_2=0;
                        Integer rate_3=0;
                        Integer rate_4=0;
                        Integer rate_5=0;
                        float rate=0;
                        for (int i=0;i<response.body().getValues().size();i++){

                            if (response.body().getValues().get(i).getName().equals("1")){
                                rate_1=Integer.parseInt(response.body().getValues().get(i).getValue());
                            }
                            if (response.body().getValues().get(i).getName().equals("2")){
                                rate_2=Integer.parseInt(response.body().getValues().get(i).getValue());
                            }
                            if (response.body().getValues().get(i).getName().equals("3")){
                                rate_3=Integer.parseInt(response.body().getValues().get(i).getValue());
                            }
                            if (response.body().getValues().get(i).getName().equals("4")){
                                rate_4=Integer.parseInt(response.body().getValues().get(i).getValue());
                            }
                            if (response.body().getValues().get(i).getName().equals("5")){
                                rate_5=Integer.parseInt(response.body().getValues().get(i).getValue());
                            }
                            if (response.body().getValues().get(i).getName().equals("rate")){
                                rate=Float.parseFloat(response.body().getValues().get(i).getValue());
                            }
                        }
                        rating_bar_guide_value_pack_activity.setRating(rate);
                        String formattedString=rate + "";


                        text_view_rate_main_pack_activity.setText(formattedString);
                        text_view_rate_1_pack_activity.setText(rate_1+"");
                        text_view_rate_2_pack_activity.setText(rate_2+"");
                        text_view_rate_3_pack_activity.setText(rate_3+"");
                        text_view_rate_4_pack_activity.setText(rate_4+"");
                        text_view_rate_5_pack_activity.setText(rate_5+"");
                        Integer total= rate_1 + rate_2 + rate_3 + rate_4 + rate_5;
                        if(total==0) {
                            total = 1;
                        }
                        progress_bar_rate_1_pack_activity.setProgress((int) ((rate_1*100) / total)  );
                        progress_bar_rate_2_pack_activity.setProgress((int) ((rate_2*100) / total)  );
                        progress_bar_rate_3_pack_activity.setProgress((int) ((rate_3*100) / total)  );
                        progress_bar_rate_4_pack_activity.setProgress((int) ((rate_4*100) / total)  );
                        progress_bar_rate_5_pack_activity.setProgress((int) ((rate_5*100) / total)  );
                    }
                }
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {


            }
        });

    }
    public void addDownload(){

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<Integer> call = service.addDownload(Integer.parseInt(stickerPack.identifier));
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()) {
                    text_view_downloads_pack.setText(response.body()+"");
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }
    public void setUser(){
        text_view_user_name.setText(stickerPack.username);
        Picasso.with(this).load(stickerPack.userimage).placeholder(getResources().getDrawable(R.drawable.profile)).placeholder(getResources().getDrawable(R.drawable.profile)).into(circle_image_view_user_image);
        if (stickerPack.trused.equals("true")){
            image_view_trusted_user.setVisibility(View.VISIBLE);
        }else{
            image_view_trusted_user.setVisibility(View.GONE);
        }
    }
    public void Addtowhatsapp(){
        Intent intent = new Intent();
        intent.setAction("com.whatsapp.intent.action.ENABLE_STICKER_PACK");
        intent.putExtra(EXTRA_STICKER_PACK_ID, stickerPack.identifier);
        intent.putExtra(EXTRA_STICKER_PACK_AUTHORITY, BuildConfig.CONTENT_PROVIDER_AUTHORITY);
        intent.putExtra(EXTRA_STICKER_PACK_NAME, stickerPack.name);
        try {
            startActivityForResult(intent, ADD_PACK);
        } catch (ActivityNotFoundException e) {
            Toasty.info(StickerDetailsActivity.this, "WhatsApp Application not installed on this device", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PACK) {
            if (resultCode == Activity.RESULT_OK) {
                addDownload();
            }
        }
    }
    private Uri saveImageExternal(Bitmap image) {
        //TODO - Should be processed in another thread
        Uri uri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "to-share.png");
            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.close();
            uri = Uri.fromFile(file);
        } catch (IOException e) {
            Log.d(TAG, "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
    }
    private Uri saveImage(Bitmap image) {
        //TODO - Should be processed in another thread
        File imagesFolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "shared_image.png");

            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(this, "com.vimalcvs.stickers_app.fileprovider", file);

        } catch (IOException e) {
            Log.d(TAG, "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
    }
    private void shareImageUri(Uri uri){
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);

        String shareBody =stickerPack.name+"\n\n"+getResources().getString(R.string.download_pack_from)+"\n"+ Config.API_URL.replace("api","share")+stickerPack.identifier+".html";

        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);

        if (1!=1){
            intent.setType("text/plain");
        }else{
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivity(Intent.createChooser(intent, " Shared via " + getResources().getString(R.string.app_name) ));

    }
    private Bitmap convertLayoutToImage() {

        //linear_layout_pack_screen_shot.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        //       View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //linear_layout_pack_screen_shot.layout(0, 0, linear_layout_pack_screen_shot.getMeasuredWidth(), linear_layout_pack_screen_shot.getMeasuredHeight());

        // linear_layout_pack_screen_shot.setDrawingCacheEnabled(true);
        //  linear_layout_pack_screen_shot.buildDrawingCache();
        return getBitmapFromView(linear_layout_pack_screen_shot);// creates bitmap and returns the same
    }
    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
    // used for scanning gallery
    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[] { path },null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void share(){


        Bitmap bitmap = convertLayoutToImage();
        if(bitmap!=null){
            shareImageUri(saveImage(bitmap));
        }


        /*try{
            File file = new File(getApplicationContext().getCacheDir()+"/Image.png");
            bitmap.compress(Bitmap.CompressFormat.PNG,100,new FileOutputStream(file));
            Uri uri = FileProvider.getUriForFile(getApplicationContext(),"com.vimalcvs.stickers_app.fileprovider", file);

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/jpeg");
            getApplicationContext().startActivity(Intent.createChooser(shareIntent, "Share"));

        }catch (FileNotFoundException e) {e.printStackTrace();}

        /*Bitmap bitmap = Bitmap.createBitmap(
                linear_layout_pack_screen_shot.getChildAt(0).getWidth(),
                linear_layout_pack_screen_shot.getChildAt(0).getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        linear_layout_pack_screen_shot.getChildAt(0).draw(c);
        Uri  uri =   saveImageExternal(bitmap);

        Toast.makeText(this, uri.getPath(), Toast.LENGTH_SHORT).show();
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "to-share.png");
        if (file.exists()){
            Toast.makeText(this, "exit", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "not exist", Toast.LENGTH_SHORT).show();
        }
        pack_try_image.setImageURI(uri);


        try {

            File cachePath = new File(getApplication().getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        File imagePath = new File(getApplication().getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.vimalcvs.stickers_app.fileprovider", newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }
        /*Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        startActivity(intent);
        */
        /*Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("image/*");



        i.putExtra(Intent.EXTRA_STREAM, saveImageExternal(bitmap));


        i.putExtra(Intent.EXTRA_TEXT, "Shared via" + getResources().getString(R.string.app_name) + " - Download it from : https://play.google.com/store/apps/details?id="+getPackageName() );
        startActivity(Intent.createChooser(i, "Shared via" + getResources().getString(R.string.app_name) ));
*/

       /* String shareBody =stickerPack.name +"\n\n"+getResources().getString(R.string.download_pack_from)+"\n"+ Config.API_URL.replace("api","share")+ stickerPack.identifier +".html";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT,  getString(R.string.app_name));
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name)));*/
    }
    public void downloadTryImage(){
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(stickerPack.trayImageUrl)
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                        /*Bitmap bitmap1 = Bitmap.createBitmap(96, 96, Bitmap.Config.ARGB_8888);
                                        Matrix matrix = new Matrix();
                                        Canvas canvas = new Canvas(bitmap1);
                                        canvas.drawColor(Color.TRANSPARENT);
                                        matrix.postTranslate(
                                                canvas.getWidth() / 2 - resource.getWidth() / 2,
                                                canvas.getHeight() / 2 - resource.getHeight() / 2
                                        );
                                        canvas.drawBitmap(resource, matrix, null);*/
                        int width = 96; // - Dimension in pixels
                        int height= 96;  // - Dimension in pixels
                        Bitmap bitmap1 =  Bitmap.createScaledBitmap(resource, width, height, false);
                        SaveTryImage(bitmap1,stickerPack.trayImageFile,stickerPack.identifier);
                        downloadStickers();
                        return false;
                    }
                })
                .submit();
    }
    public  void downloadStickers(){
        ArrayList<StickerPack> stickerPacks =  Hawk.get("whatsapp_sticker_packs",new ArrayList<StickerPack>());
        if(stickerPacks==null){
            stickerPacks = new ArrayList<>();
        }else {

        }
        for (int i = 0; i < stickerPacks.size(); i++) {
            if (stickerPacks.get(i).identifier.equals(stickerPack.identifier)){
                stickerPacks.remove(i);
                Log.e("PACKSTICKER","DELETED");
                i--;
            }
        }
        stickerPacks.add(stickerPack);
        Hawk.put("whatsapp_sticker_packs", stickerPacks);
        for (int i = 0; i < stickerPacks.size(); i++) {
            Log.e("PACKSTICKER",stickerPacks.get(i).identifier + " / " + stickerPacks.get(i).name);
        }
        Log.d("adapter", "onClick: " + stickerPack.getStickers().size());
        Log.d("adapter", "URLTRAY: " + stickerPack.trayImageUrl);
        ((Activity) this).runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (final Sticker s : stickerPack.getStickers()) {
                            Log.d("adapter", "onClick: " + s.imageFileUrl);
                            Glide.with(getApplicationContext())
                                    .asBitmap()
                                    .apply(new RequestOptions().override(512, 512))
                                    .load(s.imageFileUrl)
                                    .addListener(new RequestListener<Bitmap>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                           /* Bitmap bitmap1 = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
                                            Matrix matrix = new Matrix();
                                            Canvas canvas = new Canvas(bitmap1);
                                            canvas.drawColor(Color.TRANSPARENT);
                                            matrix.postTranslate(
                                                    canvas.getWidth() / 2 - resource.getWidth() / 2,
                                                    canvas.getHeight() / 2 - resource.getHeight() / 2
                                            );
                                            canvas.drawBitmap(resource, matrix, null);*/
                                            int width = 512; // - Dimension in pixels
                                            int height= 512;  // - Dimension in pixels
                                            Bitmap bitmap1 =  Bitmap.createScaledBitmap(resource, width, height, false);
                                            SaveImage(bitmap1, s.imageFileName, stickerPack.identifier);
                                            return true;

                                        }
                                    }).submit();
                        }


                    }
                }
        );
    }


    public static void SaveImage(Bitmap finalBitmap, String name, String identifier) {

        String root = mainpath + "/" + identifier;
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = name;
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.WEBP, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SaveTryImage(Bitmap finalBitmap, String name, String identifier) {

        String root = mainpath + "/" + identifier;
        File myDir = new File(root + "/" + "try");
        myDir.mkdirs();
        String fname = name.replace(".png","").replace(" ","_") + ".png";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 40, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void NewAddPack() {
        linear_layout_add_to_whatsapp.setVisibility(View.GONE);
        linear_layout_progress.setVisibility(View.VISIBLE);
        progress = 0;
        new DownloadTryImageFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    private  int progress =  0;

    class DownloadOneStickerFileFromURL extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {

            try {

                Log.d("adapter", "onClick: " + stickerPack.getStickers().size());
                Log.d("adapter", "URLTRAY: " + stickerPack.trayImageUrl);
                Sticker s =  stickerPack.getStickers().get(Integer.parseInt(f_url[0]));

                System.out.println("Downloading");
                URL url = new URL(s.imageFileUrl);

                URLConnection conection = url.openConnection();
                conection.connect();

                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                Bitmap resource =  BitmapFactory.decodeStream(input);
                    /*int width = 512; // - Dimension in pixels
                    int height= 512;  // - Dimension in pixels
                    Bitmap bitmap1 =  Bitmap.createScaledBitmap(resource, width, height, false);*/
                Bitmap bitmap1 = scaleBitmap(resource,512,512);
                SaveImage(bitmap1, s.imageFileName, stickerPack.identifier);
                Log.e("PACKSTICKER",s.imageFileName);
                Log.e("PACKSTICKER",s.imageFileUrl);

                progress++;
                publishProgress(""+(int)((progress*100)/stickerPack.getStickers().size()));


            } catch (Exception e) {

            }
            return null;
        }
        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            progress_bar_pack.setProgress(Integer.parseInt(progress[0]));
        }
        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            if (progress==stickerPack.getStickers().size()+1){
                linear_layout_add_to_whatsapp.setVisibility(View.VISIBLE);
                linear_layout_progress.setVisibility(View.GONE);
                Addtowhatsapp();
            }
        }
    }
    class DownloadTryImageFileFromURL extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {

            try {
                System.out.println("Downloading Try Image");
                URL urltry = new URL(stickerPack.trayImageUrl);

                URLConnection conectiontry = urltry.openConnection();
                conectiontry.connect();

                InputStream input_try = new BufferedInputStream(urltry.openStream(), 8192);

                Bitmap resource_try =  BitmapFactory.decodeStream(input_try);
                int width_try = 96; // - Dimension in pixels
                int height_try= 96;  // - Dimension in pixels
                Bitmap bitmap_try =  Bitmap.createScaledBitmap(resource_try, width_try, height_try, false);
                SaveTryImage(bitmap_try,stickerPack.trayImageFile,stickerPack.identifier);
                System.out.println("Try Image has been downloaded");
                progress++;
                publishProgress(""+(int)((progress*100)/stickerPack.getStickers().size()));

            } catch (Exception e) {

            }
            return null;
        }
        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            progress_bar_pack.setProgress(Integer.parseInt(progress[0]));
        }
        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {



            ArrayList<StickerPack> stickerPacks =  Hawk.get("whatsapp_sticker_packs",new ArrayList<StickerPack>());
            if(stickerPacks==null){
                stickerPacks = new ArrayList<>();
            }else {

            }
            for (int i = 0; i < stickerPacks.size(); i++) {
                if (stickerPacks.get(i).identifier.equals(stickerPack.identifier)){
                    stickerPacks.remove(i);
                    Log.e("PACKSTICKER","DELETED");
                    i--;
                }
            }
            stickerPacks.add(stickerPack);
            Hawk.put("whatsapp_sticker_packs", stickerPacks);
            for (int i = 0; i < stickerPacks.size(); i++) {
                Log.e("PACKSTICKER",stickerPacks.get(i).identifier + " / " + stickerPacks.get(i).name);
            }
            for (int i = 0; i < stickerPack.getStickers().size(); i++) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    new DownloadOneStickerFileFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,i+"");
                else
                    new DownloadOneStickerFileFromURL().execute(i+"");
            }
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            linear_layout_add_to_whatsapp.setVisibility(View.GONE);
            linear_layout_progress.setVisibility(View.VISIBLE);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {

            try {


                System.out.println("Downloading");
                URL urltry = new URL(stickerPack.trayImageUrl);

                URLConnection conectiontry = urltry.openConnection();
                conectiontry.connect();

                InputStream input_try = new BufferedInputStream(urltry.openStream(), 8192);

                Bitmap resource_try =  BitmapFactory.decodeStream(input_try);
                int width_try = 96; // - Dimension in pixels
                int height_try= 96;  // - Dimension in pixels
                Bitmap bitmap_try =  Bitmap.createScaledBitmap(resource_try, width_try, height_try, false);

                SaveTryImage(bitmap_try,stickerPack.trayImageFile,stickerPack.identifier);
                ArrayList<StickerPack> stickerPacks =  Hawk.get("whatsapp_sticker_packs",new ArrayList<StickerPack>());
                if(stickerPacks==null){
                    stickerPacks = new ArrayList<>();
                }else {

                }
                for (int i = 0; i < stickerPacks.size(); i++) {
                    if (stickerPacks.get(i).identifier.equals(stickerPack.identifier)){
                        stickerPacks.remove(i);
                        Log.e("PACKSTICKER","DELETED");
                        i--;
                    }
                }
                stickerPacks.add(stickerPack);
                Hawk.put("whatsapp_sticker_packs", stickerPacks);
                for (int i = 0; i < stickerPacks.size(); i++) {
                    Log.e("PACKSTICKER",stickerPacks.get(i).identifier + " / " + stickerPacks.get(i).name);
                }
                Log.d("adapter", "onClick: " + stickerPack.getStickers().size());
                Log.d("adapter", "URLTRAY: " + stickerPack.trayImageUrl);
                int progress =  0;
                for (final Sticker s : stickerPack.getStickers()) {

                    System.out.println("Downloading");
                    URL url = new URL(s.imageFileUrl);

                    URLConnection conection = url.openConnection();
                    conection.connect();

                    InputStream input = new BufferedInputStream(url.openStream(), 8192);

                    Bitmap resource =  BitmapFactory.decodeStream(input);
                    /*int width = 512; // - Dimension in pixels
                    int height= 512;  // - Dimension in pixels
                    Bitmap bitmap1 =  Bitmap.createScaledBitmap(resource, width, height, false);*/
                    Bitmap bitmap1 = scaleBitmap(resource,512,512);
                    SaveImage(bitmap1, s.imageFileName, stickerPack.identifier);
                    Log.e("PACKSTICKER",s.imageFileName);
                    Log.e("PACKSTICKER",s.imageFileUrl);

                    progress++;
                    publishProgress(""+(int)((progress*100)/stickerPack.getStickers().size()));

                }
            } catch (Exception e) {

            }
            return null;
        }
        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            progress_bar_pack.setProgress(Integer.parseInt(progress[0]));
        }
        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            linear_layout_add_to_whatsapp.setVisibility(View.VISIBLE);
            linear_layout_progress.setVisibility(View.GONE);
            Addtowhatsapp();
        }
    }
    public void initRewarded() {

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                if (autoDisplay){
                    autoDisplay = false;
                    mRewardedVideoAd.show();
                }
                Log.d("Rewarded","onRewardedVideoAdLoaded ");

            }

            @Override
            public void onRewardedVideoAdOpened() {
                Log.d("Rewarded","onRewardedVideoAdOpened ");
            }

            @Override
            public void onRewardedVideoStarted() {
                Log.d("Rewarded","onRewardedVideoStarted ");

            }

            @Override
            public void onRewardedVideoCompleted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                loadRewardedVideoAd();
                Log.d("Rewarded","onRewardedVideoAdClosed ");

            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                stickerPack.premium =  "false";
                dialog.dismiss();
                Toasty.success(getApplicationContext(),"Now you can use this premium wallpaper for free").show();
                Log.d("Rewarded","onRewarded ");

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                Log.d("Rewarded","onRewardedVideoAdLeftApplication ");

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Log.d("Rewarded","onRewardedVideoAdFailedToLoad "+ i);
            }
        });

    }
    public void loadRewardedVideoAd() {
        PrefManager     prefManager= new PrefManager(getApplicationContext());
        mRewardedVideoAd.loadAd(prefManager.getString("ADMIN_REWARDED_ADMOB_ID"),
                new AdRequest.Builder().build());
    }

    public void showDialog(){
        this.dialog = new Dialog(this,R.style.Theme_Dialog);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        final   PrefManager prf= new PrefManager(getApplicationContext());
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_subscribe);

        final TextView text_view_watch_ads=(TextView) dialog.findViewById(R.id.text_view_watch_ads);
        text_view_watch_ads.setText("WATCH AD TO DOWNLOAD");

        RelativeLayout relative_layout_watch_ads=(RelativeLayout) dialog.findViewById(R.id.relative_layout_watch_ads);
        relative_layout_watch_ads.setVisibility(View.VISIBLE);
        relative_layout_watch_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRewardedVideoAd.isLoaded()){
                    mRewardedVideoAd.show();
                }else{
                    autoDisplay =  true;
                    loadRewardedVideoAd();
                    text_view_watch_ads.setText("SHOW LOADING.");
                }
            }
        });

        TextView text_view_go_pro=(TextView) dialog.findViewById(R.id.text_view_go_pro);
        text_view_go_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.subscribe(StickerDetailsActivity.this, Config.SUBSCRIPTION_ID);
            }
        });
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    dialog.dismiss();
                }
                return true;
            }
        });
        dialog.show();
        DialogOpened=true;

    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConn);
        mRewardedVideoAd.destroy(this);
    }
    public boolean checkSUBSCRIBED(){
        PrefManager prefManager= new PrefManager(getApplicationContext());
        if (!prefManager.getString("SUBSCRIBED").equals("TRUE")) {
            return false;
        }
        return true;
    }

    public void showAdsBanner() {
            if (!checkSUBSCRIBED()) {
                PrefManager prefManager= new PrefManager(getApplicationContext());
                if (prefManager.getString("ADMIN_BANNER_TYPE").equals("FACEBOOK")){
                    showFbBanner();
                }
                if (prefManager.getString("ADMIN_BANNER_TYPE").equals("ADMOB")){
                    showAdmobBanner();
                }
                if (prefManager.getString("ADMIN_BANNER_TYPE").equals("BOTH")) {
                    if (prefManager.getString("Banner_Ads_display").equals("FACEBOOK")) {
                        prefManager.setString("Banner_Ads_display", "ADMOB");
                        showAdmobBanner();
                    } else {
                        prefManager.setString("Banner_Ads_display", "FACEBOOK");
                        showFbBanner();
                    }
                }
            }

    }
    public void showAdmobBanner(){
        PrefManager prefManager= new PrefManager(getApplicationContext());
        LinearLayout linear_layout_ads =  (LinearLayout) findViewById(R.id.linear_layout_ads);
        final AdView mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId(prefManager.getString("ADMIN_BANNER_ADMOB_ID"));
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        linear_layout_ads.addView(mAdView);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }
        });
    }
    public void showFbBanner(){
        PrefManager prefManager= new PrefManager(getApplicationContext());
        LinearLayout linear_layout_ads =  (LinearLayout) findViewById(R.id.linear_layout_ads);
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(this, prefManager.getString("ADMIN_BANNER_FACEBOOK_ID"), com.facebook.ads.AdSize.BANNER_HEIGHT_90);
        linear_layout_ads.addView(adView);
        adView.loadAd();

    }
    private RelativeLayout nativeBannerAdContainer;
    private LinearLayout adView;
    private NativeBannerAd nativeBannerAd;
    public void initAds(){
        PrefManager prefManager= new PrefManager(getApplicationContext());

        if (prefManager.getString("SUBSCRIBED").equals("TRUE"))
            return;
        // Instantiate a NativeBannerAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        nativeBannerAd = new NativeBannerAd(this,  prefManager.getString("ADMIN_NATIVE_BANNER_FACEBOOK_ID"));
        nativeBannerAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Race condition, load() called again before last ad was displayed
                if (nativeBannerAd == null || nativeBannerAd != ad) {
                    return;
                }
                // Inflate Native Banner Ad into Container
                inflateAd(nativeBannerAd);
                Log.d(TAG, "Native ad is loaded and ready to be displayed!");
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!");
            }
        });
        // load the ad
        nativeBannerAd.loadAd();
    }
    private void inflateAd(NativeBannerAd nativeBannerAd) {
        // Unregister last ad
        nativeBannerAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeBannerAdContainer = findViewById(R.id.native_banner_ad_container);
        LayoutInflater inflater = LayoutInflater.from(this);
        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
        adView = (LinearLayout) inflater.inflate(R.layout.item_native_banner_ad_layout, nativeBannerAdContainer, false);
        nativeBannerAdContainer.addView(adView);

        // Add the AdChoices icon (NativeBannerAdActivity.this, nativeBannerAd, true);
        RelativeLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdChoicesView adChoicesView = new AdChoicesView(getApplicationContext(),nativeBannerAd,true);
        adChoicesContainer.addView(adChoicesView, 0);

        // Create native UI using the ad metadata.
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        AdIconView nativeAdIconView = adView.findViewById(R.id.native_icon_view);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(
                nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
        nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
        sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());

        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeBannerAd.registerViewForInteraction(adView, nativeAdIconView, clickableViews);
    }

    @Override
    public void onBackPressed(){
        if (fromLoad){
            Intent intent =  new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        }else{
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        }
        return;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (fromLoad){
                    Intent intent =  new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
                }else{
                    super.onBackPressed();
                    overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
                }
                return true;
            case R.id.action_infos:
                Intent intent = new Intent(getApplicationContext(),InfosActivity.class);
                intent.putExtra("name",packApi.getName());
                intent.putExtra("publisher",packApi.getPublisher());
                intent.putExtra("publisherEmail",packApi.getPublisherEmail());
                intent.putExtra("publisherWebsite",packApi.getPublisherWebsite());
                intent.putExtra("privacyPolicyWebsite",packApi.getPrivacyPolicyWebsite());
                intent.putExtra("licenseAgreementWebsite",packApi.getLicenseAgreementWebsite());
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public  Bitmap scaleBitmap(Bitmap bitmapGived, int wantedWidth, int wantedHeight) {
        if (bitmapGived.getHeight()>bitmapGived.getWidth()){
            return scaleBitmapH(bitmapGived,wantedWidth,wantedHeight);
        }else{
            return scaleBitmapW(bitmapGived,wantedWidth,wantedHeight);

        }
    }
    public  Bitmap scaleBitmapH(Bitmap bitmapGived, int wantedWidth, int wantedHeight) {
        Bitmap resultBitmap= Bitmap.createBitmap(bitmapGived.getHeight(), bitmapGived.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(resultBitmap);

        Rect sourceRect = new Rect(0, 0, bitmapGived.getWidth(), bitmapGived.getHeight());
        Rect destinationRect = new Rect((resultBitmap.getWidth() - bitmapGived.getWidth())/2, (resultBitmap.getHeight() - bitmapGived.getHeight())/2, (resultBitmap.getWidth() + bitmapGived.getWidth())/2, bitmapGived.getHeight());
        c.drawBitmap(bitmapGived, sourceRect, destinationRect, null);
        return getFinalBitmap(resultBitmap,512,512);
    }
    public Bitmap getFinalBitmap(Bitmap bitmapGived, int wantedWidth, int wantedHeight) {
        Bitmap output = Bitmap.createBitmap(wantedWidth, wantedHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Matrix m = new Matrix();
        m.setScale((float) wantedWidth / bitmapGived.getWidth(), (float) wantedHeight / bitmapGived.getHeight());
        canvas.drawBitmap(bitmapGived, m, new Paint());

        return output;
    }

    public Bitmap scaleBitmapW(Bitmap originalImage, int width, int height) {
        Bitmap background = Bitmap.createBitmap((int)width, (int)height, Bitmap.Config.ARGB_8888);

        float originalWidth = originalImage.getWidth();
        float originalHeight = originalImage.getHeight();

        Canvas canvas = new Canvas(background);

        float scale = width / originalWidth;

        float xTranslation = 0.0f;
        float yTranslation = (height - originalHeight * scale) / 2.0f;

        Matrix transformation = new Matrix();
        transformation.postTranslate(xTranslation, yTranslation);
        transformation.preScale(scale, scale);

        Paint paint = new Paint();
        paint.setFilterBitmap(true);

        canvas.drawBitmap(originalImage, transformation, paint);

        return background;
    }
}
