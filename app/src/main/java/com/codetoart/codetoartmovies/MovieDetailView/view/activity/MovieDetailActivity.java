package com.codetoart.codetoartmovies.MovieDetailView.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codetoart.codetoartmovies.ApiManager.ApiManager;
import com.codetoart.codetoartmovies.ApiManager.Models.MovieDetailsModel;
import com.codetoart.codetoartmovies.ApiManager.Models.MovieImagesModel;
import com.codetoart.codetoartmovies.BuildConfig;
import com.codetoart.codetoartmovies.MovieDetailView.contract.MovieDetailScreen;
import com.codetoart.codetoartmovies.MovieDetailView.presenter.MovieDetailPresenter;
import com.codetoart.codetoartmovies.R;
import com.codetoart.codetoartmovies.ServiceCall.MovieDetails.MovieDetailsFailureEvent;
import com.codetoart.codetoartmovies.ServiceCall.MovieDetails.MovieDetailsSuccessEvent;
import com.codetoart.codetoartmovies.ServiceCall.MovieImages.MovieImagesFailureEvent;
import com.codetoart.codetoartmovies.ServiceCall.MovieImages.MovieImagesSuccessEvent;
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


public class MovieDetailActivity extends AppCompatActivity implements MovieDetailScreen {
    public static String MOVIE_ID_KEY = "MOVIE_ID";
    public static String MOVIE_NAME_KEY = "MOVIE_NAME";
    @Inject
    MovieDetailPresenter movieDetailPresenter;

    @Bind(R.id.rlMovieDetailMain)
    RelativeLayout rlMovieDetailMain;
    @Bind(R.id.scrollMovieDetails)
    ScrollView scrollMovieDetails;
    @Bind(R.id.clean_toolbar)
    Toolbar toolbar;
    @Bind(R.id.pagerImages)
    ViewPager pagerImages;
    @Bind(R.id.viewPagerIndicator)
    RelativeLayout viewPagerIndicator;
    @Bind(R.id.viewPagerCountDots)
    LinearLayout viewPagerCountDots;
    @Bind(R.id.txtMovieName)
    TextView txtMovieName;
    @Bind(R.id.txtMovieOverView)
    TextView txtMovieOverView;
    @Bind(R.id.ratingMovie)
    RatingBar ratingMovie;

    @Bind(R.id.rlRefresh)
    RelativeLayout rlRefresh;
    @Bind(R.id.ivRefresh)
    ImageView ivRefresh;
    @Bind(R.id.txtNoData)
    TextView txtNoData;

    EventBus bus;
    ApiManager apiManager;
    ProgressDialog progressDialog;
    int MOVIE_ID = -1;
    String MOVIE_NAME = "";

    int dotsCount;
    ImageView[] dots;
    ViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        DaggerInjector.get().inject(this);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        apiManager = ApiManager.getInstance();
        bus = Injector.provideEventBus();
        progressDialog = new ProgressDialog(this);

        MOVIE_ID = getIntent().getIntExtra(MOVIE_ID_KEY, -1);
        MOVIE_NAME = getIntent().getStringExtra(MOVIE_NAME_KEY);
        ab.setTitle(MOVIE_NAME);

        refreshViews();
    }

    @OnClick(R.id.ivRefresh)
    void setIvRefreshClick() {
        refreshViews();
    }

    public void refreshViews() {
        showLoading();
        if (checkIfNetworkConnected()) {
            movieDetailPresenter.callGetMovieDetails(MOVIE_ID, apiManager);
        } else {
            hideLoading();
            showError(getResources().getString(R.string.network_error));
            scrollMovieDetails.setVisibility(View.GONE);
            rlRefresh.setVisibility(View.VISIBLE);
            txtNoData.setText(getResources().getString(R.string.network_error));
        }
    }

    @Override
    public void setupMovieDetails(MovieDetailsModel movieDetailsModel) {
        scrollMovieDetails.setVisibility(View.VISIBLE);
        rlRefresh.setVisibility(View.GONE);

        txtMovieName.setText(movieDetailsModel.getMovieTitle());
        txtMovieOverView.setText(movieDetailsModel.getOverview());
        ratingMovie.setRating(movieDetailsModel.getVoteAverage());
    }

    @Override
    public void setupMovieImages(ArrayList<MovieImagesModel.MovieImagesDataModel> posters) {
        mAdapter = new ViewPagerAdapter(MovieDetailActivity.this, posters);
        pagerImages.setAdapter(mAdapter);
        pagerImages.setCurrentItem(0);
        pagerImages.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                }
                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setUiPageViewController();
    }

    @Subscribe
    public void movieDetailsSuccessEvent(MovieDetailsSuccessEvent event) {
        hideLoading();
        movieDetailPresenter.setupMovieDetailData(MovieDetailActivity.this, event.getMovieDetailsModel());
    }

    @Subscribe
    public void movieDetailsFailureEvent(MovieDetailsFailureEvent event) {
        hideLoading();
        if (event.getErrorMessageDisplay() != null) {
            showError(event.getErrorMessageDisplay());
        } else {
            showError(getResources().getString(R.string.unexpected_error));
        }
        scrollMovieDetails.setVisibility(View.GONE);
        rlRefresh.setVisibility(View.VISIBLE);
        txtNoData.setText(getResources().getString(R.string.no_data));
    }

    @Subscribe
    public void movieImagesSuccessEvent(MovieImagesSuccessEvent event) {
        movieDetailPresenter.setupMovieImagesData(MovieDetailActivity.this, event.getMovieImagesModel());
    }

    @Subscribe
    public void movieImagesFailureEvent(MovieImagesFailureEvent event) {
        hideLoading();
    }

    @Override
    public void setupNoDataError() {
        scrollMovieDetails.setVisibility(View.GONE);
        rlRefresh.setVisibility(View.VISIBLE);
        txtNoData.setText(getResources().getString(R.string.no_data));
    }

    @Override
    public void showError(String message) {
        try {
            Snackbar snackbar = Snackbar.make(rlMovieDetailMain, message, Snackbar.LENGTH_SHORT);
            snackbar.show();
        } catch (Exception e) {

        }
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
    }

    public void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            viewPagerCountDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
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

    public class ViewPagerAdapter extends PagerAdapter {

        private Context mContext;
        private ArrayList<MovieImagesModel.MovieImagesDataModel> posters;

        public ViewPagerAdapter(Context mContext, ArrayList<MovieImagesModel.MovieImagesDataModel> posters) {
            this.mContext = mContext;
            this.posters = posters;
        }

        @Override
        public int getCount() {
            return posters.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item, container, false);

            ImageView imgMovie = (ImageView) itemView.findViewById(R.id.imgMovie);
            Glide.with(mContext).load(BuildConfig.IMAGE_HOST + posters.get(position).getImagePath())
                    .placeholder(R.drawable.ic_placeholder)
                    .into(imgMovie);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

}
