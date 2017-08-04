package com.desperado.customviewcollection.imagecontainter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.desperado.customviewcollection.R;
import com.squareup.picasso.Picasso;

/**
 * Created by desperado on 17-7-31.
 */

public class PicassoImageLoaderEngine implements ImageLoaderEngine {

    private Picasso picasso;

    private int h;
    private int w;

    public PicassoImageLoaderEngine(Picasso picasso) {
        this.picasso = picasso;
    }

    public PicassoImageLoaderEngine(Picasso picasso, int h, int w) {
        this.picasso = picasso;
        this.h = h;
        this.w = w;
    }

    @Override
    public void load(@NonNull Object model, @NonNull final ImageView imageView) {
        picasso.load((int) model)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(imageView);
    }

    public static ImageLoaderEngine createImageLoader(Context context) {
        return new PicassoImageLoaderEngine(Picasso.with(context));
    }
}
