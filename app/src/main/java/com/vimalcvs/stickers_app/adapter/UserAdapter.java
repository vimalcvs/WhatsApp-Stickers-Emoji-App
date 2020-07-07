package com.vimalcvs.stickers_app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vimalcvs.stickers_app.R;
import com.vimalcvs.stickers_app.ui.UserActivity;
import com.vimalcvs.stickers_app.entity.UserApi;
import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * Created by Vimal on 07/12/2017.
 */


public class UserAdapter extends BaseAdapter {
    private List<UserApi> users ;
    private Activity context;

    public UserAdapter(List<UserApi> users, Activity context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public UserApi getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return users.get(position).getId();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.v("name of user ",users.get(position).getName());
        convertView = inflater.inflate(R.layout.user_item, null);
        AppCompatImageView image_view_user_iten_trusted=(AppCompatImageView) convertView.findViewById(R.id.image_view_user_iten_trusted);
        ImageView image_view_user_iten=(ImageView) convertView.findViewById(R.id.image_view_user_iten);
        TextView text_view_name_user=(TextView) convertView.findViewById(R.id.text_view_name_item_user);
        if (!users.get(position).getImage().isEmpty()){
            Picasso.with(context).load(users.get(position).getImage()).error(R.mipmap.ic_launcher_round).placeholder(R.drawable.profile).into(image_view_user_iten);
        }else{
            Picasso.with(context).load(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round).placeholder(R.drawable.profile).into(image_view_user_iten);
        }
        if (users.get(position).getTrusted() == true ){
            image_view_user_iten_trusted.setVisibility(View.VISIBLE);
        }else{
            image_view_user_iten_trusted.setVisibility(View.GONE);
        }
        text_view_name_user.setText(users.get(position).getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  =  new Intent(context.getApplicationContext(), UserActivity.class);
                intent.putExtra("id",users.get(position).getId());
                intent.putExtra("image",users.get(position).getImage());
                intent.putExtra("name",users.get(position).getName());
                intent.putExtra("trusted",users.get(position).getTrusted());
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        return convertView;
    }
}
