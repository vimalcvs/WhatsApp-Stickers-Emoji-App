package com.vimalcvs.stickers_app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.core.app.ActivityOptionsCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vimalcvs.stickers_app.R;
import com.vimalcvs.stickers_app.ui.CategoryActivity;
import com.vimalcvs.stickers_app.entity.CategoryApi;
import com.vimalcvs.stickers_app.entity.TagApi;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Vimal on 17/01/2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TagApi> tagList =  new ArrayList<>();
    private List<CategoryApi> categoryList =new ArrayList<>();
    private Activity activity;
    private LinearLayoutManager linearLayoutManager;
    private TagAdapter tagAdapter;

    public CategoryAdapter(List<CategoryApi> categoryList, Activity activity) {
        this.categoryList = categoryList;
        this.activity = activity;
    }
    public CategoryAdapter(List<CategoryApi> categoryList, List<TagApi> tagList, Activity activity) {
        this.tagList = tagList;
        this.categoryList = categoryList;
        this.activity = activity;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case 1: {
                View v1 = inflater.inflate(R.layout.item_category, null);
                viewHolder = new CategoryHolder(v1);
                break;
            }
            case 2: {
                View v2 = inflater.inflate(R.layout.item_tags, parent, false);
                viewHolder = new TagsHolder(v2);
                break;
            }
            case 3: {
                View v3 = inflater.inflate(R.layout.item_category_mini, parent, false);
                viewHolder = new CategoryMiniHolder(v3);
                break;
            }
        }
        return viewHolder;
    }
    @Override
    public int getItemViewType(int position) {
        return  categoryList.get(position).getViewType();
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        switch (getItemViewType(position)) {
            case 1: {
                CategoryHolder categoryHolder = (CategoryHolder) holder;
                Typeface face = Typeface.createFromAsset(activity.getAssets(), "Pattaya-Regular.ttf");
                categoryHolder.text_view_item_category.setTypeface(face);
                categoryHolder.text_view_item_category_shadow.setTypeface(face);
                int index = 0;
                for (int i = 0; i < position; i++) {
                    index ++;
                    if (index==5){
                        index = 0;
                    }
                }
                String[] colorsTxt = activity.getResources().getStringArray(R.array.colors);
                categoryHolder.card_view.setCardBackgroundColor(Color.parseColor(colorsTxt[index]));

                categoryHolder.text_view_item_category.setText(categoryList.get(position).getTitle());
                categoryHolder.text_view_item_category_shadow.setText(categoryList.get(position).getTitle());
                Picasso.with(activity.getApplicationContext()).load(categoryList.get(position).getImage()).into(((CategoryHolder) holder).image_view_item_category);
                categoryHolder.text_view_item_category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent  =  new Intent(activity.getApplicationContext(), CategoryActivity.class);
                        intent.putExtra("id",categoryList.get(position).getId());
                        intent.putExtra("title",categoryList.get(position).getTitle());
                        (activity).startActivity(intent,
                                ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth(),
                                        view.getHeight()).toBundle());
                    }
                });
                categoryHolder.image_view_item_category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent  =  new Intent(activity.getApplicationContext(), CategoryActivity.class);
                        intent.putExtra("id",categoryList.get(position).getId());
                        intent.putExtra("title",categoryList.get(position).getTitle());
                        (activity).startActivity(intent,
                                ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth(),
                                        view.getHeight()).toBundle());
                    }
                });

                break;
            }
            case 2: {
                final TagsHolder tagsHolder = (TagsHolder) holder;
                this.linearLayoutManager=  new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false);
                this.tagAdapter =new TagAdapter(tagList,activity);
                tagsHolder.recycle_view_tags_items.setHasFixedSize(true);
                tagsHolder.recycle_view_tags_items.setAdapter(tagAdapter);
                tagsHolder.recycle_view_tags_items.setLayoutManager(linearLayoutManager);
                tagAdapter.notifyDataSetChanged();
                break;
            }
            case 3: {
                int index = 0;
                for (int i = 0; i < position; i++) {
                    index ++;
                    if (index==5){
                        index = 0;
                    }
                }
                String[] colorsTxt = activity.getResources().getStringArray(R.array.colors);


                CategoryMiniHolder categoryHolder = (CategoryMiniHolder) holder;
                Typeface face = Typeface.createFromAsset(activity.getAssets(), "Pattaya-Regular.ttf");
                categoryHolder.text_view_item_category.setTypeface(face);
                categoryHolder.text_view_item_category_counter.setTypeface(face);
                categoryHolder.card_view.setCardBackgroundColor(Color.parseColor(colorsTxt[index]));

                categoryHolder.text_view_item_category.setText(categoryList.get(position).getTitle());
                categoryHolder.text_view_item_category_counter.setText(format(categoryList.get(position).getPacks())+" packs");
                Picasso.with(activity.getApplicationContext()).load(categoryList.get(position).getImage()).into(((CategoryMiniHolder) holder).image_view_item_category);
                categoryHolder.text_view_item_category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent  =  new Intent(activity.getApplicationContext(), CategoryActivity.class);
                        intent.putExtra("id",categoryList.get(position).getId());
                        intent.putExtra("title",categoryList.get(position).getTitle());
                        (activity).startActivity(intent,
                                ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth(),
                                        view.getHeight()).toBundle());
                    }
                });
                categoryHolder.image_view_item_category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent  =  new Intent(activity.getApplicationContext(), CategoryActivity.class);
                        intent.putExtra("id",categoryList.get(position).getId());
                        intent.putExtra("title",categoryList.get(position).getTitle());
                        (activity).startActivity(intent,
                                ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getX(), (int) view.getY(), view.getWidth(),
                                        view.getHeight()).toBundle());
                    }
                });

                break;
            }
        }
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder {
        private ImageView image_view_item_category;
        private TextView text_view_item_category;
        private TextView text_view_item_category_shadow;
        private CardView card_view;

        public CategoryHolder(View view) {
            super(view);
            this.text_view_item_category_shadow = (TextView) itemView.findViewById(R.id.text_view_item_category_shadow);
            this.text_view_item_category = (TextView) itemView.findViewById(R.id.text_view_item_category);
            this.image_view_item_category = (ImageView) itemView.findViewById(R.id.image_view_item_category);
            this.card_view = (CardView) itemView.findViewById(R.id.card_view);

        }
    }
    public static class CategoryMiniHolder extends RecyclerView.ViewHolder {
        private ImageView image_view_item_category;
        private TextView text_view_item_category;
        private TextView text_view_item_category_counter;
        private CardView card_view;

        public CategoryMiniHolder(View view) {
            super(view);
            this.text_view_item_category_counter = (TextView) itemView.findViewById(R.id.text_view_item_category_counter);
            this.text_view_item_category = (TextView) itemView.findViewById(R.id.text_view_item_category);
            this.image_view_item_category = (ImageView) itemView.findViewById(R.id.image_view_item_category);
            this.card_view = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
    public static class TagsHolder extends RecyclerView.ViewHolder {
        private final RecyclerView recycle_view_tags_items;
        public TagsHolder(View itemView) {
            super(itemView);
            this.recycle_view_tags_items=(RecyclerView) itemView.findViewById(R.id.recycle_view_tags_items);
        }
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
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
