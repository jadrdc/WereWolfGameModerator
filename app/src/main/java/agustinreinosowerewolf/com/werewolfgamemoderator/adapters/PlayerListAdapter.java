package agustinreinosowerewolf.com.werewolfgamemoderator.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.images.ImageManager;

import java.util.ArrayList;
import java.util.List;

import agustinreinosowerewolf.com.werewolfgamemoderator.R;
import agustinreinosowerewolf.com.werewolfgamemoderator.models.Player;

import static android.support.v7.widget.RecyclerView.ViewHolder;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder> {
    private Context context;
    private List<Player> playerList;

    public void setListener(SelectPlayerListener listener) {
        this.listener = listener;
    }

    private SelectPlayerListener listener;

    public PlayerListAdapter(Context appContext) {
        context = appContext;
        playerList = new ArrayList<>();

    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.player_info, parent, false);
        PlayerViewHolder holder = new PlayerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        holder.setPlayer(playerList.get(position), context);

    }

    public void addPlayers(List<Player> players) {
        this.playerList = players;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }


    public interface SelectPlayerListener {
        public void onSelectPlayer(Player playe, ImageView imageView);


    }

    public class PlayerViewHolder extends ViewHolder {
        public ImageView imageView;
        public TextView txtName;
        public ImageView imgRole;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.player_icon);

            txtName = itemView.findViewById(R.id.player_username);
            imgRole=itemView.findViewById(R.id.rol_icon);
        }

        public void setPlayer(final Player player, Context context) {
            txtName.setText(player.getName());
            ImageManager imageManager = ImageManager.create(context);
            imageManager.loadImage(imageView, player.getImage());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayerListAdapter.this.listener.onSelectPlayer(player,imgRole);
                }
            });

        }
    }
}
