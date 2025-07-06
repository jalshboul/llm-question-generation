#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>
#include <map>
#include <set>
#include <algorithm>
#include <numeric>
#include "matplotlibcpp.h"

namespace plt = matplotlibcpp;

using namespace std;

struct Order {
    int day;
    string city;
    int amount;
};

vector<Order> readOrders(const string& filename) {
    vector<Order> data;
    ifstream file(filename);
    string line;
    while (getline(file, line)) {
        istringstream iss(line);
        Order o;
        iss >> o.day >> o.city >> o.amount;
        data.push_back(o);
    }
    return data;
}

int ordersOnDay(const vector<Order>& data, int day) {
    return count_if(data.begin(), data.end(), [day](const Order& o) {
        return o.day == day;
    });
}

int daysWithoutOrder(const vector<Order>& data, const string& city) {
    set<int> orderedDays;
    for (const auto& o : data) {
        if (o.city == city) orderedDays.insert(o.day);
    }
    return 30 - orderedDays.size();
}

pair<int, int> maxAmount(const vector<Order>& data) {
    int maxAmt = -1, day = -1;
    for (const auto& o : data) {
        if (o.amount > maxAmt) {
            maxAmt = o.amount;
            day = o.day;
        }
    }
    return {maxAmt, day};
}

int ordersOnDayPerCity(const vector<Order>& data, int day, const string& city) {
    int sum = 0;
    for (const auto& o : data) {
        if (o.day == day && o.city == city) sum += o.amount;
    }
    return sum;
}

int countOrdersPerCity(const vector<Order>& data, const vector<int>& days, const string& city) {
    int count = 0;
    for (const auto& o : data) {
        if (find(days.begin(), days.end(), o.day) != days.end() && o.city == city) {
            count++;
        }
    }
    return count;
}

int sumOrdersOnDay(const vector<Order>& data, int day) {
    int sum = 0;
    for (const auto& o : data) {
        if (o.day == day) sum += o.amount;
    }
    return sum;
}

vector<int> sumOrdersPerCity(const vector<Order>& data, const string& city) {
    vector<int> totals(30, 0);
    for (const auto& o : data) {
        if (o.city == city && o.day >= 1 && o.day <= 30) {
            totals[o.day - 1] += o.amount;
        }
    }
    return totals;
}

void plotStackedBar(const map<string, vector<int>>& cityData) {
    vector<string> labels = {"1..10", "11..20", "21..30"};
    vector<int> x = {1, 2, 3};

    vector<int> y1 = cityData.at("PL");
    vector<int> y2 = cityData.at("TV");
    vector<int> y3 = cityData.at("NR");

    plt::bar(x, y1, "b");
    plt::bar(x, y2, "g", y1);
    vector<int> stacked(y1.size());
    transform(y1.begin(), y1.end(), y2.begin(), stacked.begin(), plus<int>());
    plt::bar(x, y3, "r", stacked);

    plt::xticks(x, labels);
    plt::xlabel("10-day Periods");
    plt::ylabel("Number of Orders");
    plt::title("Effect of advertisements");
    plt::legend({"PL", "TV", "NR"});
    plt::save("stacked_bar.png");
    plt::show();
}

void plotTimeSeries(const vector<int>& values) {
    vector<int> days(30);
    iota(days.begin(), days.end(), 1);
    plt::plot(days, values, "k--");
    plt::xlabel("Days");
    plt::ylabel("Number of Orders");
    plt::title("Time series of orders");
    plt::save("timeseries.png");
    plt::show();
}

void plotCityTimeSeries(const map<string, vector<int>>& cityOrders) {
    vector<int> days(30);
    iota(days.begin(), days.end(), 1);
    plt::plot(days, cityOrders.at("PL"), "b");
    plt::plot(days, cityOrders.at("TV"), "g");
    plt::plot(days, cityOrders.at("NR"), "r");
    plt::xlabel("Days");
    plt::ylabel("Orders");
    plt::title("Time series of orders per city");
    plt::legend({"PL", "TV", "NR"});
    plt::show();
}

int main() {
    vector<Order> data = readOrders("order.txt");

    cout << "Total orders: " << data.size() << "\n";

    int inputDay;
    cout << "Enter day (1–30): ";
    cin >> inputDay;
    cout << "Orders on day " << inputDay << ": " << ordersOnDay(data, inputDay) << "\n";

    string city = "NR";
    int noOrders = daysWithoutOrder(data, city);
    if (noOrders == 0)
        cout << "Orders came from " << city << " every day.\n";
    else
        cout << noOrders << " days had no order from " << city << "\n";

    auto [maxAmt, dayOfMax] = maxAmount(data);
    cout << "Max order: " << maxAmt << " on day " << dayOfMax << "\n";

    vector<string> cities = {"PL", "TV", "NR"};
    int day = 21;
    for (const auto& c : cities) {
        cout << "Orders on day " << day << " from " << c << ": "
             << ordersOnDayPerCity(data, day, c) << "\n";
    }

    vector<vector<int>> intervals = {
        {1,2,3,4,5,6,7,8,9,10},
        {11,12,13,14,15,16,17,18,19,20},
        {21,22,23,24,25,26,27,28,29,30}
    };

    map<string, vector<int>> barData;
    for (const auto& c : cities) {
        vector<int> barHeights;
        for (const auto& interval : intervals) {
            barHeights.push_back(countOrdersPerCity(data, interval, c));
        }
        barData[c] = barHeights;
    }
    plotStackedBar(barData);

    vector<int> totalPerDay(30);
    for (int i = 1; i <= 30; ++i) {
        totalPerDay[i - 1] = sumOrdersOnDay(data, i);
    }
    plotTimeSeries(totalPerDay);

    map<string, vector<int>> lineData;
    for (const auto& c : cities) {
        lineData[c] = sumOrdersPerCity(data, c);
    }
    plotCityTimeSeries(lineData);

    return 0;
}
