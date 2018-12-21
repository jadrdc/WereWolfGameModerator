package agustinreinosowerewolf.com.werewolfgamemoderator.views;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

import agustinreinosowerewolf.com.werewolfgamemoderator.R;
import agustinreinosowerewolf.com.werewolfgamemoderator.viewmodels.UserViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignActivity extends AppCompatActivity {

    @BindView(R.id.sign_in_button)
    public SignInButton mSignButton;
    private UserViewModel mUserViewModel;
    private int SIGN_CODE = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity);
        ButterKnife.bind(this);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    @OnClick(R.id.sign_in_button)
    public void SignIn(View view) {

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mUserViewModel.getmAuthManager().getValue().mGoogleapiClient);
        startActivityForResult(intent, SIGN_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    if(requestCode==SIGN_CODE)
    {
        GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if(result.isSuccess())
        {
            Toast.makeText(getApplicationContext(),"SE HA LOGUEADO",Toast.LENGTH_LONG).show();
        }
        else {

            Toast.makeText(getApplicationContext(),"NO SE HA LOGUEADO",Toast.LENGTH_LONG).show();
        }
    }

    }
}
