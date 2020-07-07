package com.vimalcvs.stickers_app.adapter;

import android.content.Context;
import android.os.Parcelable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vimalcvs.stickers_app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vimal on 28/11/2017.
 */


public class IntroAdapter extends PagerAdapter {
    private List<Integer> audioList =new ArrayList<Integer>();
    private Context mContext;

    public IntroAdapter(Context mContext, List<Integer> stringList) {
        this.audioList = stringList;
        this.mContext = mContext;
    }



    @Override
    public int getCount() {
        return audioList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater)this.mContext.getSystemService(this.mContext.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.item_slide_1, container, false);
        switch (audioList.get(position)){
            case 1:
                view = layoutInflater.inflate(R.layout.item_slide_1, container, false);
                break;
            case 2:
                view = layoutInflater.inflate(R.layout.item_slide_2, container, false);
                break;
            case 3:
                view = layoutInflater.inflate(R.layout.item_slide_3, container, false);
                break;
            case 4:
                view = layoutInflater.inflate(R.layout.item_slide_4, container, false);
                break;
            case 5:
                view = layoutInflater.inflate(R.layout.item_slide_5, container, false);
                break;
            case 6:
                view = layoutInflater.inflate(R.layout.item_slide_6, container, false);
                break;
            case 7:
                view = layoutInflater.inflate(R.layout.item_slide_7, container, false);
                break;
        }


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

}
