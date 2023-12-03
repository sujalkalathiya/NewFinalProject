package algonquin.cst2335.newfinalproject.Sun.Data;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import algonquin.cst2335.newfinalproject.R;

public class LookupViewHolder extends RecyclerView.ViewHolder {

    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private TextView sunriseTextView;
    private TextView sunsetTextView;

    public LookupViewHolder(@NonNull View itemView) {
        super(itemView);

        latitudeTextView = itemView.findViewById(R.id.latitudeTextView);
        longitudeTextView = itemView.findViewById(R.id.longitudeTextView);
        sunriseTextView = itemView.findViewById(R.id.sunrisetextview);
        sunsetTextView = itemView.findViewById(R.id.textView10);
    }

    public void bind(Lookup lookup) {
        latitudeTextView.setText("Latitude: " + lookup.getLatitude());
        longitudeTextView.setText("Longitude: " + lookup.getLongitude());
        sunriseTextView.setText("Sunrise: " + lookup.getSunrise());
        sunsetTextView.setText("Sunset: " + lookup.getSunset());
    }
}
