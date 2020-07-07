package com.vimalcvs.stickers_app.ui.fragmenet;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.orhanobut.hawk.Hawk;
import com.vimalcvs.stickers_app.R;
import com.vimalcvs.stickers_app.Sticker;
import com.vimalcvs.stickers_app.StickerPack;
import com.vimalcvs.stickers_app.adapter.StickerAdapter;
import com.vimalcvs.stickers_app.entity.PackApi;
import com.vimalcvs.stickers_app.entity.StickerApi;
import com.vimalcvs.stickers_app.ui.HomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    // lists
    ArrayList<StickerPack> stickerPacks = new ArrayList<>();
    List<Sticker> mStickers;
    List<String> mEmojis,mDownloadFiles;
    // Object
    StickerAdapter adapter;
    // stattis variables
    private static final String TAG = HomeActivity.class.getSimpleName();
    // views
    private View view;
    private RecyclerView recycler_view_list;
    private LinearLayout linear_layout_layout_error;
    private ImageView image_view_empty_list;
    private SwipeRefreshLayout swipe_refresh_layout_list;
    private Button button_try_again;
    private RelativeLayout relative_layout_load_more;
    private LinearLayoutManager layoutManager;

    // variables
    private Integer page = 0;
    private Integer position = 0;
    private boolean loading = true;
    private boolean loaded = false;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;


    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser){
                loaded=true;
                page = 0;
                loading = true;
                LoadPackes();


        }
        else{

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        this.view =  inflater.inflate(R.layout.fragment_favorites, container, false);

        stickerPacks = new ArrayList<>();
        mStickers = new ArrayList<>();
        mEmojis = new ArrayList<>();
        mDownloadFiles = new ArrayList<>();
        mEmojis.add("");

        initView();
        initAction();
        return view;
    }


    private void initAction() {
        swipe_refresh_layout_list.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                loading = true;

                LoadPackes();
            }
        });
        button_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page = 0;
                loading = true;
                LoadPackes();
            }
        });

    }

    private void initView() {

        relative_layout_load_more   = view.findViewById(R.id.relative_layout_load_more);
        button_try_again            = view.findViewById(R.id.button_try_again);
        swipe_refresh_layout_list   = view.findViewById(R.id.swipe_refresh_layout_list);
        image_view_empty_list       = view.findViewById(R.id.image_view_empty_list);
        linear_layout_layout_error  = view.findViewById(R.id.linear_layout_layout_error);
        recycler_view_list          = view.findViewById(R.id.recycler_view_list);

        adapter = new StickerAdapter(getActivity(), stickerPacks);
        adapter.favorite = true;
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_view_list.setHasFixedSize(true);
        recycler_view_list.setAdapter(adapter);
        recycler_view_list.setLayoutManager(layoutManager);
;

    }

    public void LoadPackes(){

        recycler_view_list.setVisibility(View.VISIBLE);
        linear_layout_layout_error.setVisibility(View.GONE);
        image_view_empty_list.setVisibility(View.GONE);
        swipe_refresh_layout_list.setRefreshing(true);

        List<PackApi> favorites_list  = new ArrayList<>();
        try {
            favorites_list = Hawk.get("favorite");
            if (favorites_list.size()!=0) {

            }
        }catch (NullPointerException e){
            favorites_list  = new ArrayList<>();
        }


            if (favorites_list.size()!=0) {
                position = 0 ;
                stickerPacks.clear();
                mStickers.clear();
                mEmojis.clear();
                mDownloadFiles.clear();
                mEmojis.add("");
                adapter.notifyDataSetChanged();

                for (int i = 0; i < favorites_list.size(); i++) {
                    PackApi packApi= favorites_list.get(i);
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
                    position++;
                }
                adapter.notifyDataSetChanged();
                page++;

                recycler_view_list.setVisibility(View.VISIBLE);
                image_view_empty_list.setVisibility(View.GONE);
                linear_layout_layout_error.setVisibility(View.GONE);
            }else{
                recycler_view_list.setVisibility(View.GONE);
                image_view_empty_list.setVisibility(View.VISIBLE);
                linear_layout_layout_error.setVisibility(View.GONE);
            }

        swipe_refresh_layout_list.setRefreshing(false);

    }
    private static String getLastBitFromUrl(final String url) {
        return url.replaceFirst(".*/([^/?]+).*", "$1");
    }
}
