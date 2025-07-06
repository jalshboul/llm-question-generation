"""
Knuth-Morris-Pratt (KMP) algorithm implementation in Python.

KMP is a string searching algorithm that uses information about the pattern
to minimize comparisons by avoiding re-examination of previously matched characters.

Time Complexity: O(n + m) where n is the length of the text and m is the length of the pattern
Space Complexity: O(m) for the LPS array
"""

def compute_lps_array(pattern):
    """
    Compute the Longest Prefix Suffix (LPS) array for the pattern.
    
    Args:
        pattern: String pattern to search for
        
    Returns:
        LPS array for the pattern
    """
    m = len(pattern)
    lps = [0] * m  # LPS array to be filled
    
    # Length of the previous longest prefix suffix
    length = 0
    i = 1
    
    # Loop to fill LPS array
    while i < m:
        if pattern[i] == pattern[length]:
            length += 1
            lps[i] = length
            i += 1
        else:
            if length != 0:
                # This is tricky. Consider the example AAACAAAA and i = 7
                length = lps[length - 1]
            else:
                lps[i] = 0
                i += 1
    
    return lps

def kmp_search(text, pattern):
    """
    Search for pattern in text using KMP algorithm.
    
    Args:
        text: String to search in
        pattern: String pattern to search for
        
    Returns:
        List of starting indices where pattern is found in text
    """
    if not pattern:
        return []  # Empty pattern matches nothing
    
    n = len(text)
    m = len(pattern)
    
    # Create LPS array to hold the longest prefix suffix values for pattern
    lps = compute_lps_array(pattern)
    
    results = []
    
    # Index for text
    i = 0
    # Index for pattern
    j = 0
    
    while i < n:
        # Current characters match, move both pointers
        if pattern[j] == text[i]:
            i += 1
            j += 1
        
        # Pattern completely found
        if j == m:
            results.append(i - j)
            # Look for the next match, start from lps[j-1]
            j = lps[j - 1]
        # Mismatch after j matches
        elif i < n and pattern[j] != text[i]:
            if j != 0:
                j = lps[j - 1]
            else:
                i += 1
    
    return results


# Example usage
if __name__ == "__main__":
    text = "ABABDABACDABABCABAB"
    pattern = "ABABCABAB"
    
    result = kmp_search(text, pattern)
    if result:
        print(f"Pattern found at indices: {result}")
    else:
        print("Pattern not found in text")
