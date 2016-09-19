package edu.galileo.android.androidrecipes.recipelist;

import com.raizlabs.android.dbflow.list.FlowCursorList;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.Arrays;
import java.util.List;

import edu.galileo.android.androidrecipes.entities.Recipe;
import edu.galileo.android.androidrecipes.entities.Recipe_Table;
import edu.galileo.android.androidrecipes.libs.EventBus;
import edu.galileo.android.androidrecipes.recipelist.events.RecipeListEvent;
import edu.galileo.android.androidrecipes.recipemain.events.RecipeMainEvent;

/**
 * Created by Lab1 on 26/07/2016.
 */
public class RecipeListRepositoryImpl implements RecipeListRepository {
    private EventBus eventBus;

    public RecipeListRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getSavedRecipes() {
        FlowCursorList<Recipe> storedRecipes = new FlowCursorList<Recipe>(false, Recipe.class);
        post(RecipeListEvent.READ_EVENT, storedRecipes.getAll());
        storedRecipes.close();
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        recipe.update();
        post();
    }

    @Override
    public void removeRecipe(Recipe recipe) {
        recipe.delete();
        post(RecipeListEvent.DELETE_EVENT, Arrays.asList(recipe));
    }

    @Override
    public void getFavoritesRecipes() {
        List<Recipe> recipes = new Select().from(Recipe.class).where(Recipe_Table.favorite.is(true)).queryList();
        post(RecipeListEvent.READ_EVENT, recipes);
    }

    private void post(int type, List<Recipe> recipeList){
        RecipeListEvent event = new RecipeListEvent();
        event.setType(type);
        event.setRecipeList(recipeList);
        eventBus.post(event);
    }

    private void post(){
      post(RecipeListEvent.UPDATE_EVENT, null);

    }
}
