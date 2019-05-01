package com.exomatik.irfanrz.kepolisian.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exomatik.irfanrz.kepolisian.ModelClass.Data.ModelTopik;
import com.exomatik.irfanrz.kepolisian.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 6/1/2018.
 */

public class SwipeAdapter extends PagerAdapter {
    public static ArrayList<ModelTopik> listModel = new ArrayList<ModelTopik>();
    private Context ctx;
    private LayoutInflater layoutInflater;

    public SwipeAdapter(Context ctx){
        this.ctx=ctx;
    }


    @Override
    public int getCount() {
        return listModel.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout, container, false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.image_view);
        TextView textView = (TextView) item_view.findViewById(R.id.image_count);

        Uri uri = Uri.parse(listModel.get(position).url);
        Picasso.with(ctx).load(uri).into(imageView);
        textView.setText(listModel.get(position).namaBerita);

        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }

}