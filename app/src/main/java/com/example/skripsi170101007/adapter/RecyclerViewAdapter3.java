package com.example.skripsi170101007.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.skripsi170101007.MyListData3;
import com.example.skripsi170101007.R;
import com.example.skripsi170101007.UpdateData3;
import com.example.skripsi170101007.model.data_plumbing;

import java.util.ArrayList;

public class RecyclerViewAdapter3 extends RecyclerView.Adapter<RecyclerViewAdapter3.ViewHolder>{

//Membuat Interfece
public interface dataListener{
    void onDeleteData(data_plumbing data, int position);
}

    //Deklarasi objek dari Interfece
    RecyclerViewAdapter3.dataListener listener;


    //Deklarasi Variable
    private ArrayList<data_plumbing> listPlumbing;
    private Context context;

    //Membuat Konstruktor, untuk menerima input dari Database
    public RecyclerViewAdapter3(ArrayList<data_plumbing> listPlumbing, Context context) {
        this.listPlumbing = listPlumbing;
        this.context = context;
        listener = (MyListData3)context;
    }

//ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
class ViewHolder extends RecyclerView.ViewHolder{

    private TextView CODE, Name, Brand, Capacity, Location, Maintenance;
    private LinearLayout ListItem;

    ViewHolder(View itemView) {
        super(itemView);
        //Menginisialisasi View-View yang terpasang pada layout RecyclerView kita
        CODE = itemView.findViewById(R.id.code);
        Name = itemView.findViewById(R.id.name);
        Brand = itemView.findViewById(R.id.brand);
        Capacity = itemView.findViewById(R.id.capacity);
        Location = itemView.findViewById(R.id.location);
        Maintenance = itemView.findViewById(R.id.maintenance);
        ListItem = itemView.findViewById(R.id.list_item);
    }
}

    @Override
    public RecyclerViewAdapter3.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_design, parent, false);
        return new RecyclerViewAdapter3.ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerViewAdapter3.ViewHolder holder, final int position) {
        //Mengambil Nilai/Value yenag terdapat pada RecyclerView berdasarkan Posisi Tertentu
        final String CODE = listPlumbing.get(position).getCode();
        final String Name = listPlumbing.get(position).getName();
        final String Brand = listPlumbing.get(position).getBrand();
        final String Capacity = listPlumbing.get(position).getCapacity();
        final String Location = listPlumbing.get(position).getLocation();
        final String Maintenance = listPlumbing.get(position).getMaintenance();

        //Memasukan Nilai/Value kedalam View (All TextView)
        holder.CODE.setText("Code: "+CODE);
        holder.Name.setText("Name: "+Name);
        holder.Brand.setText("Brand: "+Brand);
        holder.Capacity.setText("Capacity(kW): "+Capacity);
        holder.Location.setText("Floor(th): "+Location);
        holder.Maintenance.setText("Maintenance: "+Maintenance);

        //Menampilkan Menu Update dan Delete saat user melakukan long klik pada salah satu item
        holder.ListItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                final String[] action = {"Update", "Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setItems(action,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                        /*
                          Berpindah Activity pada halaman layout updateData
                          dan mengambil data pada listPlumbing, berdasarkan posisinya
                          untuk dikirim pada activity selanjutnya
                        */
                                Bundle bundle = new Bundle();
                                bundle.putString("dataCODE", listPlumbing.get(position).getCode());
                                bundle.putString("dataName", listPlumbing.get(position).getName());
                                bundle.putString("dataBrand", listPlumbing.get(position).getBrand());
                                bundle.putString("dataCapacity", listPlumbing.get(position).getCapacity());
                                bundle.putString("dataLocation", listPlumbing.get(position).getLocation());
                                bundle.putString("dataMaintenance", listPlumbing.get(position).getMaintenance());
                                bundle.putString("getPrimaryKey", listPlumbing.get(position).getKey());
                                Intent intent = new Intent(view.getContext(), UpdateData3.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                                //Menggunakan interface untuk mengirim data plumbing, yang akan dihapus
                                listener.onDeleteData(listPlumbing.get(position), position);
                                break;
                        }
                    }
                });
                alert.create();
                alert.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        //Menghitung Ukuran/Jumlah Data Yang Akan Ditampilkan Pada RecyclerView
        return listPlumbing.size();
    }

}

