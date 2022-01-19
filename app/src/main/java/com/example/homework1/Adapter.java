package com.example.homework1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework1.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ContactsViewHolder> {

    Activity activity;
    List<Model> list;

    public Adapter(List<Model> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item1, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtContactName.setText(list.get(position).getName());
        holder.txtContactNumber.setText(list.get(position).getPhone());
        holder.btnMessage.setOnClickListener(v -> {
            String url = "https://api.whatsapp.com/send?phone=" + list.get(position).getPhone();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(intent);
        });

        holder.btnCall.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(holder.btnCall.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE},0);
            }
            else
            {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", list.get(position).getPhone(), null));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {

        TextView txtContactName, txtContactNumber;
        Button btnMessage, btnCall;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtContactName = itemView.findViewById(R.id.txt_item_contact_name);
            txtContactNumber = itemView.findViewById(R.id.txt_item_contact_number);
            btnMessage = itemView.findViewById(R.id.btn_contact_message);
            btnCall = itemView.findViewById(R.id.btn_contact_call);
        }
    }
}

