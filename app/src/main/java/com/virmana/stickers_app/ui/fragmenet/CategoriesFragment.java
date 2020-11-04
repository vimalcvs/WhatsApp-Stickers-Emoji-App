package com.virmana.stickers_app.ui.fragmenet;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.virmana.stickers_app.R;
import com.virmana.stickers_app.adapter.CategoryAdapter;
import com.virmana.stickers_app.api.apiClient;
import com.virmana.stickers_app.api.apiRest;
import com.virmana.stickers_app.entity.CategoryApi;
import com.virmana.stickers_app.entity.TagApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {


    private Integer page = 0;

    private View view;
    private String language = "0";
    private boolean loaded = false;
    private RelativeLayout relative_layout_load_more;
    private Button button_try_again;
    private SwipeRefreshLayout swipe_refreshl_categroies_fragment;
    private LinearLayout linear_layout_page_error;
    private RecyclerView recycler_view_categroies_fragment;

    private List<TagApi> tagList=new ArrayList<>();
    private List<CategoryApi> categoryList =new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private GridLayoutManager gridLayoutManager;
    private ImageView image_view_empty_list;


    public CategoriesFragment() {
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser){
            if (!loaded) {
                loaded=true;
                loadTags();
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        this.view =  inflater.inflate(R.layout.fragment_categories, container, false);


        initView();
        initAction();

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }


    private void initAction() {
        this.swipe_refreshl_categroies_fragment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadTags();
            }
        });
        button_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTags();
            }
        });
    }

    private void initView() {

        this.image_view_empty_list=(ImageView) view.findViewById(R.id.image_view_empty_list);
        this.relative_layout_load_more=(RelativeLayout) view.findViewById(R.id.relative_layout_load_more);
        this.button_try_again =(Button) view.findViewById(R.id.button_try_again);
        this.swipe_refreshl_categroies_fragment=(SwipeRefreshLayout) view.findViewById(R.id.swipe_refreshl_categroies_fragment);
        this.linear_layout_page_error=(LinearLayout) view.findViewById(R.id.linear_layout_page_error);
        this.recycler_view_categroies_fragment=(RecyclerView) view.findViewById(R.id.recycler_view_categroies_fragment);
        this.gridLayoutManager=  new GridLayoutManager(getActivity(),1);

        categoryAdapter =new CategoryAdapter(categoryList,tagList,getActivity());
        recycler_view_categroies_fragment.setHasFixedSize(true);
        recycler_view_categroies_fragment.setAdapter(categoryAdapter);
        recycler_view_categroies_fragment.setLayoutManager(gridLayoutManager);

    }
    private void loadTags() {
        swipe_refreshl_categroies_fragment.setRefreshing(true);
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<TagApi>> call = service.TagList();
        call.enqueue(new Callback<List<TagApi>>() {
            @Override
            public void onResponse(Call<List<TagApi>> call, Response<List<TagApi>> response) {
                if(response.isSuccessful()){
                    categoryList.clear();
                    tagList.clear();
                    if (response.body().size()!=0){
                        for (int i=0;i<response.body().size();i++){
                            tagList.add(response.body().get(i));
                        }
                        categoryList.add(new CategoryApi().setViewType(2));
                    }
                    categoryAdapter.notifyDataSetChanged();
                    LoadCategories();
                }
            }
            @Override
            public void onFailure(Call<List<TagApi>> call, Throwable t) {
                LoadCategories();
            }
        });

    }
    public void LoadCategories(){
        Retrofit retrofit = apiClient.getClient();
        apiRest service = retrofit.create(apiRest.class);
        Call<List<CategoryApi>> call = service.AllCategories();
        call.enqueue(new Callback<List<CategoryApi>>() {
            @Override
            public void onResponse(Call<List<CategoryApi>> call, Response<List<CategoryApi>> response) {
                if(response.isSuccessful()){
                    if (response.body().size()!=0){
                        for (int i=0;i<response.body().size();i++){
                            categoryList.add(response.body().get(i));
                        }
                        categoryAdapter.notifyDataSetChanged();
                    }
                    recycler_view_categroies_fragment.setVisibility(View.VISIBLE);
                    linear_layout_page_error.setVisibility(View.GONE);
                    swipe_refreshl_categroies_fragment.setRefreshing(false);
                }else{
                    recycler_view_categroies_fragment.setVisibility(View.GONE);
                    linear_layout_page_error.setVisibility(View.VISIBLE);
                    swipe_refreshl_categroies_fragment.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(Call<List<CategoryApi>> call, Throwable t) {
                recycler_view_categroies_fragment.setVisibility(View.GONE);
                linear_layout_page_error.setVisibility(View.VISIBLE);
                swipe_refreshl_categroies_fragment.setRefreshing(false);
            }
        });
    }
}
