package com.vimalcvs.stickers_app.ui;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.Constants;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.firebase.messaging.FirebaseMessaging;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.vimalcvs.stickers_app.Manager.PrefManager;
import com.vimalcvs.stickers_app.R;
import com.vimalcvs.stickers_app.config.Config;
import com.vimalcvs.stickers_app.api.apiClient;
import com.vimalcvs.stickers_app.api.apiRest;
import com.vimalcvs.stickers_app.entity.ApiResponse;
import com.vimalcvs.stickers_app.entity.CategoryApi;
import com.vimalcvs.stickers_app.ui.fragmenet.CategoriesFragment;
import com.vimalcvs.stickers_app.ui.fragmenet.FavoritesFragment;
import com.vimalcvs.stickers_app.ui.fragmenet.FollowFragment;
import com.vimalcvs.stickers_app.ui.fragmenet.HomeFragment;
import com.vimalcvs.stickers_app.ui.fragmenet.PopularFragment;
import com.squareup.picasso.Picasso;
import com.vimalcvs.stickers_app.ui.views.ScrollHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //view
    private MaterialSearchView searchView;
    private FloatingActionButton fab;
    private ViewPager viewPager;
    private Dialog dialog;
    private TextView text_view_go_pro;
    private NavigationView navigationView;
    private TextView text_view_name_nave_header;
    private CircularImageView circle_image_view_profile_nav_header;

    // Lists
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    // object
    private ViewPagerAdapter adapter;
    private FollowFragment followFragment;

    //variables
    private  Boolean FromLogin = false;
    private  Boolean DialogOpened = false;




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
            // Toast.makeText(MainActivity.this, "set null", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
            //Toast.makeText(MainActivity.this, "set Stub", Toast.LENGTH_SHORT).show();

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initView();
        initAction();
        initBuy();
        firebaseSubscribe();
        initGDPR();
        PrefManager prf= new PrefManager(getApplicationContext());

    }
    private void firebaseSubscribe() {
        FirebaseMessaging.getInstance().subscribeToTopic("StickersAppTopic")
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Retrofit retrofit = apiClient.getClient();
                        apiRest service = retrofit.create(apiRest.class);
                        String unique_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);

                        Call<ApiResponse> call = service.addDevice(unique_id);
                        call.enqueue(new Callback<ApiResponse>() {
                            @Override
                            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                                if (response.isSuccessful())
                                    Log.v("HomeActivity","Added : "+response.body().getMessage());
                            }

                            @Override
                            public void onFailure(Call<ApiResponse> call, Throwable t) {
                                Log.v("HomeActivity","onFailure : "+ t.getMessage().toString());
                            }
                        });
                    }
                });


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
                Intent intent= new Intent(HomeActivity.this,SplashActivity.class);
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
    public Boolean isSubscribe(String SUBSCRIPTION_ID_CHECK){

        if (!bp.isSubscribed(Config.SUBSCRIPTION_ID))
            return false;
        Bundle b =  getPurchases();
        if (b==null)
            return  false;
        if( b.getInt("RESPONSE_CODE") == 0){
            // Toast.makeText(this, "RESPONSE_CODE", Toast.LENGTH_SHORT).show();
            ArrayList<String> ownedSkus =
                    b.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
            ArrayList<String>  purchaseDataList =
                    b.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
            ArrayList<String>  signatureList =
                    b.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
            String continuationToken =
                    b.getString("INAPP_CONTINUATION_TOKEN");

            if(purchaseDataList == null){
                // Toast.makeText(this, "purchaseDataList null", Toast.LENGTH_SHORT).show();
                return  false;

            }
            if(purchaseDataList.size()==0){
                //  Toast.makeText(this, "purchaseDataList empty", Toast.LENGTH_SHORT).show();
                return  false;
            }
            for (int i = 0; i < purchaseDataList.size(); ++i) {
                String purchaseData = purchaseDataList.get(i);
                String signature = signatureList.get(i);
                String sku_1 = ownedSkus.get(i);
                //Long tsLong = System.currentTimeMillis()/1000;

                try {
                    JSONObject rowOne = new JSONObject(purchaseData);
                    String  productId =  rowOne.getString("productId") ;
                    // Toast.makeText(this,productId, Toast.LENGTH_SHORT).show();

                    if (productId.equals(SUBSCRIPTION_ID_CHECK)){

                        Boolean  autoRenewing =  rowOne.getBoolean("autoRenewing");
                        if (autoRenewing){
                            // Toast.makeText(this, "is autoRenewing ", Toast.LENGTH_SHORT).show();
                            return  true;
                        }else{
                            //    Toast.makeText(this, "is not autoRenewing ", Toast.LENGTH_SHORT).show();
                            Long tsLong = System.currentTimeMillis()/1000;
                            Long  purchaseTime =  rowOne.getLong("purchaseTime")/1000;
                            if (tsLong > (purchaseTime + (Config.SUBSCRIPTION_DURATION*86400)) ){
                                //   Toast.makeText(this, "is Expired ", Toast.LENGTH_SHORT).show();
                                return  false;
                            }else{
                                return  true;
                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }else{
            return false;
        }

        return  false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                final PrefManager prf = new PrefManager(getApplicationContext());
                if (prf.getString("NOT_RATE_APP").equals("TRUE")) {
                    super.onBackPressed();
                } else {
                    rateDialog(true);
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_go_pro:
                showDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
        } else if(id == R.id.login){
            Intent intent= new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            FromLogin=true;

        }else if (id == R.id.nav_help  ){
            Intent intent = new Intent(getApplicationContext(), SupportActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
        }
        else if (id == R.id.nav_policy  ){
            Intent intent = new Intent(getApplicationContext(), PolicyActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
        }else if (id == R.id.nav_share  ){
            final String appPackageName=getApplication().getPackageName();
            String shareBody = "Download "+getString(R.string.app_name)+" From :  "+"http://play.google.com/store/apps/details?id=" + appPackageName;
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT,  getString(R.string.app_name));
            startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name)));

        }else if(id == R.id.nav_rate){
            rateDialog(false);
        }else if(id ==  R.id.nav_go_pro){
            showDialog();
        }else if (id == R.id.nav_exit) {
            final PrefManager prf = new PrefManager(getApplicationContext());
            if (prf.getString("NOT_RATE_APP").equals("TRUE")) {
                super.onBackPressed();
            } else {
                rateDialog(true);
            }
        }else if (id==R.id.my_profile){
            PrefManager prf= new PrefManager(getApplicationContext());
            if (prf.getString("LOGGED").toString().equals("TRUE")){
                Intent intent  =  new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("id", Integer.parseInt(prf.getString("ID_USER")));
                intent.putExtra("image",prf.getString("IMAGE_USER").toString());
                intent.putExtra("name",prf.getString("NAME_USER").toString());
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }else{
                Intent intent= new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                FromLogin=true;
            }
        }else if (id==R.id.logout){
            logout();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initView() {
        this.searchView = (MaterialSearchView) findViewById(R.id.search_view);
        this.navigationView = (NavigationView) findViewById(R.id.nav_view);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setOffscreenPageLimit(100);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        this.followFragment = new FollowFragment();

        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new PopularFragment());
        adapter.addFragment(followFragment);
        adapter.addFragment(new CategoriesFragment());
        adapter.addFragment(new FavoritesFragment());

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        final BubbleNavigationConstraintView bubbleNavigationLinearView = findViewById(R.id.top_navigation_constraint);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bubbleNavigationLinearView.getLayoutParams();
        layoutParams.setBehavior(new ScrollHandler());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                bubbleNavigationLinearView.setCurrentActiveItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                viewPager.setCurrentItem(position, true);
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_home),
                        getResources().getColor(R.color.transparent))
                        .build()

        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_whatshot),
                        getResources().getColor(R.color.transparent))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_subscriptions),
                        getResources().getColor(R.color.transparent))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_categories),
                        getResources().getColor(R.color.transparent))

                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_favorite_black),
                        getResources().getColor(R.color.transparent))
                        .build()
        );




        PrefManager prf= new PrefManager(getApplicationContext());
        if (!prf.getString("SUBSCRIBED").equals("FALSE")) {
            navigationView.getMenu().findItem(R.id.nav_go_pro).setVisible(false);
        }
        View headerview = navigationView.getHeaderView(0);
        this.text_view_name_nave_header=(TextView) headerview.findViewById(R.id.text_view_name_nave_header);
        this.circle_image_view_profile_nav_header=(CircularImageView) headerview.findViewById(R.id.circle_image_view_profile_nav_header);

    }

    private void initAction() {
        searchView.setVoiceSearch(true);
        searchView.setCursorDrawable(R.drawable.color_cursor_white);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent  = new Intent(HomeActivity.this,SearchActivity.class);
                intent.putExtra("query",query);
                startActivity(intent);

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    PrefManager prf= new PrefManager(getApplicationContext());
                    if (prf.getString("LOGGED").toString().equals("TRUE")) {
                        Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                       startActivity(intent);
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                    }else{
                        FromLogin=true;
                        Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                }
        });
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
        this.text_view_go_pro=(TextView) dialog.findViewById(R.id.text_view_go_pro);
        text_view_go_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.subscribe(HomeActivity.this, Config.SUBSCRIPTION_ID);
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
    private Dialog rateDialog;

    public void rateDialog(final boolean close){
        this.rateDialog = new Dialog(this,R.style.Theme_Dialog);

        rateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rateDialog.setCancelable(true);
        rateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = rateDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        final   PrefManager prf= new PrefManager(getApplicationContext());
        rateDialog.setCancelable(false);
        rateDialog.setContentView(R.layout.dialog_rating_app);
        final AppCompatRatingBar AppCompatRatingBar_dialog_rating_app=(AppCompatRatingBar) rateDialog.findViewById(R.id.AppCompatRatingBar_dialog_rating_app);
        final LinearLayout linear_layout_feedback=(LinearLayout) rateDialog.findViewById(R.id.linear_layout_feedback);
        final LinearLayout linear_layout_rate=(LinearLayout) rateDialog.findViewById(R.id.linear_layout_rate);
        final Button buttun_send_feedback=(Button) rateDialog.findViewById(R.id.buttun_send_feedback);
        final Button button_later=(Button) rateDialog.findViewById(R.id.button_later);
        final Button button_never=(Button) rateDialog.findViewById(R.id.button_never);
        final Button button_cancel=(Button) rateDialog.findViewById(R.id.button_cancel);
        button_never.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prf.setString("NOT_RATE_APP", "TRUE");
                if (close)
                    finish();
            }
        });
        button_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateDialog.dismiss();
                if (close)
                    finish();
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateDialog.dismiss();
                if (close)
                    finish();
            }
        });
        final EditText edit_text_feed_back=(EditText) rateDialog.findViewById(R.id.edit_text_feed_back);
        buttun_send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prf.setString("NOT_RATE_APP", "TRUE");
                Retrofit retrofit = apiClient.getClient();
                apiRest service = retrofit.create(apiRest.class);
                Call<ApiResponse> call = service.addSupport("Application rating feedback",AppCompatRatingBar_dialog_rating_app.getRating()+" star(s) Rating".toString(),edit_text_feed_back.getText().toString());
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if(response.isSuccessful()){
                            Toasty.success(getApplicationContext(), getResources().getString(R.string.message_sended), Toast.LENGTH_SHORT).show();
                        }else{
                            Toasty.error(getApplicationContext(), getString(R.string.error_server), Toast.LENGTH_SHORT).show();
                        }
                        rateDialog.dismiss();

                        if (close)
                            finish();

                    }
                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Toasty.error(getApplicationContext(), getString(R.string.error_server), Toast.LENGTH_SHORT).show();
                        rateDialog.dismiss();

                        if (close)
                            finish();
                    }
                });
            }
        });
        AppCompatRatingBar_dialog_rating_app.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser){
                    if (rating>3){
                        final String appPackageName = getApplication().getPackageName();
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        prf.setString("NOT_RATE_APP", "TRUE");
                        rateDialog.dismiss();
                    }else{
                        linear_layout_feedback.setVisibility(View.VISIBLE);
                        linear_layout_rate.setVisibility(View.GONE);
                    }
                }else{

                }
            }
        });
        rateDialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    rateDialog.dismiss();
                    if (close)
                        finish();
                }
                return true;

            }
        });
        rateDialog.show();

    }

    public void loadCategories(){
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<CategoryApi>> call = service.AllCategories();
        call.enqueue(new Callback<List<CategoryApi>>() {
            @Override
            public void onResponse(Call<List<CategoryApi>> call, Response<List<CategoryApi>> response) {
                if(response.isSuccessful()){
                    if (response.body().size()!=0){


                    }
                }

            }
            @Override
            public void onFailure(Call<List<CategoryApi>> call, Throwable t) {
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        loadCategories();
        PrefManager prf= new PrefManager(getApplicationContext());
        Menu nav_Menu = navigationView.getMenu();

        if (prf.getString("LOGGED").toString().equals("TRUE")){
            nav_Menu.findItem(R.id.my_profile).setVisible(true);
            nav_Menu.findItem(R.id.logout).setVisible(true);
            nav_Menu.findItem(R.id.login).setVisible(false);
            text_view_name_nave_header.setText(prf.getString("NAME_USER").toString());
            Picasso.with(getApplicationContext()).load(prf.getString("IMAGE_USER").toString()).placeholder(R.drawable.profile).error(R.drawable.profile).resize(200,200).centerCrop().into(circle_image_view_profile_nav_header);
            if (prf.getString("TYPE_USER").toString().equals("google")){
            }else {
            }
        }else{
            nav_Menu.findItem(R.id.my_profile).setVisible(false);
            nav_Menu.findItem(R.id.logout).setVisible(false);
            nav_Menu.findItem(R.id.login).setVisible(true);
            text_view_name_nave_header.setText(getResources().getString(R.string.please_login));
            Picasso.with(getApplicationContext()).load(R.drawable.profile).placeholder(R.drawable.profile).error(R.drawable.profile).resize(200,200).centerCrop().into(circle_image_view_profile_nav_header);
        }
        if (FromLogin){
            followFragment.Resume();
            FromLogin = false;
        }
    }
    public void logout(){
        loadCategories();
        PrefManager prf= new PrefManager(getApplicationContext());
        prf.remove("ID_USER");
        prf.remove("SALT_USER");
        prf.remove("TOKEN_USER");
        prf.remove("NAME_USER");
        prf.remove("TYPE_USER");
        prf.remove("USERN_USER");
        prf.remove("IMAGE_USER");
        prf.remove("LOGGED");
        if (prf.getString("LOGGED").toString().equals("TRUE")){
            text_view_name_nave_header.setText(prf.getString("NAME_USER").toString());
            Picasso.with(getApplicationContext()).load(prf.getString("IMAGE_USER").toString()).placeholder(R.drawable.profile).error(R.drawable.profile).resize(200,200).centerCrop().into(circle_image_view_profile_nav_header);
            if (prf.getString("TYPE_USER").toString().equals("google")){
            }else {
            }
        }else{
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.my_profile).setVisible(false);
            nav_Menu.findItem(R.id.logout).setVisible(false);
            nav_Menu.findItem(R.id.login).setVisible(true);
            text_view_name_nave_header.setText(getResources().getString(R.string.please_login));
            Picasso.with(getApplicationContext()).load(R.drawable.profile).placeholder(R.drawable.profile).error(R.drawable.profile).resize(200,200).centerCrop().into(circle_image_view_profile_nav_header);
        }

        followFragment.Resume();

        Toast.makeText(getApplicationContext(),getString(R.string.message_logout),Toast.LENGTH_LONG).show();
    }
    public void setFromLogin(){
        this.FromLogin = true;
    }
    private void initGDPR() {
        PrefManager prefManager= new PrefManager(getApplicationContext());

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        ConsentInformation consentInformation =
                ConsentInformation.getInstance(HomeActivity.this);
//// test
/////

        String[] publisherIds = {prefManager.getString("ADMIN_PUBLISHER_ID")};
        consentInformation.requestConsentInfoUpdate(publisherIds, new
                ConsentInfoUpdateListener() {
                    @Override
                    public void onConsentInfoUpdated(ConsentStatus consentStatus) {
// User's consent status successfully updated.
                        Log.d(TAG,"onConsentInfoUpdated");
                        switch (consentStatus){
                            case PERSONALIZED:
                                Log.d(TAG,"PERSONALIZED");
                                ConsentInformation.getInstance(HomeActivity.this)
                                        .setConsentStatus(ConsentStatus.PERSONALIZED);
                                break;
                            case NON_PERSONALIZED:
                                Log.d(TAG,"NON_PERSONALIZED");
                                ConsentInformation.getInstance(HomeActivity.this)
                                        .setConsentStatus(ConsentStatus.NON_PERSONALIZED);
                                break;


                            case UNKNOWN:
                                Log.d(TAG,"UNKNOWN");
                                if
                                        (ConsentInformation.getInstance(HomeActivity.this).isRequestLocationInEeaOrUnknown
                                        ()){
                                    URL privacyUrl = null;
                                    try {
// TODO: Replace with your app's privacy policy URL.
                                        privacyUrl = new URL(Config.API_URL.replace("/api/","/privacy_policy.html"));

                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
// Handle error.

                                    }
                                    form = new ConsentForm.Builder(HomeActivity.this,
                                            privacyUrl)
                                            .withListener(new ConsentFormListener() {
                                                @Override

                                                public void onConsentFormLoaded() {
// Consent form loaded successfully.
                                                    Log.d(TAG,"onConsentFormLoaded");

                                                    showform();
                                                }

                                                @Override
                                                public void onConsentFormOpened() {
// Consent form was displayed.
                                                    Log.d(TAG,"onConsentFormOpened");

                                                }

                                                @Override
                                                public void onConsentFormClosed(

                                                        ConsentStatus consentStatus, Boolean
                                                        userPrefersAdFree) {
// Consent form was closed.
                                                    Log.d(TAG,"onConsentFormClosed");

                                                }

                                                @Override
                                                public void onConsentFormError(String

                                                                                       errorDescription) {
// Consent form error.

                                                    Log.d(TAG,"onConsentFormError");
                                                    Log.d(TAG,errorDescription);
                                                }
                                            })

                                            .withPersonalizedAdsOption()
                                            .withNonPersonalizedAdsOption()

                                            .build();
                                    form.load();
                                } else {
                                    Log.d(TAG,"PERSONALIZED else");
                                    ConsentInformation.getInstance(HomeActivity.this)
                                            .setConsentStatus(ConsentStatus.PERSONALIZED);
                                }
                                break;


                            default:
                                break;
                        }
                    }
                    @Override
                    public void onFailedToUpdateConsentInfo(String errorDescription) {
// User's consent status failed to update.
                        Log.d(TAG,"onFailedToUpdateConsentInfo");
                        Log.d(TAG,errorDescription);
                    }
                });
    }
    private static final String TAG ="MainActivity ----- : " ;
    ConsentForm form;
    private void showform(){
        if (form!=null){
            Log.d(TAG,"show ok");
            form.show();
        }
    }
}
