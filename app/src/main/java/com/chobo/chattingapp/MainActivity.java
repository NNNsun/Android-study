package com.chobo.chattingapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    static String ProviderId;
     private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth=null;
    private ActivityResultLauncher<Intent> resultLauncher;

    // Configure Google Sign In
    GoogleSignInOptions gso = new GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("818065048580")
            .requestEmail()
            .build();


    EditText etId, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        etId = (EditText) findViewById(R.id.etId);
        etPassword = (EditText) findViewById(R.id.etPassword);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        //클릭 이벤트
        btnLogin.setOnClickListener((v)->  {
            String stEmail = etId.getText().toString();
            String stPassword = etPassword.getText().toString();
                //Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_LONG).show();
                //괄호안에 목적지를 적어준다
                //화면전환 기능
                Intent in = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(in);
        });

        Button btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener((v) ->  {

                //기입한 이메일을 가져옴
                String stEmail = etId.getText().toString();
                //기입한 패스워드를 가져옴
                String stPassword = etPassword.getText().toString();
                if (stEmail.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please insert Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (stPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please insert password", Toast.LENGTH_LONG).show();
                    return;
                }
                //Toast.makeText(MainActivity.this, "Email: " + stEmail + ", password: " + stPassword, Toast.LENGTH_LONG).show();
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            currentUser.reload();
//        }
        //updateUI(currentUser);
        // updateUI(currentUser);
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
        FirebaseAuth.getInstance().signOut();
    }

}