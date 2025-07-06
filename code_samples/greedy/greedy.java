/**
 * Greedy Algorithm implementation in Java - Activity Selection Problem.
 *
 * The Activity Selection Problem is a classic optimization problem where we need to select
 * the maximum number of activities that can be performed by a single person, given the start
 * and finish times of each activity. The person can only work on a single activity at a time.
 *
 * Time Complexity: O(n log n) where n is the number of activities (due to sorting)
 * Space Complexity: O(n) for storing the result
 */
import java.util.*;

public class ActivitySelection {
    
    /**
     * Solve the Activity Selection Problem using a greedy approach.
     *
     * @param startTimes Array of start times of activities
     * @param finishTimes Array of finish times of activities
     * @return List of indices of selected activities
     */
    public static List<Integer> activitySelection(int[] startTimes, int[] finishTimes) {
        int n = startTimes.length;
        
        // Create a list of activities with their start and finish times
        Activity[] activities = new Activity[n];
        for (int i = 0; i < n; i++) {
            activities[i] = new Activity(startTimes[i], finishTimes[i], i);
        }
        
        // Sort activities by finish time
        Arrays.sort(activities, Comparator.comparingInt(a -> a.finishTime));
        
        // Select the first activity
        List<Integer> selected = new ArrayList<>();
        selected.add(activities[0].index);
        int lastFinishTime = activities[0].finishTime;
        
        // Consider the rest of the activities
        for (int i = 1; i < n; i++) {
            // If this activity starts after the finish time of the last selected activity
            if (activities[i].startTime >= lastFinishTime) {
                // Select this activity
                selected.add(activities[i].index);
                lastFinishTime = activities[i].finishTime;
            }
        }
        
        return selected;
    }
    
    // Class to represent an activity
    static class Activity {
        int startTime;
        int finishTime;
        int index;
        
        public Activity(int startTime, int finishTime, int index) {
            this.startTime = startTime;
            this.finishTime = finishTime;
            this.index = index;
        }
    }
    
    // Example usage
    public static void main(String[] args) {
        // Example problem
        int[] startTimes = {1, 3, 0, 5, 8, 5};
        int[] finishTimes = {2, 4, 6, 7, 9, 9};
        
        List<Integer> selectedActivities = activitySelection(startTimes, finishTimes);
        
        System.out.println("Selected activities (0-indexed): " + selectedActivities);
        System.out.println("Selected activities details:");
        for (int i : selectedActivities) {
            System.out.println("Activity " + i + ": Start time = " + startTimes[i] + 
                              ", Finish time = " + finishTimes[i]);
        }
    }
}
