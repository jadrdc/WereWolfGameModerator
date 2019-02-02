package agustinreinosowerewolf.com.werewolfgamemoderator.views;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import agustinreinosowerewolf.com.werewolfgamemoderator.R;
import agustinreinosowerewolf.com.werewolfgamemoderator.adapters.SpinnerChooserAdapter;
import agustinreinosowerewolf.com.werewolfgamemoderator.models.ChracterInfoModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.os.Build.VERSION.SDK_INT;

public class AddPlayerActivity extends AppCompatActivity {

    @BindView(R.id.spinner_type)
    public Spinner spinner;
    @BindView(R.id.btn_save)
    public Button btnSave;
    @BindView(R.id.img_contact)
    ImageButton imageButton;
    @BindView(R.id.txt_player)
    EditText txtName;
    final int PICK_CONTACT = 2912;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        ButterKnife.bind(this);
        List<ChracterInfoModel> list = new ArrayList<ChracterInfoModel>();
        list.add(new ChracterInfoModel("BODYGUARD", R.drawable.bodyguard));
        list.add(new ChracterInfoModel("HUNTER", R.drawable.hunter));
        list.add(new ChracterInfoModel("CUPID", R.drawable.cupid));
        list.add(new ChracterInfoModel("WOLF", R.drawable.wolfhead));
        list.add(new ChracterInfoModel("WITCH", R.drawable.witch));
        list.add(new ChracterInfoModel("VILLAGER", R.drawable.farmer));
        SpinnerAdapter adapter = new SpinnerChooserAdapter(getApplicationContext(),
                list);

        spinner.setAdapter(adapter);
    }

    @OnClick(R.id.img_contact)
    public void pickContact(View view) {
        Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(pickContact, PICK_CONTACT);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {

        switch (requestCode) {
            case PICK_CONTACT: {
                Uri uri = intent.getData();
                Cursor cursor = null;
                if (SDK_INT > 25) {
                    cursor = getContentResolver().query(uri, null, null, null);
                    cursor.moveToFirst();
                    int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    String name = cursor.getString(nameIndex);
                    txtName.setText(name);

                }

                break;
            }
        }
    }
}
