# DragPhotoView

基于 [PhotoView](https://github.com/chrisbanes/PhotoView) 兼容实现图片单指拖动下滑finish activity功能，高仿微信可拖拽返回功能。

[![](https://jitpack.io/v/HuangGangHust/DragPhotoView.svg)](https://jitpack.io/#HuangGangHust/DragPhotoView)

[下载示例APK体验](https://github.com/HuangGangHust/DragPhotoView/raw/master/DragPhotoView-sample.apk)



## 依赖

1. 在项目的根目录的build.gradle文件中（注意：不是module的build.gradle文件）加入如下依赖：

   ```
   allprojects {
   	repositories {
   		...
   		maven { url "https://jitpack.io" }
   	}
   }
   ```


2. 在module的build.gradle文件中加入如下依赖：

   ```
   dependencies {
       compile 'com.github.HuangGangHust:DragPhotoView:1.0.0'
   }
   ```



## 功能

完美兼容PhotoView所有功能，并进一步兼容实现：单指拖拽缩放图片，并finish Activity；

PhotoView已有特性：

- Out of the box zooming, using multi-touch and double-tap.
- Scrolling, with smooth scrolling fling.
- Works perfectly when used in a scrolling parent (such as ViewPager).
- Allows the application to be notified when the displayed Matrix has changed. Useful for when you need to update your UI based on the current zoom/scroll position.
- Allows the application to be notified when the user taps on the Photo.




## 使用

1. 使用ZoomRelativeLayout或ZoomLinearLayout（使用上等同于RelativeLayout或LinearLayout）作为拖动时进行缩放的父布局，使用DragPhotoView（使用上等同于ImageView）作为展示图片的控件
2. 在实例化对应控件后，通过`dragPhotoview.setZoomParentView(zoomParentLayout)`方法给DragPhotoView绑定拖动时进行缩放的布局。如下：

```java
ZoomRelativeLayout zoomParentLayout = (ZoomRelativeLayout) findViewById(R.id.rl_zoom_parent);
DragPhotoView dragPhotoview = (DragPhotoView) findViewById(R.id.dragphotoview);
dragPhotoview.setZoomParentView(zoomParentLayout);// 绑定拖动时进行缩放的布局
dragPhotoview.setOnViewTapListener(this);// 单击监听，视需要增加
```



## 更多

若需要其他进行缩放的布局，只需继承对应的布局，其他代码等同ZoomRelativeLayout或ZoomLinearLayout即可。



## 其他说明

对于共享元素的兼容实现，后面抽空封装后补上。



## 欢迎Star，欢迎反馈问题：huangganghust@qq.com



## 感谢

* https://github.com/githubwing/DragPhotoView

  参考该库的动画