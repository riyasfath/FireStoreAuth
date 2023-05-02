package com.example.firestoreauth;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    EditText email,pass;
    Button Submit;
    TextView register;
    FirebaseFirestore fstore;
    String PASSWORD;
    String EMAIL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.User_reg);
        pass = findViewById(R.id.Pass_reg);
        Submit = findViewById(R.id.Login);
        register = findViewById(R.id.Register);
        fstore = FirebaseFirestore.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 = email.getText().toString();
                String pass1 = pass.getText().toString();
                fstore.collection("Auth").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "data read", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()){

                                EMAIL = document.getString("email");
                                PASSWORD = document.getString("password");
                                if(email1.equals(EMAIL) && pass1.equals(PASSWORD)){
                                    Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                                }else{
                                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                        else{
                            Toast.makeText(MainActivity.this, "Data not shown", Toast.LENGTH_SHORT).show();
                        }
                    }
                });





            }
        });
    }
}