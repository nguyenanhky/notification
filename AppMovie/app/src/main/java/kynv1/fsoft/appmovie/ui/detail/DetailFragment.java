package kynv1.fsoft.appmovie.ui.detail;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import kynv1.fsoft.appmovie.database.MovieDatabase;
import kynv1.fsoft.appmovie.databinding.FragmentDetailBinding;
import kynv1.fsoft.appmovie.model.Result;
import kynv1.fsoft.appmovie.notification.NotifierAlarm;
import kynv1.fsoft.appmovie.notification.ReminderAdapter;
import kynv1.fsoft.appmovie.utls.Constance;


public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;
    private Result result;
    private static final String TAG = "DetailFragment";

    private ReminderAdapter reminderAdapter;
    private List<Result> results;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        result = (Result) getArguments().getSerializable(Constance.KEY_MOVIE);

        Picasso.get().load("https://image.tmdb.org/t/p/w500" + result.getBackdrop_path()).into(binding.imgAvatar);

        setReminderText();

        addEvent();

    }

    private void setReminderText() {
        if (checkId(result)) {
            Toast.makeText(getContext(), "da ton tai", Toast.LENGTH_SHORT).show();
            List<Result>results = MovieDatabase.getInstance(requireContext()).movieDao().checkMovie(result.getId());
            if(results.size()==0){
                return;
            }else{
               // binding.txtReminder.setText(results.get(results.size()-1).getDate().toString());
                binding.txtReminder.setText(convertFromDateToString(results.get(results.size()-1).getDate()));
            }
        }else{
             binding.txtReminder.setText(convertFromDateToString(result.getDate()));
        }
    }


    private void addEvent() {
        reminderTime();
    }

    private void reminderTime() {
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
                                    result.setDate(dateReminder);
                                    MovieDatabase.getInstance(requireContext()).movieDao().insert(result);

                                    List<Result> results = MovieDatabase.getInstance(requireContext()).movieDao().getAll();
                                    result = results.get(results.size()-1);

                                    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
                                    calendar.setTime(dateReminder);
                                    calendar.set(Calendar.SECOND,0);

                                    Intent intent = new Intent(getContext(), NotifierAlarm.class);

                                    intent.putExtra("Message",result.getOriginal_title());
                                    intent.putExtra("RemindDate",result.getDate());
                                    intent.putExtra("id",result.getId());

                                    PendingIntent intent1 = PendingIntent.getBroadcast(getContext(),result.getId(),intent,PendingIntent.FLAG_UPDATE_CURRENT);

                                    AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(getContext().ALARM_SERVICE);
                                    alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),intent1);

                                    Toast.makeText(getContext(),"Inserted Successfully",Toast.LENGTH_SHORT).show();



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


    private String convertFromDateToString(Date time) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(time.getTime());
        return strDate;
    }

    private boolean checkId(Result result) {
        List<Result> list = MovieDatabase.getInstance(requireContext()).movieDao().checkMovie(result.getId());
        return list != null && !list.isEmpty();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}