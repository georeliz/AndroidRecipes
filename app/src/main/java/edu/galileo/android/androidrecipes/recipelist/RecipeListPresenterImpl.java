package edu.galileo.android.androidrecipes.recipelist;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.androidrecipes.entities.Recipe;
import edu.galileo.android.androidrecipes.libs.EventBus;
import edu.galileo.android.androidrecipes.recipelist.events.RecipeListEvent;
import edu.galileo.android.androidrecipes.recipelist.ui.RecipeListView;

/**
 * Created by Lab1 on 26/07/2016.
 */
public class RecipeListPresenterImpl implements RecipeListPresenter {
    private EventBus eventBus;
    private RecipeListView view;
    private RecipeListInteractor listInteractor;
    private StoredRecipeListInteractor storedRecipeListInteractor;

    public RecipeListPresenterImpl(EventBus eventBus, RecipeListView view, RecipeListInteractor listInteractor, StoredRecipeListInteractor storedRecipeListInteractor) {
        this.eventBus = eventBus;
        this.view = view;
        this.listInteractor = listInteractor;
        this.storedRecipeListInteractor = storedRecipeListInteractor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        view = null;

    }

    @Override
    public void getRecipes() {
        listInteractor.execute();
    }

    @Override
    public void removeRecipe(Recipe recipe) {
        storedRecipeListInteractor.executeDelete(recipe);
    }

    @Override
    public void toogleFavorite(Recipe recipe) {
        boolean fav = recipe.getFavorite();
        recipe.setFavorite(!fav);
        storedRecipeListInteractor.executeUpdate(recipe);
    }

    @Override
    @Subscribe
    public void onEventMainThread(RecipeListEvent event) {
    if (view != null){
        switch (event.getType()){
            case RecipeListEvent.READ_EVENT:
                view.setRecipes(event.getRecipeList());
                break;
            case RecipeListEvent.UPDATE_EVENT:
                view.recipeUpdated();
                break;
            case RecipeListEvent.DELETE_EVENT:
                Recipe recipe = event.getRecipeList().get(0);
                view.recipeDeleted(recipe);
                break;
        }
    }
    }

    @Override
    public RecipeListView getView() {
        return this.view;
    }
}
