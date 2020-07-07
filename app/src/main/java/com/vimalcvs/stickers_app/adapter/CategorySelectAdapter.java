package com.vimalcvs.stickers_app.adapter;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vimalcvs.stickers_app.R;
import com.vimalcvs.stickers_app.entity.CategoryApi;

import java.util.ArrayList;
import java.util.List;


public class CategorySelectAdapter extends RecyclerView.Adapter implements SelectableCategoryViewHolder.OnItemSelectedListener {

    private final List<CategoryApi> mValues;
    private boolean isMultiSelectionEnabled = false;
    SelectableCategoryViewHolder.OnItemSelectedListener listener;
    Activity activity;
    public CategorySelectAdapter( SelectableCategoryViewHolder.OnItemSelectedListener listener,
                                  List<CategoryApi> items,boolean isMultiSelectionEnabled,Activity activity) {
        this.listener = listener;
        this.isMultiSelectionEnabled = isMultiSelectionEnabled;
        this.activity=activity;
        mValues = new ArrayList<>();
        for (CategoryApi item : items) {
            mValues.add(item);
        }
    }

    @Override
    public SelectableCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_select, parent, false);

        return new SelectableCategoryViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        SelectableCategoryViewHolder holder = (SelectableCategoryViewHolder) viewHolder;
        CategoryApi selectableItem = mValues.get(position);
        String name = selectableItem.getTitle();
        holder.text_view_item_category_item_select.setText(name);

        //Picasso.with(activity.getApplicationContext()).load(selectableItem.getImage()).error(R.drawable.flag_placeholder).placeholder(R.drawable.flag_placeholder).into(holder.image_view_iten_language);

        if (isMultiSelectionEnabled) {
            TypedValue value = new TypedValue();
            holder.textView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorMultiple, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.textView.setCheckMarkDrawable(checkMarkDrawableResId);
        } else {
            TypedValue value = new TypedValue();
            holder.textView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorSingle, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.textView.setCheckMarkDrawable(checkMarkDrawableResId);
        }

        holder.mItem = selectableItem;
        holder.setChecked(holder.mItem.isSelected());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public List<CategoryApi> getSelectedItems() {

        List<CategoryApi> selectedItems = new ArrayList<>();
        for (CategoryApi item : mValues) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }

    @Override
    public int getItemViewType(int position) {
        if(isMultiSelectionEnabled){
            return SelectableCategoryViewHolder.MULTI_SELECTION;
        }
        else{
            return SelectableCategoryViewHolder.SINGLE_SELECTION;
        }
    }

    @Override
    public void onItemSelected(CategoryApi item) {

        if (!isMultiSelectionEnabled) {
            for (CategoryApi selectableItem : mValues) {
                if (!selectableItem.equals(item)
                        && selectableItem.isSelected()) {
                    selectableItem.setSelected(false);
                } else if (selectableItem.equals(item)
                        && item.isSelected()) {
                    selectableItem.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        listener.onItemSelected(item);

    }



}
