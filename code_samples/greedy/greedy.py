"""
Greedy Algorithm implementation in Python - Activity Selection Problem.

The Activity Selection Problem is a classic optimization problem where we need to select
the maximum number of activities that can be performed by a single person, given the start
and finish times of each activity. The person can only work on a single activity at a time.

Time Complexity: O(n log n) where n is the number of activities (due to sorting)
Space Complexity: O(n) for storing the result
"""

def activity_selection(start_times, finish_times):
    """
    Solve the Activity Selection Problem using a greedy approach.
    
    Args:
        start_times: List of start times of activities
        finish_times: List of finish times of activities
        
    Returns:
        List of indices of selected activities
    """
    # Create a list of activities with their start and finish times
    activities = [(start_times[i], finish_times[i], i) for i in range(len(start_times))]
    
    # Sort activities by finish time
    activities.sort(key=lambda x: x[1])
    
    # Select the first activity
    selected = [activities[0][2]]
    last_finish_time = activities[0][1]
    
    # Consider the rest of the activities
    for i in range(1, len(activities)):
        # If this activity starts after the finish time of the last selected activity
        if activities[i][0] >= last_finish_time:
            # Select this activity
            selected.append(activities[i][2])
            last_finish_time = activities[i][1]
    
    return selected


# Example usage
if __name__ == "__main__":
    # Example problem
    start_times = [1, 3, 0, 5, 8, 5]
    finish_times = [2, 4, 6, 7, 9, 9]
    
    selected_activities = activity_selection(start_times, finish_times)
    
    print(f"Selected activities (0-indexed): {selected_activities}")
    print("Selected activities details:")
    for i in selected_activities:
        print(f"Activity {i}: Start time = {start_times[i]}, Finish time = {finish_times[i]}")
