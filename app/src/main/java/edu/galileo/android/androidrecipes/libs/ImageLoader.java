package edu.galileo.android.androidrecipes.libs;

import android.widget.ImageView;

/**
 * Created by Gerson on 23/06/2016.
 */
public interface ImageLoader {
    void load(ImageView imageView, String URL);
    void setOnFinishedImageLoadingListener(Object listener);
}
