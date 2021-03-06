package alexandre.thauvin.assignment5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener, SignInRequest.sendDataResponse {
    int value = 0;

    CallbackManager callbackManager = CallbackManager.Factory.create();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);

        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.forgot).setOnClickListener(this);
        findViewById(R.id.signup).setOnClickListener(this);

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast toast = Toast.makeText(SignInActivity.this, "facebook logged", Toast.LENGTH_SHORT);
                        toast.show();

                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void sendData(final String str){

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!str.contains("Invalid")) {
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(SignInActivity.this, "Invalid SignIn", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onClick(View v){
        EditText email;
        EditText password;

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        switch (v.getId()){
            case R.id.login:
                if (Tools.isValidEmail(email.getText().toString()))
                {
                    new SignInRequest.MakeRequestTask(this, SignInActivity.this).execute(email.getText().toString(), password.getText().toString());
                }
                else {
                    Toast.makeText(SignInActivity.this, "Please enter an valid email", Toast.LENGTH_SHORT).show();
                    //emailnotvalid
                }
                break;
            case R.id.forgot:
                break;
            case R.id.signup:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }

}


