import java.io.*;
import java.util.*;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
public class Arachnophobia{
    static int MAX = Integer.MAX_VALUE, MIN = Integer.MIN_VALUE;
    static FastScanner sc = new FastScanner();
    static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args){
        solve();
        out.close();
    }
    
    static void solve(){
        int n = sc.nextint(), m = sc.nextint(), T = sc.nextint();
        List<Edge>[] adj = new ArrayList[n];
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        
        for(int i = 0; i < n; ++i) {
            adj[i] = new ArrayList<>();
        }
        
        for(int i = 0; i < m; ++i) {
            int u = sc.nextint(), v = sc.nextint(), d = sc.nextint();
            adj[u].add(new Edge(v, d));
            adj[v].add(new Edge(u, d));
        }
        
        long[] dSpider = new long[n];
        Arrays.fill(dSpider, MAX);
        int s = sc.nextint(), t = sc.nextint(), k = sc.nextint();
        for(int i = 0; i < k; ++i) {
            int u = sc.nextint();
            dSpider[u] = 0;
            pq.offer(new Pair(u, 0));
        }
        
        boolean[] visSpider = new boolean[n];
        while(!pq.isEmpty()) {
            Pair p = pq.poll();
            if(visSpider[p.u]) {
                continue;
            }
            visSpider[p.u] = true;
            for(Edge e : adj[p.u]) {
                if(dSpider[e.to] > dSpider[p.u] + e.w) {
                    dSpider[e.to]= dSpider[p.u] + e.w;
                    pq.offer(new Pair(e.to, dSpider[e.to]));
                }
            }
        }
        
        int lb = 0, hb = 100000;
        long[] d = new long[n];
        boolean[] vis = new boolean[n];
        while(lb < hb) {
            int mb = (lb+hb+1)/2;
            if(dSpider[s] < mb) {
                hb = mb-1;
                continue;
            }
            Arrays.fill(d, MAX);
            Arrays.fill(vis, false);
            pq.clear();
            d[s] = 0;
            pq.offer(new Pair(s, d[s]));
            while(!pq.isEmpty()) {
                Pair p = pq.poll();
                if(vis[p.u]) {
                    continue;
                }
                vis[p.u] = true;
                for(Edge e : adj[p.u]) {
                    if(dSpider[e.to] >= mb && d[e.to] > d[p.u] + e.w) {
                        d[e.to] = d[p.u] + e.w;
                        pq.offer(new Pair(e.to, d[e.to]));
                    }
                }
            }
            
            if(d[t] > T) {
                hb = mb-1;
            }
            else {
                lb = mb;
            }
        }
        
        out.println(lb);
    }
    
    static class Pair implements Comparable<Pair>{
        int u;
        long d;
        public Pair(int u, long d) {
            this.u = u;
            this.d = d;
        }
        public int compareTo(Pair p) {
            return Long.compare(d, p.d);
        }
    }
    
    static class Edge{
        int to, w;
        boolean on;
        public Edge(int to, int w) {
            this.to = to;
            this.w = w;
        }
    }
    
    static BufferedReader br;
    static StringTokenizer st;
    
    static class FastScanner{
        Random r = new Random();
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
