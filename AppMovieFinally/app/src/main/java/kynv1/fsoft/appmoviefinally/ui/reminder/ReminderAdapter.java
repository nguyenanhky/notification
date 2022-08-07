package kynv1.fsoft.appmoviefinally.ui.reminder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import kynv1.fsoft.appmoviefinally.R;
import kynv1.fsoft.appmoviefinally.model.reminder.Result;
import kynv1.fsoft.appmoviefinally.utils.Constance;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {
    private List<Result> results;
    private static final String TAG = "ReminderAdapter";
    private ClickItemReminder itemReminder;

    public void setData(List<Result> results){
        this.results = results;
        notifyDataSetChanged();
    }
    public ReminderAdapter(ClickItemReminder itemReminder) {
        this.itemReminder = itemReminder;
    }


    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_all_reminder,parent,false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        Result result = results.get(position);
        if(result!=null){
            Picasso.get()
                    .load(Constance.BASE_URL_IMAGE +
                            result.getBackdrop_path())
                    .into(holder.imgAvatar);

            holder.txtTitleMovie.setText(result.getOriginal_title());
            Log.d(TAG, "onBindViewHolder: "+result.getRelease_date());
            if(!result.getRelease_date().equals("")  && result.getRelease_date().length()>=4){
                holder.txtReleaseDate.setText(result.getRelease_date().substring(0,4));
            }
            holder.txtTopRateMovie.setText(result.getVote_average()+"/10");

            holder.txtReminderDate.setText(convertFromDateToString(result.getDate()));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                itemReminder.deleteReminder(result);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ReminderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView txtTitleMovie,txtReleaseDate,txtTopRateMovie,txtReminderDate;
        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtReleaseDate = itemView.findViewById(R.id.txtReleaseDate);
            txtTitleMovie= itemView.findViewById(R.id.txtTitleMovie);
            txtReminderDate = itemView.findViewById(R.id.txtReminderDate);
            txtTopRateMovie = itemView.findViewById(R.id.txtTopRateMovie);
        }
    }

    private String convertFromDateToString(Date time) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/M/yyyy hh:mm");
        String strDate = formatter.format(time.getTime());
        return strDate;
    }
}
