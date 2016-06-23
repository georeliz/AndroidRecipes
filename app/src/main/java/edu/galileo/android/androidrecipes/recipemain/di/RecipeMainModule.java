package edu.galileo.android.androidrecipes.recipemain.di;

import android.support.v7.widget.RecyclerView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.androidrecipes.api.RecipeClient;
import edu.galileo.android.androidrecipes.api.RecipeService;
import edu.galileo.android.androidrecipes.libs.EventBus;
import edu.galileo.android.androidrecipes.recipemain.GetNextRecipeInteractor;
import edu.galileo.android.androidrecipes.recipemain.GetNextRecipeInteractorImpl;
import edu.galileo.android.androidrecipes.recipemain.RecipeMainPresenter;
import edu.galileo.android.androidrecipes.recipemain.RecipeMainPresenterImpl;
import edu.galileo.android.androidrecipes.recipemain.RecipeMainRepository;
import edu.galileo.android.androidrecipes.recipemain.RecipeMainRepositoryImpl;
import edu.galileo.android.androidrecipes.recipemain.SaveRecipeInteractor;
import edu.galileo.android.androidrecipes.recipemain.SaveRecipeInteractorImpl;
import edu.galileo.android.androidrecipes.recipemain.ui.RecipeMainView;

/**
 * Created by Lab1 on 23/06/2016.
 */
@Module
public class RecipeMainModule {
    RecipeMainView view;
    public RecipeMainModule(RecipeMainView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    RecipeMainView providesRecipeMainActivity(){
        return this.view;
    }

    @Provides
    @Singleton
    RecipeMainPresenter providesRecipeMainPresenter(EventBus eventBus, RecipeMainView view, SaveRecipeInteractor saveRecipeInteractor, GetNextRecipeInteractor nextRecipeInteractor) {
        return new RecipeMainPresenterImpl(eventBus, view, saveRecipeInteractor, nextRecipeInteractor);
    }

    @Provides
    @Singleton
    SaveRecipeInteractor providesSaveRecipeInteractor(RecipeMainRepository repository) {
        return new SaveRecipeInteractorImpl(repository);
    }

    @Provides
    @Singleton
    GetNextRecipeInteractor providesGetNextRecipeInteractor(RecipeMainRepository repository) {
        return new GetNextRecipeInteractorImpl(repository);
    }

    @Provides
    @Singleton
    RecipeMainRepository providesRecipeMainRepository(EventBus eventBus, RecipeService recipeService) {
        return new RecipeMainRepositoryImpl(eventBus, recipeService);
    }

    @Provides
    @Singleton
    RecipeService providesRecipeService() {
        return new RecipeClient().getRecipeService();
    }
}
