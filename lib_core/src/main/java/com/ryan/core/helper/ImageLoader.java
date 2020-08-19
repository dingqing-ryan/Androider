package com.ryan.core.helper;

import android.content.Context;
import android.widget.ImageView;

import com.ryan.core.widget.RyanCornerImageView;
import com.ryan.core.widget.banner.loader.ImageLoaderInterface;

public abstract class ImageLoader implements ImageLoaderInterface<RyanCornerImageView> {

    @Override
    public RyanCornerImageView createImageView(Context context) {
        RyanCornerImageView imageView = new RyanCornerImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setType(RyanCornerImageView.TYPE_ROUND);
//        imageView.setRoundRadius(15);
        return imageView;
    }
}
