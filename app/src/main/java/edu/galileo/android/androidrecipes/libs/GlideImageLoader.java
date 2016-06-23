package edu.galileo.android.androidrecipes.libs;

import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;

/**
 * Created by Gerson on 23/06/2016.
 */
public class GlideImageLoader implements ImageLoader {
    private RequestManager glideRequestManager;
    private RequestListener onFinishedLoadingListener;

    public GlideImageLoader(RequestManager glideRequestManager) {
        this.glideRequestManager = glideRequestManager;
    }


    @Override
    public void load(ImageView imageView, String URL) {
        if (onFinishedLoadingListener != null){
            glideRequestManager.load(URL).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().listener(onFinishedLoadingListener).into(imageView);

        }else{
            glideRequestManager.load(URL).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(imageView);
        }

    }

    @Override
    public void setOnFinishedImageLoadingListener(Object listener) {
        if (listener instanceof RequestListener){
            this.onFinishedLoadingListener = (RequestListener) listener;
        }


    }
}
