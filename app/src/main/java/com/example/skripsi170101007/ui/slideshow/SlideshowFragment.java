package com.example.skripsi170101007.ui.slideshow;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.skripsi170101007.MainActivity;
import com.example.skripsi170101007.MyListData2;
import com.example.skripsi170101007.MyListData3;
import com.example.skripsi170101007.R;
import com.example.skripsi170101007.model.data_plumbing;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SlideshowFragment extends Fragment {

    //Deklarasi Variable
    private EditText CODE, Name, Brand, Capacity, Location, Maintenance;
    private FirebaseAuth auth;
    private ImageButton Logout;
    private FloatingActionButton Simpan;
    private FloatingActionButton Showdata;
    private ImageButton Btn_calendar;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    //Cek Fields yang kosong
    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        //Inisialisasi edittext
        CODE = root.findViewById(R.id.code);
        Name = root.findViewById(R.id.name);
        Brand = root.findViewById(R.id.brand);
        Capacity = root.findViewById(R.id.capacity);
        Location = root.findViewById(R.id.location);
        Maintenance = root.findViewById(R.id.maintenance);
        Btn_calendar = root.findViewById(R.id.btn_calendar);

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
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Mendapakan Instance Firebase Autentifikasi
        auth = FirebaseAuth.getInstance();

        //Action pada button simpan
        Simpan =  root.findViewById(R.id.save);
        Simpan.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mendapatkan UserID dari pengguna yang Terautentikasi
                String getUserID = auth.getCurrentUser().getUid();

                //Mendapatkan Instance dari Database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference getReference;

                //Menyimpan Data yang diinputkan User kedalam Variable
                String getCODE = CODE.getText().toString();
                String getName = Name.getText().toString();
                String getBrand = Brand.getText().toString();
                String getCapacity = Capacity.getText().toString();
                String getLocation = Location.getText().toString();
                String getMaintenance = Maintenance.getText().toString();

                getReference = database.getReference(); // Mendapatkan Referensi dari Database

                // Mengecek apakah ada data yang kosong
                if(isEmpty(getCODE) || isEmpty(getName) || isEmpty(getBrand) || isEmpty(getCapacity) || isEmpty(getLocation) || isEmpty(getMaintenance)){
                    //Jika Ada, maka akan menampilkan pesan singkan seperti berikut ini.
                    Toast.makeText(getActivity(), "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
                }else {
                    /*
                    Jika Tidak, maka data dapat diproses dan meyimpannya pada Database
                    Menyimpan data referensi pada Database berdasarkan User ID dari masing-masing Akun
                    */
                    getReference.child("Aset").child("Plumbing").push()
                            .setValue(new data_plumbing(getCODE, getName, getBrand, getCapacity, getLocation, getMaintenance))
                            .addOnSuccessListener( getActivity(), new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database
                                    CODE.setText("");
                                    Name.setText("");
                                    Brand.setText("");
                                    Capacity.setText("");
                                    Location.setText("");
                                    Maintenance.setText("");
//                                    Toast.makeText(getActivity(), "Data Tersimpan", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getActivity(), MyListData3.class);
                                    startActivity(i);
                                }
                            });
                }
            }
        }));


        //Action pada button logout
//        Logout = (ImageButton) root.findViewById(R.id.logout);
//        Logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AuthUI.getInstance()
//                        .signOut(getActivity())
//                        .addOnCompleteListener(new OnCompleteListener() {
//                            @Override
//                            public void onComplete(@NonNull Task task) {
//
//                                Intent i = new Intent(getActivity(), MainActivity.class);
//                                startActivity(i);
//                                finish();
//                            }
//
//                            private void finish() {
//                            }
//                        });
//                Toast.makeText(getActivity(),"Logout Berhasil", Toast.LENGTH_SHORT).show();
//            }
//        });


        //Action pada button tampil data
        Showdata = root.findViewById(R.id.showdata);
        Showdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MyListData3.class);
                startActivity(i);

            }

        });

        return root;
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Maintenance.setText(sdf.format(myCalendar.getTime()));
    }
}
