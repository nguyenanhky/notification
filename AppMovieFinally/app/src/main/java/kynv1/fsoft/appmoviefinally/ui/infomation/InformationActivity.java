package kynv1.fsoft.appmoviefinally.ui.infomation;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.IllformedLocaleException;
import java.util.Locale;

import kynv1.fsoft.appmoviefinally.R;
import kynv1.fsoft.appmoviefinally.databinding.ActivityInformationBinding;
import kynv1.fsoft.appmoviefinally.ui.MainActivity;
import kynv1.fsoft.appmoviefinally.utils.Constance;

public class InformationActivity extends AppCompatActivity {

    private ActivityInformationBinding binding;
    private SharedPreferences sharedPreferences;
    private String encodedImage;
    private String sex;
    final Calendar myCalendar= Calendar.getInstance();


    private static final String TAG = "InformationActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d(TAG, "onCreate: ");

        addControls();
        addEvents();
    }

    private void addEvents() {
        UpdateInformationOfPerson();
        CancelInformationOfPerson();
    }

    private void CancelInformationOfPerson() {
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void UpdateInformationOfPerson() {
        updateImgAvatar();
        updateBirthday();
        updateSex();
        binding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                if(encodedImage!=null){
                    myEdit.putString(Constance.AVATAR,encodedImage);
                }
                myEdit.putString(Constance.NAME, binding.edtName.getText().toString());
                myEdit.putString(Constance.EMAIL, binding.edtEmail.getText().toString());
                myEdit.putString(Constance.BIRTHDAY, binding.edtBirthday.getText().toString());
                myEdit.putString(Constance.SEX,sex);
                myEdit.apply();
                finish();
            }
        });

    }

    private void updateSex() {
        binding.radioSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                doOnDifficultyLevelChanged(radioGroup, checkedId);
            }
        });

    }

    private void doOnDifficultyLevelChanged(RadioGroup radioGroup, int checkedId) {
        int checkedRadioId = radioGroup.getCheckedRadioButtonId();
        switch (checkedRadioId){
            case R.id.radioFemale:
                sex = binding.radioFemale.getText().toString();
                Toast.makeText(this, "ptit", Toast.LENGTH_SHORT).show();

                break;
            case R.id.radioMale:
                sex = binding.radioMale.getText().toString();
                Toast.makeText(this, "nguyen anh ky hoc vien cong nghe buu chinh vien thong", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }



    private void updateBirthday() {
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        binding.edtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(InformationActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat="MM/dd/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        binding.edtBirthday.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void updateImgAvatar() {
        binding.navImvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(InformationActivity.this, "dsfdsf", Toast.LENGTH_SHORT).show();
                openGalleryToGetPhoto();
            }
        });
    }

    private void openGalleryToGetPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startForResultFromGallery.launch(intent);
    }

    private final ActivityResultLauncher<Intent> startForResultFromGallery =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK){
                try {
                    if (result.getData() != null){
                        Uri selectedImageUri = result.getData().getData();
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri));
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] b = baos.toByteArray();
                        encodedImage = android.util.Base64.encodeToString(b, Base64.DEFAULT);
                        // set bitmap to image view here........
                        binding.navImvAvatar.setImageBitmap(bitmap);
                    }
                }catch (Exception exception){
                    Log.d("TAG",""+exception.getLocalizedMessage());
                }
            }
        }
    });

    private void addControls() {
        setUpSharedPreferences();
    }

    private void setUpSharedPreferences() {

        sharedPreferences = getSharedPreferences(Constance.PERSON, Context.MODE_PRIVATE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        getImage();
        String name =sharedPreferences.getString(Constance.NAME,"");
        String email = sharedPreferences.getString(Constance.EMAIL,"");
        String birthDay = sharedPreferences.getString(Constance.BIRTHDAY,"");
        String sex = sharedPreferences.getString(Constance.SEX,"");
        Log.i(TAG, "sex: "+sex);
        binding.edtName.setText(name);
        binding.edtEmail.setText(email);
        binding.edtBirthday.setText(birthDay);


        if(sex.equals(getString(R.string.male))){
            Log.i(TAG, "Male: ");
            binding.radioMale.setChecked(true);
        }else if(sex.equals(getString(R.string.female))){
            binding.radioFemale.setChecked(true);
        }



    }



    private void getImage() {
        String imgAvatar = sharedPreferences.getString(Constance.AVATAR,"");
        if(!imgAvatar.equals("")){
            try{
                byte[] b = Base64.decode(imgAvatar, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                binding.navImvAvatar.setImageBitmap(bitmap);
            }catch (IllformedLocaleException e){
                // java.lang.IllegalArgumentException: bad base-64
            }
        }
    }
}