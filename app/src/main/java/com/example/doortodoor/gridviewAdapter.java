package com.example.doortodoor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.doortodoor.R;

import java.util.ArrayList;
import java.util.List;

public final class gridviewAdapter extends BaseAdapter{
    private List<Integer> data=new ArrayList<Integer>();
    private Context mcntext;
    gridviewAdapter(Context context)
    {
        this.mcntext=context;

    }
    void set_data(List<Integer> data)
    {
        if(this.data.size()>0)
        {
            data.clear();
        }
        this.data.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        TextView textView;

        if (view== null) {
            imageView = new ImageView(mcntext);
            imageView.setLayoutParams(new GridView.LayoutParams(500, 400));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView)view;
        }
        imageView.setImageResource(data.get(i));
        return imageView;
    }
    public  List<Integer> get_image_list()
    {
        return  data;
    }
}
