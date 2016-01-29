package georgikoemdzhiev.starwars;

import java.util.ArrayList;

/**
 * Created by koemdzhiev on 29/01/16.
 */
public class StarWarShips {
    private ArrayList<StarwarShip> mStarwarShips;

    public StarWarShips() {
        mStarwarShips = new ArrayList<>();
    }

    public void add(StarwarShip starwarShip){
        this.mStarwarShips.add(starwarShip);
    }

    public int size(){
        return mStarwarShips.size();
    }

    public ArrayList<StarwarShip> getStarwarShips() {
        return mStarwarShips;
    }

    @Override
    public String toString() {
        String toStringShips = "";

        for(StarwarShip ship:this.mStarwarShips){
            toStringShips += ship.toString() +"\n";
            toStringShips += "P*********NEW SHIP*********";
        }

        return toStringShips;
    }
}
