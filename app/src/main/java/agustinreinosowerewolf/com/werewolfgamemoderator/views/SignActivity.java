package agustinreinosowerewolf.com.werewolfgamemoderator.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import agustinreinosowerewolf.com.werewolfgamemoderator.R;
import agustinreinosowerewolf.com.werewolfgamemoderator.viewmodels.UserViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignActivity extends AppCompatActivity {

    @BindView(R.id.sign_in_button)
    public SignInButton mSignButton;
    @BindView(R.id.progrbar)
    public ProgressBar mProgressBar;
    private UserViewModel mUserViewModel;
    private int SIGN_CODE = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity);
        ButterKnife.bind(this);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mUserViewModel.getIsLoading().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                int visible = 0;
                if (aBoolean) {
                    visible = View.VISIBLE;
                } else {

                    visible = View.INVISIBLE;
                }
                mProgressBar.setVisibility(visible);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mUserViewModel.getmAuthManager().getValue().isLogged(getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.sign_in_button)
    public void SignIn(View view) {

        Intent intent = mUserViewModel.getmAuthManager().getValue().mGoogleapiClient.getSignInIntent();
        mUserViewModel.getIsLoading().setValue(true);
        startActivityForResult(intent, SIGN_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == SIGN_CODE) {
            mUserViewModel.getIsLoading().setValue(false);
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "No se ha podido Iniciar Sesion", Toast.LENGTH_LONG).show();

            }
        }

    }


}
