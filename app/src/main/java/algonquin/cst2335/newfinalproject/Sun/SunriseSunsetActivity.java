package algonquin.cst2335.newfinalproject.Sun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import algonquin.cst2335.newfinalproject.Sun.Data.Lookup;
import algonquin.cst2335.newfinalproject.Sun.Data.SunriseSunsetViewModel;
import algonquin.cst2335.newfinalproject.R;

public class SunriseSunsetActivity extends AppCompatActivity {

    private EditText latitudeEditText, longitudeEditText;
    private TextView sunriseTextView, sunsetTextView;
    private SunriseSunsetViewModel viewModel;

    private static final String PREFS_NAME = "SunriseSunsetPrefs";
    private static final String PREF_LATITUDE = "latitude";
    private static final String PREF_LONGITUDE = "longitude";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunrise_sunset);


        latitudeEditText = findViewById(R.id.latitudeEditText);
        longitudeEditText = findViewById(R.id.longitudeEditText);
        sunriseTextView = findViewById(R.id.sunriseTextView);
        sunsetTextView = findViewById(R.id.sunsetTextView);

        Button getSunriseSunsetButton = findViewById(R.id.getSunriseSunsetButton);
        Button btnShowSavedLookups = findViewById(R.id.btnShowSavedLookups);

        viewModel = new ViewModelProvider(this).get(SunriseSunsetViewModel.class);

        // Restore last entered values for latitude and longitude
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String lastLatitude = prefs.getString(PREF_LATITUDE, "");
        String lastLongitude = prefs.getString(PREF_LONGITUDE, "");
        latitudeEditText.setText(lastLatitude);
        longitudeEditText.setText(lastLongitude);

        // Set up listeners
        getSunriseSunsetButton.setOnClickListener(v -> getSunriseSunsetInfo());

        btnShowSavedLookups.setOnClickListener(v -> {
            Log.d("Button Click", "Clicked on Show Saved Lookups Button");
            Intent intent = new Intent(SunriseSunsetActivity.this, SavedLookupsActivity.class);
            startActivity(intent);
        });

        Button savebtn = findViewById(R.id.savebtn);
        savebtn.setOnClickListener(v -> saveToDatabase());
    }




    private void saveToDatabase() {
        // Fetch sunrise and sunset information
        getSunriseSunsetInfo();

        // Now, the sunrise and sunset information is available in the TextViews
        String latitude = latitudeEditText.getText().toString();
        String longitude = longitudeEditText.getText().toString();
        String sunrise = sunriseTextView.getText().toString();
        String sunset = sunsetTextView.getText().toString();

        // Ensure that latitude, longitude, sunrise, and sunset are not empty before saving
        if (!latitude.isEmpty() && !longitude.isEmpty() && !sunrise.isEmpty() && !sunset.isEmpty()) {
            // Save the data to SharedPreferences
            SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString(PREF_LATITUDE, latitude);
            editor.putString(PREF_LONGITUDE, longitude);
            editor.apply();

            // Save the data to the database using the ViewModel
            Lookup lookup = new Lookup(latitude, longitude, sunrise, sunset);
            viewModel.insert(lookup);

            // Optionally, show a toast or perform any other action to indicate successful save
            Toast.makeText(SunriseSunsetActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Show a toast or handle the case where any of the fields is empty
            Toast.makeText(SunriseSunsetActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void getSunriseSunsetInfo() {
        String latitude = latitudeEditText.getText().toString();
        String longitude = longitudeEditText.getText().toString();

        // Update the API URL with the provided latitude and longitude
        String apiUrl = "https://api.sunrisesunset.io/json?lat=" + latitude + "&lng=" + longitude + "&timezone=UTC&date=today";

        StringRequest request = new StringRequest(Request.Method.GET, apiUrl, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject resultsObject = jsonObject.getJSONObject("results");

                String sunrise = resultsObject.getString("sunrise");
                String sunset = resultsObject.getString("sunset");

                sunriseTextView.setText("Sunrise: " + sunrise);
                sunsetTextView.setText("Sunset: " + sunset);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(SunriseSunsetActivity.this, "Error parsing sunrise and sunset data", Toast.LENGTH_SHORT).show();
            }

        }, error -> {
            // Handle error
            Toast.makeText(SunriseSunsetActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
        });

        // Add the request to the request queue
        Volley.newRequestQueue(this).add(request);
    }

    private void showHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.help_title)
                .setMessage(R.string.help_message_sunrise_sunset)
                .setPositiveButton("Got it", null)
                .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("SunriseSunsetActivity", "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.sunrise_sunset_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.helpMenuItem) {
            // Handle the help menu item click
            showHelpDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
