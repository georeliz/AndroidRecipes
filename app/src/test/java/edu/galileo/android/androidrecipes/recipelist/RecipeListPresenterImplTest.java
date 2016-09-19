package edu.galileo.android.androidrecipes.recipelist;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.Arrays;

import edu.galileo.android.androidrecipes.BaseTest;
import edu.galileo.android.androidrecipes.entities.Recipe;
import edu.galileo.android.androidrecipes.libs.EventBus;
import edu.galileo.android.androidrecipes.recipelist.events.RecipeListEvent;
import edu.galileo.android.androidrecipes.recipelist.ui.RecipeListView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Lab1 on 04/08/2016.
 */
public class RecipeListPresenterImplTest extends BaseTest {
    @Mock
    private EventBus eventBus;
    @Mock
    private RecipeListView view;
    @Mock
    private RecipeListInteractor listInteractor;
    @Mock
    private StoredRecipeListInteractor storedRecipeListInteractor;
    @Mock
    private Recipe recipe;
    @Mock
    private RecipeListEvent event;

    private RecipeListPresenterImpl presenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        presenter = new RecipeListPresenterImpl(eventBus, view, listInteractor, storedRecipeListInteractor);
    }

    @Test
    public void testOnCreate_subscribedToEventBus() throws Exception {
        presenter.onCreate();
        verify(eventBus).register(presenter);
    }

    @Test
    public void testOnDestroy_UnsubscribedToEventBus() throws Exception {
        presenter.onDestroy();
        verify(eventBus).unregister(presenter);
        assertNull(presenter.getView());
    }

    @Test
    public void testGetRecipes_ExecuteListInteractor() throws Exception {
        presenter.getRecipes();
        verify(listInteractor).execute();
    }

    @Test
    public void testRemoveRecipe_ExecuteStoredInteractor() throws Exception {
        presenter.removeRecipe(recipe);
        verify(storedRecipeListInteractor).executeDelete(recipe);
    }

    @Test
    public void testToggleFavorite_True() throws Exception {
        Recipe recipe = new Recipe();
        boolean favorite = true;
        recipe.setFavorite(favorite);

        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        presenter.toogleFavorite(recipe);
        verify(storedRecipeListInteractor).executeUpdate(recipeArgumentCaptor.capture());

        assertEquals(!favorite, recipeArgumentCaptor.getValue().getFavorite());
    }

    @Test
    public void testToggleFavorite_False() throws Exception {
        Recipe recipe = new Recipe();
        boolean favorite = false;
        recipe.setFavorite(favorite);

        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        presenter.toogleFavorite(recipe);
        verify(storedRecipeListInteractor).executeUpdate(recipeArgumentCaptor.capture());

        assertEquals(!favorite, recipeArgumentCaptor.getValue().getFavorite());
    }

    @Test
    public void testOnReadEvent_SetRecipesOnView() throws Exception {
        when(event.getType()).thenReturn(RecipeListEvent.READ_EVENT);
        when(event.getRecipeList()).thenReturn(Arrays.asList(recipe));

        presenter.onEventMainThread(event);
        assertNotNull(presenter.getView());
        verify(view).setRecipes(Arrays.asList(recipe));
    }

    @Test
    public void testOnUpdateEvent_callUpdateOnView() throws Exception {
        when(event.getType()).thenReturn(RecipeListEvent.UPDATE_EVENT);


        presenter.onEventMainThread(event);
        assertNotNull(presenter.getView());
        verify(view).recipeUpdated();
    }

    @Test
    public void testOnDeleteEvent_removesFromView() throws Exception {
        when(event.getType()).thenReturn(RecipeListEvent.DELETE_EVENT);
        when(event.getRecipeList()).thenReturn(Arrays.asList(recipe));


        presenter.onEventMainThread(event);
        assertNotNull(presenter.getView());
        verify(view).recipeDeleted(recipe);
    }

    @Test
    public void testGetView_returnsView() throws Exception {
        assertEquals(view, presenter.getView());
    }
}
