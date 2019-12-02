package com.example.wujunfeng.nestedscrollingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.wujunfeng.nestedscrollingapp.nestedscrollapp.NestedScrollActivity;
import com.example.wujunfeng.nestedscrollingapp.test.NestScrollActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initV();
    }

    private void initV() {
        TextView txtRecycler = (TextView) findViewById(R.id.txtRecycler);
        txtRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NestScrollActivity.class));
            }
        });
        TextView txtTxt = (TextView)findViewById(R.id.txtTxt);
        txtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NestedScrollActivity.class));
            }
        });

    }
}
