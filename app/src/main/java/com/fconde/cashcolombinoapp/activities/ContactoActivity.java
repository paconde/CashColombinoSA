package com.fconde.cashcolombinoapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fconde.cashcolombinoapp.R;

public class ContactoActivity extends AppCompatActivity {

    ImageButton imgBtnCall1, imgBtnCall2;
    TextView txtVwEmail, txtVwWeb;
    static final String NUM_TEL_1 = "959233330";
    static final String NUM_TEL_2 = "959233319";
    static final String EMAIL = "cashcolombino@barea.com";
    private final int PHONE_CALL_CODE = 100;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        imgBtnCall1 = (ImageButton) findViewById(R.id.imageButtonLlamada1);
        imgBtnCall2 = (ImageButton) findViewById(R.id.imageButtonLlamada2);
        txtVwEmail = (TextView)findViewById(R.id.textViewEmail);
        txtVwWeb = (TextView)findViewById(R.id.textViewWeb);

        imgBtnCall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // comprobar la version de android que estamos corriendo
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    phoneNumber = NUM_TEL_1;
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                } else {
                    OlderVersions(NUM_TEL_1);
                }
            }
        });

        imgBtnCall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // comprobar la version de android que estamos corriendo
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    phoneNumber = NUM_TEL_2;
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                } else {
                    OlderVersions(NUM_TEL_2);
                }
            }
        });

        txtVwEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMail = new Intent(Intent.ACTION_SEND);
                intentMail.setData(Uri.parse("mailto:"));
                intentMail.setType("text/plain");
                intentMail.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL});
                startActivity(intentMail);
            }
        });

        txtVwWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.barea.com";
                Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intentWeb);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PHONE_CALL_CODE:

                String permission = permissions[0];
                int result = grantResults[0];
                if (permission.equals(Manifest.permission.CALL_PHONE)) {
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intentCall);
                    }else {
                        Toast.makeText(ContactoActivity.this, "Se ha declinado el acceso", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private void OlderVersions(String phoneNumber){
        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        if(CheckPermission(Manifest.permission.CALL_PHONE)){
            startActivity(intentCall);
        }else{
            Toast.makeText(ContactoActivity.this, "Se ha declinado el acceso", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean CheckPermission(String permission){
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
