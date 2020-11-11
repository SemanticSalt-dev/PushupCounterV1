package com.example.pushupcounterv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    public static final String counter = "pushUpCount";
    Button button1,button2;
    TextView theCount;
    ConstraintLayout root;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkView();
        initializeCounter(sharedPref);
        theCount.setText(String.valueOf(sharedPref.getInt(counter, 0)));

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = sharedPref.getInt(counter, 0);
                Editor editor = sharedPref.edit();
                i++;
                editor.putInt(counter, i);
                editor.apply();
                theCount.setText(String.valueOf(i));
            }
        });
    }
    private void linkView() {
        theCount = (TextView) findViewById(R.id.theCount);
        button1=(Button) findViewById(R.id.reset);
        button2=(Button) findViewById(R.id.add10);
        root = (ConstraintLayout) findViewById(R.id.mainLayout);
        sharedPref = getPreferences(Context.MODE_PRIVATE);
    }
    public void doSomethingMathish(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.reset:
                int i = 0;
                Editor backTo0 = sharedPref.edit();
                backTo0.putInt(counter, i);
                backTo0.apply();
                theCount.setText(String.valueOf(i));
                break;
            case R.id.add10:
                int g = sharedPref.getInt(counter, 0);
                Editor add10 = sharedPref.edit();
                g = g + 10;
                add10.putInt(counter, g);
                add10.apply();
                theCount.setText(String.valueOf(g));
                break;
        }
    }
    private void initializeCounter(SharedPreferences sharedPref) {
        if(!sharedPref.contains(counter)){
            Editor editor = sharedPref.edit();
            final Editor editor1 = editor.putInt(counter, 0);
            editor.apply();
        };
    }
}