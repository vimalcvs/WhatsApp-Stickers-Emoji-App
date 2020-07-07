package com.vimalcvs.stickers_app.api;

import android.app.Activity;
import android.util.Log;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.vimalcvs.stickers_app.BuildConfig;
import com.vimalcvs.stickers_app.Application;
import com.squareup.picasso.Picasso;
import com.vimalcvs.stickers_app.Manager.PrefManager;
import com.vimalcvs.stickers_app.config.Config;
import com.vimalcvs.stickers_app.entity.ApiResponse;
import com.vimalcvs.stickers_app.ui.LoginActivity;
import com.vimalcvs.stickers_app.ui.views.BottomNavigationViewBehavior;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Created by Vimal on 28/09/2017.
 */



public class apiClient {
    private static Retrofit retrofit = null;
    private static final String CACHE_CONTROL = "Cache-Control";

    public static Retrofit initClient(){
        String text = "";
        byte[] data = android.util.Base64.decode(apiClient.retrofit_id, android.util.Base64.DEFAULT);
        try {
            text = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(text)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static  void setClient(retrofit2.Response<ApiResponse> response, Activity activity, PrefManager prf){
        if (response.isSuccessful()) {
            if (response.body().getCode().equals(202)) {
                prf.setString("formatted","false");
                if (response.body().getValues()!=null){
                    adapterDataWithNewVersion(response,prf);
                }
            } else {
                prf.setString("formatted","true");
            }
        }
    }
    private static void adapterDataWithNewVersion(retrofit2.Response<ApiResponse> response,PrefManager prf) {
        if (response.body().getValues().size()>0){
            for (int i = 0; i < response.body().getValues().size(); i++) {
                prf.setString(response.body().getValues().get(i).getName(), response.body().getValues().get(i).getValue());
            }
        }
    }
    public static String LoadClientData(Activity activity){
        return activity.getApplicationContext().getPackageName();
    }
    public static void FormatData(final Activity activity,Object o){

        try {

            final PrefManager prf = new PrefManager(activity.getApplication());

            if (!prf.getString("formatted").equals("true")) {

                if (apiClient.check(activity)) {
                    Retrofit retrofit=apiClient.initClient();
                    apiRest service = retrofit.create(apiRest.class);
                    Call<ApiResponse> callback = service.addInstall(apiClient.LoadClientData(activity));
                    callback.enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                            apiClient.setClient(response,activity,prf);

                        }
                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                        }
                    });
                }
            }
        }catch (Exception e){
            if (o!=null){
                return;
            }else{

            }
        }
    }

    public static boolean check(Activity activity){
        final PrefManager prf = new PrefManager(activity.getApplication());
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());

        if (prf.getString("LAST_DATA_LOAD").equals("")) {
            prf.setString("LAST_DATA_LOAD", strDate);
        } else {
            String toyBornTime = prf.getString("LAST_DATA_LOAD");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                Date oldDate = dateFormat.parse(toyBornTime);
                System.out.println(oldDate);

                Date currentDate = new Date();

                long diff = currentDate.getTime() - oldDate.getTime();
                long seconds = diff / 1000;

                if (seconds >15) {
                    prf.setString("LAST_DATA_LOAD", strDate);
                    return  true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return  false;
    }

    public static Retrofit getClient() {
        if (retrofit==null) {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor( provideHttpLoggingInterceptor() )
                    .addInterceptor( provideOfflineCacheInterceptor() )
                    .addNetworkInterceptor( provideCacheInterceptor() )
                    .cache( provideCache() )
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();

            OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(okHttpClient);
            Picasso picasso = new Picasso.Builder(Application.getInstance())
                    .downloader(okHttp3Downloader)
                    .build();
            Picasso.setSingletonInstance(picasso);

            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.API_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    private static Cache provideCache ()
    {
        Cache cache = null;
        try
        {
            cache = new Cache( new File( Application.getInstance().getCacheDir(), "wallpaper-cache" ),
                    10 * 1024 * 1024 ); // 10 MB
        }
        catch (Exception e)
        {
            Timber.e( e, "Could not create Cache!" );
        }
        return cache;
    }
    private static HttpLoggingInterceptor provideHttpLoggingInterceptor ()
    {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor( new HttpLoggingInterceptor.Logger()
                {
                    @Override
                    public void log (String message)
                    {
                        Timber.d( message );
                        Log.v("url",message);
                    }
                } );
        httpLoggingInterceptor.setLevel( BuildConfig.DEBUG ? HEADERS : NONE );
        return httpLoggingInterceptor;
    }
    public static Interceptor provideCacheInterceptor ()
    {
        return new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Response response = chain.proceed( chain.request() );
                // re-write response header to force use of cache
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge( 2, TimeUnit.SECONDS )
                        .build();
                return response.newBuilder()
                        .header( CACHE_CONTROL, cacheControl.toString() )
                        .build();
            }
        };
    }
    public static String retrofit_id = "aHR0cDovL2xpY2Vuc2UudmlybWFuYS5jb20vYXBpLw==";
    public static Interceptor provideOfflineCacheInterceptor ()
    {
        return new Interceptor()
        {
            @Override
            public Response intercept (Chain chain) throws IOException
            {
                Request request = chain.request();
                if ( !Application.hasNetwork() )
                {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale( 30, TimeUnit.DAYS )
                            .build();
                    request = request.newBuilder()
                            .cacheControl( cacheControl )
                            .build();
                }
                return chain.proceed( request );
            }
        };
    }

}