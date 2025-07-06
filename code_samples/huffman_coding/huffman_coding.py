"""
Huffman Coding implementation in Python.

Huffman coding is a lossless data compression algorithm that assigns variable-length codes
to input characters based on their frequencies. The most frequent character gets the smallest code.

Time Complexity: O(n log n) where n is the number of unique characters
Space Complexity: O(n)
"""

import heapq
from collections import Counter

class HuffmanNode:
    def __init__(self, char, freq):
        self.char = char
        self.freq = freq
        self.left = None
        self.right = None
        
    # For comparison in the priority queue
    def __lt__(self, other):
        return self.freq < other.freq

def build_huffman_tree(text):
    """
    Build a Huffman tree from the given text.
    
    Args:
        text: Input text to be encoded
        
    Returns:
        Root of the Huffman tree
    """
    # Count frequency of each character
    frequency = Counter(text)
    
    # Create a leaf node for each character and add it to the priority queue
    priority_queue = [HuffmanNode(char, freq) for char, freq in frequency.items()]
    heapq.heapify(priority_queue)
    
    # Build the Huffman tree
    while len(priority_queue) > 1:
        # Remove the two nodes of highest priority (lowest frequency)
        left = heapq.heappop(priority_queue)
        right = heapq.heappop(priority_queue)
        
        # Create a new internal node with these two nodes as children
        # and with frequency equal to the sum of the two nodes' frequencies
        internal_node = HuffmanNode(None, left.freq + right.freq)
        internal_node.left = left
        internal_node.right = right
        
        # Add the new node to the priority queue
        heapq.heappush(priority_queue, internal_node)
    
    # The remaining node is the root of the Huffman tree
    return priority_queue[0]

def generate_huffman_codes(node, code="", mapping=None):
    """
    Generate Huffman codes for each character in the tree.
    
    Args:
        node: Current node in the Huffman tree
        code: Current code string (used in recursive calls)
        mapping: Dictionary to store character-to-code mappings
        
    Returns:
        Dictionary mapping each character to its Huffman code
    """
    if mapping is None:
        mapping = {}
    
    # If this is a leaf node (has a character)
    if node.char is not None:
        mapping[node.char] = code
    else:
        # Traverse left (add '0')
        generate_huffman_codes(node.left, code + "0", mapping)
        # Traverse right (add '1')
        generate_huffman_codes(node.right, code + "1", mapping)
    
    return mapping

def huffman_encoding(text):
    """
    Encode the given text using Huffman coding.
    
    Args:
        text: Input text to be encoded
        
    Returns:
        Tuple of encoded text and the Huffman tree
    """
    # Handle empty text
    if not text:
        return "", None
    
    # Handle text with only one unique character
    if len(set(text)) == 1:
        return "0" * len(text), {text[0]: "0"}
    
    # Build the Huffman tree
    root = build_huffman_tree(text)
    
    # Generate Huffman codes
    codes = generate_huffman_codes(root)
    
    # Encode the text
    encoded_text = "".join(codes[char] for char in text)
    
    return encoded_text, codes

def huffman_decoding(encoded_text, codes):
    """
    Decode the given encoded text using the Huffman codes.
    
    Args:
        encoded_text: Encoded text to be decoded
        codes: Dictionary mapping each character to its Huffman code
        
    Returns:
        Decoded text
    """
    # Handle empty text
    if not encoded_text:
        return ""
    
    # Invert the codes dictionary for decoding
    reverse_mapping = {code: char for char, code in codes.items()}
    
    # Decode the text
    decoded_text = []
    current_code = ""
    
    for bit in encoded_text:
        current_code += bit
        if current_code in reverse_mapping:
            decoded_text.append(reverse_mapping[current_code])
            current_code = ""
    
    return "".join(decoded_text)


# Example usage
if __name__ == "__main__":
    text = "this is an example for huffman encoding"
    
    # Encode
    encoded_text, codes = huffman_encoding(text)
    
    print("Original text:", text)
    print("Encoded text:", encoded_text)
    print("Huffman Codes:")
    for char, code in codes.items():
        print(f"'{char}': {code}")
    
    # Calculate compression ratio
    original_size = len(text) * 8  # Assuming 8 bits per character
    compressed_size = len(encoded_text)
    compression_ratio = original_size / compressed_size
    
    print(f"Original size: {original_size} bits")
    print(f"Compressed size: {compressed_size} bits")
    print(f"Compression ratio: {compression_ratio:.2f}x")
    
    # Decode
    decoded_text = huffman_decoding(encoded_text, codes)
    print("Decoded text:", decoded_text)
    print("Decoding successful:", text == decoded_text)
