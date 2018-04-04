# BannerView
 
 
 
## 使用
 
 ### 1 依赖
 compile 'com.zh:SuperBannerView:1.1.4'
 ### 2 布局文件中添加SuperBannerView
 ![](https://github.com/DeveloperZH/BannerView/blob/master/app/src/main/res/mipmap-xhdpi/layout_eg.png)
 ### 3 在activity和fragment中配置SuperBannerView的数据源
  在creatView方法中设置自己的item布局
  onBind方法中处理自己的数据 position和data 分别对应当前viewpager位置和数据
 ![](https://github.com/DeveloperZH/BannerView/blob/master/app/src/main/res/mipmap-xhdpi/layout_eg2.png)
 
 ## 自定义属性说明
 ![](https://github.com/DeveloperZH/BannerView/blob/master/app/src/main/res/mipmap-xhdpi/layout_eg4.png)
 
 ## set方法
 1 public void setOpenSuperMode(boolean openSuperMode)   //设置是否开启Super模式  
 2 public void setSuperModeMargin(int sideMargin, int pageMargin)  //开启super模式的时候  中间图片到屏幕两边的距离以及中间图片到两边两张图片的距离  可为负数  
 3  public void setSideAlpha(float sideAlpha)  //设置两边图片的透明度      
 4  public void showIndicator(boolean showIndicator)  //设置是否显示指示器     
 5  public void setIndicatorAlign(IndicatorAlign mIndicatorAlign)  ////设置指示器显示位置   
 6  public void setIndicatorMarginSide(int indicatorMarginSide)  //当指示器位置为right或者left的时候 距离屏幕的边距     
 7  public void setCircleIndicatorDrawable(@DrawableRes int circleNormalDrawable, @DrawableRes int circleSelectDrawable) //设置指示器的Drawable  circleNormalDrawable 未选中  circleSelectDrawable 选中      
 8  public void setmIndicatorInfo(int radius, int margin, int bottomMargin) //设置圆形指示器的大小  间距  到底部距离    
 9  public void openLoop(boolean openLoop)  //是否开启轮播     
 10 public void startLoop() /public void stopLoop()   //开始轮播/关闭轮播     
 11 public void setLoopTime(int millisecond)   //设置轮播间隔时间    
 
 ###扫描下面的二维码安装体验Demo     
 ![](https://github.com/DeveloperZH/BannerView/blob/master/app/src/main/res/mipmap-xhdpi/layout_eg_3.png)
 
