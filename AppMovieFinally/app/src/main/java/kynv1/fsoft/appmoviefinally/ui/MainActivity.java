package kynv1.fsoft.appmoviefinally.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.navigation.NavigationView;

import java.util.IllformedLocaleException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kynv1.fsoft.appmoviefinally.R;
import kynv1.fsoft.appmoviefinally.database.MovieDatabase;
import kynv1.fsoft.appmoviefinally.databinding.ActivityMainBinding;
import kynv1.fsoft.appmoviefinally.model.reminder.Result;
import kynv1.fsoft.appmoviefinally.viewmodel.ViewModelNumberOfFavorite;
import kynv1.fsoft.appmoviefinally.ui.infomation.InformationActivity;
import kynv1.fsoft.appmoviefinally.ui.reminder.ClickItemReminder;
import kynv1.fsoft.appmoviefinally.ui.reminder.ReminderAdapter;
import kynv1.fsoft.appmoviefinally.utils.Constance;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , ClickItemReminder {
    private ActivityMainBinding binding;
    private View headerLayout;
    private Button btnEdit, btnShowAll;
    private TextView txtName, txtEmail, txtBirthday, txtSex;
    private CircleImageView nav_imv_avatar;
    private SharedPreferences sharedPreferences;
    private ViewModelNumberOfFavorite viewModelNumberOfFavorite;
    NavController navController;
    private SharedPreferences shTypeMovie;
    private static final String TAG = "MainActivity";

    private ReminderAdapter reminderAdapter;
    private List<Result> results;
    private RecyclerView rcvReminderTwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        Log.d(TAG, "onCreate: ");
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        viewModelNumberOfFavorite = new ViewModelProvider(this).get(ViewModelNumberOfFavorite.class);
        setupTypeMovie();
        addControls();
        addEvents();


    }

    private void setupTypeMovie() {
        shTypeMovie = getSharedPreferences(Constance.KEY_TYPE_MOVIE, MODE_PRIVATE);
        SharedPreferences.Editor editor = shTypeMovie.edit();
        editor.putInt(Constance.KEY_TITLE_MOVIE, Constance.VALUE_TITLE_POPULAR_MOVIE);
        editor.apply();
    }

    private void addEvents() {
        eventNavigationView();
        addHeaderLayout();
        eventToolbarSelectItemMovie();
        eventNumberOfFavorite();

        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.bottomNavigationView.setSelectedItemId(R.id.settingFragment);
                navController.navigate(R.id.reminderFragment);
            }
        });
    }


    private void eventNumberOfFavorite() {
        BadgeDrawable badge = binding.bottomNavigationView.getOrCreateBadge(R.id.favoriteFragment);
        badge.setNumber(MovieDatabase.getInstance(this).movieDao().getCount());
        viewModelNumberOfFavorite.getCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                badge.setNumber(integer);
            }
        });

    }

    private void eventToolbarSelectItemMovie() {
        binding.imaArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.imaArrow.setImageResource(R.drawable.ic__arrow_up);
                binding.layoutItemMovie.setVisibility(View.VISIBLE);
                selectItemMovie();
            }
        });
    }

    private void selectItemMovie() {
        binding.txtPopularMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: txtPopularMovie");
                binding.layoutItemMovie.setVisibility(View.GONE);
                binding.imaArrow.setImageResource(R.drawable.ic_arrow);
                SharedPreferences.Editor editor = shTypeMovie.edit();
                editor.putInt(Constance.KEY_TITLE_MOVIE, Constance.VALUE_TITLE_POPULAR_MOVIE);
                editor.apply();

                int id = navController.getCurrentDestination().getId();
                if (id == R.id.movieFragment) {
                    navController.popBackStack(id, true);
                    navController.navigate(id);
                }
                getSupportActionBar().setTitle(R.string.popular_movies);
            }
        });

        binding.txtTopRateMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: txtTopRateMovie");
                binding.layoutItemMovie.setVisibility(View.GONE);
                binding.imaArrow.setImageResource(R.drawable.ic_arrow);

                SharedPreferences.Editor editor = shTypeMovie.edit();
                editor.putInt(Constance.KEY_TITLE_MOVIE, Constance.VALUE_TITLE_TOP_RATE_MOVIE);
                editor.apply();

                int id = navController.getCurrentDestination().getId();
                if (id == R.id.movieFragment) {
                    navController.popBackStack(id, true);
                    navController.navigate(id);
                }
                getSupportActionBar().setTitle(R.string.top_rate_movies);
            }
        });

        binding.txtUpcomingMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: txtUpafdsf");
                binding.layoutItemMovie.setVisibility(View.GONE);
                binding.imaArrow.setImageResource(R.drawable.ic_arrow);

                SharedPreferences.Editor editor = shTypeMovie.edit();
                editor.putInt(Constance.KEY_TITLE_MOVIE, Constance.VALUE_TITLE_UPCOMING_MOVIE);
                editor.apply();

                int id = navController.getCurrentDestination().getId();
                if (id == R.id.movieFragment) {
                    navController.popBackStack(id, true);
                    navController.navigate(id);
                }
                getSupportActionBar().setTitle(R.string.upcoming_movies);
            }
        });

        binding.txtNowPlayingMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.layoutItemMovie.setVisibility(View.GONE);
                Log.d(TAG, "onClick: txtNowPlayingMovie");
                binding.imaArrow.setImageResource(R.drawable.ic_arrow);

                SharedPreferences.Editor editor = shTypeMovie.edit();
                editor.putInt(Constance.KEY_TITLE_MOVIE, Constance.VALUE_TITLE_NOW_PLAYING_MOVIE);
                editor.apply();

                int id = navController.getCurrentDestination().getId();
                if (id == R.id.movieFragment) {
                    navController.popBackStack(id, true);
                    navController.navigate(id);
                }
                getSupportActionBar().setTitle(R.string.now_playing_moives);
            }
        });
    }


    private void addHeaderLayout() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                startActivity(intent);
                Log.d(TAG, "onClick: " + "Ptit");
            }
        });
    }

    private void eventNavigationView() {
        binding.navView.setNavigationItemSelectedListener(this);
    }

    private void addControls() {
        setupNavigation();
        setupToolbar();
        setupHeaderLayoutInformation();


    }


    private void setupNavigation() {

        setSupportActionBar(binding.toolbar);
        navController = Navigation.findNavController(this, R.id.fragmentContainerView2);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.movieFragment, R.id.favoriteFragment, R.id.settingFragment, R.id.aboutFragment,R.id.reminderFragment)
                .setOpenableLayout(binding.drawerLayout)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(
                binding.toolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);


        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            switch (navDestination.getId()) {
                case R.id.movieFragment:
                    binding.imaArrow.setVisibility(View.VISIBLE);
                    break;
                case R.id.aboutFragment:
                case R.id.favoriteFragment:
                case R.id.settingFragment:
                case R.id.movieDetailFragment:
                    binding.imaArrow.setVisibility(View.GONE);
                    break;
            }
        });


    }

    private void setupHeaderLayoutInformation() {
        headerLayout = binding.navView.getHeaderView(0);
        btnEdit = headerLayout.findViewById(R.id.btnEdit);
        txtName = headerLayout.findViewById(R.id.txtName);
        txtEmail = headerLayout.findViewById(R.id.txtEmail);
        txtBirthday = headerLayout.findViewById(R.id.txtBirthday);
        txtSex = headerLayout.findViewById(R.id.txtSex);
        nav_imv_avatar = headerLayout.findViewById(R.id.nav_imv_avatar);
        btnShowAll = headerLayout.findViewById(R.id.btnShowAll);




        rcvReminderTwo = headerLayout.findViewById(R.id.rcvReminderTwo);
        reminderAdapter = new ReminderAdapter(this);
        results = MovieDatabase.getInstance(this).reminderDao().getListLimitTwo();
        reminderAdapter.setData(results);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvReminderTwo.setLayoutManager(linearLayoutManager);
        rcvReminderTwo.setAdapter(reminderAdapter);
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onNavigationItemSelected: ");
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpSharedPreferences();
    }

    private void setUpSharedPreferences() {
        sharedPreferences = getSharedPreferences(Constance.PERSON, Context.MODE_PRIVATE);
        getImage();
        String name = sharedPreferences.getString(Constance.NAME, getString(R.string.nguyen_anh_ky));
        String email = sharedPreferences.getString(Constance.EMAIL, getString(R.string.kynv1_fsoft_com_vn));
        String birthday = sharedPreferences.getString(Constance.BIRTHDAY, getString(R.string.birthday));
        String sex = sharedPreferences.getString(Constance.SEX, getString(R.string.male));

        txtName.setText(name);
        txtEmail.setText(email);
        txtBirthday.setText(birthday);
        txtSex.setText(sex);

    }

    private void getImage() {
        String imgAvatar = sharedPreferences.getString(Constance.AVATAR, "");
        if (!imgAvatar.equals("")) {
            try {
                byte[] b = Base64.decode(imgAvatar, Base64.DEFAULT);
                Log.d(TAG, "getAvatar#b.length" + b.length);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                nav_imv_avatar.setImageBitmap(bitmap);
            } catch (IllformedLocaleException e) {
                // java.lang.IllegalArgumentException: bad base-64
                android.util.Log.i(TAG, "", e);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: ");
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString(Constance.NAME, txtName.getText().toString());
        myEdit.putString(Constance.EMAIL, txtEmail.getText().toString());
        myEdit.putString(Constance.BIRTHDAY, txtBirthday.getText().toString());
        myEdit.putString(Constance.SEX, txtSex.getText().toString());
        myEdit.apply();
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }


    @Override
    public void deleteReminder(Result result) {

    }
}