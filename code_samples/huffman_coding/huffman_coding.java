/**
 * Huffman Coding implementation in Java.
 *
 * Huffman coding is a lossless data compression algorithm that assigns variable-length codes
 * to input characters based on their frequencies. The most frequent character gets the smallest code.
 *
 * Time Complexity: O(n log n) where n is the number of unique characters
 * Space Complexity: O(n)
 */
import java.util.*;

public class Huffman {
    
    // Node class for Huffman Tree
    static class HuffmanNode implements Comparable<HuffmanNode> {
        Character character;
        Integer frequency;
        HuffmanNode left;
        HuffmanNode right;
        
        public HuffmanNode(Character character, Integer frequency) {
            this.character = character;
            this.frequency = frequency;
            this.left = null;
            this.right = null;
        }
        
        @Override
        public int compareTo(HuffmanNode other) {
            return this.frequency.compareTo(other.frequency);
        }
    }
    
    /**
     * Build a Huffman tree from the given text.
     *
     * @param text Input text to be encoded
     * @return Root of the Huffman tree
     */
    public static HuffmanNode buildHuffmanTree(String text) {
        // Count frequency of each character
        Map<Character, Integer> frequency = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }
        
        // Create a priority queue to store nodes
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        
        // Create a leaf node for each character and add it to the priority queue
        for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }
        
        // Build the Huffman tree
        while (priorityQueue.size() > 1) {
            // Remove the two nodes of highest priority (lowest frequency)
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            
            // Create a new internal node with these two nodes as children
            // and with frequency equal to the sum of the two nodes' frequencies
            HuffmanNode internalNode = new HuffmanNode(null, left.frequency + right.frequency);
            internalNode.left = left;
            internalNode.right = right;
            
            // Add the new node to the priority queue
            priorityQueue.add(internalNode);
        }
        
        // The remaining node is the root of the Huffman tree
        return priorityQueue.poll();
    }
    
    /**
     * Generate Huffman codes for each character in the tree.
     *
     * @param node Current node in the Huffman tree
     * @param code Current code string (used in recursive calls)
     * @param mapping Map to store character-to-code mappings
     */
    public static void generateHuffmanCodes(HuffmanNode node, String code, Map<Character, String> mapping) {
        // If this is a leaf node (has a character)
        if (node.character != null) {
            mapping.put(node.character, code);
            return;
        }
        
        // Traverse left (add '0')
        generateHuffmanCodes(node.left, code + "0", mapping);
        // Traverse right (add '1')
        generateHuffmanCodes(node.right, code + "1", mapping);
    }
    
    /**
     * Encode the given text using Huffman coding.
     *
     * @param text Input text to be encoded
     * @return Pair of encoded text and the Huffman codes
     */
    public static Map<String, Object> huffmanEncoding(String text) {
        // Handle empty text
        if (text == null || text.isEmpty()) {
            Map<String, Object> result = new HashMap<>();
            result.put("encodedText", "");
            result.put("codes", new HashMap<>());
            return result;
        }
        
        // Handle text with only one unique character
        if (text.chars().distinct().count() == 1) {
            Map<Character, String> codes = new HashMap<>();
            codes.put(text.charAt(0), "0");
            
            StringBuilder encodedText = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                encodedText.append("0");
            }
            
            Map<String, Object> result = new HashMap<>();
            result.put("encodedText", encodedText.toString());
            result.put("codes", codes);
            return result;
        }
        
        // Build the Huffman tree
        HuffmanNode root = buildHuffmanTree(text);
        
        // Generate Huffman codes
        Map<Character, String> codes = new HashMap<>();
        generateHuffmanCodes(root, "", codes);
        
        // Encode the text
        StringBuilder encodedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            encodedText.append(codes.get(c));
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("encodedText", encodedText.toString());
        result.put("codes", codes);
        return result;
    }
    
    /**
     * Decode the given encoded text using the Huffman codes.
     *
     * @param encodedText Encoded text to be decoded
     * @param codes Map mapping each character to its Huffman code
     * @return Decoded text
     */
    public static String huffmanDecoding(String encodedText, Map<Character, String> codes) {
        // Handle empty text
        if (encodedText == null || encodedText.isEmpty()) {
            return "";
        }
        
        // Invert the codes map for decoding
        Map<String, Character> reverseMapping = new HashMap<>();
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            reverseMapping.put(entry.getValue(), entry.getKey());
        }
        
        // Decode the text
        StringBuilder decodedText = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();
        
        for (char bit : encodedText.toCharArray()) {
            currentCode.append(bit);
            if (reverseMapping.containsKey(currentCode.toString())) {
                decodedText.append(reverseMapping.get(currentCode.toString()));
                currentCode.setLength(0);
            }
        }
        
        return decodedText.toString();
    }
    
    // Example usage
    public static void main(String[] args) {
        String text = "this is an example for huffman encoding";
        
        // Encode
        Map<String, Object> result = huffmanEncoding(text);
        String encodedText = (String) result.get("encodedText");
        Map<Character, String> codes = (Map<Character, String>) result.get("codes");
        
        System.out.println("Original text: " + text);
        System.out.println("Encoded text: " + encodedText);
        System.out.println("Huffman Codes:");
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            System.out.println("'" + entry.getKey() + "': " + entry.getValue());
        }
        
        // Calculate compression ratio
        int originalSize = text.length() * 8;  // Assuming 8 bits per character
        int compressedSize = encodedText.length();
        double compressionRatio = (double) originalSize / compressedSize;
        
        System.out.println("Original size: " + originalSize + " bits");
        System.out.println("Compressed size: " + compressedSize + " bits");
        System.out.println("Compression ratio: " + String.format("%.2f", compressionRatio) + "x");
        
        // Decode
        String decodedText = huffmanDecoding(encodedText, codes);
        System.out.println("Decoded text: " + decodedText);
        System.out.println("Decoding successful: " + text.equals(decodedText));
    }
}
