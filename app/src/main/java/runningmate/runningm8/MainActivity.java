package runningmate.runningm8;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static String[] timeVals = new String[]{"10", "15", "20", "30", "45", "60", "5"};

    public Location _location = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button clickButton = (Button) findViewById(R.id.runButton);
        clickButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                launchMapsIntent();
            }
        });

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numPick);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(6);
        numberPicker.setDisplayedValues(timeVals);


        //set up GPS
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                _location = location;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        _location = new Location(this.getLocalClassName());
        _location.setLongitude(40.3553426);
        _location.setLatitude(-74.6686537);

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException e){
            System.out.println("Couldn't use location manager.");
        }
    }

    private String toLongCord(double lng) {
        return (lng < 0 ? Double.toString(lng*-1.0)+"W" : Double.toString(lng)+"E");
    }

    private String toLatCord(double lat) {
        return (lat < 0 ? Double.toString(lat*-1.0)+"S" : Double.toString(lat)+"N");
    }

    protected void launchMapsIntent() {
        // Creates an Intent that will load a map of San Francisco
        NumberPicker np = (NumberPicker) findViewById(R.id.numPick);

        double curLong = _location.getLongitude();
        double curLat = _location.getLatitude();

        double x = Double.parseDouble(timeVals[np.getValue()])*0.003888888888/4;
        double pi = Math.PI;
        Random generator = new Random();
        double r1 = generator.nextDouble()*2*pi;

        double destLat = curLat + Math.sin(r1)*x;
        double destLong = curLong + Math.cos(r1)*x;

        Uri gmmIntentUri = Uri.parse("google.navigation:q=+" + toLongCord(destLong)
                + ",+" + toLatCord(destLat) +"&mode=w");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

}
