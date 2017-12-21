package com.zh.bannerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zh.bannerview.view.SuperBannerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<String> bannerUrlList;

    private SuperBannerView mSuperBannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSuperBannerView = findViewById(R.id.mSuperBannerView);

        bannerUrlList = new ArrayList<>();
        bannerUrlList.add("http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1308/29/c3/25027747_1377732388226.jpg");
        bannerUrlList.add("http://dair.images.blessi.cn/o_1c08cjqt71phdbci16r7k89572a.png");
        bannerUrlList.add("http://dair.images.blessi.cn/o_1c08cja8et91t4j1bd215v51evka.png");

        mSuperBannerView.setViewData(bannerUrlList);

    }
}
