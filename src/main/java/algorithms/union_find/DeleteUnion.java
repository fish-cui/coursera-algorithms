package algorithms.union_find;

/**
 * @author Cui
 * @create 2020-02-05 09:32
 **/
public class DeleteUnion {
    private int[] dels;
    private int[] size;
    private int[] bigs;
    DeleteUnion(int n){
        dels = new int[n];
        size = new int[n];
        bigs = new int[n];
        for (int i=0; i<n; i++) {
            dels[i] = i;
            size[i]=1;
            bigs[i]=i;
        }
    }

    public Integer root(int i){
        if(i<0 || i>=dels.length){
            return null;
        }
        while(dels[i]!=i){
            dels[i]=dels[dels[i]];
            i=dels[i];
        }
        return i;
    }

    private boolean union(int p, int q){
        int pRoot = root(p);
        int qRoot = root(q);
        if(pRoot!=qRoot){
            if(size[pRoot]<=size[qRoot]){
                dels[pRoot]=qRoot;
                size[qRoot]+=size[pRoot];
                bigs[qRoot]=Math.max(bigs[pRoot], bigs[qRoot]);
            }else{
                dels[qRoot]=pRoot;
                size[pRoot]+=size[qRoot];
                bigs[pRoot]=Math.max(bigs[pRoot], bigs[qRoot]);
            }
            return true;
        }
        return false;
    }

    public boolean del(int i){
        return union(i, i+1);
    }

    public int find(int i){
        return bigs[root(i)];
    }

    public static void main(String[] args) {
        DeleteUnion deleteUnion = new DeleteUnion(100);
        System.out.println(deleteUnion.find(3));
        System.out.println(deleteUnion.del(3));
        System.out.println(deleteUnion.find(3));

        System.out.println(deleteUnion.del(5));
        System.out.println(deleteUnion.find(5));

        System.out.println(deleteUnion.del(4));
        System.out.println(deleteUnion.find(4));
        System.out.println(deleteUnion.del(5));

    }
}
