package com.chobo.chattingapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chobo.chattingapp.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private SignInButton signInButton;


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    static String ProviderId;
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private ActivityResultLauncher<Intent> resultLauncher;
    private ActivityMainBinding mBinding;
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

        signInButton = findViewById(R.id.signInButton);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        etId = (EditText) findViewById(R.id.etId);
        etPassword = (EditText) findViewById(R.id.etPassword);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        //클릭 이벤트
        btnLogin.setOnClickListener((v) -> {
            String stEmail = etId.getText().toString();
            String stPassword = etPassword.getText().toString();
            //Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_LONG).show();
            //괄호안에 목적지를 적어준다
            //화면전환 기능
            Intent in =new Intent();
            startActivity(in);
        });

        Button btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener((v) -> {

            //기입한 이메일을 가져옴
            String stEmail = etId.getText().toString();
            //기입한 패스워드를 가져옴
            String stPassword = etPassword.getText().toString();
            if (stEmail.isEmpty()) {
                Toast.makeText(this, "Please insert Email", Toast.LENGTH_LONG).show();
                return;
            }
            if (stPassword.isEmpty()) {
                Toast.makeText(this, "Please insert password", Toast.LENGTH_LONG).show();
                return;
            }
            //Toast.makeText(MainActivity.this, "Email: " + stEmail + ", password: " + stPassword, Toast.LENGTH_LONG).show();
        });

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
        updateUI(currentUser);

    }

    private void firebaseAuthWithGoogle(String idToken) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                            updateUI(null);
                        }
                    }
                });

        FirebaseAuth.getInstance().signOut();
    }

    private void updateUI(FirebaseUser user) { //update ui code here
        if (user != null) {
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void ActivityForResultCallback(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result ->{
                    if(result.getData()==null){
                        return;
                    }
                    Uri uri = result.getData().getData();
                    Glide.with(this).load(uri).into(binding.ivImg);
                }
        );
    }
}
