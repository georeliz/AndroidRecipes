package edu.galileo.android.androidrecipes.recipemain.di;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import edu.galileo.android.androidrecipes.libs.ImageLoader;
import edu.galileo.android.androidrecipes.libs.di.LibsModule;
import edu.galileo.android.androidrecipes.recipemain.RecipeMainPresenter;
import edu.galileo.android.androidrecipes.recipemain.ui.RecipeMainActivity;

/**
 * Created by Lab1 on 23/06/2016.
 */
@Singleton
@Component(modules ={RecipeMainModule.class, LibsModule.class})
public interface RecipeMainComponent {
    //void inject(RecipeMainActivity activity);
    ImageLoader getImageLoader();
    RecipeMainPresenter getPresenter();
}
