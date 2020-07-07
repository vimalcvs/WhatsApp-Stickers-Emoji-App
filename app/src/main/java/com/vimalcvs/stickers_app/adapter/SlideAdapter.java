package com.vimalcvs.stickers_app.adapter;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Parcelable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.vimalcvs.stickers_app.R;
import com.vimalcvs.stickers_app.Sticker;
import com.vimalcvs.stickers_app.StickerPack;
import com.vimalcvs.stickers_app.ui.CategoryActivity;
import com.vimalcvs.stickers_app.ui.StickerDetailsActivity;
import com.vimalcvs.stickers_app.entity.PackApi;
import com.vimalcvs.stickers_app.entity.SlideApi;
import com.vimalcvs.stickers_app.entity.StickerApi;
import com.squareup.picasso.Picasso;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.vimalcvs.stickers_app.MainActivity.EXTRA_STICKERPACK;

public class SlideAdapter extends PagerAdapter {
    private List<SlideApi> slideList =new ArrayList<SlideApi>();
    private Activity activity;
    List<Sticker> mStickers;
    List<String> mEmojis,mDownloadFiles;
    public SlideAdapter( Activity activity,List<SlideApi> stringList) {
        this.slideList = stringList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return slideList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater)this.activity.getSystemService(this.activity.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_slide_one, container, false);

        TextView text_view_item_slide_one_title =  (TextView)  view.findViewById(R.id.text_view_item_slide_one_title);
        ImageView image_view_item_slide_one =  (ImageView)  view.findViewById(R.id.image_view_item_slide_one);

        Typeface face = Typeface.createFromAsset(activity.getAssets(), "Pattaya-Regular.ttf");
        text_view_item_slide_one_title.setTypeface(face);

        byte[] data = android.util.Base64.decode(slideList.get(position).getTitle(), android.util.Base64.DEFAULT);
        final String final_text = new String(data, Charset.forName("UTF-8"));

        text_view_item_slide_one_title.setText(final_text);
        CardView card_view_item_slide_one = (CardView) view.findViewById(R.id.card_view_item_slide_one);
        card_view_item_slide_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slideList.get(position).getType().equals("3") && slideList.get(position).getPack()!=null){
                    mStickers = new ArrayList<>();
                    mDownloadFiles = new ArrayList<>();
                    mEmojis = new ArrayList<>();
                    mEmojis.add("");
                    PackApi packApi= slideList.get(position).getPack();
                    StickerPack stickerPack = new StickerPack(
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
                    );
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
                    stickerPack.setStickers(Hawk.get(packApi.getIdentifier()+"",new ArrayList<Sticker>()));
                    mStickers.clear();

                    (activity).startActivity(new Intent((activity), StickerDetailsActivity.class)
                                    .putExtra(EXTRA_STICKERPACK, stickerPack),
                            ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(),
                                    v.getHeight()).toBundle());

                }
                if (slideList.get(position).getType().equals("1") && slideList.get(position).getCategory()!=null){
                    Intent intent  =  new Intent(activity.getApplicationContext(), CategoryActivity.class);
                    intent.putExtra("id",slideList.get(position).getCategory().getId());
                    intent.putExtra("title",slideList.get(position).getCategory().getTitle());
                    activity.startActivity(intent,
                            ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(),
                                    v.getHeight()).toBundle());
                }
                if (slideList.get(position).getType().equals("2") && slideList.get(position).getUrl()!=null){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(slideList.get(position).getUrl()));
                    activity.startActivity(browserIntent,
                            ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(), v.getWidth(),
                                    v.getHeight()).toBundle());
                }
            }
        });

        Picasso.with(activity).load(slideList.get(position).getImage()).placeholder(R.drawable.placeholder).into(image_view_item_slide_one);

        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public float getPageWidth (int position) {
        return 1f;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);

    }
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);

    }
    @Override
    public Parcelable saveState() {
        return null;
    }
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    private static String getLastBitFromUrl(final String url) {
        return url.replaceFirst(".*/([^/?]+).*", "$1");
    }
}
