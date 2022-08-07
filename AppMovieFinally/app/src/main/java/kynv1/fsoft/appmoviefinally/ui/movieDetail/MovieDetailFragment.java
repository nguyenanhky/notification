package kynv1.fsoft.appmoviefinally.ui.movieDetail;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import kynv1.fsoft.appmoviefinally.R;
import kynv1.fsoft.appmoviefinally.database.MovieDatabase;
import kynv1.fsoft.appmoviefinally.databinding.FragmentMovieDetailBinding;
import kynv1.fsoft.appmoviefinally.model.Crew;
import kynv1.fsoft.appmoviefinally.model.Result;
import kynv1.fsoft.appmoviefinally.viewmodel.ViewModelNumberOfFavorite;
import kynv1.fsoft.appmoviefinally.ui.reminder.NotifierAlarm;
import kynv1.fsoft.appmoviefinally.viewmodel.MovieViewModel;
import kynv1.fsoft.appmoviefinally.utils.Constance;


public class MovieDetailFragment extends Fragment {

    private FragmentMovieDetailBinding binding;
    private ViewModelNumberOfFavorite viewModelNumberOfFavorite;
    private MovieViewModel movieViewModel;
    private Result result;
    private kynv1.fsoft.appmoviefinally.model.reminder.Result resultReminder;
    private List<Crew> crews;
    private DetailAdapter detailAdapter;
    private LinearLayoutManager linearLayoutManager;

    private static final String TAG = "MovieDetailFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false);
        viewModelNumberOfFavorite = new ViewModelProvider(requireActivity()).get(ViewModelNumberOfFavorite.class);
        movieViewModel = new ViewModelProvider(requireActivity()).get(MovieViewModel.class);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        result = (Result) getArguments().getSerializable(Constance.KEY_POPULAR);
        resultReminder = new kynv1.fsoft.appmoviefinally.model.reminder.Result();

        setTitleToolbar();
        receivedFromMovieFragment();
        addControls();
        showItemCastAndCrew();
        eventReminder();

    }


    private boolean chekResult(Result result) {
        List<Result> list = MovieDatabase.getInstance(getContext()).movieDao().checkMovie(result.getId());
        return list != null && !list.isEmpty();
    }

    private void eventReminder() {
        final Calendar newCalender = Calendar.getInstance();
        binding.btnReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                        final Calendar newDate = Calendar.getInstance();
                        Calendar newTime = Calendar.getInstance();
                        TimePickerDialog time = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                newDate.set(year, month, dayOfMonth, hourOfDay, minute, 0);
                                Calendar tem = Calendar.getInstance();
                                Log.w("TIME", System.currentTimeMillis() + "");
                                if (newDate.getTimeInMillis() - tem.getTimeInMillis() > 0) {

                                    // convert from date ro String
                                    binding.txtReminder.setText(convertFromDateToString(newDate.getTime()));

                                    Date dateReminder = newDate.getTime();
                                   // result.setDate(dateReminder);
                                    resultReminder.setDate(dateReminder);


                                    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
                                    calendar.setTime(dateReminder);
                                    calendar.set(Calendar.SECOND, 0);

                                    Intent intent = new Intent(getContext(), NotifierAlarm.class);

                                    resultReminder.setId(result.getId());
                                    resultReminder.setBackdrop_path(result.getBackdrop_path());
                                    resultReminder.setOriginal_language(result.getOriginal_language());
                                    resultReminder.setOriginal_title(result.getOriginal_title());
                                    resultReminder.setOverview(result.getOverview());
                                    resultReminder.setPopularity(result.getPopularity());
                                    resultReminder.setPoster_path(result.getPoster_path());
                                    resultReminder.setRelease_date(result.getRelease_date());
                                    resultReminder.setTitle(result.getTitle());
                                    resultReminder.setVote_average(result.getVote_average());
                                    resultReminder.setVote_count(result.getVote_count());

                                    MovieDatabase.getInstance(requireContext()).reminderDao().insert(resultReminder);

                                    List<kynv1.fsoft.appmoviefinally.model.reminder.Result> results = MovieDatabase.getInstance(requireContext()).reminderDao().getAll();
                                    resultReminder = results.get(results.size() - 1);

                                    intent.putExtra("Message", resultReminder.getOriginal_title());
                                    intent.putExtra("RemindDate", resultReminder.getDate().toString());
                                    intent.putExtra("id", resultReminder.getId());

                                    PendingIntent intent1 = PendingIntent.getBroadcast(getContext(), result.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

                                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getContext().ALARM_SERVICE);
                                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), intent1);

                                    Toast.makeText(getContext(), "Inserted Successfully", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(getContext(), "Invalid time", Toast.LENGTH_SHORT).show();

                                }

                            }
                        }, newTime.get(Calendar.HOUR_OF_DAY), newTime.get(Calendar.MINUTE), true);
                        time.show();

                    }
                }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();
            }
        });
    }


    private void showItemCastAndCrew() {
        movieViewModel.getDetailMovies(result.getId(), Constance.KEY_VALUE_MOVIE).observe(requireActivity(), detail -> {
            crews.addAll(detail.getCrew());
            detailAdapter.setData(crews);
        });
    }

    private void addControls() {

        crews = new ArrayList<>();
        detailAdapter = new DetailAdapter();
        linearLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        detailAdapter.setData(crews);
        binding.rcvCastAndCrew.setLayoutManager(linearLayoutManager);
        binding.rcvCastAndCrew.setAdapter(detailAdapter);

    }


    private String convertFromDateToString(Date time) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/M/yyyy hh:mm");
        String strDate = formatter.format(time.getTime());
        return strDate;
    }

    private void receivedFromMovieFragment() {
        binding.txtReleaseDate.setText(result.getRelease_date());
        binding.txtVoteAverage.setText(result.getVote_average() + "/10");
        binding.txtOverView.setText(result.getOverview());

        Picasso.get()
                .load("https://image.tmdb.org/t/p/w500" +
                        result.getBackdrop_path())
                .into(binding.imgAvatar);

        if (checkListIdMovie(result)) {
            binding.imgFavorite.setImageResource(R.drawable.ic_star_choose);
        } else {
            binding.imgFavorite.setImageResource(R.drawable.ic_star);
        }

        binding.imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkListIdMovie(result)) {
                    binding.imgFavorite.setImageResource(R.drawable.ic_star);
                    MovieDatabase.getInstance(getContext()).movieDao().delete(result);
                    viewModelNumberOfFavorite.setupNumberOfFavorite(MovieDatabase.getInstance(getContext()).movieDao().getCount());
                } else {
                    binding.imgFavorite.setImageResource(R.drawable.ic_star_choose);
                    MovieDatabase.getInstance(getContext()).movieDao().insert(result);
                    viewModelNumberOfFavorite.setupNumberOfFavorite(MovieDatabase.getInstance(getContext()).movieDao().getCount());
                }
            }
        });

    }

    private void setTitleToolbar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(result.getOriginal_title());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24);
    }

    private boolean checkListIdMovie(Result result) {
        List<Result> results = MovieDatabase.getInstance(getContext()).movieDao().checkMovie(result.getId());
        return results != null && !results.isEmpty();
    }

}