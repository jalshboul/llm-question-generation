#include <iostream>
#include <string>
#include <vector>
#include <sstream>
#include <fstream>
#include <iomanip>
#include <cstdlib>
#include <map>
#include <algorithm>
#include <stdexcept>
#include <curl/curl.h>

using namespace std;

// Helper for CURL response
size_t WriteCallback(void* contents, size_t size, size_t nmemb, string* output) {
    size_t totalSize = size * nmemb;
    output->append((char*)contents, totalSize);
    return totalSize;
}

double greg2JD(int year, int month, int day) {
    double y, m;
    if (month < 3) {
        y = year - 1;
        m = month + 12;
    } else {
        y = year;
        m = month;
    }

    int a = 0, b = 0;
    if ((y + m / 12.0 + day / 365.0) > 1582.87166) {
        a = static_cast<int>(y / 100);
        b = 2 - a + static_cast<int>(a / 4.0);
    }

    int c = (y < 0.0) ? static_cast<int>(365.25 * y - 0.75) : static_cast<int>(365.25 * y);
    int d = static_cast<int>(30.6001 * (m + 1));
    return b + c + d + day + 1720994.5;
}

pair<double, double> quarterDates(int quarter) {
    vector<double> Qstart = {2454953.5, 2454964.5, 2454998.5};
    vector<double> Qstop  = {2454962.5, 2454997.5, 2455100.5};

    if (quarter < static_cast<int>(Qstart.size())) {
        return {Qstart[quarter] - 10, Qstop[quarter] + 10};
    } else {
        throw runtime_error("No spacecraft roll dates recorded for quarter " + to_string(quarter));
    }
}

vector<string> split(const string& s, char delimiter) {
    vector<string> tokens;
    string token;
    istringstream tokenStream(s);
    while (getline(tokenStream, token, delimiter)) {
        tokens.push_back(token);
    }
    return tokens;
}

void getMetaData(const string& invid, int quarter) {
    auto [Qstart, Qstop] = quarterDates(quarter);

    string url = "http://archive.stsci.edu/kepler/data_search/search.php?"
                 "action=Search&max_records=100000&verb=3"
                 "&ktc_investigation_id=" + invid +
                 "&ktc_target_type[]=LC&ktc_target_type[]=SC"
                 "&outputformat=CSV";

    CURL* curl;
    CURLcode res;
    string readBuffer;

    curl_global_init(CURL_GLOBAL_DEFAULT);
    curl = curl_easy_init();

    if (curl) {
        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteCallback);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, &readBuffer);
        res = curl_easy_perform(curl);
        curl_easy_cleanup(curl);
    }

    curl_global_cleanup();

    istringstream ss(readBuffer);
    string line;
    vector<string> kepid, invids, kepmag, mode, start, stop, release;

    while (getline(ss, line)) {
        auto fields = split(line, ',');

        if (fields.size() > 22 &&
            fields[0].find("Kepler") == string::npos &&
            fields[0].find("integer") == string::npos &&
            fields[0].find("no rows found") == string::npos) {

            auto startDate = split(fields[7].substr(0, 10), '-');
            auto stopDate  = split(fields[8].substr(0, 10), '-');

            double JDstart = greg2JD(stoi(startDate[0]), stoi(startDate[1]), stoi(startDate[2]));
            double JDstop = greg2JD(stoi(stopDate[0]), stoi(stopDate[1]), stoi(stopDate[2]));

            if (JDstart > Qstart && JDstop < Qstop) {
                kepid.push_back(fields[0]);
                invids.push_back(fields[1]);
                kepmag.push_back(fields[22]);
                mode.push_back(fields[6]);
                start.push_back(fields[7]);
                stop.push_back(fields[8]);
                release.push_back(fields[9]);
            }
        }
    }

    // Print output (demo)
    cout << "Found " << kepid.size() << " entries:\n";
    for (size_t i = 0; i < kepid.size(); ++i) {
        cout << "KepID: " << kepid[i] << ", Mag: " << kepmag[i] << ", Mode: " << mode[i] << endl;
    }
}

int main(int argc, char* argv[]) {
    string invid;
    int quarter = -1;

    for (int i = 1; i < argc; ++i) {
        string arg = argv[i];
        if ((arg == "-h" || arg == "--help") && i + 1 < argc) {
            cout << "Help is not implemented.\n";
        } else if ((arg == "-i" || arg == "--invid") && i + 1 < argc) {
            invid = argv[++i];
        } else if ((arg == "-q" || arg == "--quarter") && i + 1 < argc) {
            quarter = stoi(argv[++i]);
        }
    }

    if (invid.empty() || quarter < 0) {
        cerr << "Missing required arguments (-i and -q)\n";
        return 1;
    }

    try {
        getMetaData(invid, quarter);
    } catch (const exception& e) {
        cerr << "Error: " << e.what() << endl;
        return 1;
    }

    return 0;
}
