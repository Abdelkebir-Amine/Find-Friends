package poly.mobile.findfriends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import poly.mobile.findfriends.databinding.ActivityLoginBinding;


public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth mAuth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        binding = ActivityLoginBinding.inflate(LayoutInflater.from(Login.this));

        setContentView(binding.getRoot());

        binding.tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,NewUser.class));
            }
        });

        binding.btnConnectLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = binding.mailLogin.getText().toString();
                String password = binding.passwordLogin.getText().toString();

                if (!mail.isEmpty() && !password.isEmpty()){
                    mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(Login.this,MainActivity.class));
                            }
                            else{
                                Toast.makeText(Login.this, "Error : "+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(Login.this, "Missing data !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}












