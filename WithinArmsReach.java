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
        solve();
        out.close();
    }
    
    static void solve(){
        int n = sc.nextint();
        double[] s = new double[n];
        double totLength = 0;
        for(int i = 0; i < n; ++i) {
            s[i] = sc.nextdouble();
            totLength += s[i];
        }
        
        double x = sc.nextdouble(), y = sc.nextdouble();
        double dirX = -x, dirY = -y, curX = 0, curY = 0, d = sqrt(x*x+y*y), dist = sqrt(x*x+y*y);
        if(dirX == 0 && dirY == 0) {
            dirX = 1;
            dirY = 1;
            d = Math.sqrt(2);
        }
        
        List<double[]> v = new ArrayList<>();
        int idx = 0;
        
        while(idx < n && dist + s[idx] <= totLength - s[idx]){
            curX += s[idx] * dirX/ d;
            curY += s[idx] * dirY/ d;
            v.add(new double[] {curX, curY});
            dist += s[idx];
            totLength -= s[idx];
            ++idx;
        }
        
        double origX = curX, origY = curY;
        curX = getBestX(x, y, origX, origY, s[idx], totLength - s[idx]);
        curY = getBestY(x, y, origX, origY, s[idx], totLength - s[idx]);
        
        v.add(new double[] {curX, curY});
        dirX = x - curX;
        dirY = y - curY;
        d = sqrt(dirX * dirX + dirY * dirY);
        for(int i = idx+1; i < n; ++i){
            curX += s[i] * dirX / d;
            curY += s[i] * dirY / d;
            v.add(new double[] {curX, curY});
        }
        
        for(double[] p : v) {
            out.println(p[0] + " " + p[1]);
        }
    }
    
    static double getBestX(double x, double y, double curX, double curY, double r, double rem) {
        //(curX, curY)
        //circle of radius r
        //get as close to rem units away from (x, y).
        double curDist = sqrt((curX-x-r)*(curX-x-r)+(curY-y)*(curY-y));
        double bestX = curX-r;
        for(double i = 0; i < 2 * Math.PI; i += 0.00001) {
            double tempX = r * Math.cos(i) + curX;
            double tempY = r * Math.sin(i) + curY;
            double dist = sqrt((tempX-x)*(tempX-x) + (tempY-y)*(tempY-y));
            if(abs(rem - dist) < abs(rem - curDist)) {
                bestX = tempX;
                curDist = dist;
            }
        }
        
        return bestX;
    }
    
    static double getBestY(double x, double y, double curX, double curY, double r, double rem) {
        //(curX, curY)
        //circle of radius r
        //get as close to rem units away from (x, y).
        double curDist = sqrt((curX-x-r)*(curX-x-r)+(curY-y)*(curY-y));
        double bestY = curY;
        for(double i = 0; i < 2 * Math.PI; i += 0.00001) {
            double tempX = r * Math.cos(i) + curX;
            double tempY = r * Math.sin(i) + curY;
            double dist = sqrt((tempX-x)*(tempX-x) + (tempY-y)*(tempY-y));
            if(abs(rem - dist) < abs(rem - curDist)) {
                bestY = tempY;
                curDist = dist;
            }
        }
        
        return bestY;
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
