package avlTrees;

public class Test
{

    public static void main(String[] args)
    {
        BSTInterface<Integer,Integer> t = new AVL<Integer,Integer>();
        t.add(60, 1);
        t.add(50, 1);
        t.add(20, 1);
        System.out.println(t + "\n");
        
        t.add(80, 1);
        t.add(90, 1);
        System.out.println(t + "\n");
        
        t.add(70, 1);
        System.out.println(t + "\n");
        
        t.add(10, 1);
        t.add(40, 1);
        t.add(35, 1);
        t.add(55, 1);
        System.out.println(t + "\n");
    }
    
}
