package edu.galileo.android.androidrecipes.entities;

import org.hamcrest.core.Is;
import org.junit.Test;

import edu.galileo.android.androidrecipes.BaseTest;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

/**
 * Created by Lab1 on 08/08/2016.
 */
public class RecipeTest extends BaseTest{

    private final static String RECIPE_ID = "recipeId";
    private final static String TITLE = "title";
    private final static String IMAGE_URL = "imageUrl";
    private final static String SOURCE_URL = "sourceUrl";
    private final static boolean FAVORITE = false;
    private Recipe recipe;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        recipe = new Recipe();
    }

    @Test
    public void testGetSet() throws Exception {
        recipe.setRecipeId(RECIPE_ID);
        recipe.setTitle(TITLE);
        recipe.setImageURL(IMAGE_URL);
        recipe.setSourceURL(SOURCE_URL);
        recipe.setFavorite(FAVORITE);

        assertThat(recipe.getRecipeId(), is(RECIPE_ID));
        assertThat(recipe.getTitle(), is(TITLE));
        assertThat(recipe.getImageURL(), is(IMAGE_URL));
        assertThat(recipe.getSourceURL(), is(SOURCE_URL));
        assertThat(recipe.getFavorite(), is(FAVORITE));
    }

    @Test
    public void testEquals_true() throws Exception {
        recipe.setRecipeId(RECIPE_ID);

        Recipe other = new Recipe();
        other.setRecipeId(RECIPE_ID);

        boolean result = recipe.equals(other);
        assertTrue(result);
    }

    @Test
    public void testEquals_false() throws Exception {
        recipe.setRecipeId(RECIPE_ID);

        Recipe other = new Recipe();
        other.setRecipeId("other");

        String other2 = "";

        boolean result = recipe.equals(other2);
        assertFalse(result);
    }
}