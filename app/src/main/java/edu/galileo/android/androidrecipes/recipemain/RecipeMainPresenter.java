package edu.galileo.android.androidrecipes.recipemain;

import edu.galileo.android.androidrecipes.entities.Recipe;
import edu.galileo.android.androidrecipes.recipemain.events.RecipeMainEvent;
import edu.galileo.android.androidrecipes.recipemain.ui.RecipeMainView;

/**
 * Created by Lab1 on 23/06/2016.
 */
public interface RecipeMainPresenter {
    void onCreate();
    void onDestroy();

    void dismissRecipe();
    void getNextRecipe();
    void saveRecipe(Recipe recipe);
    void onEventMainThread(RecipeMainEvent event);


    void imageError(String error);
    void imageReady();
    RecipeMainView getView();
}
