package com.zh.bannerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zh.superbanneribrary.SuperBannerView;
import com.zh.superbanneribrary.SuperHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>公司名       tsingning</p>
 * <p>创建者       Z H</p>
 * <p>创建时间     2018/1/17 20:13</p>
 * <p>包名         com.zh.bannerview</p>
 * <p>描述         TODO </p>
 * <p>svn版本      $Revision$ </p>
 * <p>更新者       $Author$</p>
 * <p>更新时间     $Date$</p>
 */
public class RefreshActivity extends AppCompatActivity {

    private List<String> bannerUrlList;
    private SuperBannerView superBannerView;
    private ImageView iv_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_layout);
        superBannerView = findViewById(R.id.superBannerView);
        bannerUrlList = new ArrayList<>();
        bannerUrlList.add("http://dair.images.blessi.cn/o_1c1cpkiv9146guukr3la635hra.png");
        bannerUrlList.add("http://dair.images.blessi.cn/o_1c1co86b1g63obi1mr2is51235a.png");
        bannerUrlList.add("http://dair.images.blessi.cn/o_1c1co8mbs11br1135toa1ork22va.png");

        superBannerView.setOpenSuperMode(true);
        superBannerView.showIndicator(true);
        superBannerView.setSuperModeMargin(30, 20,false);
        superBannerView.setIndicatorAlign(SuperBannerView.IndicatorAlign.CENTER);
        superBannerView.setCircleIndicatorDrawable(R.drawable.indicator_normal, R.drawable.draw1);

        superBannerView.setViewData(bannerUrlList, new SuperHolder<String>() {
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
