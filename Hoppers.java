import java.io.*;
import java.util.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Hoppers{
    static int MAX = Integer.MAX_VALUE, MIN = Integer.MIN_VALUE;
    static FastScanner sc = new FastScanner();
    static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args){
        solve();
        out.close();
    }
    
    static void solve(){
        int n = sc.nextint(), m = sc.nextint();
        Node[] nodes = new Node[n];
        for(int i = 0; i < n; ++i) {
            nodes[i] = new Node(i);
        }
        for(int i = 0; i < m; ++i) {
            int n1 = sc.nextint()-1, n2 = sc.nextint()-1;
            nodes[n1].add(nodes[n2]);
            nodes[n2].add(nodes[n1]);
        }
        
        List<List<Node>> comps = new ArrayList<>();
        for(int i = 0; i < n; ++i) {
            if(!nodes[i].used) {
                List<Node> comp = new ArrayList<>();
                nodes[i].used = true;
                comp.add(nodes[i]);
                dfs(nodes[i], comp);
                comps.add(comp);
            }
        }
        
        //now we have our components
        int ans = comps.size()-1;
        boolean ok = false;
        for(List<Node> comp : comps) {
            if(comp.size() == 1) {
                continue;
            }
            size = 1;
            Node start = comp.get(0);
            start.vis = true;
            dfs0(start);
            if(size == comp.size()) {
                //non bipartite coloring
                ok = true;
                break;
            }
        }
        
        out.println(ok?ans:(ans+1));
    }
    
    static void dfs0(Node u) {
        for(Node v : u.adj) {
            for(Node w : v.adj) {
                if(!w.vis) {
                    w.vis = true;
                    ++size;
                    dfs0(w);
                }
            }
        }
    }
    
    static void dfs(Node u, List<Node> comp) {
        for(Node v : u.adj) {
            if(!v.used) {
                v.used = true;
                comp.add(v);
                dfs(v, comp);
            }
        }
    }
    
    static class Node{
        int id;
        boolean used, vis;
        List<Node> adj = new ArrayList<>();
        public Node(int id) {
            this.id = id;
        }
        
        void add(Node n) {
            adj.add(n);
        }
    }
    
    static void sort(int[] a) {
        Random r = new Random();
        int n = a.length;
        for(int i = 0; i < n; ++i) {
            int idx = r.nextInt(n);
            int temp = a[idx];
            a[idx] = a[i];
            a[i] = temp;
        }
        Arrays.sort(a);
    }
    
    static BufferedReader br;
    static StringTokenizer st;
    
    static class FastScanner{
        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        
        String next() {
            while(st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
        
        int[] readint(int n) {
            int[] res = new int[n];
            for(int i = 0; i < n; i++) {
                res[i] = nextint();
            }
            return res;
        }
        
        long[] readlong(int n) {
            long[] res = new long[n];
            for(int i = 0; i < n; i++) {
                res[i] = nextlong();
            }
            return res;
        }
        
        int nextint(){
            return Integer.parseInt(next());
        }
        
        long nextlong() {
            return Long.parseLong(next());
        }
        
        double nextdouble() {
            return Double.parseDouble(next());
        }
        
        String nextline() {
            String str = "";
            try {
                str = br.readLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
