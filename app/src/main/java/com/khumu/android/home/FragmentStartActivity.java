package com.khumu.android.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.khumu.android.R;



public class FragmentStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_start);

        Button arm=findViewById(R.id.arm);
        arm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WorkArmActivity.class );
                startActivityForResult(intent,1);
            }
        });

        Button back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WorkBackActivity.class );
                startActivityForResult(intent,2);
            }
        });

        Button leg=findViewById(R.id.leg);
        leg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WorkLegActivity.class );
                startActivityForResult(intent,3);
            }
        });

        Button stomach=findViewById(R.id.stomach);
        stomach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WorkStomachActivity.class );
                startActivityForResult(intent,4);
            }
        });

    }


}
