package edu.galileo.android.androidrecipes.recipelist;

import edu.galileo.android.androidrecipes.entities.Recipe;

/**
 * Created by Lab1 on 26/07/2016.
 */
public class StoredRecipeListInteractorImpl implements StoredRecipeListInteractor {
    private RecipeListRepository repository;

    public StoredRecipeListInteractorImpl(RecipeListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executeUpdate(Recipe recipe) {
        repository.updateRecipe(recipe);
    }

    @Override
    public void executeDelete(Recipe recipe) {
        repository.removeRecipe(recipe);
    }
}
