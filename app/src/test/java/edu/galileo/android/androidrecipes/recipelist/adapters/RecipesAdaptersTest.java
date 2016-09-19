package edu.galileo.android.androidrecipes.recipelist.adapters;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.share.model.ShareContent;
import com.facebook.share.widget.SendButton;
import com.facebook.share.widget.ShareButton;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.internal.ShadowExtractor;
import org.robolectric.shadows.ShadowExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.galileo.android.androidrecipes.BaseTest;
import edu.galileo.android.androidrecipes.BuildConfig;
import edu.galileo.android.androidrecipes.R;
import edu.galileo.android.androidrecipes.entities.Recipe;
import edu.galileo.android.androidrecipes.libs.ImageLoader;
import edu.galileo.android.androidrecipes.support.ShadowReciclerViewAdapter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Lab1 on 05/08/2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21,
        shadows = {ShadowReciclerViewAdapter.class})
public class RecipesAdaptersTest extends BaseTest {
    @Mock
    private List<Recipe> recipeList;
    @Mock
    private ImageLoader imageLoader;
    @Mock
    private OnItemClickListener onItemClickListener;
    @Mock
    private Recipe recipe;

    private String URL;
    private RecipesAdapters adapters;
    private ShadowReciclerViewAdapter shadowReciclerViewAdapter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        URL = "http://galileo.edu";
        when(recipe.getSourceURL()).thenReturn(URL);

        adapters = new RecipesAdapters(recipeList, imageLoader, onItemClickListener);
        shadowReciclerViewAdapter = (ShadowReciclerViewAdapter) ShadowExtractor.extract(adapters);

        Activity activity = Robolectric.setupActivity(Activity.class);
        RecyclerView recyclerView = new RecyclerView(activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        recyclerView.setAdapter(adapters);
    }

    @Test
    public void testSetRecipes_ItemCountMatches() throws Exception {
        int itemCount = 5;
        when(recipeList.size()).thenReturn(itemCount);
        adapters.setRecipes(recipeList);

        assertEquals(itemCount, adapters.getItemCount());
    }

    @Test
    public void testRemoveRecipe_IsRemovedFromAdapter() throws Exception {
        adapters.removeRecipe(recipe);
        verify(recipeList).remove(recipe);
    }

    @Test
    public void testOnItemClick_ShouldCallListener() throws Exception {
        int positionToClick = 3;
        when(recipeList.get(positionToClick)).thenReturn(recipe);

        shadowReciclerViewAdapter.itemVisible(positionToClick);
        shadowReciclerViewAdapter.perfomItemClick(positionToClick);

        verify(onItemClickListener).onItemClick(recipe);
    }

    @Test
    public void testViewHolder_ShouldRenderTitle() throws Exception {
        int positionToShow = 0;
        String recipeTitle = "title";
        when(recipe.getTitle()).thenReturn(recipeTitle);
        when(recipeList.get(positionToShow)).thenReturn(recipe);

        shadowReciclerViewAdapter.itemVisible(positionToShow);

        View view = shadowReciclerViewAdapter.getViewHolderPosition(positionToShow);
        TextView txtRecipeName = (TextView) view.findViewById(R.id.txtRecipeName);

        assertEquals(recipeTitle, txtRecipeName.getText().toString());
    }

    @Test
    public void testOnFavoriteClick_ShouldCallListener() throws Exception {
        int positionToClick = 0;
        boolean favorite = true;
        when(recipe.getFavorite()).thenReturn(favorite);
        when(recipeList.get(positionToClick)).thenReturn(recipe);

        shadowReciclerViewAdapter.itemVisible(positionToClick);
        shadowReciclerViewAdapter.perfomItemClickOverViewInHolder(positionToClick, R.id.imgFav);

        View view = shadowReciclerViewAdapter.getViewHolderPosition(positionToClick);
        ImageButton imgFav = (ImageButton) view.findViewById(R.id.imgFav);

        assertEquals(favorite, imgFav.getTag());
        verify(onItemClickListener).onFavClick(recipe);


    }

    @Test
    public void testOnNonFavoriteclick_ShouldCallListener() throws Exception {
        int positionToClick = 0;
        boolean favorite = false;
        when(recipe.getFavorite()).thenReturn(favorite);
        when(recipeList.get(positionToClick)).thenReturn(recipe);

        shadowReciclerViewAdapter.itemVisible(positionToClick);
        shadowReciclerViewAdapter.perfomItemClickOverViewInHolder(positionToClick, R.id.imgFav);

        View view = shadowReciclerViewAdapter.getViewHolderPosition(positionToClick);
        ImageButton imgFav = (ImageButton) view.findViewById(R.id.imgFav);

        assertEquals(favorite, imgFav.getTag());

        verify(onItemClickListener).onFavClick(recipe);
    }

    @Test
    public void testOnDeleteClick_ShouldCallListener() throws Exception {
        int positionToClick = 0;
        when(recipeList.get(positionToClick)).thenReturn(recipe);

        shadowReciclerViewAdapter.itemVisible(positionToClick);
        shadowReciclerViewAdapter.perfomItemClickOverViewInHolder(positionToClick, R.id.imgDelete);

        verify(onItemClickListener).onDeleteClick(recipe);
    }

    @Test
    public void testFBShareBind_shareContentSet() throws Exception {
        int positionToShow = 0;
        when(recipeList.get(positionToShow)).thenReturn(recipe);

        shadowReciclerViewAdapter.itemVisible(positionToShow);

        View view = shadowReciclerViewAdapter.getViewHolderPosition(positionToShow);
        ShareButton fbShare = (ShareButton) view.findViewById(R.id.fbShare);

        ShareContent shareContent = fbShare.getShareContent();
        assertNotNull(shareContent);
        assertEquals(URL, shareContent.getContentUrl().toString());

    }

    @Test
    public void testFBSendBind_shareContentSet() throws Exception {
        int positionToShow = 0;
        when(recipeList.get(positionToShow)).thenReturn(recipe);

        shadowReciclerViewAdapter.itemVisible(positionToShow);

        View view = shadowReciclerViewAdapter.getViewHolderPosition(positionToShow);
        SendButton fbSend = (SendButton) view.findViewById(R.id.fbSend);

        ShareContent shareContent = fbSend.getShareContent();
        assertNotNull(shareContent);
        assertEquals(URL, shareContent.getContentUrl().toString());

    }
}