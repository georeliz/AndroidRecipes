package edu.galileo.android.androidrecipes.recipelist;

import org.junit.Test;
import org.mockito.Mock;

import edu.galileo.android.androidrecipes.BaseTest;

import static org.mockito.Mockito.verify;

/**
 * Created by Lab1 on 04/08/2016.
 */
public class RecipeListInteractorImplTest extends BaseTest {
    @Mock
    private RecipeListRepository repository;
    private RecipeListInteractorImpl interactor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        interactor = new RecipeListInteractorImpl(repository);
    }

    @Test
    public void testExecute_ShouldCallRepository() throws Exception {
        interactor.execute();
        verify(repository).getSavedRecipes();
    }


}
