package com.example.elielpc.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnLogout;
   TextView etName, etUsername, etAge;

    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (TextView) findViewById(R.id.etNombreMain);
        etUsername = (TextView) findViewById(R.id.etNombreUsuarioMain);
        etAge = (TextView)findViewById(R.id.etEdadMain);

        userLocalStore = new UserLocalStore(this);
    }

    protected void onStart(){
        super.onStart();
        if (authenticate() == true){
            User user = userLocalStore.getLoggedInUser();
            etUsername.setText(user.getUsername());
            etName.setText(user.getName());
            etAge.setText(user.getAge() + "");
        }
        else{
            startActivity(new Intent(MainActivity.this,loginn.class));
        }
    }

    private boolean authenticate() {
        if (userLocalStore.getLoggedInUser() == null) {
            Intent intent = new Intent(this, loginn.class);
            startActivity(intent);

            return false;
        }
        return true;
    }

    private void displayUserDetails() {
        User user = userLocalStore.getLoggedInUser();
        etUsername.setText(user.getUsername());
        etName.setText(user.getName());
        etAge.setText(user.getAge() + "");
    }

    public void onClickCerrarSesion(View view){
        userLocalStore.clearUserData();
        userLocalStore.setUserLoggedIn(false);

        Intent intent = new Intent(this,loginn.class);
        startActivity(intent);
        finish();
    }

}
