package com.example.elielpc.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class advertencia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertencia);
    }

    public void onClickContinuar(View view){
        Intent intent = new Intent(this,registro.class);
        startActivity(intent);
    }

    public void onClickVolver(View view){
        Intent intent = new Intent(this,loginn.class);
        startActivity(intent);
    }
}
