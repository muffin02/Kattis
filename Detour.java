import java.util.*;
import java.io.*;
import static java.lang.Math.min;
import static java.lang.Math.max;
public class Detour{
    static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
    static FastScanner sc = new FastScanner();
    static int MAX = Integer.MAX_VALUE;
    static int MIN = Integer.MIN_VALUE;
    public static void main(String[] args) {
        solve();
        out.close();
    }
    
    static void solve(){
        int n = sc.nextint(), m = sc.nextint();
        long[] dMin = new long[n];
        Node[] nodes = new Node[n];
        
        for(int i = 0; i < n; ++i) {
            nodes[i] = new Node(i);
        }
        
        for(int i = 0; i < m; ++i) {
            int u = sc.nextint(), v = sc.nextint(), w = sc.nextint();
            nodes[u].add(nodes[v], w);
            nodes[v].add(nodes[u], w);
        }
        
        Arrays.fill(dMin, (long) 1e14);
        PriorityQueue<Pair> pqMin = new PriorityQueue<>();
        
        boolean[] visMin = new boolean[n];
        
        int[] prev = new int[n];
        prev[1] = -1;
        pqMin.offer(new Pair(nodes[1], 0));
        dMin[1] = 0;
        //run dijkstra's from amsterdam.
        
        while(!pqMin.isEmpty()) {
            Pair p = pqMin.poll();
            if(visMin[p.u.id]) {
                continue;
            }
            visMin[p.u.id] = true;
            if(prev[p.u.id] != -1) {
                p.u.adj.get(nodes[prev[p.u.id]]).deleted = true;
            }
            for(Node v : p.u.adj.keySet()) {
                long dist = p.u.adj.get(v).weight;
                if(!visMin[v.id] && dMin[v.id] > dMin[p.u.id] + dist) {
                    dMin[v.id]= dMin[p.u.id] + dist;
                    prev[v.id] = p.u.id;
                    pqMin.offer(new Pair(v, dMin[v.id]));
                }
            }
        }
        
        int[] prev2 = new int[n];
        boolean[] vis = new boolean[n];
        Queue<Integer> q = new ArrayDeque<>();
        vis[0] = true;
        q.offer(0);
        while(!q.isEmpty() && !vis[1]) {
            int cur = q.poll();
            for(Node v : nodes[cur].adj.keySet()) {
                if(!vis[v.id] && !nodes[cur].adj.get(v).deleted) {
                    vis[v.id] = true;
                    q.offer(v.id);
                    prev2[v.id] = cur; 
                }
            }
        }
        
        if(!vis[1]) {
            out.println("impossible");
            return;
        }
        
        prev2[0] = -1;
        Stack<Integer> stk = new Stack<Integer>();
        for(int i = 1; i != -1; i = prev2[i]) {
            stk.push(i);
        }
        out.print(stk.size()+ " ");
        while(!stk.isEmpty()) {
            out.print(stk.pop() + " ");
        }
    }
    
    static void dfs(Node n, boolean[] vis) {
        boolean ok = false;
        for(Node v : n.adj.keySet()) {
            if(!vis[v.id]) {
                vis[v.id] = true; 
            }
        }
    }
    
    static class Pair implements Comparable<Pair>{
        Node u;
        long weight;
        public Pair(Node u, long weight) {
            this.u = u;
            this.weight = weight;
        }
        public int compareTo(Pair p) {
            return Long.compare(weight, p.weight);
        }
    }
    
    static class Node{
        Map<Node, Edge> adj = new HashMap<>();
        int id;
        public Node(int id) {
            this.id = id;
        }
        void add(Node n, long weight) {
            adj.put(n, new Edge(n, weight));
        }
        
        void remove(Node n) {
            adj.remove(n);
        }
    }
    
    static StringTokenizer st;
    static BufferedReader br;
    static Random r = new Random();
    static class FastScanner{
        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        public String next() {
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
        
        public void sort(long[] a) {
            for(int i = 0; i < a.length; ++i) {
                int idx = r.nextInt(a.length);
                long temp = a[idx];
                a[idx] = a[i];
                a[i] = temp;
            }
            
            Arrays.sort(a);
        }
        
        public void sort(int[] a) {
            for(int i = 0; i < a.length; ++i) {
                int idx = r.nextInt(a.length);
                int temp = a[idx];
                a[idx] = a[i];
                a[i] = temp;
            }
            Arrays.sort(a);
        }
        
        public int[] readint(int k) {
            int[] res = new int[k];
            for(int i = 0; i < k; ++i) {
                res[i] = nextint();
            }
            return res;
        }
        
        public long[] readlong(int k) {
            long[] res = new long[k];
            for(int i = 0; i < k; ++i) {
                res[i] = nextlong();
            }
            return res;
        }
        
        public int nextint() {
            return Integer.parseInt(next());
        }
        
        public long nextlong() {
            return Long.parseLong(next());
        }
        
        public double nextdouble() {
            return Double.parseDouble(next());
        }
        
        public String nextline() {
            String res = "";
            try {
                res = br.readLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }
    }
}
