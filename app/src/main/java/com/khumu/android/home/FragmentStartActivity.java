package com.khumu.android.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.khumu.android.R;

public class FragmentStartActivity extends AppCompatActivity {
    public static String bodyPart = "";
    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public static String getBodyPart() {
        return bodyPart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_start);

        Button arm=findViewById(R.id.arm);
        arm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent("arm", 1);
            }
        });

        Button back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent("back", 2);
            }
        });

        Button leg=findViewById(R.id.leg);
        leg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent("leg", 3);
            }
        });

        Button stomach=findViewById(R.id.stomach);
        stomach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent("stomach", 4);
            }
        });

    }

    private void sendIntent(String bodyPart, int requestCode) {
        setBodyPart(bodyPart);
        Intent intent = new Intent(getApplicationContext(), WorkActivity.class);
        startActivityForResult(intent, requestCode);
    }
}
