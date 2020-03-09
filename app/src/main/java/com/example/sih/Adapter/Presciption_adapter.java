package com.example.sih.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sih.Models.Presciption_model;
import com.example.sih.R;

import java.util.List;

public class Presciption_adapter extends RecyclerView.Adapter<Presciption_adapter.HoldView>{

    private Context mcontext;
    private List<Presciption_model> models;

    public Presciption_adapter(Context mcontext, List<Presciption_model> models) {
        this.mcontext = mcontext;
        this.models = models;
    }

    @NonNull
    @Override
    public HoldView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.presciption_layout , parent , false);
        return new Presciption_adapter.HoldView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HoldView holder, final int position) {

            final Presciption_model uploadCurrent = models.get(position);

            holder.mmedicine.setText(uploadCurrent.getMedicine());
            holder.mdosage.setText(uploadCurrent.getDosage());
            holder.mfrequency.setText(uploadCurrent.getFrequency());

            holder.mdosage.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    uploadCurrent.setDosage(s.toString());
                }
            });

            holder.mfrequency.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    uploadCurrent.setFrequency(s.toString());
                }
            });

            holder.mmedicine.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    uploadCurrent.setMedicine(s.toString());
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

    public class HoldView extends RecyclerView.ViewHolder{

        EditText mmedicine , mdosage , mfrequency;
        ImageView mdelete;

        public HoldView(@NonNull View itemView) {
            super(itemView);

            mdelete = itemView.findViewById(R.id.delete);
            mmedicine = itemView.findViewById(R.id.medicine);
            mdosage = itemView.findViewById(R.id.dosage);
            mfrequency = itemView.findViewById(R.id.frequency);

        }
    }


}
