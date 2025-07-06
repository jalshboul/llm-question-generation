/**
 * Huffman Coding implementation in C++.
 *
 * Huffman coding is a lossless data compression algorithm that assigns variable-length codes
 * to input characters based on their frequencies. The most frequent character gets the smallest code.
 *
 * Time Complexity: O(n log n) where n is the number of unique characters
 * Space Complexity: O(n)
 */
#include <iostream>
#include <string>
#include <queue>
#include <unordered_map>
#include <vector>

// Node class for Huffman Tree
class HuffmanNode {
public:
    char character;
    int frequency;
    HuffmanNode* left;
    HuffmanNode* right;
    
    HuffmanNode(char character, int frequency) : 
        character(character), frequency(frequency), left(nullptr), right(nullptr) {}
    
    // Destructor to clean up memory
    ~HuffmanNode() {
        delete left;
        delete right;
    }
    
    // Check if this is a leaf node
    bool isLeaf() const {
        return left == nullptr && right == nullptr;
    }
};

// Comparison class for priority queue
struct CompareNodes {
    bool operator()(HuffmanNode* a, HuffmanNode* b) {
        return a->frequency > b->frequency;  // Min-heap
    }
};

/**
 * Build a Huffman tree from the given text.
 *
 * @param text Input text to be encoded
 * @return Root of the Huffman tree
 */
HuffmanNode* buildHuffmanTree(const std::string& text) {
    // Count frequency of each character
    std::unordered_map<char, int> frequency;
    for (char c : text) {
        frequency[c]++;
    }
    
    // Create a priority queue to store nodes
    std::priority_queue<HuffmanNode*, std::vector<HuffmanNode*>, CompareNodes> priorityQueue;
    
    // Create a leaf node for each character and add it to the priority queue
    for (const auto& pair : frequency) {
        priorityQueue.push(new HuffmanNode(pair.first, pair.second));
    }
    
    // Build the Huffman tree
    while (priorityQueue.size() > 1) {
        // Remove the two nodes of highest priority (lowest frequency)
        HuffmanNode* left = priorityQueue.top();
        priorityQueue.pop();
        
        HuffmanNode* right = priorityQueue.top();
        priorityQueue.pop();
        
        // Create a new internal node with these two nodes as children
        // and with frequency equal to the sum of the two nodes' frequencies
        HuffmanNode* internalNode = new HuffmanNode('\0', left->frequency + right->frequency);
        internalNode->left = left;
        internalNode->right = right;
        
        // Add the new node to the priority queue
        priorityQueue.push(internalNode);
    }
    
    // The remaining node is the root of the Huffman tree
    return priorityQueue.top();
}

/**
 * Generate Huffman codes for each character in the tree.
 *
 * @param node Current node in the Huffman tree
 * @param code Current code string (used in recursive calls)
 * @param mapping Map to store character-to-code mappings
 */
void generateHuffmanCodes(HuffmanNode* node, const std::string& code, 
                         std::unordered_map<char, std::string>& mapping) {
    // If this is a leaf node (has a character)
    if (node->isLeaf()) {
        mapping[node->character] = code;
        return;
    }
    
    // Traverse left (add '0')
    if (node->left) {
        generateHuffmanCodes(node->left, code + "0", mapping);
    }
    
    // Traverse right (add '1')
    if (node->right) {
        generateHuffmanCodes(node->right, code + "1", mapping);
    }
}

/**
 * Encode the given text using Huffman coding.
 *
 * @param text Input text to be encoded
 * @param codes Output parameter for the Huffman codes
 * @return Encoded text
 */
std::string huffmanEncoding(const std::string& text, std::unordered_map<char, std::string>& codes) {
    // Handle empty text
    if (text.empty()) {
        return "";
    }
    
    // Handle text with only one unique character
    if (std::unordered_map<char, int>{{text[0], 1}}.size() == 1) {
        codes[text[0]] = "0";
        return std::string(text.length(), '0');
    }
    
    // Build the Huffman tree
    HuffmanNode* root = buildHuffmanTree(text);
    
    // Generate Huffman codes
    generateHuffmanCodes(root, "", codes);
    
    // Encode the text
    std::string encodedText;
    for (char c : text) {
        encodedText += codes[c];
    }
    
    // Clean up the tree (avoid memory leak)
    delete root;
    
    return encodedText;
}

/**
 * Decode the given encoded text using the Huffman codes.
 *
 * @param encodedText Encoded text to be decoded
 * @param codes Map mapping each character to its Huffman code
 * @return Decoded text
 */
std::string huffmanDecoding(const std::string& encodedText, 
                           const std::unordered_map<char, std::string>& codes) {
    // Handle empty text
    if (encodedText.empty()) {
        return "";
    }
    
    // Invert the codes map for decoding
    std::unordered_map<std::string, char> reverseMapping;
    for (const auto& pair : codes) {
        reverseMapping[pair.second] = pair.first;
    }
    
    // Decode the text
    std::string decodedText;
    std::string currentCode;
    
    for (char bit : encodedText) {
        currentCode += bit;
        if (reverseMapping.find(currentCode) != reverseMapping.end()) {
            decodedText += reverseMapping[currentCode];
            currentCode.clear();
        }
    }
    
    return decodedText;
}

// Example usage
int main() {
    std::string text = "this is an example for huffman encoding";
    
    // Encode
    std::unordered_map<char, std::string> codes;
    std::string encodedText = huffmanEncoding(text, codes);
    
    std::cout << "Original text: " << text << std::endl;
    std::cout << "Encoded text: " << encodedText << std::endl;
    std::cout << "Huffman Codes:" << std::endl;
    for (const auto& pair : codes) {
        std::cout << "'" << pair.first << "': " << pair.second << std::endl;
    }
    
    // Calculate compression ratio
    int originalSize = text.length() * 8;  // Assuming 8 bits per character
    int compressedSize = encodedText.length();
    double compressionRatio = static_cast<double>(originalSize) / compressedSize;
    
    std::cout << "Original size: " << originalSize << " bits" << std::endl;
    std::cout << "Compressed size: " << compressedSize << " bits" << std::endl;
    std::cout << "Compression ratio: " << std::fixed << std::setprecision(2) << compressionRatio << "x" << std::endl;
    
    // Decode
    std::string decodedText = huffmanDecoding(encodedText, codes);
    std::cout << "Decoded text: " << decodedText << std::endl;
    std::cout << "Decoding successful: " << (text == decodedText ? "Yes" : "No") << std::endl;
    
    return 0;
}
