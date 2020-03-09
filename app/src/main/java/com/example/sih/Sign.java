package com.example.sih;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Sign extends AppCompatActivity {

    private ImageView minfo;
    private SignaturePad msign;
    private Button mclear , msave;

    private SignaturePad signaturePad;
    private String ImagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        signaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Sign.this);
                final AlertDialog.Builder progressbuilder = new AlertDialog.Builder(Sign.this);
                View mview = getLayoutInflater().inflate(R.layout.dialog_box_login, null);
                final View progress = getLayoutInflater().inflate(R.layout.dialog_process, null);
                Button myes = (Button) mview.findViewById(R.id.yes);
                Button mno = (Button) mview.findViewById(R.id.no);

                mBuilder.setView(mview);
                progressbuilder.setView(progress);

                final AlertDialog dialog = mBuilder.create();
                final AlertDialog progressdialog = progressbuilder.create();


                myes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        progressdialog.show();

                        Bitmap signatureBitmap = signaturePad.getTransparentSignatureBitmap();
                        if (addJpgSignatureToGallery(signatureBitmap)) {
                            Toast.makeText(Sign.this, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                            progressdialog.dismiss();

                            //TODO Intent to next activity

                            signaturePad.clear();

                            Toast.makeText(Sign.this, ImagePath, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(Sign.this, "Unable to store the signature", Toast.LENGTH_SHORT).show();
                            progressdialog.dismiss();
                        }


                    }
                });

                mno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(Sign.this, "NO", Toast.LENGTH_SHORT).show();

                    }
                });

                dialog.show();

            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
            }
        });

    }

    private boolean restorePrefData() {


        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isSigned",false);
        return  isIntroActivityOpnendBefore;



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

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);

            ImagePath = photo.getAbsolutePath() ;
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        Sign.this.sendBroadcast(mediaScanIntent);
    }


    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }




}
