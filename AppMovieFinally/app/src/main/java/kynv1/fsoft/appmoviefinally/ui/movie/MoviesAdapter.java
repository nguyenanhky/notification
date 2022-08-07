package kynv1.fsoft.appmoviefinally.ui.movie;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kynv1.fsoft.appmoviefinally.R;
import kynv1.fsoft.appmoviefinally.database.MovieDatabase;
import kynv1.fsoft.appmoviefinally.model.Result;
import kynv1.fsoft.appmoviefinally.ui.favorite.IClickDeleteItem;
import kynv1.fsoft.appmoviefinally.utils.Constance;

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Result> results = new ArrayList<>();
    private Context context;
    public IClickDeleteItem iClickDeleteItem;
    private static final String TAG = "PopularAdapter";

    public void setData(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }


    public MoviesAdapter(IClickDeleteItem iClickDeleteItem, Context context) {
        this.iClickDeleteItem = iClickDeleteItem;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == Result.TYPE_LIST) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
            return new PopularViewHolderList(view);
        } else if (viewType == Result.TYPE_GRID) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_grid, parent, false);
            return new PopularViewHolderGrid(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Result result = results.get(position);

        if (getItemViewType(position) == Result.TYPE_LIST) {
            Picasso.get()
                    .load(Constance.BASE_URL_IMAGE +
                            result.getBackdrop_path())
                    .into(((PopularViewHolderList) holder).imgAvatar);

            ((PopularViewHolderList) holder).txtOverView.setText(result.getOverview());
            ((PopularViewHolderList) holder).txtReleaseDate.setText(result.getRelease_date());
            ((PopularViewHolderList) holder).txtTitleMovie.setText(result.getOriginal_title());
            ((PopularViewHolderList) holder).txtVoteAverage.setText(result.getVote_average() + "/10");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constance.KEY_POPULAR, result);
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.movieDetailFragment, bundle);
                }
            });

            ((PopularViewHolderList) holder).imgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!checkId(result, view)) {
                        ((PopularViewHolderList) holder).imgFavorite.setImageResource(R.drawable.ic_star_choose);
                        iClickDeleteItem.clickInsertFavorite(result);
                    } else {
                        ((PopularViewHolderList) holder).imgFavorite.setImageResource(R.drawable.ic_star);
                        iClickDeleteItem.clickDeleteFavorite(result);

                    }
                }
            });

            if (checkListIdMovie(result)) {
                ((PopularViewHolderList) holder).imgFavorite.setImageResource(R.drawable.ic_star_choose);
            }

        } else if (getItemViewType(position) == Result.TYPE_GRID) {
            Picasso.get()
                    .load(Constance.BASE_URL_IMAGE + result.getBackdrop_path())
                    .into(((PopularViewHolderGrid) holder).imgAvatar);

            ((PopularViewHolderGrid) holder).txtTitleMovie.setText(result.getOriginal_title());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constance.KEY_POPULAR, result);
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.movieDetailFragment, bundle);
                }
            });
        } else if(holder instanceof PopularLoadingViewHolder){
            showLoadingView((PopularLoadingViewHolder) holder,position);
        }
    }

    private boolean checkListIdMovie(Result result) {
        List<Result> list = MovieDatabase.getInstance(context).movieDao().checkMovie(result.getId());
        return list != null && !list.isEmpty();
    }


    private boolean checkId(Result result, View view) {
        List<Result> list = MovieDatabase.getInstance(view.getContext()).movieDao().checkMovie(result.getId());
        return list != null && !list.isEmpty();
    }

    @Override
    public int getItemViewType(int position) {
        Result result = results.get(position);
        return result.getTypeDisplay();
    }

    @Override
    public int getItemCount() {
        if (results == null) {
            Log.d(TAG, "getItemCount: ");
        }
        return results.size();
    }

    public class PopularViewHolderList extends RecyclerView.ViewHolder {
        private ImageView imgAvatar, imgFavorite;
        private TextView txtReleaseDate, txtVoteAverage, txtOverView, txtTitleMovie;

        public PopularViewHolderList(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtReleaseDate = itemView.findViewById(R.id.txtReleaseDate);
            txtVoteAverage = itemView.findViewById(R.id.txtVoteAverage);
            txtOverView = itemView.findViewById(R.id.txtOverView);
            txtTitleMovie = itemView.findViewById(R.id.txtTitleMovie);
            imgFavorite = itemView.findViewById(R.id.imgFavorite);
        }
    }

    public class PopularViewHolderGrid extends RecyclerView.ViewHolder {

        private ImageView imgAvatar;
        private TextView txtTitleMovie;

        public PopularViewHolderGrid(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtTitleMovie = itemView.findViewById(R.id.txtTitleMovie);
        }
    }

    public class PopularLoadingViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public PopularLoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }
    private void showLoadingView(PopularLoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }
}
