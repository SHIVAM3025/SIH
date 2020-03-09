package com.example.sih.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sih.Models.Symptoms_model;
import com.example.sih.R;

import java.util.List;

public class Symptoms_adapter extends RecyclerView.Adapter<Symptoms_adapter.HoldView> {

    private List<Symptoms_model> models;
    private Context mcontext;

    public Symptoms_adapter( Context mcontext , List<Symptoms_model> models) {
        this.models = models;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public HoldView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(mcontext).inflate(R.layout.recycler_view_display ,parent , false);
       return new Symptoms_adapter.HoldView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HoldView holder, final int position) {

        final Symptoms_model uploadCurrent = models.get(position);
        holder.mfirst.setText(uploadCurrent.getFirst());
        holder.msecond.setText(uploadCurrent.getSecond());

        holder.msecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                uploadCurrent.setSecond(s.toString());
            }
        });

        holder.mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                models.remove(position);
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class HoldView extends RecyclerView.ViewHolder
    {
        EditText msecond ;
        TextView mfirst;
        ImageView mdelete;

        public HoldView(@NonNull View itemView) {

            super(itemView);

            mfirst  =itemView.findViewById(R.id.one);
            msecond = itemView.findViewById(R.id.second);
            mdelete = itemView.findViewById(R.id.delete);

        }
    }
}
