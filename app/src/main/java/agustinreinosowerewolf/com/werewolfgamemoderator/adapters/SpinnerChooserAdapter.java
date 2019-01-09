package agustinreinosowerewolf.com.werewolfgamemoderator.adapters;
import agustinreinosowerewolf.com.werewolfgamemoderator.R;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import agustinreinosowerewolf.com.werewolfgamemoderator.models.ChracterInfoModel;

public class SpinnerChooserAdapter extends ArrayAdapter<ChracterInfoModel>
{


    List<ChracterInfoModel> listOfInfo;

    public SpinnerChooserAdapter(Context context, List<ChracterInfoModel> objects) {
        super(context, R.layout.spinner_row,R.id.txt_text, objects);
        listOfInfo=objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, null);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChracterInfoModel info = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_row,parent,false);
        }
        ((ImageView)convertView.findViewById(R.id.spinner_image_chracter)).setImageResource(info.getImage());
        ((TextView)convertView.findViewById(R.id.txt_text)).setText(info.getName());
        return convertView;
    }

    @Override
    public int getCount() {
        return listOfInfo.size();
    }

    @Override
    public ChracterInfoModel getItem(int position) {
        return listOfInfo.get(position);
    }
}
