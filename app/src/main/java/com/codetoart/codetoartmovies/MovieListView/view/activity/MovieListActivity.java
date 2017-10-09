package com.codetoart.codetoartmovies.MovieListView.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codetoart.codetoartmovies.ApiManager.ApiManager;
import com.codetoart.codetoartmovies.ApiManager.Models.UpcomingMoviesDataModel;
import com.codetoart.codetoartmovies.InformationView.InformationActivity;
import com.codetoart.codetoartmovies.MovieDetailView.view.activity.MovieDetailActivity;
import com.codetoart.codetoartmovies.MovieListView.contract.MovieListScreen;
import com.codetoart.codetoartmovies.MovieListView.presenter.MovieListPresenter;
import com.codetoart.codetoartmovies.MovieListView.view.adapter.Movie_Adapter;
import com.codetoart.codetoartmovies.R;
import com.codetoart.codetoartmovies.ServiceCall.UpcomingMovies.UpcomingMoviesFailureEvent;
import com.codetoart.codetoartmovies.ServiceCall.UpcomingMovies.UpcomingMoviesSuccessEvent;
import com.codetoart.codetoartmovies.Utils.Injector;
import com.codetoart.codetoartmovies.Utils.Network;
import com.codetoart.codetoartmovies.dagger.DaggerInjector;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Keyur Tailor on 09-Oct-17.
 */


public class MovieListActivity extends AppCompatActivity implements MovieListScreen {

    @Inject
    MovieListPresenter movieListPresenter;

    @Bind(R.id.rlMovieListLayout)
    RelativeLayout rlMovieListLayout;
    @Bind(R.id.clean_toolbar)
    Toolbar toolbar;
    @Bind(R.id.swipeRefresh)
    SwipeRefreshLayout swipeContainer;
    @Bind(R.id.recycleView)
    RecyclerView recyclerView;
    @Bind(R.id.rlRefresh)
    RelativeLayout rlRefresh;
    @Bind(R.id.ivRefresh)
    ImageView ivRefresh;
    @Bind(R.id.txtNoData)
    TextView txtNoData;

    EventBus bus;
    ApiManager apiManager;
    ProgressDialog progressDialog;
    Movie_Adapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);
        DaggerInjector.get().inject(this);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        apiManager = ApiManager.getInstance();
        bus = Injector.provideEventBus();
        ab.setTitle(getResources().getString(R.string.upcoming_movies));
        progressDialog = new ProgressDialog(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshViews();
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        recyclerView.addOnItemTouchListener(new Movie_Adapter.RecyclerTouchListener(getBaseContext(), recyclerView, new Movie_Adapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                movieListPresenter.redirectToMovieDetail(MovieListActivity.this, position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        refreshViews();
    }

    @OnClick(R.id.ivRefresh)
    void setIvRefreshClick() {
        refreshViews();
    }

    public void refreshViews() {
        showLoading();
        if (checkIfNetworkConnected()) {
            movieListPresenter.callGetUpcomingMovies(apiManager);
        } else {
            hideLoading();
            showError(getResources().getString(R.string.network_error));
            recyclerView.setVisibility(View.GONE);
            rlRefresh.setVisibility(View.VISIBLE);
            txtNoData.setText(getResources().getString(R.string.network_error));
        }
    }

    @Override
    public void setupMovieListAdapter(ArrayList<UpcomingMoviesDataModel> upcomingMoviesModel) {
        recyclerView.removeAllViewsInLayout();
        recyclerView.setVisibility(View.VISIBLE);
        rlRefresh.setVisibility(View.GONE);
        movieAdapter = new Movie_Adapter(MovieListActivity.this, upcomingMoviesModel);
        recyclerView.setAdapter(movieAdapter);
    }

    @Subscribe
    public void upcomingMoviesSuccessEvent(UpcomingMoviesSuccessEvent event) {
        hideLoading();
        movieListPresenter.setupMovieListData(MovieListActivity.this, event.getUpcomingMoviesModel());
    }

    @Subscribe
    public void upcomingMoviesErrorEvent(UpcomingMoviesFailureEvent event) {
        hideLoading();
        if (event.getErrorMessageDisplay() != null) {
            showError(event.getErrorMessageDisplay());
        } else {
            showError(getResources().getString(R.string.unexpected_error));
        }
        recyclerView.setVisibility(View.GONE);
        rlRefresh.setVisibility(View.VISIBLE);
        txtNoData.setText(getResources().getString(R.string.no_data));
    }

    @Override
    public void setupNoDataError() {
        recyclerView.setVisibility(View.GONE);
        rlRefresh.setVisibility(View.VISIBLE);
        txtNoData.setText(getResources().getString(R.string.no_data));
    }

    @Override
    public void showError(String message) {
        try {
            Snackbar snackbar = Snackbar.make(rlMovieListLayout, message, Snackbar.LENGTH_SHORT);
            snackbar.show();
        } catch (Exception e) {

        }
    }

    @Override
    public void redirectToMovieDetail(UpcomingMoviesDataModel upcomingMoviesDataModel) {
        Intent movieDetail = new Intent(MovieListActivity.this, MovieDetailActivity.class);
        movieDetail.putExtra(MovieDetailActivity.MOVIE_ID_KEY, upcomingMoviesDataModel.getMovieId());
        movieDetail.putExtra(MovieDetailActivity.MOVIE_NAME_KEY, upcomingMoviesDataModel.getMovieTitle());
        startActivity(movieDetail);
    }


    public boolean checkIfNetworkConnected() {
        return new Network(this).getConnectivityStatus();
    }

    public void showLoading() {
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
    }

    public void hideLoading() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

        if (swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.info:
                Intent infoScreen = new Intent(MovieListActivity.this, InformationActivity.class);
                startActivity(infoScreen);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            if (!EventBus.getDefault().isRegistered(this)) {
                bus.register(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        bus.unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
