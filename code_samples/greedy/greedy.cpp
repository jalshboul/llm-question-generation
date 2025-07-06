/**
 * Greedy Algorithm implementation in C++ - Activity Selection Problem.
 *
 * The Activity Selection Problem is a classic optimization problem where we need to select
 * the maximum number of activities that can be performed by a single person, given the start
 * and finish times of each activity. The person can only work on a single activity at a time.
 *
 * Time Complexity: O(n log n) where n is the number of activities (due to sorting)
 * Space Complexity: O(n) for storing the result
 */
#include <iostream>
#include <vector>
#include <algorithm>
#include <utility>

/**
 * Solve the Activity Selection Problem using a greedy approach.
 *
 * @param startTimes Vector of start times of activities
 * @param finishTimes Vector of finish times of activities
 * @return Vector of indices of selected activities
 */
std::vector<int> activitySelection(const std::vector<int>& startTimes, const std::vector<int>& finishTimes) {
    int n = startTimes.size();
    
    // Create a vector of activities with their start and finish times
    std::vector<std::tuple<int, int, int>> activities;
    for (int i = 0; i < n; i++) {
        activities.push_back(std::make_tuple(startTimes[i], finishTimes[i], i));
    }
    
    // Sort activities by finish time
    std::sort(activities.begin(), activities.end(), 
              [](const auto& a, const auto& b) {
                  return std::get<1>(a) < std::get<1>(b);
              });
    
    // Select the first activity
    std::vector<int> selected;
    selected.push_back(std::get<2>(activities[0]));
    int lastFinishTime = std::get<1>(activities[0]);
    
    // Consider the rest of the activities
    for (int i = 1; i < n; i++) {
        // If this activity starts after the finish time of the last selected activity
        if (std::get<0>(activities[i]) >= lastFinishTime) {
            // Select this activity
            selected.push_back(std::get<2>(activities[i]));
            lastFinishTime = std::get<1>(activities[i]);
        }
    }
    
    return selected;
}

// Example usage
int main() {
    // Example problem
    std::vector<int> startTimes = {1, 3, 0, 5, 8, 5};
    std::vector<int> finishTimes = {2, 4, 6, 7, 9, 9};
    
    std::vector<int> selectedActivities = activitySelection(startTimes, finishTimes);
    
    std::cout << "Selected activities (0-indexed): ";
    for (size_t i = 0; i < selectedActivities.size(); i++) {
        std::cout << selectedActivities[i];
        if (i < selectedActivities.size() - 1) {
            std::cout << ", ";
        }
    }
    std::cout << std::endl;
    
    std::cout << "Selected activities details:" << std::endl;
    for (int i : selectedActivities) {
        std::cout << "Activity " << i << ": Start time = " << startTimes[i] 
                 << ", Finish time = " << finishTimes[i] << std::endl;
    }
    
    return 0;
}
