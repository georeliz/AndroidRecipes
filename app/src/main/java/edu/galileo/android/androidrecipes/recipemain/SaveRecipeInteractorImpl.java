package edu.galileo.android.androidrecipes.recipemain;

import edu.galileo.android.androidrecipes.entities.Recipe;

/**
 * Created by Lab1 on 23/06/2016.
 */
public class SaveRecipeInteractorImpl implements SaveRecipeInteractor {

    RecipeMainRepository repository;

    public SaveRecipeInteractorImpl(RecipeMainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Recipe recipe) {
        repository.saveRecipe(recipe);
    }
}
