/**
 * Knuth-Morris-Pratt (KMP) algorithm implementation in Java.
 *
 * KMP is a string searching algorithm that uses information about the pattern
 * to minimize comparisons by avoiding re-examination of previously matched characters.
 *
 * Time Complexity: O(n + m) where n is the length of the text and m is the length of the pattern
 * Space Complexity: O(m) for the LPS array
 */
public class KnuthMorrisPratt {
    
    /**
     * Compute the Longest Prefix Suffix (LPS) array for the pattern.
     *
     * @param pattern String pattern to search for
     * @return LPS array for the pattern
     */
    private static int[] computeLPSArray(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m]; // LPS array to be filled
        
        // Length of the previous longest prefix suffix
        int length = 0;
        int i = 1;
        
        // Loop to fill LPS array
        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
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
     * @return Array of starting indices where pattern is found in text
     */
    public static int[] kmpSearch(String text, String pattern) {
        if (pattern.isEmpty()) {
            return new int[0]; // Empty pattern matches nothing
        }
        
        int n = text.length();
        int m = pattern.length();
        
        // Create LPS array to hold the longest prefix suffix values for pattern
        int[] lps = computeLPSArray(pattern);
        
        // Use a dynamic array to store results
        java.util.ArrayList<Integer> resultList = new java.util.ArrayList<>();
        
        // Index for text
        int i = 0;
        // Index for pattern
        int j = 0;
        
        while (i < n) {
            // Current characters match, move both pointers
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            
            // Pattern completely found
            if (j == m) {
                resultList.add(i - j);
                // Look for the next match, start from lps[j-1]
                j = lps[j - 1];
            }
            // Mismatch after j matches
            else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        
        // Convert ArrayList to array
        int[] results = new int[resultList.size()];
        for (int k = 0; k < resultList.size(); k++) {
            results[k] = resultList.get(k);
        }
        
        return results;
    }
    
    // Example usage
    public static void main(String[] args) {
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        
        int[] result = kmpSearch(text, pattern);
        if (result.length > 0) {
            System.out.print("Pattern found at indices: ");
            for (int index : result) {
                System.out.print(index + " ");
            }
            System.out.println();
        } else {
            System.out.println("Pattern not found in text");
        }
    }
}
