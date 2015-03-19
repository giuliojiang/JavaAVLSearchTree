package avlTrees;

public class AVL<K extends Comparable<K>, V> implements BSTInterface<K, V>
{

    private Node<K,V> root;
    
    /* (non-Javadoc) ==============================================
     * @see avlTrees.AVLTree#isEmpty()
     */
    @Override
    public boolean isEmpty()
    {
        return root == null;
    }

    /* Add method ================================================
     * 
     */
    @Override
    public void add(K key, V value)
    {
        root = addHelper(root, key, value);
        root = rebalance(root);
    }
    
    private Node<K,V> addHelper(Node<K,V> node, K key, V value)
    {
        if (node == null)
        { // create new leaf
            Node<K,V> leaf = new Node<K,V>(key, value);
            return leaf;
        } else if (key.compareTo(node.key) < 0)
        {
            Node<K,V> newLeft = addHelper(node.left, key, value);
            node.setLeft(rebalance(newLeft));
            return node;
        } else if (key.compareTo(node.key) > 0)
        {
            Node<K,V> newRight = addHelper(node.right, key, value);
            node.setRight(rebalance(newRight));
            return node;
        } else // (key.compareTo(node.key) == 0)
        {
            node.value = value;
            return node;
        }
    }


    /* (non-Javadoc) =======================================
     * @see avlTrees.AVLTree#get(java.lang.Comparable)
     */
    @Override
    public V get(K key)
    {
        return getNode(root,key).value;
    }
    
    public Node<K,V> getNode(Node<K,V> node, K key)
    {
        if (node == null)
            throw new RuntimeException("element not found");
        
        if (key.compareTo(node.key) == 0)
        {
            return node;
        } else if (key.compareTo(node.key) < 0)
        {
            return getNode(node.left, key);
        } else
        {
            return getNode(node.right, key);
        }
    }

    /* (non-Javadoc) ============================================
     * @see avlTrees.AVLTree#remove(java.lang.Comparable)
     */
    @Override
    public void remove(K key)
    {
        root = deleteElem(root, key);
    }
    
    private Node<K,V> deleteElem(Node<K,V> node, K key)
    {
        if (node == null)
            throw new RuntimeException();
        
        if (key.compareTo(node.key) == 0)
        {
            node = deleteNode(node);
            return rebalance(node);
        } else if (key.compareTo(node.key) < 0)
        {
            node.setLeft(deleteElem(node.left, key));
            return rebalance(node);
        } else
        {
            node.setRight(deleteElem(node.right, key));
            return rebalance(node);
        }
    }
    
    private Node<K,V> deleteNode(Node<K,V> node)
    {
        if (node == null)
            return null;
        
        if (isLeaf(node))
        {
            return null;
        } else if (hasOnlyLeft(node))
        {
            return node.left;
        } else if (hasOnlyRight(node))
        {
            return node.right;
        } else
        {
            Node<K,V> replacement = findRightMost(node.left);
            node.setLeft(deleteRightMost(node.left));
            replacement.setLeft(node.left);
            replacement.setRight(node.right);
            node = null;
            return replacement;
        }
        
    }
    
    private boolean isLeaf(Node<K,V> node)
    {
        return node.left == null && node.right == null;
    }
    
    private boolean hasOnlyLeft(Node<K,V> node)
    {
        return node.left != null && node.right == null;
    }
    
    private boolean hasOnlyRight(Node<K,V> node)
    {
        return node.right != null && node.left == null;
    }
    
    private Node<K,V> findRightMost(Node<K,V> node)
    {
        if (node.right == null)
        {
            return node;
        } else
        {
            return findRightMost(node.right);
        }
    }
    
    private Node<K,V> deleteRightMost(Node<K,V> node)
    {
        if (node.right == null)
        {
            return node.left;
        } else
        {
            node.setRight(deleteRightMost(node.right));
            return node;
        }
    }
    
    /** =======================================================
     * Rebalancing algorithm
     * 
     * @param start
     * @return
     */
    private Node<K,V> rebalance(Node<K,V> start)
    {
        if (start == null)
            return null;
        
        System.out.println("Analyzing " + start);
        
        int ch = calculateHeightDifference(start);
        
        if (ch == 0)
        {
            return start;
        } else if (ch > 1) // left is taller
        {
            int gch = calculateHeightDifference(start.left);
            if (gch < 0) // right is taller
            {
                return rotateLeftRight(start);
            } else
            {
                return rotateRight(start);
            }
        } else if (ch < -1) // right is taller
        {
            int gch = calculateHeightDifference(start.right);
            if (gch > 0) //  left is taller
            {
                return rotateRightLeft(start);
            } else
            {
                return rotateLeft(start);
            }
        } else
        {
            return start;
        }
    }
    
