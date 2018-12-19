package agustinreinosowerewolf.com.werewolfgamemoderator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.SignInButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignActivity extends AppCompatActivity {

    @BindView(R.id.sign_in_button)
    public SignInButton mSignButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sign_in_button)
    public void SignIn(View view) {

    }
}
