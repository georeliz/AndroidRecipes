package edu.galileo.android.androidrecipes.recipelist.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.Toolbar;

import com.facebook.FacebookActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.internal.ShadowExtractor;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import java.util.List;

import edu.galileo.android.androidrecipes.BaseTest;
import edu.galileo.android.androidrecipes.BuildConfig;
import edu.galileo.android.androidrecipes.R;
import edu.galileo.android.androidrecipes.entities.Recipe;
import edu.galileo.android.androidrecipes.libs.ImageLoader;
import edu.galileo.android.androidrecipes.login.ui.LoginActivity;
import edu.galileo.android.androidrecipes.recipelist.RecipeListPresenter;
import edu.galileo.android.androidrecipes.recipelist.adapters.OnItemClickListener;
import edu.galileo.android.androidrecipes.recipelist.adapters.RecipesAdapters;
import edu.galileo.android.androidrecipes.recipemain.ui.RecipeMainActivity;
import edu.galileo.android.androidrecipes.support.ShadowReciclerView;
import edu.galileo.android.androidrecipes.support.ShadowReciclerViewAdapter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Lab1 on 08/08/2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21,
        shadows = {ShadowReciclerView.class, ShadowReciclerViewAdapter.class})
public class RecipeListActivityTest extends BaseTest{
    @Mock
    private List<Recipe> recipeList;
    @Mock
    private Recipe recipe;
    @Mock
    private ImageLoader imageLoader;
    @Mock
    private RecipesAdapters adapters;
    @Mock
    private RecipeListPresenter presenter;


    private RecipeListView view;
    private RecipeListActivity activity;
    private OnItemClickListener onItemClickListener;

    private ShadowActivity shadowActivity;
    private ActivityController<RecipeListActivity> controller;
    private ShadowReciclerViewAdapter shadowAdapter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RecipeListActivity recipeListActivity = new RecipeListActivity(){
            @Override
            public void setTheme(int resid) {
                super.setTheme(R.style.AppTheme_NoActionBar);
            }

            public RecipesAdapters getAdapter(){return adapters;}

            public RecipeListPresenter getPresenter(){return presenter;}
        };

        controller = ActivityController.of(Robolectric.getShadowsAdapter(), recipeListActivity).create().visible();
        activity = controller.get();
        view = (RecipeListView)activity;
        onItemClickListener = (OnItemClickListener) activity;

        shadowActivity = shadowOf(activity);
    }

    @Test
    public void testOnCreate_ShouldCallPresenter() throws Exception {
        verify(presenter).onCreate();
        verify(presenter).getRecipes();
    }

    @Test
    public void testOnDestroy_ShouldCallPresenter() throws Exception {
        controller.destroy();
        verify(presenter).onDestroy();
    }

    @Test
    public void testLogoutMenuClicked_ShouldLaunchLoginActivity() throws Exception {
        shadowActivity.clickMenuItem(R.id.action_logout);
        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(new ComponentName(activity, LoginActivity.class), intent.getComponent());
    }

    @Test
    public void testMainMenuClicked_ShouldLaunchRecipeMainActivity() throws Exception {
        shadowActivity.clickMenuItem(R.id.action_main);
        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(new ComponentName(activity, RecipeMainActivity.class), intent.getComponent());
    }

    @Test
    public void testSetRecipes_ShouldSetInAdapter() throws Exception {
        view.setRecipes(recipeList);
        verify(adapters).setRecipes(recipeList);
    }

    @Test
    public void testRecipeUpdated_ShouldUpdateAdapter() throws Exception {
        view.recipeUpdated();
        verify(adapters).notifyDataSetChanged();
    }

    @Test
    public void testRecipeDeleted_ShouldUpdateAdapter() throws Exception {
        view.recipeDeleted(recipe);
        verify(adapters).removeRecipe(recipe);
    }

    @Test
    public void testOnRecyclerViewScroll_ShouldChangeScrollPosition() throws Exception {
        int scrollPosition = 1;

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
        ShadowReciclerView shadowRecyclerView = (ShadowReciclerView) ShadowExtractor.extract(recyclerView);

        recyclerView.smoothScrollToPosition(scrollPosition);
        assertEquals(scrollPosition, shadowRecyclerView.getSmoothScrollPosition());

    }
    @Test
    public void testOnToolbarClicked_RecyclerViewShouldScrollToTop() throws Exception {
        int scrollPosition = 1;
        int topScrollPosition = 0;
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
        ShadowReciclerView shadowRecyclerView = (ShadowReciclerView) ShadowExtractor.extract(recyclerView);
        recyclerView.smoothScrollToPosition(scrollPosition);



        toolbar.performClick();
        assertEquals(topScrollPosition, shadowRecyclerView.getSmoothScrollPosition());
    }

    @Test
    public void testRecyclerViewItemClicked_ShouldStartViewActivity() throws Exception {
        int positionToClick = 0;
        setUpShadowAdapter(positionToClick);

        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.perfomItemClick(positionToClick);

        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(Intent.ACTION_VIEW, intent.getAction());
        assertEquals(recipeList.get(positionToClick).getSourceURL(), intent.getDataString() );
    }

    @Test
    public void testRecyclerViewFavoriteClicked_ShouldCallPresenter() throws Exception {
        int positionToClick = 0;
        setUpShadowAdapter(positionToClick);

        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.perfomItemClickOverViewInHolder(positionToClick, R.id.imgFav);

         verify(presenter).toogleFavorite(recipe);
    }

    @Test
    public void testRecyclerViewRemoveClicked_ShouldCallPresenter() throws Exception {
        int positionToClick = 0;
        setUpShadowAdapter(positionToClick);

        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.perfomItemClickOverViewInHolder(positionToClick, R.id.imgDelete);

        verify(presenter).removeRecipe(recipe);
    }

    @Test
    public void testRecyclerViewFbShareClicked_ShouldStartFbActivity() throws Exception {
        int positionToClick = 0;
        setUpShadowAdapter(positionToClick);

        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.perfomItemClickOverViewInHolder(positionToClick, R.id.fbShare);

        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(new ComponentName(RuntimeEnvironment.application, FacebookActivity.class), intent.getComponent() );
    }

    @Test
    public void testRecyclerViewFbSendClicked_ShouldStartFbActivity() throws Exception {
        int positionToClick = 0;
        setUpShadowAdapter(positionToClick);

        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.perfomItemClickOverViewInHolder(positionToClick, R.id.fbSend);

        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(new ComponentName(RuntimeEnvironment.application, FacebookActivity.class), intent.getComponent() );
    }


    private void setUpShadowAdapter(int positionToClick){
        when(recipe.getSourceURL()).thenReturn("http://galileo.edu");
        when(recipeList.get(positionToClick)).thenReturn(recipe);

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
        RecipesAdapters adapterPupulated = new RecipesAdapters(recipeList, imageLoader, onItemClickListener);
        recyclerView.setAdapter(adapterPupulated);
        shadowAdapter = (ShadowReciclerViewAdapter) ShadowExtractor.extract(recyclerView.getAdapter());
    }
}