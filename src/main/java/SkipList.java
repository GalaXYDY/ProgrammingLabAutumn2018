import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SkipList {

    class Node {
        int value;
        Node left;
        Node down;

        Node(int x, Node down, Node left) {
            this.value = x;
            this.down = down;
            this.left = left;
        }

        Node(int x) {
            this(x, null, null);
        }
    }

    private List<Node> head;
    private double p;
    private int size;
    private Random random;

    private SkipList(double p, int maxHeight) {
        this.p = p;
        this.head = new ArrayList<Node>();
        this.size = 0;
        if (maxHeight < 0) throw new IllegalArgumentException();
        for (int i = 0; i < maxHeight; i++) {
            head.add(new Node(0));
            if (i > 0) {
                head.get(i).down = head.get(i - 1);
            }
        }
        random = new Random();
    }


    SkipList() {
        this(0.5, 16);
    }

    private int randomHeight() {
        int height = 0;
        while (random.nextDouble() < p && height < head.size() - 1) height++;
        return height + 1;
    }

    void add(int value) {
        final int maxHeight = head.size();
        Node node = head.get(maxHeight - 1);
        int h = maxHeight - 1;
        Node[] update = new Node[maxHeight];

        while (node != null) {
            if (node.left == null || value < node.left.value) {
                update[h--] = node;
                node = node.down;
            } else {
                node = node.left;
            }
        }
        int currentHeight = randomHeight();
        Node[] current = new Node[currentHeight];
        for (h = 0; h < currentHeight; h++) {
            current[h] = new Node(value);
            if (h > 0) {
                current[h].down = current[h - 1];
            }
        }
        if (maxHeight < currentHeight) {
            for (h = maxHeight; h < currentHeight; h++) {
                head.add(new Node(0, head.get(h - 1), current[h]));
            }
            for (h = 0; h < maxHeight; h++) {
                current[h].left = update[h].left;
                update[h].left = current[h];
            }
        } else {
            for (h = 0; h < currentHeight; h++) {
                current[h].left = update[h].left;
                update[h].left = current[h];
            }
            size++;
        }
    }

    boolean check(int value) {
        final int maxHeight = head.size();
        Node node = head.get(maxHeight - 1);
        while (node != null) {
            if (node.left == null || value <= node.left.value) {
                if (node.left != null && node.left.value == value) return true;
                node = node.down;
            } else {
                node = node.left;
            }
        }
        return false;
    }

    int findMin() {
        final int maxHeight = head.size();
        Node node = head.get(maxHeight - 1);
        if (node != null) {
            while (node.down != null)
                node = node.down;
        }
        return node.left.value;
    }

    int findMax() {
        final int maxHeight = head.size();
        Node node = head.get(maxHeight - 1);
        if (node != null) {
            while (node.left.left != null)
                node = node.left;
            while (node.down != null)
                node = node.down;
            while (node.left.left != null)
                node = node.left;
        }
        return node.left.value;
    }

    void remove(int value) {
        final int maxHeight = head.size();
        Node node = head.get(maxHeight - 1);
        boolean available = false;
        while (node != null) {
            if (node.left == null || value <= node.left.value) {
                if (node.left != null && node.left.value == value) {
                    available = true;
                    node.left = node.left.left;
                }
                node = node.down;
            } else {
                node = node.left;
            }
        }
        if (available) {
            for (int i = head.size() - 1; i >= 1; i--) {
                if (head.get(i).left == null) {
                    head.remove(i);
                }
            }
            size--;
        }
    }


    int size() {
        return size;
    }

    int[] toArray() {
        int[] values = new int[size];
        int i = 0;
        Node node;
        for (node = head.get(0).left; node != null; node = node.left) {
            values[i++] = node.value;
        }
        return values;
    }

    void print() {
        for (int i = head.size() - 1; i >= 0; i--) {
            Node node = head.get(i).left;
            System.out.print("Level " + i + ": ");
            while (node != null) {
                System.out.print(node.value + " ");
                node = node.left;

            }
            System.out.println();
        }
    }
}
