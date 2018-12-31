package agustinreinosowerewolf.com.werewolfgamemoderator.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import agustinreinosowerewolf.com.werewolfgamemoderator.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPlayerActivity extends AppCompatActivity {

    @BindView(R.id.btn_save)
    public Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        ButterKnife.bind(this);
    }
}
