package com.example.sih;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sih.Adapter.Advice_adapter;
import com.example.sih.Adapter.Diagnosis_adapter;
import com.example.sih.Adapter.Presciption_adapter;
import com.example.sih.Adapter.Symptoms_adapter;
import com.example.sih.Models.Advice_model;
import com.example.sih.Models.Diagnosis_model;
import com.example.sih.Models.Name_model;
import com.example.sih.Models.Presciption_model;
import com.example.sih.Models.Symptoms_model;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DISPLAY_ACTIVITY extends AppCompatActivity {

    private ImageView mconvert;
    private EditText mname;
    private DatabaseReference mdatabase;
    private String time_in_millis;
    private LinearLayoutManager symptoms_linear, presciption_linear, advice_linear, diagnosis_linear;

    //adapter

    private Advice_adapter advice_adapter;
    private Diagnosis_adapter diagnosis_adapter;
    private Presciption_adapter presciption_adapter;
    private Symptoms_adapter symptoms_adapter;

    //List

    private List<Advice_model> advice_model;
    private List<Diagnosis_model> diagnosis_models;
    private List<Presciption_model> presciption_models;
    private List<Symptoms_model> symptoms_models;

    private int i = 1;


    private RecyclerView msymptoms, mdiagnosis, mpresciption, madvice;
    private ImageView symp_add, diag_add, pres_add, advice_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__activity);

        symptoms_linear = new LinearLayoutManager(DISPLAY_ACTIVITY.this);
        presciption_linear = new LinearLayoutManager(DISPLAY_ACTIVITY.this);
        advice_linear = new LinearLayoutManager(DISPLAY_ACTIVITY.this);
        diagnosis_linear = new LinearLayoutManager(DISPLAY_ACTIVITY.this);

        mconvert = (ImageView) findViewById(R.id.convert_to_pdf);
        mname = (EditText) findViewById(R.id.name);

        //Recyeler View

        msymptoms = (RecyclerView) findViewById(R.id.symptoms_recycler);
        mdiagnosis = (RecyclerView) findViewById(R.id.diagnosis_recycler);
        mpresciption = (RecyclerView) findViewById(R.id.presciption_recycler);
        madvice = (RecyclerView) findViewById(R.id.advice_recycler);


        //linear set to al recyceler View
        msymptoms.setLayoutManager(symptoms_linear);
        mdiagnosis.setLayoutManager(diagnosis_linear);
        mpresciption.setLayoutManager(presciption_linear);
        madvice.setLayoutManager(advice_linear);

        //recycler view add button
        symp_add = (ImageView) findViewById(R.id.symptoms_add);
        diag_add = (ImageView) findViewById(R.id.diagnosis_add);
        pres_add = (ImageView) findViewById(R.id.presciption_add);
        advice_add = (ImageView) findViewById(R.id.advice_add);

        //fixed size
        msymptoms.setHasFixedSize(true);
        mdiagnosis.setHasFixedSize(true);
        mpresciption.setHasFixedSize(true);
        madvice.setHasFixedSize(true);

        //list initialization
        advice_model = new ArrayList<>();
        diagnosis_models = new ArrayList<>();
        presciption_models = new ArrayList<>();
        symptoms_models = new ArrayList<>();


        //adapter initialization
        advice_adapter = new Advice_adapter(DISPLAY_ACTIVITY.this, advice_model);
        diagnosis_adapter = new Diagnosis_adapter(DISPLAY_ACTIVITY.this, diagnosis_models);
        presciption_adapter = new Presciption_adapter(DISPLAY_ACTIVITY.this, presciption_models);
        symptoms_adapter = new Symptoms_adapter(DISPLAY_ACTIVITY.this, symptoms_models);


        //adpapter segregation

        msymptoms.setAdapter(symptoms_adapter);
        mdiagnosis.setAdapter(diagnosis_adapter);
        mpresciption.setAdapter(presciption_adapter);
        madvice.setAdapter(advice_adapter);


        mdatabase = FirebaseDatabase.getInstance().getReference().child("Android");

        mdatabase.child("NAME").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot post : dataSnapshot.getChildren()) {

                    mname.setText(post.getValue(String.class).toUpperCase());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mdatabase.child("DIAGNOSIS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                diagnosis_models.clear();
                i = 1;

                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    Diagnosis_model event = new Diagnosis_model("", "");
                    event.setSecond(post.getValue(String.class));
                    event.setFirst(Integer.toString(i));
                    i++;
                    diagnosis_models.add(event);

                }

                diagnosis_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mdatabase.child("ADVICE").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i = 1;
                advice_model.clear();

                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    Advice_model event = new Advice_model("", "");
                    event.setSecond(post.getValue(String.class));
                    event.setFirst(Integer.toString(i));
                    ++i;
                    advice_model.add(event);

                }

                advice_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mdatabase.child("SYMPTOMS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i = 1;
                symptoms_models.clear();

                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    Symptoms_model event = new Symptoms_model("", "");
                    event.setSecond(post.getValue(String.class));
                    event.setFirst(Integer.toString(i));
                    ++i;
                    symptoms_models.add(event);
                }

                symptoms_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mdatabase.child("PRESCIPTION").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                presciption_models.clear();

                for (DataSnapshot post : dataSnapshot.getChildren()) {

                    Presciption_model event = new Presciption_model(post.getKey(), "", "");
                    Name_model getValue = post.getValue(Name_model.class);
                    event.setDosage(getValue.getDosage());
                    event.setFrequency(getValue.getFrequency());
                    presciption_models.add(event);

                }

                presciption_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //button methods

        symp_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(DISPLAY_ACTIVITY.this, "", Toast.LENGTH_SHORT).show();

                //TODO minus button

                Symptoms_model model = new Symptoms_model(Integer.toString(symptoms_models.size()+ 1), "symptom");
                symptoms_models.add(model);

                symptoms_adapter.notifyDataSetChanged();

            }
        });

        diag_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO minus sign

                Diagnosis_model model = new Diagnosis_model(Integer.toString(diagnosis_models.size() +1), "diagnos");
                diagnosis_models.add(model);

                diagnosis_adapter.notifyDataSetChanged();

            }
        });

        pres_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO minus sign

                Presciption_model model = new Presciption_model("Medicine name", "dosage", "frequency");
                presciption_models.add(model);

                presciption_adapter.notifyDataSetChanged();

            }
        });

        advice_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO  minus sign

                Advice_model model = new Advice_model(Integer.toString(advice_model.size() +1), "ADVICE");
                advice_model.add(model);

                advice_adapter.notifyDataSetChanged();


            }
        });


        mconvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO pdf created

                time_in_millis = String.format("%s", System.currentTimeMillis());


                String text = "";
                text += "                                                                " + "HOSPITAL NAME";
                text += "\n\n\n" + "NAME of the patient : " +"     "+ mname.getText().toString();
                text += "\n\n" + "Symptoms" + "\n\n";
                for (int j = 0; j < symptoms_models.size(); ++j) {
                    text += symptoms_models.get(j).getFirst() + "         ";
                    text += symptoms_models.get(j).getSecond() + "\n";
                }

                String text2 = "\nDIAGNOSIS" + "\n\n";

                for (int j = 0; j < diagnosis_models.size(); ++j) {

                    text2 += diagnosis_models.get(j).getFirst() + "         ";
                    text2 += diagnosis_models.get(j).getSecond() + "\n";

                }

                String text3 = "\nPRESCIPTION" + "\n\n";

                for (int j = 0; j < presciption_models.size(); ++j) {

                    text3 += presciption_models.get(j).getMedicine() + "            " + presciption_models.get(j).getDosage() + "              " + presciption_models.get(j).getFrequency();
                    text3 += "\n";
                }

                String text4 = "\nADVICE" + "\n\n";

                for (int j = 0; j < advice_model.size(); ++j) {

                    text4 += advice_model.get(j).getFirst() + "         ";
                    text4 += advice_model.get(j).getSecond() + "\n";

                }

                makeFile(time_in_millis + ".txt", text + text2 + text3 + text4);
                String outputPath = Environment.getExternalStorageDirectory() + File.separator + "/Pictures/Text_Folder" + "/" + time_in_millis + ".pdf";
                convertText(outputPath);

                // uploadtheFile(outputPath);


                Toast.makeText(DISPLAY_ACTIVITY.this, "PDF CREATION", Toast.LENGTH_SHORT).show();

            }

        });


    }

    private void uploadFiles(String filePAth) {

        Uri file = Uri.fromFile(new File(filePAth));
        final StorageReference riversRef = FirebaseStorage.getInstance().getReference().child(file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if (task.isSuccessful()) {

                    final String path = task.getResult().toString();

                    FirebaseDatabase.getInstance().getReference().child("pdf path").child("URL").setValue(path).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {


                            Intent send_intent = new Intent(Intent.ACTION_SEND);
                            send_intent.setType("text/plain");
                            send_intent.putExtra(Intent.EXTRA_TEXT, "Your Report " + "\n\n" + path);
                            startActivity(Intent.createChooser(send_intent, "Share Using"));
                        }
                    });
                }

            }
        });


    }


    private void convertText(String outputPath) {
        FileInputStream fis = null;
        DataInputStream in = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            File file = new File(getAlbumStorageDir("Text_Folder"), String.format(time_in_millis + ".txt", System.currentTimeMillis()));
            if (file.exists()) {
                fis = new FileInputStream(file);
                in = new DataInputStream(fis);
                isr = new InputStreamReader(in);
                br = new BufferedReader(isr);
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    Paragraph para = new Paragraph(strLine + "\n");
                    para.setAlignment(Element.ALIGN_JUSTIFIED);
                    document.add(para);
                }

                uploadFiles(outputPath);
                // showAlertDialog("Converting text...", "Converting text to PDF finished... Generated PDF saved in " + outputPath);
            } else {
                showAlertDialog("Converting text...", "File " + "File Path" + " does not exist!");
            }
            document.close();
        } catch (Exception e) {
            showAlertDialog("Converting text...", "An error has occurred: " + e.getMessage());
        }
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    private void makeFile(String filename, String text) {
        try {
            File gpxfile = new File(getAlbumStorageDir("Text_Folder"), filename);


            FileWriter writer = new FileWriter(gpxfile, true);
            writer.append(text + "\n\n");
            writer.flush();
            writer.close();
            // Toast.makeText(this, "Data has been written to Report File", Toast.LENGTH_SHORT).show();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }

        return file;
    }


}
