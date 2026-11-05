package modele;

import com.google.gson.annotations.SerializedName;

public class ReponseIss {

    @SerializedName("iss_position")
    private Position position;
    private long timestamp;

    public Position getPosition() {
        return position;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public static class Position {
        private double latitude;
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}
