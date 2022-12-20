package _20_12_2022.avl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AVLTree {
    public static class Node {
        private Node left;
        private Node right;
        private Node parent;
        private int value;

        private int height = 1;

        public Node(int value) {
            this.value = value;
        }
    }

    private int height(Node n) {
        if (n == null) {
            return 0;
        }
        return n.height;
    }

    private Node insert(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insert(node.left, value);
        } else {
            node.right = insert(node.right, value);
        }

        //Update height
        node.height = Math.max(height(node.left), height(node.right) + 1);

        //Баланс фактор right - left
        int balance = getBalance(node); // [1] [> or < 1] [0]

        //Если наше дерево не сбалансировано, то
        //тогда рассматриваем разные варианты сдвига

        //1. LL
        if (balance > 1 && value < node.left.value) {
            return rightRotate(node);
        }

        //2. RR
        if (balance < -1 && value > node.right.value) {
            return leftRotate(node);
        }

        //3. LR
        if (balance > 1 && value > node.left.value) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        //4. RL
        if (balance < -1 && value < node.right.value) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private int getBalance(Node n) {
        if (n == null) {
            return 0;
        }
        return height(n.left) - height(n.right);
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node t = y.left;

        //Ротация
        y.left = x;
        x.right = t;

        //Update heights
        x.height = Math.max(height(x.left), height(x.right) + 1);
        y.height = Math.max(height(y.left), height(y.right) + 1);

        //new root
        return y;
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node t = x.right;

        //Ротация
        x.right = y;
        y.left = t;

        //Update heights
        y.height = Math.max(height(y.left), height(y.right) + 1);
        x.height = Math.max(height(x.left), height(x.right) + 1);

        //new root
        return x;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private Node deleteNode(Node root, int value) {
        if (root == null) {
            return root;
        }

        if (value < root.value) {
            root.left = deleteNode(root.left, value);
        } else if (value > root.value) {
            root.right = deleteNode(root.right, value);
        } else {
            if ((root.left == null) || (root.right == null)) {
                Node temp;
                if (root.left != null) {
                    temp = root.left;
                } else {
                    temp = root.right;
                }

                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
                temp = null;
            } else {
                Node temp = minValueNode(root.right);
                root.value = temp.value;
                root.right = deleteNode(root.right, temp.value);
            }
        }

        if (root == null) {
            return root;
        }

        root.height = Math.max(height(root.left), height(root.right) + 1);
        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    public void print(Node root) {
        if (root == null) {
            System.out.println("(XXXXXX)");
            return;
        }
        int height = root.height,
                width = (int) Math.pow(2, height - 1);

        // Preparing variables for loop.
        List<Node> current = new ArrayList<Node>(1),
                next = new ArrayList<Node>(2);
        current.add(root);

        final int maxHalfLength = 4;
        int elements = 1;

        StringBuilder sb = new StringBuilder(maxHalfLength * width);
        for (int i = 0; i < maxHalfLength * width; i++)
            sb.append(' ');
        String textBuffer;

        // Iterating through height levels.
        for (int i = 0; i < height; i++) {
            sb.setLength(maxHalfLength * ((int) Math.pow(2, height - 1 - i) - 1));

            // Creating spacer space indicator.
            textBuffer = sb.toString();

            // Print tree node elements
            for (Node n : current) {
                System.out.print(textBuffer);
                if (n == null) {
                    System.out.print("        ");
                    next.add(null);
                    next.add(null);

                } else {
                    System.out.printf("(%6d)", n.value);
                    next.add(n.left);
                    next.add(n.right);

                }
                System.out.print(textBuffer);
            }

            System.out.println();
            // Print tree node extensions for next level.
            if (i < height - 1) {
                for (Node n : current) {
                    System.out.print(textBuffer);
                    if (n == null)
                        System.out.print("        ");
                    else
                        System.out.printf("%s      %s",
                                n.left == null ? " " : "/", n.right == null ? " " : "\\");
                    System.out.print(textBuffer);
                }
                System.out.println();
            }

            // Renewing indicators for next run.
            elements *= 2;
            current = next;
            next = new ArrayList<Node>(elements);

        }
    }

    public static void main(String[] args) {
        AVLTree t = new AVLTree();
        Node root = null;
        while (true) {
            System.out.println("\n\n(1) Insert");
            System.out.println("(2) Delete");

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String s = bufferedReader.readLine();

                if (Integer.parseInt(s) == 1) {
                    System.out.println("Value to be inserted: ");
                    root = t.insert(root, Integer.parseInt(bufferedReader.readLine()));
                } else if (Integer.parseInt(s) == 2) {
                    System.out.println("Value to be deleted: ");
                    root = t.deleteNode(root, Integer.parseInt(bufferedReader.readLine()));
                } else {
                    System.out.println("Incorrect command");
                    continue;
                }

                t.print(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}