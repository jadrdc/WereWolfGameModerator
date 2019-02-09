package agustinreinosowerewolf.com.werewolfgamemoderator.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.images.ImageManager;

import java.util.List;

import agustinreinosowerewolf.com.werewolfgamemoderator.R;
import agustinreinosowerewolf.com.werewolfgamemoderator.models.Player;

public class PartyInformationAdapter extends  RecyclerView.Adapter<PartyInformationAdapter.PartyInformationViewHolder> {

    private  List<Player> playerList;

    @NonNull
    @Override
    public PartyInformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PartyInformationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PartyInformationViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView txtName;
        public ImageView imgRole;

        public PartyInformationViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.player_icon);
            txtName = itemView.findViewById(R.id.player_username);
            imgRole = itemView.findViewById(R.id.rol_icon);

        }

        public void setPlayer(final Player player, Context context) {
            txtName.setText(player.getName());
            ImageManager imageManager = ImageManager.create(context);
            imageManager.loadImage(imageView, player.getImage());

            if (player.getWolfPlayerActions() == null) {
                imgRole.setImageResource(R.drawable.farmer);
            } else {
                imgRole.setImageResource(R.drawable.wolfhead);

            }
        }


    }
}
