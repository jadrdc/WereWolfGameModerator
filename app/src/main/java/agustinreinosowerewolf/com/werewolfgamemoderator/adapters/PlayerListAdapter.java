package agustinreinosowerewolf.com.werewolfgamemoderator.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import agustinreinosowerewolf.com.werewolfgamemoderator.R;
import agustinreinosowerewolf.com.werewolfgamemoderator.models.Player;
import butterknife.BindView;

import static android.support.v7.widget.RecyclerView.*;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder> {
    private Context context;
    private List<Player> playerList;

    public PlayerListAdapter(Context appContext, List<Player> players) {
        context = appContext;
        playerList = players;

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
        holder.setPlayer(playerList.get(position));

    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public static class PlayerViewHolder extends ViewHolder {
        public ImageView imageView;
        public TextView txtName;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.player_icon);
            txtName = itemView.findViewById(R.id.player_username);
        }

        public void setPlayer(Player player) {
            txtName.setText(player.name);
            imageView.setImageURI(player.image);
        }
    }
}
