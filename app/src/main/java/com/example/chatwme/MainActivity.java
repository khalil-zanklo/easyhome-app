package com.example.chatwme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
private EditText mPhoneNumber, mCode;
private Button mSend;
private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

String mVerificationId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userIsLoggedIn();
        mPhoneNumber = findViewById(R.id.phoneNumber);
        mCode = findViewById(R.id.code);
        mSend = findViewById(R.id.send);



mSend.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(mVerificationId != null)
            veriftyCodeNumberWithCode();
        else
        PhoneNumberverfiy();}
    });

mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
    @Override
    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
        signInWithPhoneAuth(phoneAuthCredential);
    }



    @Override
    public void onVerificationFailed(@NonNull FirebaseException e) {
    }

    @Override
    public void onCodeSent(@NonNull String mverificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        super.onCodeSent(mverificationId, forceResendingToken);
        mVerificationId = mverificationId;
        mSend.setText("verifty code");

    }
};}

    private void veriftyCodeNumberWithCode(){
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(mVerificationId, mCode.getText().toString());
        signInWithPhoneAuth(credential);
    }


    private void signInWithPhoneAuth(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
if (task.isSuccessful())
     userIsLoggedIn();
            }
        });
    }

    private void userIsLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null ){
            startActivity(new Intent(getApplicationContext(), MainTestActivity.class));
            finish();
            return;
        }
    }

    private void PhoneNumberverfiy() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mPhoneNumber.getText().toString(),
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);

    }
}
