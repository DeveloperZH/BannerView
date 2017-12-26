package com.zh.bannerview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zh.bannerview.view.SuperBannerView;
import com.zh.bannerview.view.SuperHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<String> bannerUrlList;

    private SuperBannerView mSuperBannerView;

    private ImageView iv_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSuperBannerView = findViewById(R.id.mSuperBannerView);

        bannerUrlList = new ArrayList<>();
        bannerUrlList.add("http://dair.images.blessi.cn/o_1c1cpkiv9146guukr3la635hra.png");
        bannerUrlList.add("http://dair.images.blessi.cn/o_1c1co86b1g63obi1mr2is51235a.png");
        bannerUrlList.add("http://dair.images.blessi.cn/o_1c1co8mbs11br1135toa1ork22va.png");
        bannerUrlList.add("http://dair.images.blessi.cn/o_1c1cpkiv9146guukr3la635hra.png");

//        mSuperBannerView.setSideAlpha(0.5f);
//        mSuperBannerView.setIndicatorAlign(SuperBannerView.IndicatorAlign.CENTER);

        mSuperBannerView.setViewData(bannerUrlList, new SuperHolder() {
            @Override
            public View createView(Context context) {
                View view = LayoutInflater.from(context).inflate(R.layout.vp_item, null);
                iv_item = view.findViewById(R.id.iv_item);
                return view;
            }

            @Override
            public void onBind(final Context context, final int position, Object data) {
                Glide.with(context).load(bannerUrlList.get(position)).into(iv_item);
                iv_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,"position = " + position,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mSuperBannerView.setOpenSuperMode(30, 20);

    }
}
