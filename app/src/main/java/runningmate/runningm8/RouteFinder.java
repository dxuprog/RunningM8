package runningmate.runningm8;

import java.util.Random;
public class RouteFinder {
    Random generator = new Random();
    public String generate(int time, double lat, double lon) {
        double x = time*0.03888888888 * 1;
        double pi = Math.PI;
        double r1 = generator.nextDouble()*2*pi;

        double lat1 = lat + Math.sin(r1)*x;
        double lon1 = lon + Math.cos(r1)*x;

        String request = "https://maps.googleapis.com/maps/api/directions/json?origin=";
        request = request.concat("&destination=");
        request = request.concat(Double.toString(lat));
        request = request.concat("%2C");
        request = request.concat(Double.toString(lon));
        request = request.concat("&waypoints=via:");
        request = request.concat(Double.toString(lat1));
        request = request.concat("%2C");
        request = request.concat(Double.toString(lon1));
        request = request.concat("%7C");

        request = request.concat("&key=YOUR_API_KEY");
        //ENTER THE API KEY ABOVE
        return(request);
    }
}
