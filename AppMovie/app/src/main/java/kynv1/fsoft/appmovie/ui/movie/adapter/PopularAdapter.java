package kynv1.fsoft.appmovie.ui.movie.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kynv1.fsoft.appmovie.R;
import kynv1.fsoft.appmovie.database.MovieDatabase;
import kynv1.fsoft.appmovie.model.Popular;
import kynv1.fsoft.appmovie.model.Result;
import kynv1.fsoft.appmovie.utls.Constance;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {
    private List<Result> results = new ArrayList<>();
    private Context context;
    private Date date = new Date();
    private static final String TAG = "PopularAdapter";

    public void setData(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public PopularAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemmovie, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder viewHolder, int position) {
        Result result = results.get(position);
        result.setDate(date);


        Picasso.get().load("https://image.tmdb.org/t/p/w500" + result.getBackdrop_path()).into(viewHolder.imaAvatar);
        viewHolder.txtOverView.setText(result.getOverview());
        viewHolder.txtReleaseDate.setText(result.getRelease_date());
        viewHolder.txtTitleMovie.setText(result.getOriginal_title());
        viewHolder.txtVoteAverage.setText(result.getVote_average() + "/10");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constance.KEY_MOVIE, result);
                navController.navigate(R.id.action_movieFragment_to_detailFragment, bundle);

            }
        });

        viewHolder.imgStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                if(!checkId(result)){
                    MovieDatabase.getInstance(context).movieDao().insert(result);
                    Toast.makeText(context, "them thanh cong", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "da ton tai roi ", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    private boolean checkId(Result result) {
        List<Result> list = MovieDatabase.getInstance(context).movieDao().checkMovie(result.getId());
        return list != null && !list.isEmpty();
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitleMovie, txtReleaseDate, txtVoteAverage, txtOverView;
        private ImageView imaAvatar, imgStart;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitleMovie = itemView.findViewById(R.id.txtTitleMovie);
            txtReleaseDate = itemView.findViewById(R.id.txtReleaseDate);
            txtVoteAverage = itemView.findViewById(R.id.txtVoteAverage);
            txtOverView = itemView.findViewById(R.id.txtOverView);

            imaAvatar = itemView.findViewById(R.id.imaAvatar);
            imgStart = itemView.findViewById(R.id.imgStart);

        }
    }


}
