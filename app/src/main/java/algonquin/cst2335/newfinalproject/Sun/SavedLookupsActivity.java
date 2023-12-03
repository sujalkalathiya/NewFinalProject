package algonquin.cst2335.newfinalproject.Sun;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import algonquin.cst2335.newfinalproject.R;
import algonquin.cst2335.newfinalproject.Sun.Data.Lookup;
import algonquin.cst2335.newfinalproject.Sun.Data.LookupAdapter;
import algonquin.cst2335.newfinalproject.Sun.Data.SunriseSunsetViewModel;

public class SavedLookupsActivity extends AppCompatActivity {

    private SunriseSunsetViewModel viewModel;
    private LookupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_lookups);

        // Set up the RecyclerView
        RecyclerView savedLookupsRecyclerView = findViewById(R.id.savedLookupsRecyclerView);
        savedLookupsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LookupAdapter();
        savedLookupsRecyclerView.setAdapter(adapter);

        // Initialize the ViewModel
        viewModel = new ViewModelProvider(this).get(SunriseSunsetViewModel.class);

        // Observe any changes to the lookups data and update the UI accordingly
        viewModel.getAllLookups().observe(this, lookups -> {
            // Update the adapter with the new list of lookups
            adapter.setLookups(lookups);
        });

        // Set up the "Clear All" button click listener
        Button btnClearAll = findViewById(R.id.btnClearAll);
        btnClearAll.setOnClickListener(v -> clearAllRecords());
    }

    private void clearAllRecords() {
        // Call the method in the ViewModel to clear all records from the database
        viewModel.clearAllLookups();
    }

    private void updateLookups(View view) {
        // Get the list of saved lookups from the ViewModel
        List<Lookup> savedLookups = viewModel.getLookups().getValue();

        if (savedLookups != null && !savedLookups.isEmpty()) {
            for (Lookup lookup : savedLookups) {
                // Fetch the new sunrise and sunset times using latitude and longitude
                fetchNewSunriseSunset(lookup);
            }
        } else {
            Toast.makeText(this, "No saved lookups to update", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchNewSunriseSunset(Lookup lookup) {
        String latitude = lookup.getLatitude();
        String longitude = lookup.getLongitude();

        // Replace this URL with your actual JSON endpoint
        String apiUrl = "https://api.sunrisesunset.io/json?lat=" + latitude + "&lng=" + longitude + "&timezone=UTC&date=today";

        StringRequest request = new StringRequest(Request.Method.GET, apiUrl, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject resultsObject = jsonObject.getJSONObject("results");

                // Get the new sunrise and sunset times from the JSON response
                String newSunrise = resultsObject.getString("sunrise");
                String newSunset = resultsObject.getString("sunset");

                // Update the sunrise and sunset times in the database using the ViewModel
                lookup.setSunrise(newSunrise);
                lookup.setSunset(newSunset);
                viewModel.updateLookup(lookup);

                Toast.makeText(this, "Lookups updated successfully", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error parsing sunrise and sunset data", Toast.LENGTH_SHORT).show();
            }

        }, error -> {
            // Handle error
            Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show();
        });

        // Add the request to the request queue
        Volley.newRequestQueue(this).add(request);
    }
}
