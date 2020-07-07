package com.vimalcvs.stickers_app.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.vimalcvs.stickers_app.Manager.PrefManager;
import com.vimalcvs.stickers_app.R;
import com.vimalcvs.stickers_app.adapter.UserAdapter;
import com.vimalcvs.stickers_app.api.apiClient;
import com.vimalcvs.stickers_app.api.apiRest;
import com.vimalcvs.stickers_app.entity.ApiResponse;
import com.vimalcvs.stickers_app.entity.CategoryApi;
import com.vimalcvs.stickers_app.entity.PackApi;
import com.vimalcvs.stickers_app.entity.UserApi;
import com.squareup.picasso.Picasso;
import com.vimalcvs.stickers_app.ui.fragmenet.UserFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UserActivity extends AppCompatActivity {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    private Toolbar toolbar;
    private int id;
    private String name;
    private String image;
    private String facebook;
    private String email;
    private String instagram;
    private String twitter;

    private CircularImageView image_view_profile_user_activity;
    private TextView text_view_profile_user_activity;
    private TextView text_view_followers_count_user_activity;
    private TextView text_view_following_count_activity_user;
    private LinearLayout linear_layout_page_error;
    private RelativeLayout relative_layout_load_more;
    private Button button_follow_user_activity;
    private List<PackApi> packApiList = new ArrayList<>();
    private Integer page = 0;
    private LinearLayout linear_layout_followers;
    private LinearLayout linear_layout_following;

    private AlertDialog.Builder builderFollowing;
    private List<UserApi> followings = new ArrayList<>();

    private AlertDialog.Builder builderFollowers;
    private List<UserApi> followers = new ArrayList<>();
    private ProgressDialog loading_progress;


    private boolean trusted;
    private ViewPager viewPager;

    private ImageView image_view_activity_user_email;
    private ImageView image_view_activity_user_instagram;
    private ImageView image_view_activity_user_twitter;
    private ImageView image_view_activity_user_facebook;
    private Button button_edit_user_activity;
    private CoordinatorLayout coordinatorLayout;
    private TextView text_view_ringtone_count_activity_user;
    private Button button_try_again;
    private ImageView image_view_ringtone_activity_trusted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();


        PrefManager prf = new PrefManager(getApplicationContext());

        this.id = bundle.getInt("id");
        this.name = bundle.getString("name");
        this.image = bundle.getString("image");
        this.trusted = bundle.getBoolean("trusted");

        getSection();
        initView();
        initAction();
        initUser();


    }

    public void getSection() {
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<CategoryApi>> call = service.AllCategories();
        call.enqueue(new Callback<List<CategoryApi>>() {
            @Override
            public void onResponse(Call<List<CategoryApi>> call, Response<List<CategoryApi>> response) {
            }

            @Override
            public void onFailure(Call<List<CategoryApi>> call, Throwable t) {
            }
        });
    }

    private void initUser() {
        text_view_profile_user_activity.setText(name);
        if (!image.isEmpty()) {
            Picasso.with(getApplicationContext()).load(image).error(R.drawable.profile).placeholder(R.drawable.profile).into(this.image_view_profile_user_activity);
        } else {
            Picasso.with(getApplicationContext()).load(R.drawable.profile).error(R.drawable.profile).placeholder(R.drawable.profile).into(this.image_view_profile_user_activity);
        }
        PrefManager prf = new PrefManager(getApplicationContext());
        if (prf.getString("LOGGED").toString().equals("TRUE")) {
            Integer me = Integer.parseInt(prf.getString("ID_USER"));
            if (id == me) {
                button_follow_user_activity.setVisibility(View.GONE);
                button_edit_user_activity.setVisibility(View.VISIBLE);
            } else {
                button_follow_user_activity.setVisibility(View.VISIBLE);
                button_edit_user_activity.setVisibility(View.GONE);
            }
        }
        if (trusted) {
            image_view_ringtone_activity_trusted.setVisibility(View.VISIBLE);
        } else {
            image_view_ringtone_activity_trusted.setVisibility(View.GONE);

        }
        getUser();
    }

    private void initView() {


        setContentView(R.layout.activity_user);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        this.coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        this.button_try_again = (Button) findViewById(R.id.button_try_again);
        this.image_view_profile_user_activity = (CircularImageView) findViewById(R.id.image_view_profile_user_activity);
        this.text_view_profile_user_activity = (TextView) findViewById(R.id.text_view_profile_user_activity);
        this.text_view_followers_count_user_activity = (TextView) findViewById(R.id.text_view_followers_count_user_activity);
        this.text_view_following_count_activity_user = (TextView) findViewById(R.id.text_view_following_count_activity_user);
        this.linear_layout_page_error = (LinearLayout) findViewById(R.id.linear_layout_page_error);
        this.relative_layout_load_more = (RelativeLayout) findViewById(R.id.relative_layout_load_more);
        this.button_follow_user_activity = (Button) findViewById(R.id.button_follow_user_activity);
        this.linear_layout_followers = (LinearLayout) findViewById(R.id.linear_layout_followers);
        this.linear_layout_following = (LinearLayout) findViewById(R.id.linear_layout_following);
        this.image_view_activity_user_email = (ImageView) findViewById(R.id.image_view_activity_user_email);
        this.image_view_activity_user_instagram = (ImageView) findViewById(R.id.image_view_activity_user_instagram);
        this.image_view_activity_user_twitter = (ImageView) findViewById(R.id.image_view_activity_user_twitter);
        this.image_view_activity_user_facebook = (ImageView) findViewById(R.id.image_view_activity_user_facebook);
        this.button_edit_user_activity = (Button) findViewById(R.id.button_edit_user_activity);
        this.text_view_ringtone_count_activity_user = (TextView) findViewById(R.id.text_view_ringtone_count_activity_user);
        this.image_view_ringtone_activity_trusted = (ImageView) findViewById(R.id.image_view_ringtone_activity_trusted);
        viewPager = (ViewPager) findViewById(R.id.main_view_pager);


        setupViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


        Bundle bundle_video = new Bundle();
        bundle_video.putInt("user", id);
        bundle_video.putString("type", "video");
        UserFragment userFragment = new UserFragment();
        userFragment.setArguments(bundle_video);


        adapter.addFragment(userFragment, "Videos");

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void initAction() {
        this.image_view_activity_user_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", email, null));
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
        this.image_view_activity_user_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(facebook));
                startActivity(i);
            }
        });
        this.image_view_activity_user_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(instagram));
                startActivity(i);
            }
        });
        this.image_view_activity_user_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(twitter));
                startActivity(i);
            }
        });
        this.linear_layout_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                builderFollowing = new AlertDialog.Builder(UserActivity.this);
                builderFollowing.setIcon(R.drawable.ic_follow);
                builderFollowing.setTitle("Following");

                View view = (View) getLayoutInflater().inflate(R.layout.user_row, null);

                ListView listView = (ListView) view.findViewById(R.id.user_rows);
                listView.setAdapter(new UserAdapter(followings, UserActivity.this));
                builderFollowing.setView(view);
                builderFollowing.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderFollowing.show();
            }
        });
        this.linear_layout_followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFollowers();

            }
        });
        this.linear_layout_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFollowings();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.button_follow_user_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                follow();
            }
        });
        this.button_edit_user_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, EditActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("image", image);
                startActivity(intent);
            }
        });
    }

    public void loadFollowings() {
        loading_progress = ProgressDialog.show(this, null, getResources().getString(R.string.operation_progress), true);

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<UserApi>> call = service.getFollowing(id);
        call.enqueue(new Callback<List<UserApi>>() {
            @Override
            public void onResponse(Call<List<UserApi>> call, Response<List<UserApi>> response) {
                if (response.isSuccessful()) {
                    builderFollowing = new AlertDialog.Builder(UserActivity.this);
                    builderFollowing.setIcon(R.drawable.ic_follow);
                    builderFollowing.setTitle("Followings");
                    View view = (View) getLayoutInflater().inflate(R.layout.user_row, null);
                    ListView listView = (ListView) view.findViewById(R.id.user_rows);
                    listView.setAdapter(new UserAdapter(response.body(), UserActivity.this));
                    builderFollowing.setView(view);
                    builderFollowing.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builderFollowing.show();
                }
                loading_progress.dismiss();
            }

            @Override
            public void onFailure(Call<List<UserApi>> call, Throwable t) {
                loading_progress.dismiss();
            }
        });

    }

    public void loadFollowers() {
        loading_progress = ProgressDialog.show(this, null, getResources().getString(R.string.operation_progress), true);

        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<UserApi>> call = service.getFollowers(id);
        call.enqueue(new Callback<List<UserApi>>() {
            @Override
            public void onResponse(Call<List<UserApi>> call, Response<List<UserApi>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < followers.size(); i++) {
                        followers.add(response.body().get(i));
                    }
                    builderFollowers = new AlertDialog.Builder(UserActivity.this);
                    builderFollowers.setIcon(R.drawable.ic_follow);
                    builderFollowers.setTitle("Followers");
                    View view = (View) getLayoutInflater().inflate(R.layout.user_row, null);
                    ListView listView = (ListView) view.findViewById(R.id.user_rows);
                    listView.setAdapter(new UserAdapter(response.body(), UserActivity.this));
                    builderFollowers.setView(view);
                    builderFollowers.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builderFollowers.show();
                }
                loading_progress.dismiss();
            }

            @Override
            public void onFailure(Call<List<UserApi>> call, Throwable t) {
                loading_progress.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        return;
    }

    public void follow() {

        PrefManager prf = new PrefManager(getApplicationContext());
        if (prf.getString("LOGGED").toString().equals("TRUE")) {
            button_follow_user_activity.setText(getResources().getString(R.string.loading));
            button_follow_user_activity.setEnabled(false);
            String follower = prf.getString("ID_USER");
            String key = prf.getString("TOKEN_USER");
            Retrofit retrofit = apiClient.getClient();
            apiRest service = retrofit.create(apiRest.class);
            Call<ApiResponse> call = service.follow(id, Integer.parseInt(follower), key);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getCode().equals(200)) {
                            button_follow_user_activity.setText("UnFollow");
                            text_view_followers_count_user_activity.setText((Integer.parseInt(text_view_followers_count_user_activity.getText().toString()) + 1) + "");
                        } else if (response.body().getCode().equals(202)) {
                            button_follow_user_activity.setText("Follow");
                            text_view_followers_count_user_activity.setText((Integer.parseInt(text_view_followers_count_user_activity.getText().toString()) - 1) + "");

                        }
                    }
                    button_follow_user_activity.setEnabled(true);

                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    button_follow_user_activity.setEnabled(true);
                }
            });
        } else {
            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void getUser() {
        PrefManager prf = new PrefManager(getApplicationContext());
        Integer follower = -1;
        if (prf.getString("LOGGED").toString().equals("TRUE")) {
            button_follow_user_activity.setEnabled(false);
            follower = Integer.parseInt(prf.getString("ID_USER"));
        }
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<ApiResponse> call = service.getUser(id, follower);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {

                    for (int i = 0; i < response.body().getValues().size(); i++) {
                        if (response.body().getValues().get(i).getName().equals("followers")) {
                            text_view_followers_count_user_activity.setText(format(Integer.parseInt(response.body().getValues().get(i).getValue())));
                        }
                        if (response.body().getValues().get(i).getName().equals("followings")) {
                            text_view_following_count_activity_user.setText(format(Integer.parseInt(response.body().getValues().get(i).getValue())));
                        }
                        if (response.body().getValues().get(i).getName().equals("packs")) {
                            text_view_ringtone_count_activity_user.setText(format(Integer.parseInt(response.body().getValues().get(i).getValue())));
                        }
                        if (response.body().getValues().get(i).getName().equals("facebook")) {
                            facebook = response.body().getValues().get(i).getValue();
                            if (facebook != null) {
                                if (!facebook.isEmpty()) {
                                    if (facebook.startsWith("http://") || facebook.startsWith("https://")) {
                                        image_view_activity_user_facebook.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                        if (response.body().getValues().get(i).getName().equals("twitter")) {
                            twitter = response.body().getValues().get(i).getValue();
                            if (twitter != null) {

                                if (!twitter.isEmpty()) {
                                    if (twitter.startsWith("http://") || twitter.startsWith("https://")) {
                                        image_view_activity_user_twitter.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                        if (response.body().getValues().get(i).getName().equals("instagram")) {

                            instagram = response.body().getValues().get(i).getValue();
                            if (instagram != null) {

                                if (!instagram.isEmpty()) {
                                    if (instagram.startsWith("http://") || instagram.startsWith("https://")) {

                                        image_view_activity_user_instagram.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                        if (response.body().getValues().get(i).getName().equals("email")) {
                            email = response.body().getValues().get(i).getValue();
                            if (email != null) {
                                if (!email.isEmpty()) {
                                    image_view_activity_user_email.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                        if (response.body().getValues().get(i).getName().equals("follow")) {
                            if (response.body().getValues().get(i).getValue().equals("true"))
                                button_follow_user_activity.setText("UnFollow");
                            else
                                button_follow_user_activity.setText("Follow");
                        }
                        if (response.body().getValues().get(i).getName().equals("follow")) {
                            if (response.body().getValues().get(i).getValue().equals("true"))
                                button_follow_user_activity.setText("UnFollow");
                            else
                                button_follow_user_activity.setText("Follow");
                        }
                        if (response.body().getValues().get(i).getName().equals("trusted")) {
                            if (response.body().getValues().get(i).getValue().equals("true"))
                                image_view_ringtone_activity_trusted.setVisibility(View.VISIBLE);
                            else
                                image_view_ringtone_activity_trusted.setVisibility(View.GONE);
                        }
                    }

                } else {
                    Toasty.error(getApplicationContext(),getResources().getString(R.string.error_server),Toast.LENGTH_LONG).show();
                }
                button_follow_user_activity.setEnabled(true);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                button_follow_user_activity.setEnabled(true);
                Toasty.error(getApplicationContext(),getResources().getString(R.string.error_server),Toast.LENGTH_LONG).show();
            }
        });
    }

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }
}