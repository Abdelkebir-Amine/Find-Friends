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
import com.google.firebase.auth.FirebaseAuth;

import poly.mobile.findfriends.databinding.ActivityLoginBinding;
import poly.mobile.findfriends.databinding.ActivityNewUserBinding;

public class NewUser extends AppCompatActivity {

    ActivityNewUserBinding binding;
    FirebaseAuth mAuth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewUserBinding.inflate(LayoutInflater.from(NewUser.this));

        mAuth = FirebaseAuth.getInstance();

        setContentView(binding.getRoot());

        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = binding.mailNewUser.getText().toString();
                String password = binding.passwordNewUser.getText().toString();
                String confirmPassword = binding.confirmPasswordNewUser.getText().toString();

                if(password.equals(confirmPassword) && password.length() >= 6 && confirmPassword.length() >= 6 && mail.contains("@") ){
                    mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(NewUser.this,MainActivity.class));
                                Toast.makeText(NewUser.this, "User added successfully", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(NewUser.this, "Error : "+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(NewUser.this, "Invalid mail or password ! ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnExitNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewUser.this.finish();
            }
        });
    }
}