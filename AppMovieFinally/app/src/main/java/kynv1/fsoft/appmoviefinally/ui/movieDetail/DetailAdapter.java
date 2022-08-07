package kynv1.fsoft.appmoviefinally.ui.movieDetail;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kynv1.fsoft.appmoviefinally.R;
import kynv1.fsoft.appmoviefinally.model.Crew;
import kynv1.fsoft.appmoviefinally.utils.Constance;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {
    private List<Crew> crews;

    private static final String TAG = "DetailAdapter";

    public void setData(List<Crew> crews) {
        this.crews = crews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast_crew, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        Crew crew = crews.get(position);
        Picasso.get()
                .load(Constance.BASE_URL_IMAGE + crew.getProfile_path())
                .into(holder.imgAvatar);

        holder.txtName.setText(crew.getName());
        if(crew==null){
            Log.d(TAG, "onBindViewHolder: da la phan tu null roi ");
        }

    }

    @Override
    public int getItemCount() {
        return crews.size();

    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView txtName;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }
}
