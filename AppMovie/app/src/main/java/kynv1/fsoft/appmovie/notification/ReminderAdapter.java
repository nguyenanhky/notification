package kynv1.fsoft.appmovie.notification;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kynv1.fsoft.appmovie.R;
import kynv1.fsoft.appmovie.model.Result;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {
    private List<Result> results;

    public ReminderAdapter(List<Result> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder,parent,false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        Result result = results.get(position);
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + result.getBackdrop_path()).into(holder.imgAvatar);
        holder.txtReminder.setText(result.getOriginal_title());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ReminderViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgAvatar;
        private TextView txtReminder;

        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtReminder = itemView.findViewById(R.id.txtReminder);


        }
    }
}
