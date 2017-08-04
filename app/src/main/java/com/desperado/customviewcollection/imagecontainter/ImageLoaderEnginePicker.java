package com.desperado.customviewcollection.imagecontainter;

import android.content.Context;

/**
 * Created by desperado on 17-7-31.
 */

public final class ImageLoaderEnginePicker {

    private static final String PICASSO_ENGINE = "com.squareup.picasso.Picasso";

    private static final String GLIDE_ENGINE = "com.bumptech.glide.Glide";

    private static final String UIL_ENGINE = "com.nostra13.universalimageloader.core.ImageLoader";

    private static boolean checkImageLoaderEngine(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ImageLoaderEngine pickImageLoaderEngine(Context context) {
        if (checkImageLoaderEngine(UIL_ENGINE)) {
            return null;
        } else if (checkImageLoaderEngine(GLIDE_ENGINE)) {
            return null;
        } else if (checkImageLoaderEngine(PICASSO_ENGINE)) {
            return PicassoImageLoaderEngine.createImageLoader(context);
        } else {
            throw new IllegalStateException("this lib only support picasso, glide and uil!");
        }
    }
}
