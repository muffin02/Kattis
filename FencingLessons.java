import java.util.*;
import java.io.*;
import static java.lang.Math.min;
import static java.lang.Math.max;
public class FencingLessons{
    static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
    static FastScanner sc = new FastScanner();
    static int MAX = Integer.MAX_VALUE;
    static int MIN = Integer.MIN_VALUE;
    static TreeSet<Integer> ts = new TreeSet<Integer>();
    static HashSet<Long> s = new HashSet<Long>();
    public static void main(String[] args) {
        solve();
        out.close();
    }
    
    static void solve(){
        int n = sc.nextint(), m = sc.nextint();
        int[][] trees = new int[n][2];
        
        for(int i = 0; i < n; ++i) {
            trees[i][0] = sc.nextint();
            trees[i][1] = sc.nextint();
        }
        
        //first find the perimeter of the convex hull.
        
        Point[] points = new Point[m];
        for(int j = 0; j < m; ++j) {
            points[j] = new Point(sc.nextint(), sc.nextint());
        }
        
        ArrayList<Point> hull = getConvexHull(points);
        
        double dist = 0;
        for(int i = 0; i+1 < hull.size(); ++i) {
            double dx = hull.get(i).x - hull.get(i+1).x;
            double dy = hull.get(i).y - hull.get(i+1).y;
            dist += Math.sqrt(dx*dx+dy*dy);
        }
        
        Point start = hull.get(0);
        Point end = hull.get(hull.size()-1);
        double dx = start.x - end.x;
        double dy = start.y - end.y;
        dist += Math.sqrt(dx*dx+dy*dy);
        
        int min = (int) Math.ceil(dist);
        
        //now do dp in trees.
        
        int ans = 1000000000;
        int[][] dp = new int[n+1][4010];
        for(int i = 0; i <= n; ++i) {
            for(int j = 0; j < 4010; ++j) {
                dp[i][j] = 1000000000;
            }
        }
        
        dp[0][0] = 0;
        for(int i = 0; i < n; ++i) {
            for(int j = 0; j < 4010; ++j) {
                dp[i+1][j] = min(dp[i+1][j], dp[i][j]);
                int k = min(j+trees[i][0], 4009);
                dp[i+1][k] = min(dp[i+1][k], min(dp[i][k], dp[i][j] + trees[i][1]));
            }
        }
        
        for(int i = min; i < 4010; ++i) {
            ans = min(ans, dp[n][i]);
        }
        
        out.println(ans);
    }
    
    static class Point{
        int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public boolean equals(Point p) {
            return x == p.x && y == p.y;
        }
    }
    
    static ArrayList<Point> getConvexHull(Point[] points){
        int n = points.length;
        
        Arrays.sort(points, (a,b)->{
            if(a.y == b.y) {
                return Integer.compare(a.x, b.x);
            }
            return Integer.compare(a.y, b.y);
        });
        
        Arrays.sort(points, (a,b)->{
            return -getSign(points[0], a, b);
        });
        
        Stack<Point> hull = new Stack<Point>();
        hull.push(points[0]);
        
        int k = 0;
        while(k < n) {
            if(!points[0].equals(points[k])) {
                break;
            }
            ++k;
        }
        
        hull.push(points[k]);
        
        for(int i = k; i < n; ++i) {
            Point prev = hull.pop();
            while(!hull.isEmpty() && getSign(hull.peek(), prev, points[i]) <= 0) {
                prev = hull.pop();
            }
            hull.push(prev);
            hull.push(points[i]);
        }
        
        return new ArrayList<Point>(hull);
    }
    
    static int getSign(Point a, Point b, Point c) {
        long x1 = a.x, y1 = a.y;
        long x2 = b.x, y2 = b.y;
        long x3 = c.x, y3 = c.y;
        long area = x1 * y2 + x2 * y3 + x3 * y1 - (x2 * y1 + x3 * y2 + x1 * y3);
        if(area > 0) {
            return 1;
        }
        else if(area < 0) {
            return -1;
        }
        return 0;
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
