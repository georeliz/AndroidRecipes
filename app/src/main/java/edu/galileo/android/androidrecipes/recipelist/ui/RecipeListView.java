package edu.galileo.android.androidrecipes.recipelist.ui;

import java.util.List;

import edu.galileo.android.androidrecipes.entities.Recipe;

/**
 * Created by Lab1 on 14/07/2016.
 */
public interface RecipeListView {
    void setRecipes(List<Recipe> data);
    void recipeUpdated();
    void recipeDeleted(Recipe recipe);
}
