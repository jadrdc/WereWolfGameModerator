package agustinreinosowerewolf.com.werewolfgamemoderator.views;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import agustinreinosowerewolf.com.werewolfgamemoderator.R;
import agustinreinosowerewolf.com.werewolfgamemoderator.adapters.ViewPagerFragmentAdapter;
import agustinreinosowerewolf.com.werewolfgamemoderator.viewmodels.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.logout: {
                mUserViewModel.getmAuthManager();
                mUserViewModel.signOut();
                Intent intent = new Intent(getApplicationContext(), SignActivity.class);
                startActivity(intent);
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
