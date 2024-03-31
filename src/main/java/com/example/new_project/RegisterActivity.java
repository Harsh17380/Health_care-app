package com.example.new_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    EditText edUsername ,edEmail , edPassword , edConfirm ;
    Button btn ;
    TextView tv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edUsername = findViewById(R.id.editTextRegUsername);
        edEmail = findViewById(R.id.editTextRegEmail);
        edPassword = findViewById(R.id.editTextRegPassword);
        edConfirm = findViewById(R.id.editTextRegConfirmPassword);
        btn = findViewById(R.id.buttonRegister);
        tv = findViewById(R.id.textViewExistingUser);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                String email = edEmail.getText().toString();
                String confirm = edConfirm.getText().toString();
                Database db = new Database(getApplicationContext(),"healthcare",null ,1);

                if(username.isEmpty() || password.isEmpty() || email.isEmpty() || confirm.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill all the details" , Toast.LENGTH_SHORT).show();
                } else {
                    if (password.compareTo(confirm) == 0){
                        if (validatePassword(password)){
                            db.register(username, email , password);
                            Toast.makeText(getApplicationContext(),"Registration Successful" , Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(),"Password must contain 8 letters , Uppercase , lowercase , number and a special character" , Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"Password and confirm password didn't match" , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public static boolean validatePassword(String passwordhere) {
        // Minimum length check
        if (passwordhere.length() < 8) {
            return false;
        }
        // Uppercase letter check
        if (!Pattern.compile("[A-Z]").matcher(passwordhere).find()) {
            return false;
        }
        // Lowercase letter check
        if (!Pattern.compile("[a-z]").matcher(passwordhere).find()) {
            return false;
        }
        // Digit check
        if (!Pattern.compile("[0-9]").matcher(passwordhere).find()) {
            return false;
        }
        // Special character check (optional)
        if (!Pattern.compile("[!@#$%^&*()-+=]").matcher(passwordhere).find()) {
            return false;
        }
        // No whitespace check
        if (passwordhere.contains(" ")) {
            return false;
        }
        return true;
    }
}