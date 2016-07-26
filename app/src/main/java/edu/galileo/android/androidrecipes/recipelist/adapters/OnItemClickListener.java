package edu.galileo.android.androidrecipes.recipelist.adapters;

import edu.galileo.android.androidrecipes.entities.Recipe;

/**
 * Created by Lab1 on 14/07/2016.
 */
public interface OnItemClickListener {
    void onFavClick(Recipe recipe);
    void onItemClick(Recipe recipe);
    void onDeleteClick(Recipe recipe);

}
