package edu.galileo.android.androidrecipes.recipelist;

import com.raizlabs.android.dbflow.sql.language.Select;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import edu.galileo.android.androidrecipes.BaseTest;
import edu.galileo.android.androidrecipes.BuildConfig;
import edu.galileo.android.androidrecipes.FacebookRecipesApp;
import edu.galileo.android.androidrecipes.entities.Recipe;
import edu.galileo.android.androidrecipes.entities.Recipe_Table;
import edu.galileo.android.androidrecipes.libs.EventBus;
import edu.galileo.android.androidrecipes.recipelist.events.RecipeListEvent;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by Lab1 on 04/08/2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class RecipeListRepositoryImplTest extends BaseTest{

    @Mock
    private EventBus eventBus;
    private RecipeListRepositoryImpl repository;
    private FacebookRecipesApp app;
    private ArgumentCaptor<RecipeListEvent> recipeListEventArgumentCaptor;
    private static final int RECIPES_IN_DELETE_EVENT = 1;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        repository = new RecipeListRepositoryImpl(eventBus);
        app = (FacebookRecipesApp) RuntimeEnvironment.application;
        recipeListEventArgumentCaptor = ArgumentCaptor.forClass(RecipeListEvent.class);

        app.onCreate();
    }

    @After
    public void tearDown() throws Exception {
        app.onTerminate();
    }

    @Test
    public void testGetSavedRecipes_eventPosted() throws Exception {
        int recipesToStore = 5;
        Recipe currentRecipe;
        List<Recipe> testRecipeList = new ArrayList<>();
        for (int i = 0; i < recipesToStore; i++){
             currentRecipe = new Recipe();
            currentRecipe.setRecipeId("id"+ i);
            currentRecipe.save();
            testRecipeList.add(currentRecipe);
        }
        List<Recipe> recipesFromDB = new Select().from(Recipe.class).queryList();
        repository.getSavedRecipes();

        verify(eventBus).post(recipeListEventArgumentCaptor.capture());
        RecipeListEvent event = recipeListEventArgumentCaptor.getValue();

        assertEquals(RecipeListEvent.READ_EVENT, event.getType());
        assertEquals(recipesFromDB, event.getRecipeList());

        for (Recipe recipe : testRecipeList){
            recipe.delete();
        }
    }

    @Test
    public void testUpdateRecipe() throws Exception {

    }

    @Test
    public void testRemoveRecipe() throws Exception {
        String newRecipeId = "id1";
        String titleBefore = "title before update";
        String titleAfter = "title after update";
        Recipe recipe = new Recipe();
        recipe.setRecipeId(newRecipeId);
        recipe.setTitle(titleBefore);
        recipe.save();
        recipe.setTitle(titleAfter);

        repository.updateRecipe(recipe);

        Recipe recipeFromDB = new Select().from(Recipe.class).where(Recipe_Table.recipeId.is(newRecipeId)).querySingle();
        assertEquals(titleAfter, recipeFromDB.getTitle());
        verify(eventBus).post(recipeListEventArgumentCaptor.capture());
        RecipeListEvent event = recipeListEventArgumentCaptor.getValue();

        assertEquals(RecipeListEvent.UPDATE_EVENT, event.getType());
        recipe.delete();
    }

    @Test
    public void testGetFavoritesRecipes() throws Exception {
        String newRecipeId = "id1";
        Recipe recipe = new Recipe();
        recipe.setRecipeId(newRecipeId);
        recipe.save();

        repository.removeRecipe(recipe);
        assertFalse(recipe.exists());
        verify(eventBus).post(recipeListEventArgumentCaptor.capture());

        RecipeListEvent event = recipeListEventArgumentCaptor.getValue();
        assertEquals(RecipeListEvent.DELETE_EVENT, event.getType());
        assertEquals(RECIPES_IN_DELETE_EVENT, event.getRecipeList().size());
        assertEquals(recipe, event.getRecipeList().get(0));

        recipe.delete();

    }
}