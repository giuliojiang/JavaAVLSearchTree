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
            node.setLeft(addHelper(node.left, key, value));
            return node;
        } else if (key.compareTo(node.key) > 0)
        {
            node.setRight(addHelper(node.right, key, value));
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
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc) ============================================
     * @see avlTrees.AVLTree#remove(java.lang.Comparable)
     */
    @Override
    public void remove(K key)
    {
        // TODO Auto-generated method stub
        
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
        
        int lh = getHeight(start.left);
        int rh = getHeight(start.right);
        System.out.println(lh + "\t" + rh);
        
        if (lh == rh) // case 1
        {
            System.out.println("Case1");
            return start;
        } else if (lh == 0 && rh == 2) // case 2
        {
            System.out.println("Case2");
            return rotateLeft(start);
        } else if (lh == 2 && rh == 0) // case 3
        {
            System.out.println("Case3");
            return rotateRight(start);
        } else if (lh == 1 && rh == 3) // case 4
        {
            System.out.println("Case4");
            return rotateRightLeft(start);
        } else if (lh == 3 && rh == 1) // case 5
        {
            System.out.println("Case5");
            return rotateLeftRight(start);
        } else if (lh > rh) // case 6
        {
            System.out.println("Case6");
            start.setLeft(rebalance(start.left));
            return start;
        } else // (rh > lh) // case 7
        {
            System.out.println("Case7");
            start.setRight(rebalance(start.right));
            return start;
        }
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
            System.out.println("===   getheight " + "null" + " " + 0);
            return 0;
        } else
        {
            System.out.println("===   getheight " + node.toString() + " " + node.height);
            return node.height;
        }
    }
    
    @Override
    public String toString()
    {
        if (root == null)
            return "null";
        
        return root.getString(root);
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
        
    }
    
}
