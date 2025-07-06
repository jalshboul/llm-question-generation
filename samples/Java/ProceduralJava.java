import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class OrderAnalyzerWithCharts {

    public static List<String[]> readTxt(String file) throws IOException {
        List<String[]> data = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            data.add(line.trim().split(" "));
        }
        reader.close();
        return data;
    }

    public static int ordersOnDay(List<String[]> data, String day) {
        int count = 0;
        for (String[] row : data) {
            if (row[0].equals(day)) {
                count++;
            }
        }
        return count;
    }

    public static int daysWithoutOrder(List<String[]> data, String city) {
        Set<String> days = new HashSet<>();
        for (String[] row : data) {
            if (row[1].equals(city)) {
                days.add(row[0]);
            }
        }
        return 30 - days.size();
    }

    public static Map.Entry<String, String> maxAmount(List<String[]> data) {
        int max = Integer.MIN_VALUE;
        String day = "";
        for (String[] row : data) {
            int amount = Integer.parseInt(row[2]);
            if (amount > max) {
                max = amount;
                day = row[0];
            }
        }
        return new AbstractMap.SimpleEntry<>(String.valueOf(max), day);
    }

    public static int ordersOnDayPerCity(List<String[]> data, int day, String city) {
        int sum = 0;
        for (String[] row : data) {
            if (Integer.parseInt(row[0]) == day && row[1].equals(city)) {
                sum += Integer.parseInt(row[2]);
            }
        }
        return sum;
    }

    public static int countOrdersPerCity(List<String[]> data, List<Integer> days, String city) {
        int count = 0;
        for (String[] row : data) {
            if (days.contains(Integer.parseInt(row[0])) && row[1].equals(city)) {
                count++;
            }
        }
        return count;
    }

    public static int sumUpOrders(List<String[]> data, int day) {
        int sum = 0;
        for (String[] row : data) {
            if (Integer.parseInt(row[0]) == day) {
                sum += Integer.parseInt(row[2]);
            }
        }
        return sum;
    }

    public static List<Integer> sumUpOrdersPerCity(List<String[]> data, String city) {
        List<Integer> result = new ArrayList<>();
        for (int day = 1; day <= 30; day++) {
            int sum = 0;
            for (String[] row : data) {
                if (row[1].equals(city) && Integer.parseInt(row[0]) == day) {
                    sum += Integer.parseInt(row[2]);
                }
            }
            result.add(sum);
        }
        return result;
    }

    // Visualization - Stacked Bar Chart
    public static void createStackedBarChart(List<List<Object>> table, List<String> cities) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String[] periods = {"1..10", "10..20", "20..30"};

        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < periods.length; j++) {
                int value = (int) table.get(i + 1).get(j + 1);
                dataset.addValue(value, cities.get(i), periods[j]);
            }
        }

        JFreeChart chart = ChartFactory.createStackedBarChart(
                "Effect of advertisements",
                "10-days period",
                "Number of orders",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        displayChart(chart, "Stacked Bar Chart");
        saveChart(chart, "advertisements.png", 800, 600);
    }

    // Visualization - Line Chart
    public static void createTimeSeriesChart(List<Integer> totals) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int day = 1; day <= 30; day++) {
            dataset.addValue(totals.get(day - 1), "Orders", String.valueOf(day));
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Time series of orders",
                "Days",
                "Number of orders",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        displayChart(chart, "Line Chart");
        saveChart(chart, "timeseries.png", 800, 600);
    }

    public static void createCityTimeSeriesChart(List<Integer> pl, List<Integer> tv, List<Integer> nr) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 1; i <= 30; i++) {
            dataset.addValue(pl.get(i - 1), "PL", String.valueOf(i));
            dataset.addValue(tv.get(i - 1), "TV", String.valueOf(i));
            dataset.addValue(nr.get(i - 1), "NR", String.valueOf(i));
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Time series of orders per city",
                "Days",
                "Number of orders",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        displayChart(chart, "City Orders Time Series");
    }

    public static void displayChart(JFreeChart chart, String title) {
        JFrame frame = new JFrame(title);
        frame.setContentPane(new ChartPanel(chart));
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void saveChart(JFreeChart chart, String filename, int width, int height) {
        try {
            ChartUtils.saveChartAsPNG(new File(filename), chart, width, height);
        } catch (IOException e) {
            System.err.println("Failed to save chart: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        List<String[]> data = readTxt("order.txt");
        System.out.println("Number of orders: " + data.size());

        Scanner scanner = new Scanner(System.in);
        System.out.print("Give a day: ");
        String inputDay = scanner.nextLine();
        System.out.println("Orders on day " + inputDay + ": " + ordersOnDay(data, inputDay));

        String city = "NR";
        int noOrders = daysWithoutOrder(data, city);
        System.out.println((noOrders == 0)
                ? "Order from " + city + " every day"
                : noOrders + " days had no order from " + city);

        Map.Entry<String, String> max = maxAmount(data);
        System.out.println("Max order: " + max.getKey() + ", first day: " + max.getValue());

        List<String> cities = Arrays.asList("PL", "TV", "NR");
        int day = 21;
        for (String c : cities) {
            System.out.println("Day " + day + " from " + c + ": " + ordersOnDayPerCity(data, day, c));
        }

        List<List<Integer>> intervals = Arrays.asList(
                Arrays.asList(1,2,3,4,5,6,7,8,9,10),
                Arrays.asList(10,11,12,13,14,15,16,17,18,19,20),
                Arrays.asList(20,21,22,23,24,25,26,27,28,29,30)
        );

        List<List<Object>> table = new ArrayList<>();
        table.add(Arrays.asList("Days", "1..10", "10..20", "20..30"));

        for (String c : cities) {
            List<Object> row = new ArrayList<>();
            row.add(c);
            for (List<Integer> range : intervals) {
                row.add(countOrdersPerCity(data, range, c));
            }
            table.add(row);
        }

        for (List<Object> row : table) {
            for (Object item : row) {
                System.out.print(item + "\t");
            }
            System.out.println();
        }

        createStackedBarChart(table, cities);

        List<Integer> totals = new ArrayList<>();
        for (int d = 1; d <= 30; d++) {
            totals.add(sumUpOrders(data, d));
        }

        createTimeSeriesChart(totals);

        List<Integer> pl = sumUpOrdersPerCity(data, "PL");
        List<Integer> tv = sumUpOrdersPerCity(data, "TV");
        List<Integer> nr = sumUpOrdersPerCity(data, "NR");
        createCityTimeSeriesChart(pl, tv, nr);
    }
}
