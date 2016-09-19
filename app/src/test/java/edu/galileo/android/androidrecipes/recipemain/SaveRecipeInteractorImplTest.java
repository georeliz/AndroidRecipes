package edu.galileo.android.androidrecipes.recipemain;

import org.junit.Test;
import org.mockito.Mock;

import edu.galileo.android.androidrecipes.BaseTest;
import edu.galileo.android.androidrecipes.entities.Recipe;

import static org.mockito.Mockito.verify;

/**
 * Created by Lab1 on 01/08/2016.
 */
public class SaveRecipeInteractorImplTest extends BaseTest {
    @Mock
    private RecipeMainRepository repository;
    @Mock
    private Recipe recipe;
    private SaveRecipeInteractor interactor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        interactor = new SaveRecipeInteractorImpl(repository);
    }

    @Test
    public void testExecute_callRepository() throws Exception {
        interactor.execute(recipe);
        verify(repository).saveRecipe(recipe);
    }
}
