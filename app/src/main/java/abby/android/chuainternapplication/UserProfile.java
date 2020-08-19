package abby.android.chuainternapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.ResultSet;

public class UserProfile extends AppCompatActivity {
    TextView email;
    TextView uuid;
    Button logout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        // Initializing Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Initializing variables
        email = findViewById(R.id.emailSaved);
        uuid = findViewById(R.id.uuid);
        logout = findViewById(R.id.logout);

        //Initializing events
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        displayUser(currentUser);
    }

    public void displayUser(FirebaseUser currentUser) {

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        if (currentUser != null) {
            String emailSaved = currentUser.getEmail();
            String userUuid = currentUser.getUid();
            email.setText(emailSaved);
            uuid.setText(userUuid);
        }
    }


    public void signOut() {
        mAuth.signOut();
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}