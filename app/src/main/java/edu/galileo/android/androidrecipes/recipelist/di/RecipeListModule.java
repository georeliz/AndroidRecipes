package edu.galileo.android.androidrecipes.recipelist.di;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.androidrecipes.entities.Recipe;
import edu.galileo.android.androidrecipes.libs.EventBus;
import edu.galileo.android.androidrecipes.libs.ImageLoader;
import edu.galileo.android.androidrecipes.recipelist.RecipeListInteractor;
import edu.galileo.android.androidrecipes.recipelist.RecipeListInteractorImpl;
import edu.galileo.android.androidrecipes.recipelist.RecipeListPresenter;
import edu.galileo.android.androidrecipes.recipelist.RecipeListPresenterImpl;
import edu.galileo.android.androidrecipes.recipelist.RecipeListRepository;
import edu.galileo.android.androidrecipes.recipelist.RecipeListRepositoryImpl;
import edu.galileo.android.androidrecipes.recipelist.StoredRecipeListInteractor;
import edu.galileo.android.androidrecipes.recipelist.StoredRecipeListInteractorImpl;
import edu.galileo.android.androidrecipes.recipelist.adapters.OnItemClickListener;
import edu.galileo.android.androidrecipes.recipelist.adapters.RecipesAdapters;
import edu.galileo.android.androidrecipes.recipelist.ui.RecipeListView;

/**
 * Created by Lab1 on 26/07/2016.
 */
@Module
public class RecipeListModule {
    RecipeListView view;
    OnItemClickListener clickListener;

    public RecipeListModule(RecipeListView view, OnItemClickListener clickListener) {
        this.view = view;
        this.clickListener = clickListener;
    }

    @Provides
    @Singleton
    RecipeListView providesRecipeListView(){
        return this.view;
    }

    @Provides
    @Singleton
    RecipeListPresenter providesRecipeListPresenter(EventBus eventBus, RecipeListView view, RecipeListInteractor listInteractor, StoredRecipeListInteractor storedRecipeListInteractor){
        return new RecipeListPresenterImpl(eventBus, view, listInteractor, storedRecipeListInteractor);
    }

    @Provides
    @Singleton
    StoredRecipeListInteractor providesStoredRecipeListInteractor(RecipeListRepository repository){
        return new StoredRecipeListInteractorImpl(repository);
    }

    @Provides
    @Singleton
    RecipeListInteractor providesRecipeListInteractor(RecipeListRepository repository){
        return new RecipeListInteractorImpl(repository);
    }

    @Provides
    @Singleton
    RecipeListRepository providesRecipeListRepository(EventBus eventBus){
        return new RecipeListRepositoryImpl(eventBus);
    }

    @Provides
    @Singleton
    RecipesAdapters providesRecipesAdapters(List<Recipe> recipeList, ImageLoader imageLoader, OnItemClickListener onItemClickListener){
        return new RecipesAdapters(recipeList, imageLoader, onItemClickListener);
    }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener(){
        return this.clickListener;
    }

    @Provides
    @Singleton
    List<Recipe> providesListRecipe(){
        return new ArrayList<Recipe>();
    }

}
