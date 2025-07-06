import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class GeneralCode {

    public static void main(String[] args) {
        String invid = "";
        int quarter = -1;

        // Command-line argument parsing
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-h":
                case "--help":
                    System.out.println("Help not implemented.");
                    return;
                case "-i":
                case "--invid":
                    if (i + 1 < args.length) {
                        invid = args[++i];
                    }
                    break;
                case "-q":
                case "--quarter":
                    if (i + 1 < args.length) {
                        quarter = Integer.parseInt(args[++i]);
                    }
                    break;
            }
        }

        if (invid.isEmpty() || quarter < 0) {
            System.err.println("Missing required arguments.");
            return;
        }

        try {
            Map<String, List<String>> result = getMetaData(invid, quarter);
            System.out.println("Kepler IDs: " + result.get("kepid"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double greg2JD(int year, int month, int day) {
        double y = year;
        double m = month;

        if (month < 3) {
            y -= 1;
            m += 12;
        }

        int a = 0, b = 0;
        if ((y + m / 12.0 + day / 365.0) > 1582.87166) {
            a = (int) (y / 100);
            b = 2 - a + (int) (a / 4.0);
        }

        int c = (y < 0.0) ? (int) (365.25 * y - 0.75) : (int) (365.25 * y);
        int d = (int) (30.6001 * (m + 1));
        return b + c + d + day + 1720994.5;
    }

    public static double[] quarterDates(int quarter) {
        double[] Qstart = {2454953.5, 2454964.5, 2454998.5};
        double[] Qstop = {2454962.5, 2454997.5, 2455100.5};

        if (quarter < Qstart.length) {
            return new double[]{Qstart[quarter] - 10, Qstop[quarter] + 10};
        } else {
            throw new RuntimeException("No spacecraft roll dates recorded for quarter " + quarter +
                ".\nFind an updated script at http://keplergo.arc.nasa.gov");
        }
    }

    public static Map<String, List<String>> getMetaData(String invid, int quarter) throws Exception {
        double[] bounds = quarterDates(quarter);
        double Qstart = bounds[0];
        double Qstop = bounds[1];

        String urlStr = "http://archive.stsci.edu/kepler/data_search/search.php?"
                + "action=Search&max_records=100000&verb=3"
                + "&ktc_investigation_id=" + invid
                + "&ktc_target_type[]=LC&ktc_target_type[]=SC"
                + "&outputformat=CSV";

        URL url = new URL(urlStr);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;

        List<String> kepid = new ArrayList<>();
        List<String> invIds = new ArrayList<>();
        List<String> kepmag = new ArrayList<>();
        List<String> mode = new ArrayList<>();
        List<String> start = new ArrayList<>();
        List<String> stop = new ArrayList<>();
        List<String> release = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens.length > 8 &&
                    !tokens[0].contains("Kepler") &&
                    !tokens[0].contains("integer") &&
                    !tokens[0].contains("no rows found")) {
                String[] gregStart = tokens[7].substring(0, 10).split("-");
                String[] gregStop = tokens[8].substring(0, 10).split("-");
                double jdStart = greg2JD(
                        Integer.parseInt(gregStart[0]),
                        Integer.parseInt(gregStart[1]),
                        Integer.parseInt(gregStart[2])
                );
                double jdStop = greg2JD(
                        Integer.parseInt(gregStop[0]),
                        Integer.parseInt(gregStop[1]),
                        Integer.parseInt(gregStop[2])
                );

                if (jdStart > Qstart && jdStop < Qstop) {
                    kepid.add(tokens[0]);
                    invIds.add(tokens[1]);
                    kepmag.add(tokens[22]);
                    mode.add(tokens[6]);
                    start.add(tokens[7]);
                    stop.add(tokens[8]);
                    release.add(tokens[9]);
                }
            }
        }

        Map<String, List<String>> data = new HashMap<>();
        data.put("kepid", kepid);
        data.put("invid", invIds);
        data.put("kepmag", kepmag);
        data.put("mode", mode);
        data.put("start", start);
        data.put("stop", stop);
        data.put("release", release);
        return data;
    }
}
