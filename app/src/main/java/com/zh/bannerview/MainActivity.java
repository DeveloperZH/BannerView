package com.zh.bannerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zh.superbanneribrary.SuperBannerView;
import com.zh.superbanneribrary.SuperHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<String> bannerUrlList;

    private SuperBannerView mSuperBannerView,mSuperBannerView1,mSuperBannerView2;

    private ImageView iv_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSuperBannerView = findViewById(R.id.mSuperBannerView);
        mSuperBannerView1 = findViewById(R.id.mSuperBannerView1);
        mSuperBannerView2 = findViewById(R.id.mSuperBannerView2);

        bannerUrlList = new ArrayList<>();
        bannerUrlList.add("http://dair.images.blessi.cn/o_1c1cpkiv9146guukr3la635hra.png");
        bannerUrlList.add("http://dair.images.blessi.cn/o_1c1co86b1g63obi1mr2is51235a.png");
        bannerUrlList.add("http://dair.images.blessi.cn/o_1c1co8mbs11br1135toa1ork22va.png");

        mSuperBannerView.setOpenSuperMode(true);
        mSuperBannerView.showIndicator(true);
        mSuperBannerView.setSuperModeMargin(30, 20);
        mSuperBannerView.setIndicatorAlign(SuperBannerView.IndicatorAlign.CENTER);
        mSuperBannerView.setCircleIndicatorDrawable(R.drawable.indicator_normal, R.drawable.draw1);

        mSuperBannerView.setViewData(bannerUrlList, new SuperHolder<String>() {
            @Override
            public View createView(Context context) {
                View view = LayoutInflater.from(context).inflate(R.layout.vp_item, null);
                iv_item = view.findViewById(R.id.iv_item);
                return view;
            }

            @Override
            public void onBind(final Context context, final int position, final String data) {
                Glide.with(context).load(data).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_item);
                iv_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "position = " + position + data, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        mSuperBannerView1.setViewData(bannerUrlList, new SuperHolder<String>() {
            @Override
            public View createView(Context context) {
                View view = LayoutInflater.from(context).inflate(R.layout.vp_item, null);
                iv_item = view.findViewById(R.id.iv_item);
                return view;
            }

            @Override
            public void onBind(final Context context, final int position, final String data) {
                Glide.with(context).load(data).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_item);
                iv_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "position = " + position + data, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mSuperBannerView2.setOpenSuperMode(true);
        mSuperBannerView2.showIndicator(true);
        mSuperBannerView2.setSuperModeMargin(30, -20);
        mSuperBannerView2.setViewData(bannerUrlList, new SuperHolder<String>() {
            @Override
            public View createView(Context context) {
                View view = LayoutInflater.from(context).inflate(R.layout.vp_item, null);
                iv_item = view.findViewById(R.id.iv_item);
                return view;
            }
            @Override
            public void onBind(final Context context, final int position, final String data) {
                Glide.with(context).load(data).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_item);
                iv_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "position = " + position + data, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
