package edu.galileo.android.androidrecipes.recipelist;

import edu.galileo.android.androidrecipes.entities.Recipe;
import edu.galileo.android.androidrecipes.recipelist.events.RecipeListEvent;
import edu.galileo.android.androidrecipes.recipelist.ui.RecipeListView;

/**
 * Created by Lab1 on 14/07/2016.
 */
public interface RecipeListPresenter {
    void onCreate();
    void onDestroy();

    void getRecipes();
    void removeRecipe(Recipe recipe);
    void toogleFavorite(Recipe recipe);
    void onEventMainThread(RecipeListEvent event);

    RecipeListView getView();
}
