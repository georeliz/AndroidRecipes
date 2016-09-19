package edu.galileo.android.androidrecipes.support;

import android.support.v7.widget.RecyclerView;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowViewGroup;

/**
 * Created by Lab1 on 08/08/2016.
 */
@Implements(RecyclerView.class)
public class ShadowReciclerView extends ShadowViewGroup {
    private  int smoothScrollPosition = -1;

   @Implementation
   public void smoothScrollToPosition(int pos){
       setSmoothScrollPosition(pos);
   }

    public int getSmoothScrollPosition() {
        return smoothScrollPosition;
    }

    public void setSmoothScrollPosition(int pos){
        setSmoothScrollPosition(pos);
    }
}
