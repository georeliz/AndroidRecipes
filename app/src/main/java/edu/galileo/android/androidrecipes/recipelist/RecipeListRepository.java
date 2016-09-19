package edu.galileo.android.androidrecipes.recipelist;

import edu.galileo.android.androidrecipes.entities.Recipe;

/**
 * Created by Lab1 on 14/07/2016.
 */
public interface RecipeListRepository {
    void getSavedRecipes();
    void updateRecipe(Recipe recipe);
    void removeRecipe(Recipe recipe);

    void getFavoritesRecipes();
}
