package com.ryan.core.helper;


import android.content.Context;
import com.bumptech.glide.Glide;
import com.ryan.core.widget.RyanCornerImageView;

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, RyanCornerImageView imageView) {
        Glide.with(context.getApplicationContext())
                .load(path)
                .fitCenter()
                .into(imageView);
    }
}
