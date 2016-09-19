package edu.galileo.android.androidrecipes.recipelist.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.androidrecipes.FacebookRecipesApp;
import edu.galileo.android.androidrecipes.R;
import edu.galileo.android.androidrecipes.entities.Recipe;
import edu.galileo.android.androidrecipes.recipelist.RecipeListPresenter;
import edu.galileo.android.androidrecipes.recipelist.adapters.OnItemClickListener;
import edu.galileo.android.androidrecipes.recipelist.adapters.RecipesAdapters;
import edu.galileo.android.androidrecipes.recipelist.di.RecipeListComponent;
import edu.galileo.android.androidrecipes.recipemain.ui.RecipeMainActivity;

public class RecipeListActivity extends AppCompatActivity implements RecipeListView, OnItemClickListener{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private RecipesAdapters adapter;
    private RecipeListPresenter presenter;
    private RecipeListComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);
        
        setupToolbar();
        setupInjection();
        setupRecyclerView();

        presenter.onCreate();
        presenter.getRecipes();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_main){
            navigateToMainScreen();
            return true;
        }else if (id == R.id.action_show_all){
            presenter.showAll();
            return true;
        } else if (id == R.id.action_fav){
            presenter.showFav();
            return true;
        } else if (id == R.id.action_logout){
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FacebookRecipesApp app = (FacebookRecipesApp )getApplication();
        app.logout();
    }

    private void navigateToMainScreen() {
        startActivity(new Intent(this, RecipeMainActivity.class));
    }

    private void setupInjection() {
     FacebookRecipesApp app = (FacebookRecipesApp) getApplication();
        component = app.getRecipeListComponent(this, this, this);
        presenter = getPresenter();
        adapter = getAdapter();
    }

    private RecipesAdapters getAdapter() {
        return component.getAdapter();
    }

    private RecipeListPresenter getPresenter() {
        return component.getPresenter();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.toolbar)
    public void onToolbarClick(){
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void setRecipes(List<Recipe> data) {
        adapter.setRecipes(data);
    }

    @Override
    public void recipeUpdated() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void recipeDeleted(Recipe recipe) {
        adapter.removeRecipe(recipe);
    }

    @Override
    public void onFavClick(Recipe recipe) {
        presenter.toogleFavorite(recipe);
    }

    @Override
    public void onItemClick(Recipe recipe) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(recipe.getSourceURL()));
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Recipe recipe) {
        presenter.removeRecipe(recipe);
    }
}
