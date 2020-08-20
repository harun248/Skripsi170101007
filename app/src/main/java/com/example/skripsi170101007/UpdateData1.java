package com.example.skripsi170101007;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.skripsi170101007.model.data_electrical;
import com.example.skripsi170101007.model.data_electrical;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.os.Build.VERSION_CODES.M;

public class UpdateData1 extends AppCompatActivity {

    //Deklarasi Variable
    private EditText codeBaru, nameBaru, brandBaru, capacityBaru, locationBaru, maintenanceBaru;
    private Button update;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private String cekCODE, cekName, cekBrand, cekCapacity, cekLocation, cekMaintenance;
    private ImageButton Btn_calendar;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data1);
        getSupportActionBar().setTitle("Update Data");
        codeBaru = findViewById(R.id.new_code);
        nameBaru = findViewById(R.id.new_name);
        brandBaru = findViewById(R.id.new_brand);
        capacityBaru = findViewById(R.id.new_capacity);
        locationBaru = findViewById(R.id.new_location);
        maintenanceBaru = findViewById(R.id.new_maintenance);
        Btn_calendar = findViewById(R.id.btn_calendar);
        update = findViewById(R.id.update);

        //Input format tanggal
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        Btn_calendar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(UpdateData1.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Mendapatkan Instance autentikasi dan Referensi dari Database
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        getData();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Mendapatkan Data Electrical yang akan dicek
                cekCODE = codeBaru.getText().toString();
                cekName = nameBaru.getText().toString();
                cekBrand = brandBaru.getText().toString();
                cekCapacity = capacityBaru.getText().toString();
                cekLocation = locationBaru.getText().toString();
                cekMaintenance = maintenanceBaru.getText().toString();

                //Mengecek agar tidak ada data yang kosong, saat proses update
                if(isEmpty(cekCODE) || isEmpty(cekName) || isEmpty(cekBrand) || isEmpty(cekCapacity) || isEmpty(cekLocation) || isEmpty(cekMaintenance)){
                    Toast.makeText(UpdateData1.this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
                }else {
                    /*
                      Menjalankan proses update data.
                      Method Setter digunakan untuk mendapakan data baru yang diinputkan User.
                    */
                    data_electrical setElectrical = new data_electrical();
                    setElectrical.setCode(codeBaru.getText().toString());
                    setElectrical.setName(nameBaru.getText().toString());
                    setElectrical.setBrand(brandBaru.getText().toString());
                    setElectrical.setCapacity(capacityBaru.getText().toString());
                    setElectrical.setLocation(locationBaru.getText().toString());
                    setElectrical.setMaintenance(maintenanceBaru.getText().toString());
                    updateElectrical(setElectrical);
                }
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        maintenanceBaru.setText(sdf.format(myCalendar.getTime()));
    }

    // Mengecek apakah ada data yang kosong, sebelum diupdate
    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    //Menampilkan data yang akan di update
    private void getData(){
        //Menampilkan data dari item yang dipilih sebelumnya
        final String getCODE = getIntent().getExtras().getString("dataCODE");
        final String getName = getIntent().getExtras().getString("dataName");
        final String getBrand = getIntent().getExtras().getString("dataBrand");
        final String getCapacity = getIntent().getExtras().getString("dataCapacity");
        final String getLocation = getIntent().getExtras().getString("dataLocation");
        final String getMaintenance = getIntent().getExtras().getString("dataMaintenance");
        codeBaru.setText(getCODE);
        nameBaru.setText(getName);
        brandBaru.setText(getBrand);
        capacityBaru.setText(getCapacity);
        locationBaru.setText(getLocation);
        maintenanceBaru.setText(getMaintenance);
    }

    //Proses Update data yang sudah ditentukan
    private void updateElectrical(data_electrical electrical){
        String userID = auth.getUid();
        String getKey = getIntent().getExtras().getString("getPrimaryKey");
        database.child("Aset")
                .child("Electrical")
                .child(getKey)
                .setValue(electrical)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        codeBaru.setText("");
                        nameBaru.setText("");
                        brandBaru.setText("");
                        capacityBaru.setText("");
                        locationBaru.setText("");
                        maintenanceBaru.setText("");
                        Toast.makeText(UpdateData1.this, "Data Berhasil diubah", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}
