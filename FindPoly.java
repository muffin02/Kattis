import java.io.*;
import java.util.*;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
public class CP{
    static int MAX = Integer.MAX_VALUE, MIN = Integer.MIN_VALUE;
    static FastScanner sc = new FastScanner();
    static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args){
        try {
            solve();
        }
        catch(IOException e) {};
        out.close();
    }
    
    static void solve() throws IOException{
        Point[][] points = new Point[100][100];
        int[][] deg = new int[100][100];
        boolean[][] vis = new boolean[100][100];
        while(true) {
            String s = br.readLine();
            if(s == null) {
                break;
            }
            String[] pairs = s.split("\\W");
            List<Integer> a = new ArrayList<>();
            for(String p : pairs) {
                if(!p.equals("")) {
                    a.add(Integer.parseInt(p));
                }
            }
            for(int i = 0; i < a.size(); i += 4) {
                int x1 = a.get(i), y1 = a.get(i+1);
                if(points[x1][y1] == null) {
                    points[x1][y1] = new Point(x1, y1);
                }
                int x2 = a.get(i+2), y2 = a.get(i+3);
                if(points[x2][y2] == null) {
                    points[x2][y2] = new Point(x2, y2);
                }
                points[x1][y1].add(points[x2][y2]);
                points[x2][y2].add(points[x1][y1]);
                deg[x1][y1]++;
                deg[x2][y2]++;
            }
        }
        
        int tot = 0,  ans = 0;
        for(int i = 0; i < 100; ++i) {
            for(int j = 0; j < 100; ++j) {
                if(points[i][j] != null && !vis[i][j]) {
                    ++tot;
                    int d = 0;
                    boolean ok = true;
                    List<Point> comp = new ArrayList<>();
                    dfs(points[i][j], vis, comp);
                    for(Point p : comp) {
                        d += deg[p.x][p.y];
                        if(deg[p.x][p.y] != 2) {
                            ok = false;
                        }
                    }
                    if(ok && d == comp.size() * 2) {
                        ++ans;
                    }
                }
            }
        }
        out.println(tot + " " + ans);
    }
    
    static void dfs(Point p, boolean[][] vis, List<Point> comp) {
        comp.add(p);
        vis[p.x][p.y] = true;
        for(Point q : p) {
            if(!vis[q.x][q.y]) {
                dfs(q, vis, comp);
            }
        }
    }
    
    static class Point implements Iterable<Point>{
        int x, y;
        Set<Point> adj = new HashSet<>();
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public void add(Point p) {
            adj.add(p);
        }
        public boolean contains(Point p) {
            return adj.contains(p);
        }
        public Iterator<Point> iterator(){
            return adj.iterator();
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
 RSS feed for new problems | Powered by Kattis | Support Kattis on Patreon!
