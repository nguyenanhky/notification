package kynv1.fsoft.appmovie.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import kynv1.fsoft.appmovie.R;
import kynv1.fsoft.appmovie.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addControls();
        setupNavigation();
        addEvent();

    }

    private void addEvent() {

    }

    private void addControls() {

    }

    private void setupNavigation() {
        NavController navController  =  Navigation.findNavController(this,R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController);
    }


}