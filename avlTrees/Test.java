package avlTrees;

import java.util.Random;

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
        t.add(90, 45);
        System.out.println(t + "\n");
        
        t.add(70, 1);
        System.out.println(t + "\n");
        
        t.add(100, 1);
        System.out.println(t + "\n");
        
        t.remove(80);
        System.out.println(t + "\n");
        
        t.remove(60);
        System.out.println(t + "\n");
        
        System.out.println(t.get(90));
//        
//        t.add(10, 1);
//        t.add(40, 1);
//        t.add(35, 1);
//        t.add(55, 1);
//        System.out.println(t + "\n");
//        
//        t = new AVL<Integer,Integer>();
//        Random r = new Random();
//        for (int i = 0; i < 16; i++)
//        {
//            t.add(r.nextInt(50), 1);
//        }
//        System.out.println(t + "\n");
    }
    
}
