package edu.galileo.android.androidrecipes.recipemain;

import org.junit.Test;
import org.mockito.Mock;

import edu.galileo.android.androidrecipes.BaseTest;
import edu.galileo.android.androidrecipes.entities.Recipe;
import edu.galileo.android.androidrecipes.libs.EventBus;
import edu.galileo.android.androidrecipes.recipemain.events.RecipeMainEvent;
import edu.galileo.android.androidrecipes.recipemain.ui.RecipeMainView;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Lab1 on 29/07/2016.
 */
public class RecipeMainPresenterImplTest extends BaseTest {
    @Mock
    private EventBus eventBus;
    @Mock
    private RecipeMainView view;
    @Mock
    private SaveRecipeInteractor saveRecipeInteractor;
    @Mock
    private GetNextRecipeInteractor nextRecipeInteractor;
    @Mock
    Recipe recipe = new Recipe();
    @Mock
    RecipeMainEvent event;

    private RecipeMainPresenterImpl presenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        presenter = new RecipeMainPresenterImpl(eventBus, view, saveRecipeInteractor, nextRecipeInteractor);
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
    public void testSaveRecipe_executeSaveInteractor() throws Exception {
        presenter.saveRecipe(recipe);

        assertNotNull(presenter.getView());
        verify(view).saveAnimation();
        verify(view).hideElements();
        verify(view).showProgressBar();
        verify(saveRecipeInteractor).execute(recipe);
    }

    @Test
    public void testDismissRecipe_executeGetNextRecipeInteractor() throws Exception {
        presenter.dismissRecipe();
        assertNotNull(presenter.getView());
        verify(view).dismissAnimation();
    }

    @Test
    public void testGetNextRecipe_executeGetNextRecipeInteractor() throws Exception {
        presenter.getNextRecipe();
        assertNotNull(presenter.getView());
        verify(view).hideElements();
        verify(view).showProgressBar();
        verify(nextRecipeInteractor).execute();
    }

    @Test
    public void testOnEvent_hasError() throws Exception {
        String erroMsg = "error";

        when(event.getError()).thenReturn(erroMsg);
        presenter.onEventMainThread(event);

        assertNotNull(presenter.getView());
        verify(view).hideProgressBar();
        verify(view).onGetRecipeError(event.getError());
    }

    @Test
    public void testOnNextEvent_setRecipeOnView() throws Exception {
        when(event.getType()).thenReturn(RecipeMainEvent.NEXT_EVENT);
        when(event.getRecipe()).thenReturn(recipe);

        presenter.onEventMainThread(event);

        assertNotNull(presenter.getView());
        verify(view).setRecipe(event.getRecipe());

    }

    @Test
    public void testOnSaveEvent_notifyViewAndCallGetNextRecipe() throws Exception {
        when(event.getType()).thenReturn(RecipeMainEvent.SAVE_EVENT);


        presenter.onEventMainThread(event);

        assertNotNull(presenter.getView());
        verify(view).onRecipeSaved();
        verify(nextRecipeInteractor).execute();
    }

    @Test
    public void testImageReady_updateUI() throws Exception {
        presenter.imageReady();
        
        assertNotNull(presenter.getView());
        verify(view).hideProgressBar();
        verify(view).showElements();
    }

    @Test
    public void testImageError_updateUI() throws Exception {
        String error = "error";

        presenter.imageError(error);

        assertNotNull(presenter.getView());
        verify(view).onGetRecipeError(error);
    }

    @Test
    public void testGetView_returnView() throws Exception {
        assertEquals(view,presenter.getView());
    }
}
