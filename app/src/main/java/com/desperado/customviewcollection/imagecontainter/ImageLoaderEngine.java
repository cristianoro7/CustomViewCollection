package com.desperado.customviewcollection.imagecontainter;

import android.support.annotation.NonNull;
import android.widget.ImageView;

/**
 * Created by desperado on 17-7-31.
 */

public interface ImageLoaderEngine {
    void load(@NonNull Object model, @NonNull ImageView imageView);
}
