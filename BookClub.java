import java.io.*;
import java.util.*;
import static java.lang.Math.max;
import static java.lang.Math.min;
public class BookClub{
    static int MAX = Integer.MAX_VALUE, MIN = Integer.MIN_VALUE;
    static FastScanner sc = new FastScanner();
    static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args){
        solve();
        out.close();
    }
    
    static void solve(){
        int n = sc.nextint(), m = sc.nextint();
        Node[] nodes1 = new Node[n];
        Node[] nodes2 = new Node[n];
        Node source = new Node(0), sink = new Node(1);
        int idx = 2;
        for(int i = 0; i < n; ++i) {
            nodes1[i] = new Node(idx++);
            nodes2[i] = new Node(idx++);
            Edge e1 = new Edge(source, nodes1[i], 1);
            Edge e2 = new Edge(nodes1[i], source, 0);
            Edge e3 = new Edge(nodes2[i], sink, 1);
            Edge e4 = new Edge(sink, nodes2[i], 0);
            e1.residual = e2;
            e2.residual = e1;
            e3.residual = e4;
            e4.residual = e3;
            source.add(e1);
            nodes1[i].add(e2);
            nodes2[i].add(e3);
            sink.add(e4);
        }
        
        for(int i = 0; i < m; ++i) {
            int n1 = sc.nextint(), n2 = sc.nextint();
            Edge e1 = new Edge(nodes1[n1], nodes2[n2], 1);
            Edge e2 = new Edge(nodes2[n2], nodes1[n1], 0);
            e1.residual = e2;
            e2.residual = e1;
            nodes1[n1].add(e1);
            nodes2[n2].add(e2);
        }
        
        int[] level = new int[idx], next = new int[idx];
        int maxFlow = 0;
        while(bfs(source, sink, level)) {
            Arrays.fill(next, 0);
            for(int flow = dfs(source, sink, next, level, 1); flow != 0; flow = dfs(source, sink, next, level, 1)) {
                maxFlow += flow;
            }
        }
        out.println(maxFlow==n?"YES":"NO");
    }
    
    static boolean bfs(Node source, Node sink, int[] level) {
        Arrays.fill(level, -1);
        Queue<Node> q = new ArrayDeque<>();
        q.offer(source);
        level[source.id] = 0;
        while(!q.isEmpty()) {
            Node u = q.poll();
            for(Edge e : u.adj) {
                if(e.remainingCapacity() > 0 && level[e.to.id] == -1) {
                    level[e.to.id]= level[u.id] + 1;
                    q.offer(e.to);
                }
            }
        }
        
        return level[sink.id] != -1; 
    }
    
    static int dfs(Node at, Node sink, int[] next, int[] level, int flow) {
        if(at.equals(sink)) {
            return flow;
        }
        int numEdges = at.adj.size();
        for(;next[at.id] < numEdges; next[at.id]++) {
            Edge e = at.adj.get(next[at.id]);
            int cap = e.remainingCapacity();
            if(cap > 0 && level[e.to.id] == level[at.id] + 1) {
                int bottleNeck = dfs(e.to, sink, next, level, Math.min(flow, cap));
                if(bottleNeck > 0) {
                    e.augment(bottleNeck);
                    return bottleNeck;
                }
            }
        }
        
        return 0;
    }
    
    static class Edge{
        Node from, to;
        Edge residual;
        int flow, capacity;
        public Edge(Node from, Node to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
        }
        
        public void augment(int bottleNeck) {
            flow += bottleNeck;
            residual.flow -= bottleNeck;
        }
        
        public int remainingCapacity() {
            return capacity - flow;
        }
    }
    
    static class Node{
        int id;
        ArrayList<Edge> adj = new ArrayList<>();
        public Node(int id) {
            this.id = id;
        }
        
        void add(Edge e) {
            adj.add(e);
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
