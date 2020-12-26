package com.example.isss_usr;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder>  {

    private String[] SubjectValues;
    private Context context;

    public AdapterRecyclerView(Context context1, String[] SubjectValues1) {

        SubjectValues = SubjectValues1;
        context = context1;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        TextView detail;

        ViewHolder(View v) {

            super(v);

            textView = v.findViewById(R.id.show);
            detail = v.findViewById(R.id.detail);
        }
    }
    @NonNull

    public AdapterRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_laporan, parent, false);

        return new ViewHolder(view);
    }


    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textView.setText(SubjectValues[position]);
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, Detail_laporan.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }


    public int getItemCount() {

        return SubjectValues.length;
    }
}
