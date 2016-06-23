package edu.galileo.android.androidrecipes.recipemain;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.androidrecipes.entities.Recipe;
import edu.galileo.android.androidrecipes.libs.EventBus;
import edu.galileo.android.androidrecipes.recipemain.events.RecipeMainEvent;
import edu.galileo.android.androidrecipes.recipemain.ui.RecipeMainView;

/**
 * Created by Lab1 on 23/06/2016.
 */
public class RecipeMainPresenterImpl implements RecipeMainPresenter {
    private EventBus eventBus;
    private RecipeMainView view;
    private SaveRecipeInteractor saveRecipeInteractor;
    private GetNextRecipeInteractor nextRecipeInteractor;

    public RecipeMainPresenterImpl(EventBus eventBus, RecipeMainView view, SaveRecipeInteractor saveRecipeInteractor, GetNextRecipeInteractor nextRecipeInteractor) {
        this.eventBus = eventBus;
        this.view = view;
        this.saveRecipeInteractor = saveRecipeInteractor;
        this.nextRecipeInteractor = nextRecipeInteractor;
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
    public void dismissRecipe() {
        if (this.view != null){
            view.dismissAnimation();
        }
        getNextRecipe();

    }

    @Override
    public void getNextRecipe() {
        if (this.view != null){
            view.hideElements();
            view.showProgressBar();
        }
        nextRecipeInteractor.execute();

    }

    @Override
    public void saveRecipe(Recipe recipe) {
        if (this.view != null){
            view.saveAnimation();
            view.hideElements();
            view.showProgressBar();
        }
        saveRecipeInteractor.execute(recipe);

    }

    @Override
    @Subscribe
    public void onEventMainThread(RecipeMainEvent event) {
        if (this.view != null){
            String error = event.getError();
            if (error != null){
                view.hideProgressBar();
                view.onGetRecipeError(error);
            }else {
                if (event.getType() == RecipeMainEvent.NEXT_EVENT){
                    view.setRecipe(event.getRecipe());
                }else if (event.getType() == RecipeMainEvent.SAVE_EVENT){
                    view.onRecipeSaved();
                    nextRecipeInteractor.execute();

                }
            }
        }

    }

    @Override
    public void imageError(String error) {
        if (this.view != null){
            view.onGetRecipeError(error);
        }

    }

    @Override
    public void imageReady() {
        if (this.view != null){
            view.hideProgressBar();
            view.showElements();
        }

    }

    @Override
    public RecipeMainView getView() {
        return this.view;
    }
}
