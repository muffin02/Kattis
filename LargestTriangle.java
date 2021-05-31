import java.io.*;
import java.util.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class LargestTriangle{
    static int MAX = Integer.MAX_VALUE, MIN = Integer.MIN_VALUE;
    static FastScanner sc = new FastScanner();
    static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
    static int size;
    public static void main(String[] args){
        solve();
        out.close();
    }
    
    static void solve(){
        int n = sc.nextint();
        Point[] points = new Point[n];
        for(int i = 0; i < n; ++i) {
            points[i] = new Point(sc.nextint(), sc.nextint());
        }
        
        List<Point> hull = getConvexHull(points);
        if(hull.size() < 3) {
            out.println(0);
            return;
        }
        
        long max = getArea(hull.get(0), hull.get(1), hull.get(2));
        int sz = hull.size(), b = 1, c = 2;
        for(int a = 0; a < sz; ++a) {
            b = (a+1)%sz; c = (b+1)%sz;
            while(c != a) {
                while(getArea(hull.get(a), hull.get(b), hull.get(c)) < getArea(hull.get(a), hull.get(b), hull.get((c+1)%sz))) {
                    max = max(max, getArea(hull.get(a), hull.get(b), hull.get((c+1)%sz)));
                    c = (c+1)%sz;
                }
                max = max(max, getArea(hull.get(a), hull.get((b+1)%sz), hull.get(c)));
                b = (b+1)%sz;
                if(b == c) {
                    c = (c+1)%sz;
                }
            }
        }
        
        out.println(max/(double)2);
    }
    
    static long getArea(Point p, Point q, Point r) {
        return Math.abs(p.x*q.y + q.x*r.y + r.x*p.y - (q.x*p.y + r.x*q.y + r.y*p.x));
    }
    
    static ArrayList<Point> getConvexHull(Point[] points){
        int n = points.length;
        
        Arrays.sort(points, (a,b)->{
            if(a.y == b.y) {
                return Long.compare(a.x, b.x);
            }
            return Long.compare(a.y, b.y);
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
        
        if(k == n) {
            ArrayList<Point> res = new ArrayList<>();
            res.add(points[0]);
            return res;
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
        long area = x1*y2+x2*y3+x3*y1 - (x2*y1+x3*y2+y3*x1);
        if(area < 0) {
            return -1;
        }
        else if(area > 0) {
            return 1;
        }
        return 0;
    }
    
    static class Point{
        long x, y;
        public Point(long x, long y) {
            this.x = x;
            this.y = y;
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