    private int calculateHeightDifference(Node<K,V> node)
    {
        if (node == null)
            return 0;
        
        Node<K,V> l = node.left;
        Node<K,V> r = node.right;
        
        int lh = getHeight(l);
        int rh = getHeight(r);
        
        return lh - rh;
    }
    
    
    /** =============================================
     * Rotations
     * 
     * @param node
     * @return
     */
    private Node<K,V> rotateRight(Node<K,V> node)
    {
        if (node == null)
            return null;
        
        if (node.left == null)
            return node;
        
        Node<K,V> l = node.left;
        Node<K,V> lr = l.right;
        Node<K,V> n = node;
        
        n.setLeft(lr);
        l.setRight(n);
        
        return l;
    }
    
    private Node<K,V> rotateLeft(Node<K,V> node)
    {
        if (node == null)
            return null;
        
        if (node.right == null)
            return node;
        
        Node<K,V> r = node.right;
        Node<K,V> rl = r.left;
        Node<K,V> n = node;
        
        n.setRight(rl);
        r.setLeft(n);
        
        return r;
    }
    
    private Node<K,V> rotateRightLeft(Node<K,V> node)
    {
        if (node == null)
            return null;
        
        Node<K,V> nr = rotateRight(node.right);
        node.setRight(nr);
        return rotateLeft(node);
    }
    
    private Node<K,V> rotateLeftRight(Node<K,V> node)
    {
        if (node == null)
            return null;
        
        Node<K,V> nl = rotateLeft(node.left);
        node.setLeft(nl);
        return rotateRight(node);
    }
    
    /** ==================================
     * Height calculator
     * 
     * @param node
     * @return
     */
    private int getHeight(Node<K,V> node)
    {
        if (node == null)
        {
            // System.out.println("===   getheight " + "null" + " " + 0);
            return 0;
        } else
        {
            // System.out.println("===   getheight " + node.toString() + " " + node.height);
            return node.height;
        }
    }
    
    @Override
    public String toString()
    {
        if (root == null)
            return "null";
        
        return root.getString(root) + "\n" + root.heightString(root);
    }
    
    
    /**
     * Node class
     * 
     * @author gj
     *
     * @param <K>
     * @param <V>
     */
    private class Node<K extends Comparable<K>,V>
    {
        private K key;
        private V value;
        private int height;
        
        private Node<K,V> parent;
        private Node<K,V> left;
        private Node<K,V> right;
        
        private Node(K key, V value)
        {
            this.key = key;
            this.value = value;
            this.height = 1;
            this.parent = null;
            this.left = null;
            this.right = null;
        }
        
        private void recalculateHeight()
        {
            if (left == null && right == null)
            {
                height = 1;
            } else if (left == null)
            {
                height = 1 + right.height;
            } else if (right == null)
            {
                height = 1 + left.height;
            } else
            {
                height = 1 + Math.max(left.height, right.height);
            }
            // System.out.println("   " + this.toString() + " height: " + height);
        }
        
        private void setParent(Node<K,V> parent)
        {
            this.parent = parent;
        }
        
        private void setLeft(Node<K,V> left)
        {
            this.left = left;
            if (this.left != null)
            {
                this.left.setParent(this);
            }
            recalculateHeight();
        }
        
        private void setRight(Node<K,V> right)
        {
            this.right = right;
            if (this.right != null)
            {
                this.right.setParent(this);
            }
            recalculateHeight();
        }
        
        private String getString(Node<K,V> node)
        {
            if (node == null)
                return ".";
            return node.toString();
        }
        
        @Override
        public String toString()
        {
            StringBuilder out = new StringBuilder();
            out.append(key);
            out.append("[" + getString(left) + "]");
            out.append("[" + getString(right) + "]");
            return out.toString();
        }
        
        private String heightString(Node<K,V> n)
        {
            if (n == null)
                return "";
            
            return n.key + ":" + n.height + "\n" + heightString(n.left) + heightString(n.right);
        }
        
    }
    
}
