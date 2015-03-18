package avlTrees;

public interface BSTInterface<K extends Comparable<K>, V>
{

    public boolean isEmpty();
    
    public void add(K key, V value);
    
    public V get(K key);
    
    public void remove(K key);
    
    
    
}
