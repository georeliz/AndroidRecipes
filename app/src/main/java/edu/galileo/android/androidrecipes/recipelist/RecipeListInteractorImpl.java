package edu.galileo.android.androidrecipes.recipelist;

/**
 * Created by Lab1 on 26/07/2016.
 */
public class RecipeListInteractorImpl implements RecipeListInteractor {
    public RecipeListRepository repository;

    public RecipeListInteractorImpl(RecipeListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        repository.getSavedRecipes();
    }
}
