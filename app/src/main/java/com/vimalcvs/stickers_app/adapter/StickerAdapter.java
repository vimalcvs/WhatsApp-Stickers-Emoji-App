package com.vimalcvs.stickers_app.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.github.vivchar.viewpagerindicator.ViewPagerIndicator;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.orhanobut.hawk.Hawk;
import com.vimalcvs.stickers_app.Manager.PrefManager;
import com.vimalcvs.stickers_app.R;
import com.vimalcvs.stickers_app.StickerPack;
import com.vimalcvs.stickers_app.entity.CategoryApi;
import com.vimalcvs.stickers_app.ui.StickerDetailsActivity;
import com.vimalcvs.stickers_app.ui.UserActivity;
import com.vimalcvs.stickers_app.api.apiClient;
import com.vimalcvs.stickers_app.api.apiRest;
import com.vimalcvs.stickers_app.entity.ApiResponse;
import com.vimalcvs.stickers_app.entity.PackApi;
import com.vimalcvs.stickers_app.entity.SlideApi;
import com.vimalcvs.stickers_app.entity.UserApi;
import com.vimalcvs.stickers_app.ui.views.ClickableViewPager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.vimalcvs.stickers_app.MainActivity.EXTRA_STICKERPACK;

public class StickerAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private InterstitialAd admobInterstitialAd;
    private com.facebook.ads.InterstitialAd facebookInterstitialAd;


    private  List<CategoryApi> categoryList = new ArrayList<>();
    public  Boolean favorite =  false;
    // lists
    List<StickerPack> StickerPack;
    private List<SlideApi> slideList= new ArrayList<>();
    private  List<UserApi> userList = new ArrayList<>();
    private Dialog dialog_progress;

    // objects
    private Activity activity;
    private SlideAdapter slide_adapter;
    private FollowAdapter followAdapter;

    private LinearLayoutManager linearLayoutManager;
    public StickerAdapter(Activity activity, ArrayList<StickerPack> StickerPack) {
        this.activity = activity;
        this.StickerPack = StickerPack;
    }
    public StickerAdapter(Activity activity, ArrayList<StickerPack> StickerPack,List<SlideApi> slideList) {
        this.activity = activity;
        this.StickerPack = StickerPack;
        this.slideList = slideList;
    }
    public StickerAdapter(Activity activity, ArrayList<StickerPack> StickerPack,List<SlideApi> slideList,List<CategoryApi> categoryList,Boolean b) {
        this.activity = activity;
        this.StickerPack = StickerPack;
        this.slideList = slideList;
        this.categoryList = categoryList;
    }
    public StickerAdapter(Activity activity, ArrayList<StickerPack> StickerPack,List<SlideApi> slideList,List<UserApi> userList){
        this.activity = activity;
        this.StickerPack = StickerPack;
        this.slideList = slideList;
        this.userList = userList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case 1: {
                View v1 = inflater.inflate(R.layout.item_pack, parent, false);
                viewHolder = new PackViewHolder(v1);
                break;
            }
            case 2: {
                View v2 = inflater.inflate(R.layout.item_slide, parent, false);
                viewHolder = new SlideHolder(v2);
                break;
            }
            case 3: {
                View v3 = inflater.inflate(R.layout.item_followings, parent, false);
                viewHolder = new FollowHolder(v3);
                break;
            }
            case 4:{
                View v5 = inflater.inflate(R.layout.item_facebook_ads, parent, false);
                viewHolder = new FacebookNativeHolder(v5);
                break;
            }
            case 5: {
                View v5 = inflater.inflate(R.layout.item_categories, parent, false);
                viewHolder = new CategoriesHolder(v5 );
                break;
            }
            case 6: {
                View v6 = inflater.inflate(R.layout.item_admob_native_ads, parent, false);
                viewHolder = new AdmobNativeHolder(v6);
                break;
            }
        }
        return viewHolder;
    }
    @Override
    public int getItemViewType(int position) {
        return StickerPack.get(position).getViewType();
    }
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder_parent, final int position) {

        switch (StickerPack.get(position).getViewType()) {
            case 1: {

                final PackViewHolder viewHolder = (PackViewHolder) holder_parent;


                viewHolder.item_pack_name.setText(StickerPack.get(position).name);
                viewHolder.item_pack_publisher.setText(StickerPack.get(position).publisher);
                viewHolder.item_pack_downloads.setText(StickerPack.get(position).downloads);
                viewHolder.item_pack_size.setText(StickerPack.get(position).size);
                viewHolder.item_pack_created.setText(StickerPack.get(position).created);
                viewHolder.item_pack_username.setText(StickerPack.get(position).username);

                if (StickerPack.get(position).premium.equals("true")) {
                    viewHolder.item_pack_premium.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.item_pack_premium.setVisibility(View.GONE);
                }
                if (StickerPack.get(position).review.equals("true")) {
                    viewHolder.item_pack_review.setVisibility(View.VISIBLE);
                    viewHolder.item_pack_delete.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.item_pack_delete.setVisibility(View.GONE);
                }

                Picasso.with(activity.getApplicationContext())
                        .load(StickerPack.get(position).userimage)
                        .into(viewHolder.pack_item_image_view_userimage);

                Picasso.with(activity.getApplicationContext())
                        .load(StickerPack.get(position).trayImageUrl)
                        .into(viewHolder.pack_try_image);
                Picasso.with(activity.getApplicationContext())
                        .load(StickerPack.get(position).getStickers().get(0).imageFileUrlThum)
                        .into(viewHolder.imone);

                Picasso.with(activity.getApplicationContext())
                        .load(StickerPack.get(position).getStickers().get(1).imageFileUrlThum)
                        .into(viewHolder.imtwo);

                Picasso.with(activity.getApplicationContext())
                        .load(StickerPack.get(position).getStickers().get(2).imageFileUrlThum)
                        .into(viewHolder.imthree);

                if (StickerPack.get(position).getStickers().size() > 3) {
                    Picasso.with(activity.getApplicationContext())
                            .load(StickerPack.get(position).getStickers().get(3).imageFileUrlThum)
                            .into(viewHolder.imfour);
                } else {
                    viewHolder.imfour.setVisibility(View.INVISIBLE);
                }
                if (StickerPack.get(position).getStickers().size() > 4) {
                    Picasso.with(activity.getApplicationContext())
                            .load(StickerPack.get(position).getStickers().get(4).imageFileUrlThum)
                            .into(viewHolder.imfive);
                } else {
                    viewHolder.imfive.setVisibility(View.INVISIBLE);
                }
                Log.e("trayImageUrl", StickerPack.get(position).trayImageUrl);
                Log.e("trayImageUrl", StickerPack.get(position).trayImageFile);

                viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent((activity), StickerDetailsActivity.class).putExtra(EXTRA_STICKERPACK, StickerPack.get(viewHolder.getAdapterPosition()));

                        PrefManager prefManager= new PrefManager(activity);

                        if(checkSUBSCRIBED()){
                            (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                        }else{
                            if( prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("ADMOB")){
                                requestAdmobInterstitial();

                                if(prefManager.getInt("ADMIN_INTERSTITIAL_CLICKS")<=prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")){
                                    if (admobInterstitialAd.isLoaded()) {
                                        prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",0);
                                        admobInterstitialAd.show();
                                        admobInterstitialAd.setAdListener(new AdListener() {
                                            @Override
                                            public void onAdClosed() {
                                                requestAdmobInterstitial();
                                                (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                                            }
                                        });
                                    }else{
                                        (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                                        requestAdmobInterstitial();
                                    }
                                }else{
                                    (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                                    prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")+1);
                                }
                            }else if(prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("FACEBOOK")){
                                requestFacebookInterstitial();
                                if(prefManager.getInt("ADMIN_INTERSTITIAL_CLICKS")<=prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")){
                                    if (facebookInterstitialAd.isAdLoaded()) {
                                        prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",0);
                                        facebookInterstitialAd.show();
                                        facebookInterstitialAd.setAdListener(new InterstitialAdListener() {
                                            @Override
                                            public void onInterstitialDisplayed(Ad ad) {
                                                Log.d("MYADSNOW","onInterstitialDisplayed");
                                            }

                                            @Override
                                            public void onInterstitialDismissed(Ad ad) {
                                                (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                                            }

                                            @Override
                                            public void onError(Ad ad, AdError adError) {
                                                Log.d("MYADSNOW","onError");

                                            }

                                            @Override
                                            public void onAdLoaded(Ad ad) {
                                                Log.d("MYADSNOW","onAdLoaded");

                                            }

                                            @Override
                                            public void onAdClicked(Ad ad) {

                                                Log.d("MYADSNOW","onAdClicked");
                                            }

                                            @Override
                                            public void onLoggingImpression(Ad ad) {
                                                Log.d("MYADSNOW","onLoggingImpression");
                                            }
                                        });
                                    }else{
                                        (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                                        requestFacebookInterstitial();
                                    }
                                }else{
                                    (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                                    prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")+1);
                                }
                            }else if(prefManager.getString("ADMIN_INTERSTITIAL_TYPE").equals("BOTH")){
                                requestAdmobInterstitial();
                                requestFacebookInterstitial();
                                if(prefManager.getInt("ADMIN_INTERSTITIAL_CLICKS")<=prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")) {
                                    if (prefManager.getString("AD_INTERSTITIAL_SHOW_TYPE").equals("ADMOB")){
                                        if (admobInterstitialAd.isLoaded()) {
                                            prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",0);
                                            prefManager.setString("AD_INTERSTITIAL_SHOW_TYPE","FACEBOOK");
                                            admobInterstitialAd.show();
                                            admobInterstitialAd.setAdListener(new AdListener(){
                                                @Override
                                                public void onAdClosed() {
                                                    super.onAdClosed();
                                                    (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                                                    requestFacebookInterstitial();
                                                }
                                            });
                                        }else{
                                            (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                                            requestFacebookInterstitial();
                                        }
                                    }else{
                                        if (facebookInterstitialAd.isAdLoaded()) {
                                            prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",0);
                                            prefManager.setString("AD_INTERSTITIAL_SHOW_TYPE","ADMOB");
                                            facebookInterstitialAd.show();
                                            facebookInterstitialAd.setAdListener(new InterstitialAdListener() {
                                                @Override
                                                public void onInterstitialDisplayed(Ad ad) {

                                                }

                                                @Override
                                                public void onInterstitialDismissed(Ad ad) {
                                                    (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                                                    activity.startActivity(intent);
                                                }

                                                @Override
                                                public void onError(Ad ad, AdError adError) {

                                                }

                                                @Override
                                                public void onAdLoaded(Ad ad) {

                                                }

                                                @Override
                                                public void onAdClicked(Ad ad) {

                                                }

                                                @Override
                                                public void onLoggingImpression(Ad ad) {

                                                }
                                            });
                                        }else{
                                            (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                                            requestFacebookInterstitial();
                                        }
                                    }
                                }else{
                                    (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                                    prefManager.setInt("ADMOB_INTERSTITIAL_COUNT_CLICKS",prefManager.getInt("ADMOB_INTERSTITIAL_COUNT_CLICKS")+1);
                                }
                            }else{
                                (activity).startActivity(intent, ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(), v.getHeight()).toBundle());
                            }
                        }
                    }
                });
                List<PackApi> favorites_list =Hawk.get("favorite");
                Boolean exist = false;
                if (favorites_list == null) {
                    favorites_list = new ArrayList<>();
                }

                for (int i = 0; i < favorites_list.size(); i++) {
                    if (favorites_list.get(i).getIdentifier().equals(StickerPack.get(position).packApi.getIdentifier())) {
                        exist = true;
                    }
                }
                if (exist){
                    viewHolder.image_view_item_pack_fav.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_black));
                }else{
                    viewHolder.image_view_item_pack_fav.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_border));

                }
                viewHolder.image_view_item_pack_fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        List<PackApi> favorites_list =Hawk.get("favorite");
                        Boolean exist = false;
                        if (favorites_list == null) {
                            favorites_list = new ArrayList<>();
                        }
                        int fav_position = -1;
                        for (int i = 0; i < favorites_list.size(); i++) {
                            if (favorites_list.get(i).getIdentifier().equals(StickerPack.get(position).packApi.getIdentifier())) {
                                exist = true;
                                fav_position = i;
                            }
                        }
                        if (exist == false) {
                            favorites_list.add(StickerPack.get(position).packApi);
                            Hawk.put("favorite",favorites_list);
                            viewHolder.image_view_item_pack_fav.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_black));

                        }else{
                            favorites_list.remove(fav_position);
                            Hawk.put("favorite",favorites_list);
                            viewHolder.image_view_item_pack_fav.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_favorite_border));
                            if (favorite) {
                                StickerPack.remove(position);
                                notifyItemRemoved(position);
                                notifyDataSetChanged();
                            }

                        }

                    }
                });
                viewHolder.image_view_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog_progress= ProgressDialog.show(activity, null,activity.getResources().getString(R.string.operation_progress), true);
                        final PrefManager prf= new PrefManager(activity.getApplicationContext());
                        String user_id = prf.getString("ID_USER");
                        String user_key = prf.getString("TOKEN_USER");
                        Retrofit retrofit = apiClient.getClient();
                        apiRest service = retrofit.create(apiRest.class);
                        Call<ApiResponse> call = service.deletePack(Integer.parseInt(user_id),user_key,Integer.parseInt(StickerPack.get(position).identifier));
                        call.enqueue(new Callback<ApiResponse>() {
                            @Override
                            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                                if(response.isSuccessful()) {
                                    if (response.body().getCode() ==  200){
                                        Toasty.success(activity,response.body().getMessage(),Toast.LENGTH_LONG).show();
                                        Intent intent  =  new Intent(activity.getApplicationContext(), UserActivity.class);
                                        intent.putExtra("id", Integer.parseInt(prf.getString("ID_USER")));
                                        intent.putExtra("image",prf.getString("IMAGE_USER").toString());
                                        intent.putExtra("name",prf.getString("NAME_USER").toString());
                                        activity.startActivity(intent);
                                        activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                                        activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                                        activity.finish();
                                    }else{
                                        Toasty.error(activity,response.body().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toasty.error(activity,activity.getResources().getString(R.string.error_server),Toast.LENGTH_LONG).show();
                                }
                                if (dialog_progress!=null){
                                    dialog_progress.dismiss();
                                }
                            }
                            @Override
                            public void onFailure(Call<ApiResponse> call, Throwable t) {
                                Toasty.error(activity,activity.getResources().getString(R.string.error_server),Toast.LENGTH_LONG).show();
                                if (dialog_progress!=null){
                                    dialog_progress.dismiss();
                                }
                            }
                        });
                    }
                });
            }
            break;
            case 2: {
                final SlideHolder holder = (SlideHolder) holder_parent;

                slide_adapter = new SlideAdapter(activity, slideList);
                holder.view_pager_slide.setAdapter(slide_adapter);
                holder.view_pager_slide.setOffscreenPageLimit(1);

                holder.view_pager_slide.setClipToPadding(false);
                holder.view_pager_slide.setPageMargin(0);
                holder.view_pager_indicator.setupWithViewPager(holder.view_pager_slide);

                holder.view_pager_slide.setCurrentItem(slideList.size() / 2);
            }
            break;
            case 3: {
                final FollowHolder holder = (FollowHolder) holder_parent;
                this.linearLayoutManager=  new LinearLayoutManager((activity.getApplicationContext()),LinearLayoutManager.HORIZONTAL,false);
                this.followAdapter =new FollowAdapter(userList,activity);
                holder.recycle_view_follow_items.setHasFixedSize(true);
                holder.recycle_view_follow_items.setAdapter(followAdapter);
                holder.recycle_view_follow_items.setLayoutManager(linearLayoutManager);
                followAdapter.notifyDataSetChanged();
                Log.v("WE ARE ONE","FollowHolder");
            }
            break;

        }

    }

    @Override
    public int getItemCount() {
        return StickerPack.size();
    }

    public class PackViewHolder extends RecyclerView.ViewHolder {

        TextView item_pack_name,
                item_pack_publisher,
                item_pack_size,
                item_pack_created,
                item_pack_downloads,
                item_pack_username
        ;
        CircularImageView pack_item_image_view_userimage;
        ImageView imone, imtwo, imthree, imfour,imfive,imsix,pack_try_image,image_view_item_pack_fav,image_view_delete;
        CardView cardView;
        RelativeLayout item_pack_premium;
        RelativeLayout item_pack_review;
        RelativeLayout item_pack_delete;

        public PackViewHolder(@NonNull View itemView) {
            super(itemView);

            item_pack_size      = itemView.findViewById(R.id.item_pack_size);
            item_pack_publisher = itemView.findViewById(R.id.item_pack_publisher);
            item_pack_name      = itemView.findViewById(R.id.item_pack_name);
            item_pack_created   = itemView.findViewById(R.id.item_pack_created);
            item_pack_downloads = itemView.findViewById(R.id.item_pack_downloads);
            item_pack_username  = itemView.findViewById(R.id.item_pack_username);

            pack_item_image_view_userimage  = itemView.findViewById(R.id.pack_item_image_view_userimage);
            item_pack_premium               = itemView.findViewById(R.id.item_pack_premium);
            item_pack_review               = itemView.findViewById(R.id.item_pack_review);
            item_pack_delete               = itemView.findViewById(R.id.item_pack_delete);
            image_view_item_pack_fav        = itemView.findViewById(R.id.image_view_item_pack_fav);

            image_view_delete = itemView.findViewById(R.id.image_view_delete);

            imone = itemView.findViewById(R.id.sticker_one);
            imtwo = itemView.findViewById(R.id.sticker_two);
            imthree = itemView.findViewById(R.id.sticker_three);
            imfour = itemView.findViewById(R.id.sticker_four);
            imfive = itemView.findViewById(R.id.sticker_five);
            imsix = itemView.findViewById(R.id.sticker_six);
            pack_try_image = itemView.findViewById(R.id.pack_try_image);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
    private class SlideHolder extends RecyclerView.ViewHolder {
        private final ViewPagerIndicator view_pager_indicator;
        private final ClickableViewPager view_pager_slide;
        public SlideHolder(View itemView) {
            super(itemView);
            this.view_pager_indicator=(ViewPagerIndicator) itemView.findViewById(R.id.view_pager_indicator);
            this.view_pager_slide=(ClickableViewPager) itemView.findViewById(R.id.view_pager_slide);


        }

    }
    public class CategoriesHolder extends RecyclerView.ViewHolder {
        private final LinearLayoutManager linearLayoutManager;
        private final CategoryAdapter categoryVideoAdapter;
        public RecyclerView recycler_view_item_categories;

        public CategoriesHolder(View view) {
            super(view);
            this.recycler_view_item_categories = (RecyclerView) itemView.findViewById(R.id.recycler_view_item_categories);
            this.linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
            this.categoryVideoAdapter = new CategoryAdapter(categoryList, activity);
            recycler_view_item_categories.setHasFixedSize(true);
            recycler_view_item_categories.setAdapter(categoryVideoAdapter);
            recycler_view_item_categories.setLayoutManager(linearLayoutManager);
        }
    }
    public static class FollowHolder extends  RecyclerView.ViewHolder {
        private final RecyclerView recycle_view_follow_items;
        public FollowHolder(View view) {
            super(view);
            recycle_view_follow_items = (RecyclerView) itemView.findViewById(R.id.recycle_view_follow_items);
        }
    }


    public class AdmobNativeHolder extends RecyclerView.ViewHolder {
        private UnifiedNativeAd nativeAd;
        private FrameLayout frameLayout;

        public AdmobNativeHolder(@NonNull View itemView) {
            super(itemView);


            PrefManager prefManager= new PrefManager(activity);

            frameLayout = (FrameLayout) itemView.findViewById(R.id.fl_adplaceholder);
            AdLoader.Builder builder = new AdLoader.Builder(activity, prefManager.getString("ADMIN_NATIVE_ADMOB_ID"));

            builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                // OnUnifiedNativeAdLoadedListener implementation.
                @Override
                public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                    // You must call destroy on old ads when you are done with them,
                    // otherwise you will have a memory leak.
                    if (nativeAd != null) {
                        nativeAd.destroy();
                    }
                    nativeAd = unifiedNativeAd;

                    UnifiedNativeAdView adView = (UnifiedNativeAdView) activity.getLayoutInflater()
                            .inflate(R.layout.ad_unified, null);
                    populateUnifiedNativeAdView(unifiedNativeAd, adView);
                    frameLayout.removeAllViews();
                    frameLayout.addView(adView);
                }

            });

            VideoOptions videoOptions = new VideoOptions.Builder()
                    .setStartMuted(true)
                    .build();

            NativeAdOptions adOptions = new NativeAdOptions.Builder()
                    .setVideoOptions(videoOptions)
                    .build();

            builder.withNativeAdOptions(adOptions);

            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {


                }
            }).build();

            adLoader.loadAd(new AdRequest.Builder().build());
        }
    }


    /**
     * Populates a {@link UnifiedNativeAdView} object with data from a given
     * {@link UnifiedNativeAd}.
     *
     * @param nativeAd the object containing the ad's assets
     * @param adView          the view to be populated
     */
    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Set the media view. Media content will be automatically populated in the media view once
        // adView.setNativeAd() is called.
        com.google.android.gms.ads.formats.MediaView mediaView = adView.findViewById(R.id.ad_media);

        mediaView.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                if (child instanceof ImageView) {
                    ImageView imageView = (ImageView) child;
                    imageView.setAdjustViewBounds(true);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {
            }
        });
        adView.setMediaView(mediaView);

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline is guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad. The SDK will populate the adView's MediaView
        // with the media content from this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {
            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    super.onVideoEnd();
                }
            });
        } else {

        }
    }


    public  class FacebookNativeHolder extends  RecyclerView.ViewHolder {
        private final String TAG = "WALLPAPERADAPTER";
        private LinearLayout nativeAdContainer;
        private LinearLayout adView;
        private NativeAd nativeAd;
        public FacebookNativeHolder(View view) {
            super(view);
            loadNativeAd(view);
        }

        private void loadNativeAd(final View view) {
            // Instantiate a NativeAd object.
            // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
            // now, while you are testing and replace it later when you have signed up.
            // While you are using this temporary code you will only get test ads and if you release
            // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
            PrefManager prefManager= new PrefManager(activity);

            nativeAd = new NativeAd(activity,prefManager.getString("ADMIN_NATIVE_FACEBOOK_ID"));
            nativeAd.setAdListener(new NativeAdListener() {
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
                    // Native ad is loaded and ready to be displayed
                    Log.d(TAG, "Native ad is loaded and ready to be displayed!");
                    // Race condition, load() called again before last ad was displayed
                    if (nativeAd == null || nativeAd != ad) {
                        return;
                    }
                   /* NativeAdViewAttributes viewAttributes = new NativeAdViewAttributes()
                            .setBackgroundColor(activity.getResources().getColor(R.color.colorPrimaryDark))
                            .setTitleTextColor(Color.WHITE)
                            .setDescriptionTextColor(Color.WHITE)
                            .setButtonColor(Color.WHITE);

                    View adView = NativeAdView.render(activity, nativeAd, NativeAdView.Type.HEIGHT_300, viewAttributes);

                    LinearLayout nativeAdContainer = (LinearLayout) view.findViewById(R.id.native_ad_container);
                    nativeAdContainer.addView(adView);*/
                    // Inflate Native Ad into Container
                    inflateAd(nativeAd,view);
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

            // Request an ad
            nativeAd.loadAd();
        }

        private void inflateAd(NativeAd nativeAd, View view) {

            nativeAd.unregisterView();

            // Add the Ad view into the ad container.
            nativeAdContainer = view.findViewById(R.id.native_ad_container);
            LayoutInflater inflater = LayoutInflater.from(activity);
            // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
            adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout_1, nativeAdContainer, false);
            nativeAdContainer.addView(adView);

            // Add the AdChoices icon
            LinearLayout adChoicesContainer = view.findViewById(R.id.ad_choices_container);
            AdChoicesView adChoicesView = new AdChoicesView(activity, nativeAd, true);
            adChoicesContainer.addView(adChoicesView, 0);

            // Create native UI using the ad metadata.
            AdIconView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
            TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
            MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
            TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
            TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
            TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
            Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

            // Set the Text.
            nativeAdTitle.setText(nativeAd.getAdvertiserName());
            nativeAdBody.setText(nativeAd.getAdBodyText());
            nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
            nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
            nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
            sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

            // Create a list of clickable views
            List<View> clickableViews = new ArrayList<>();
            clickableViews.add(nativeAdTitle);
            clickableViews.add(nativeAdCallToAction);

            // Register the Title and CTA button to listen for clicks.
            nativeAd.registerViewForInteraction(
                    adView,
                    nativeAdMedia,
                    nativeAdIcon,
                    clickableViews);
        }

    }

    private void requestFacebookInterstitial() {
        if (facebookInterstitialAd==null) {
            PrefManager prefManager= new PrefManager(activity);
            facebookInterstitialAd = new com.facebook.ads.InterstitialAd(activity, prefManager.getString("ADMIN_INTERSTITIAL_FACEBOOK_ID"));
        }
        if (!facebookInterstitialAd.isAdLoaded())
            facebookInterstitialAd.loadAd();
    }
    private void requestAdmobInterstitial() {
        if (admobInterstitialAd==null){
            PrefManager prefManager= new PrefManager(activity);
            admobInterstitialAd = new InterstitialAd(activity.getApplicationContext());
            admobInterstitialAd.setAdUnitId(prefManager.getString("ADMIN_INTERSTITIAL_ADMOB_ID"));
        }
        if (!admobInterstitialAd.isLoaded()){
            AdRequest adRequest = new AdRequest.Builder()
                    .build();
            admobInterstitialAd.loadAd(adRequest);
        }
    }
    public boolean checkSUBSCRIBED(){
        PrefManager prefManager= new PrefManager(activity);
        if (!prefManager.getString("SUBSCRIBED").equals("TRUE")) {
            return false;
        }
        return true;
    }
}
