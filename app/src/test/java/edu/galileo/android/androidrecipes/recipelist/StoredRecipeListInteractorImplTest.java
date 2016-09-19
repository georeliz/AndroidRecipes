package edu.galileo.android.androidrecipes.recipelist;

import org.bouncycastle.jce.provider.symmetric.ARC4;
import org.junit.Test;
import org.mockito.Mock;

import edu.galileo.android.androidrecipes.BaseTest;
import edu.galileo.android.androidrecipes.entities.Recipe;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by Lab1 on 04/08/2016.
 */
public class StoredRecipeListInteractorImplTest extends BaseTest{
    @Mock
    private RecipeListRepository repository;
    @Mock
    private Recipe recipe;
    private StoredRecipeListInteractorImpl interactor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        interactor = new StoredRecipeListInteractorImpl(repository);
    }

    @Test
    public void testExecuteUpdate_CallsRepository() throws Exception {
        interactor.executeUpdate(recipe);
        verify(repository).updateRecipe(recipe);
    }

    @Test
    public void testExecuteDelete_CallsRepository() throws Exception {
        interactor.executeDelete(recipe);
        verify(repository).removeRecipe(recipe);
    }
}