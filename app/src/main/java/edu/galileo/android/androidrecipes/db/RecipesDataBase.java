package edu.galileo.android.androidrecipes.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Gerson on 22/06/2016.
 */
@Database(name = RecipesDataBase.NAME, version = RecipesDataBase.VERSION)
public class RecipesDataBase {
    public static final int VERSION = 1;
    public static final String NAME = "Recipes";
}
