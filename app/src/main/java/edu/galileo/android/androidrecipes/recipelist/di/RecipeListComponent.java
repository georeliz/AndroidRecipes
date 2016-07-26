package edu.galileo.android.androidrecipes.recipelist.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.androidrecipes.libs.di.LibsModule;
import edu.galileo.android.androidrecipes.recipelist.RecipeListPresenter;
import edu.galileo.android.androidrecipes.recipelist.adapters.RecipesAdapters;
import edu.galileo.android.androidrecipes.recipemain.RecipeMainPresenter;

/**
 * Created by Lab1 on 26/07/2016.
 */
@Singleton
@Component(modules = {RecipeListModule.class, LibsModule.class})
public interface RecipeListComponent {
    RecipesAdapters getAdapter();
    RecipeListPresenter getPresenter();
}