package algonquin.cst2335.newfinalproject.Sun.Data;

// Lookup.java
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lookups")
public class Lookup {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String latitude;
    private String longitude;
    private String sunrise;
    private String sunset;

    public Lookup(String latitude, String longitude, String sunrise, String sunset) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}
