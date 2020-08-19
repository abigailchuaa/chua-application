package abby.android.chuainternapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends AppCompatActivity {
    EditText email;
    EditText password;
    EditText confirm;
    Button create;
    Button cancel;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Initializing Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Initializing variables
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirmPassword);
        create = findViewById(R.id.createAccount);
        cancel = findViewById(R.id.cancel);

        //Initializing events
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(email.getText().toString(), password.getText().toString());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createAccount(String email, String password) {
        Log.d("Register", "createAccount:" + email);
        if (confirm.getText().toString().equals(password)) {
            // Creating a user with using email and password
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Register", "Create user by email successful");
                                Toast toast = Toast.makeText(getApplicationContext(), "Register successful", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 80);
                                toast.show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                finish();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Register", "Error creating user by email", task.getException());
                                Toast toast = Toast.makeText(getApplicationContext(), "Something went wrong. Please try again.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 80);
                                toast.show();
                                updateUI(null);
                            }
                        }
                    });
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Confirm password does not match", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 80);
            toast.show();
        }
    }

        public void updateUI (FirebaseUser account){
            if (account != null) {
                mAuth.signOut();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Log.d("Register", "Back to MainActivity");
            }
        }

    public void cancel (){
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        }
    }



