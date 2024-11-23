class HuffmanNode {
    char c;
    String code;
    double probability;
    HuffmanNode left;
    HuffmanNode right;
    public HuffmanNode(char c, double probability) {
        this.c = c;
        this.probability = probability;
        this.left = null;
        this.right = null;
    }
}