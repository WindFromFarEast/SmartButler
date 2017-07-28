package com.studio.smartbutler.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.studio.smartbutler.application.BaseApplication;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.utils
 * file name: PicassoUtils
 * creator: WindFromFarEast
 * created time: 2017/7/28 16:42
 * description: Picasso图片加载库封装类
 */

public class PicassoUtils
{
    //直接加载图片
    public static void loadImg(String url, ImageView imageView)
    {
        Picasso.with(BaseApplication.getContext()).load(url).into(imageView);
    }

    //加载裁剪图片
    public static void loadImgWithSize(String url, ImageView imageView, int width,int height)
    {
        Picasso.with(BaseApplication.getContext()).load(url).resize(width, height).centerCrop().into(imageView);
    }

    //加载拥有默认图片
    public static void loadImgWithDefault(String url, ImageView imageView,int defaultImg,int errorImg)
    {
        Picasso.with(BaseApplication.getContext()).load(url).placeholder(defaultImg).error(errorImg).into(imageView);
    }

    //加载图片为自定义形状(默认矩形)
    public static void loadImgWithCrop(String url, ImageView imageView)
    {
        Picasso.with(BaseApplication.getContext()).load(url).transform(new CropSquareTransformation()).into(imageView);
    }

    public static class CropSquareTransformation implements Transformation
    {
        @Override public Bitmap transform(Bitmap source)
        {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source)
            {
                source.recycle();
            }
            return result;
        }

        @Override public String key()
        {
            return "WindFromFarEast";
        }
    }
}
