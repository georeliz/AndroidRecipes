package edu.galileo.android.androidrecipes.recipemain.ui;

import edu.galileo.android.androidrecipes.entities.Recipe;

/**
 * Created by Lab1 on 23/06/2016.
 */
public interface RecipeMainView {
    void showProgressBar();
    void hideProgressBar();
    void showElements();
    void hideElements();

    void saveAnimation();
    void dismissAnimation();

    void onRecipeSaved();

    void setRecipe(Recipe recipe);
    void onGetRecipeError(String erorr);

}
