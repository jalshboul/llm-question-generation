/**
 * Knuth-Morris-Pratt (KMP) algorithm implementation in C++.
 *
 * KMP is a string searching algorithm that uses information about the pattern
 * to minimize comparisons by avoiding re-examination of previously matched characters.
 *
 * Time Complexity: O(n + m) where n is the length of the text and m is the length of the pattern
 * Space Complexity: O(m) for the LPS array
 */
#include <iostream>
#include <vector>
#include <string>

/**
 * Compute the Longest Prefix Suffix (LPS) array for the pattern.
 *
 * @param pattern String pattern to search for
 * @return LPS array for the pattern
 */
std::vector<int> computeLPSArray(const std::string& pattern) {
    int m = pattern.length();
    std::vector<int> lps(m, 0); // LPS array to be filled
    
    // Length of the previous longest prefix suffix
    int length = 0;
    int i = 1;
    
    // Loop to fill LPS array
    while (i < m) {
        if (pattern[i] == pattern[length]) {
            length++;
            lps[i] = length;
            i++;
        } else {
            if (length != 0) {
                // This is tricky. Consider the example AAACAAAA and i = 7
                length = lps[length - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }
    }
    
    return lps;
}

/**
 * Search for pattern in text using KMP algorithm.
 *
 * @param text String to search in
 * @param pattern String pattern to search for
 * @return Vector of starting indices where pattern is found in text
 */
std::vector<int> kmpSearch(const std::string& text, const std::string& pattern) {
    std::vector<int> results;
    
    if (pattern.empty()) {
        return results; // Empty pattern matches nothing
    }
    
    int n = text.length();
    int m = pattern.length();
    
    // Create LPS array to hold the longest prefix suffix values for pattern
    std::vector<int> lps = computeLPSArray(pattern);
    
    // Index for text
    int i = 0;
    // Index for pattern
    int j = 0;
    
    while (i < n) {
        // Current characters match, move both pointers
        if (pattern[j] == text[i]) {
            i++;
            j++;
        }
        
        // Pattern completely found
        if (j == m) {
            results.push_back(i - j);
            // Look for the next match, start from lps[j-1]
            j = lps[j - 1];
        }
        // Mismatch after j matches
        else if (i < n && pattern[j] != text[i]) {
            if (j != 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
    }
    
    return results;
}

// Example usage
int main() {
    std::string text = "ABABDABACDABABCABAB";
    std::string pattern = "ABABCABAB";
    
    std::vector<int> result = kmpSearch(text, pattern);
    if (!result.empty()) {
        std::cout << "Pattern found at indices: ";
        for (int index : result) {
            std::cout << index << " ";
        }
        std::cout << std::endl;
    } else {
        std::cout << "Pattern not found in text" << std::endl;
    }
    
    return 0;
}
