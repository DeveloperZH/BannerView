package com.zh.bannerview;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private List<String> bannerUrlList1;

    private SuperBannerView mSuperBannerView, mSuperBannerView1, mSuperBannerView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSuperBannerView = findViewById(R.id.mSuperBannerView);
        mSuperBannerView1 = findViewById(R.id.mSuperBannerView1);
        mSuperBannerView2 = findViewById(R.id.mSuperBannerView2);

        bannerUrlList = new ArrayList<>();
        bannerUrlList1 = new ArrayList<>();
        bannerUrlList.add("http://dair.images.blessi.cn/o_1c1cpkiv9146guukr3la635hra.png");
        bannerUrlList.add("http://dair.images.blessi.cn/o_1c1co86b1g63obi1mr2is51235a.png");
        bannerUrlList.add("http://dair.images.blessi.cn/o_1c1co8mbs11br1135toa1ork22va.png");

        bannerUrlList1.add("http://dair.images.blessi.cn/o_1c1cpkiv9146guukr3la635hra.png");
        bannerUrlList1.add("http://dair.images.blessi.cn/o_1c1co86b1g63obi1mr2is51235a.png");
        bannerUrlList1.add("http://dair.images.blessi.cn/o_1c1co8mbs11br1135toa1ork22va.png");

        mSuperBannerView.setOpenSuperMode(true);
        mSuperBannerView.showIndicator(true);
        mSuperBannerView.setSuperModeMargin(12, 8, false);
        mSuperBannerView.setIndicatorAlign(SuperBannerView.IndicatorAlign.CENTER);
        mSuperBannerView.setCircleIndicatorDrawable(R.drawable.circle_banner_normal, R.drawable.circle_banner_selector);
        mSuperBannerView.setIndicatorInfo(10, 2, 20, 10);

        mSuperBannerView.setViewData(bannerUrlList, new SuperHolder<String>() {


            @Override
            public View createView(ViewGroup parent) {
                return LayoutInflater.from(parent.getContext()).inflate(R.layout.vp_item, null);
            }

            @Override
            public void onBind(View view, final int position, final String data) {
                ImageView iv_item = view.findViewById(R.id.iv_item);
                Glide.with(view.getContext()).load(data).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_item);
                iv_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), "position = " + position + data, Toast.LENGTH_SHORT).show();
                    }
                });
            }


        });


        mSuperBannerView2.setOpenSuperMode(true);
        mSuperBannerView2.showIndicator(true);
        mSuperBannerView2.setSuperModeMargin(30, -20, true);
        mSuperBannerView2.setViewData(bannerUrlList1, new SuperHolder<String>() {
            @Override
            public View createView(ViewGroup parent) {
                return LayoutInflater.from(parent.getContext()).inflate(R.layout.vp_item, null);
            }

            @Override
            public void onBind(View view, final int position, final String data) {
                ImageView iv_item = view.findViewById(R.id.iv_item);
                Glide.with(view.getContext()).load(data).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_item);
                iv_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), "position = " + position + data, Toast.LENGTH_SHORT).show();
                    }
                });
            }


        });


        findViewById(R.id.tv_go_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                bannerUrlList.clear();
//                bannerUrlList.addAll(bannerUrlList1);
                bannerUrlList.add("http://t.opercenter.com/2018/05/03/16323f57367a6bed887285b4bae9e998.png");
                mSuperBannerView.setViewData(bannerUrlList, new SuperHolder<String>() {
                    @Override
                    public View createView(ViewGroup parent) {
                        return LayoutInflater.from(parent.getContext()).inflate(R.layout.vp_item, null);
                    }

                    @Override
                    public void onBind(View view, final int position, final String data) {
                        ImageView iv_item = view.findViewById(R.id.iv_item);
                        Glide.with(view.getContext()).load(data).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_item);
                        iv_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(view.getContext(), "position = " + position + data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                });
            }
        });
    }

}
