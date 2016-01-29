package georgikoemdzhiev.starwars;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by koemdzhiev on 29/01/16.
 */
public class ShipAdapter extends RecyclerView.Adapter<ShipAdapter.ShipViewHolder> {
    private Context mContext;
    private ArrayList<StarwarShip>mStarwarShips;

    public ShipAdapter(ArrayList<StarwarShip> ships,Context context) {
        mStarwarShips = ships;
        mContext = context;
    }

    @Override
    public ShipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ship_layout,parent,false);

        return new ShipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShipAdapter.ShipViewHolder holder, int position) {
        String shipName = mStarwarShips.get(position).getName();
        holder.mShipName.setText(shipName);
    }


    @Override
    public int getItemCount() {
        return this.mStarwarShips.size();
    }

    public class ShipViewHolder extends RecyclerView.ViewHolder{
        protected TextView mShipName;

        public ShipViewHolder(View itemView) {
            super(itemView);

            mShipName = (TextView) itemView.findViewById(R.id.shipName);
            mShipName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,DetailActivity.class);
                    intent.putExtra(Constants.selectedShip,getAdapterPosition());
                    mContext.startActivity(intent);
                }
            });

        }
    }
}
