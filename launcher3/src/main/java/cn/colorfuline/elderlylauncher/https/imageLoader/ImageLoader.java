package cn.colorfuline.elderlylauncher.https.imageLoader;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;

import cn.colorfuline.base.utils.StringUtils;

/**
 * Created by Administrator on 2016/4/29.
 */
public class ImageLoader {
    /**
     * 单例对象
     */
    private static ImageLoader imageLoader;

    private ImageLoader() {
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static ImageLoader getInstance() {
        if (imageLoader == null) {
            imageLoader = new ImageLoader();
        }
        return imageLoader;
    }

    /**
     * @param context
     * @param url
     * @param imageView
     * @return
     */
    public Target<GlideDrawable> load(Context context, String url, ImageView imageView) {
        return Glide.with(context).load(url).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(imageView);
    }

    public Target<GlideDrawable> load(Context context, String url, GlideDrawableImageViewTarget glideDrawableImageViewTarget) {
        return Glide.with(context).load(url).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(glideDrawableImageViewTarget);
    }

    /**
     * 加载图片带占位图
     *
     * @param context
     * @param url
     * @param defaultImg
     * @param imageView
     * @return
     */
    public Target<GlideDrawable> load(Context context, String url, int defaultImg, ImageView imageView) {
        return Glide.with(context).load(StringUtils.isEmpty(url) ? url : url.trim()).dontAnimate().placeholder(defaultImg).error(defaultImg).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    /**
     * @param context
     * @param url
     * @param round
     * @param imageView
     * @return
     */
    public Target<GlideDrawable> loadWithRound(Context context, String url, int round, ImageView imageView) {
        return Glide.with(context).load(url).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new RoundedCornersTransformation(context, round, 0)).crossFade().into(imageView);
    }

    /**
     * @param context
     * @param url
     * @param defaultImg
     * @param round
     * @param imageView
     * @return
     */
    public Target<GlideDrawable> load(Context context, String url, int defaultImg, int round, ImageView imageView) {
        return Glide.with(context).load(url).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new RoundedCornersTransformation(context, round, 0)).crossFade().placeholder(defaultImg).error(defaultImg).into(imageView);
    }

    /**
     * @param context
     * @param url
     * @param defaultImg
     * @param round
     * @param imageView
     * @return
     */
    public Target<GlideDrawable> loadCenterCrop(Context context, String url, int defaultImg, int round, ImageView imageView) {
        //.bitmapTransform(news RoundedCornersTransformation(context, round, 0))
        return Glide.with(context).load(url).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().crossFade().placeholder(defaultImg).error(defaultImg).into(imageView);
    }

    /**
     * @param context
     * @param url
     * @param defaultImg
     * @param round
     * @param cornerType
     * @param imageView
     * @return
     */
    public Target<GlideDrawable> load(Context context, String url, int defaultImg, int round, RoundedCornersTransformation.CornerType cornerType, ImageView imageView) {
        return Glide.with(context).load(url).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new RoundedCornersTransformation(context, round, 0, cornerType)).crossFade().placeholder(defaultImg).error(defaultImg).into(imageView);
    }

    /**
     * @param activity
     * @param url
     * @param imageView
     * @return
     */
    public Target<GlideDrawable> load(Activity activity, String url, ImageView imageView) {
        return Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(imageView);
    }

    /**
     * @param fragment
     * @param url
     * @param imageView
     * @return
     */
    public Target<GlideDrawable> load(Fragment fragment, String url, ImageView imageView) {
        return Glide.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(imageView);
    }
}
