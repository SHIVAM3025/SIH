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

import com.example.sih.Models.Advice_model;
import com.example.sih.R;


import java.util.List;


public class Advice_adapter  extends RecyclerView.Adapter<Advice_adapter.HoldView>{

    private Context mcontext;
    private List<Advice_model> models;

    public Advice_adapter(Context mcontext, List<Advice_model> models) {
        this.mcontext = mcontext;
        this.models = models;
    }

    @NonNull
    @Override
    public HoldView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.recycler_view_display ,parent , false);
        return new Advice_adapter.HoldView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HoldView holder, final int position) {

        final Advice_model uploadCurrent = models.get(position);
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

        holder.mminus.setOnClickListener(new View.OnClickListener() {
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
        ImageView mminus;

        public HoldView(@NonNull View itemView) {

            super(itemView);

            mminus =itemView.findViewById(R.id.delete);
            mfirst  =itemView.findViewById(R.id.one);
            msecond = itemView.findViewById(R.id.second);

        }
    }

}
