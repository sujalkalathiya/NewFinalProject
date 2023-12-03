package algonquin.cst2335.newfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import algonquin.cst2335.newfinalproject.Sun.SunriseSunsetActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the "Sujal" button in the layout
        Button sujalButton = findViewById(R.id.sujal);

        // Set a click listener for the "Sujal" button
        sujalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the SunriseSunsetActivity
                Intent intent = new Intent(MainActivity.this, SunriseSunsetActivity.class);

                // Start the SunriseSunsetActivity
                startActivity(intent);
            }
        });
    }
}
