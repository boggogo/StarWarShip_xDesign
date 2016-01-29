package georgikoemdzhiev.starwars;

/**
 * Created by koemdzhiev on 29/01/16.
 */
public class StarwarShip {
    private String name;
    private String manifacturer;
    private String cost_in_credits;
    private String length;
    private String max_atmosphering_speed;
    private String cargo_capacity_kg;
    private String hyperdrive_rating;
    private String docking_station_longitude;
    private String docking_station_latityde;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManifacturer() {
        return manifacturer;
    }

    public void setManifacturer(String manifacturer) {
        this.manifacturer = manifacturer;
    }

    public String getMax_atmosphering_speed() {
        return max_atmosphering_speed;
    }

    public void setMax_atmosphering_speed(String max_atmosphering_speed) {
        this.max_atmosphering_speed = max_atmosphering_speed;
    }

    public String getHyperdrive_rating() {
        return hyperdrive_rating;
    }

    public void setHyperdrive_rating(String hyperdrive_rating) {
        this.hyperdrive_rating = hyperdrive_rating;
    }

    public String getCost_in_credits() {
        return cost_in_credits;
    }

    public void setCost_in_credits(String cost_in_credits) {
        this.cost_in_credits = cost_in_credits;
    }

    public String getDocking_station_longitude() {
        return docking_station_longitude;
    }

    public void setDocking_station_longitude(String docking_station_longitude) {
        this.docking_station_longitude = docking_station_longitude;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getCargo_capacity_kg() {
        return cargo_capacity_kg;
    }

    public void setCargo_capacity_kg(String cargo_capacity_kg) {
        this.cargo_capacity_kg = cargo_capacity_kg;
    }

    public String getDocking_station_latityde() {
        return docking_station_latityde;
    }

    public void setDocking_station_latityde(String docking_station_latityde) {
        this.docking_station_latityde = docking_station_latityde;
    }

    @Override
    public String toString() {
        return "StarwarShip{" + "\n" +
                "name='" + name + '\'' + "\n" +
                ", manifacturer='" + manifacturer + '\''  + "\n" +
                ", cost_in_credits='" + cost_in_credits + '\''  + "\n" +
                ", length='" + length + '\''  + "\n" +
                ", max_atmosphering_speed='" + max_atmosphering_speed + '\''  + "\n" +
                ", cargo_capacity_kg='" + cargo_capacity_kg + '\''  + "\n" +
                ", hyperdrive_rating='" + hyperdrive_rating + '\''  + "\n" +
                ", docking_station_longitude='" + docking_station_longitude + '\''  + "\n" +
                ", docking_station_latityde='" + docking_station_latityde + '\''  + "\n" +
                '}';
    }
}
