package edu.galileo.android.androidrecipes.recipelist;

import edu.galileo.android.androidrecipes.entities.Recipe;

/**
 * Created by Lab1 on 14/07/2016.
 */
public interface StoredRecipeListInteractor {
    void executeUpdate(Recipe recipe);
    void executeDelete(Recipe recipe);
}
