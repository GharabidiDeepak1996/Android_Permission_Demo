package com.example.permissionsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_result );
        textView=findViewById( R.id.text );
        if(getIntent().getExtras()!=null){

            String message=getIntent().getExtras().getString( "Message" );
            textView.setText( message );
        }
    }
}
