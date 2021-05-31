#include <iostream>
#include <iomanip>
#include <cstdio>
#include <cstring>
#include <vector>
#include <algorithm>
#include <iterator>
#include <set>
#include <map>
#include <unordered_map>
#include <unordered_set>
#include <queue>
#include <stack>
#include <deque>
#include <tgmath.h>
using namespace std;
#define ll long long
#define ull unsigned long long
#define f first
#define s second
#define endl "\n"
struct Point{
    int x ,y;
    void construct(int xx, int yy){
        x = xx;
        y = yy;
    }
    bool equals(Point p){
        return x == p.x && y == p.y;
    }
};

Point minY;

int getSign(Point a, Point b, Point c){
    ll x1 = a.x, y1 = a.y;
    ll x2 = b.x, y2 = b.y;
    ll x3 = c.x, y3 = c.y;
    ll area = x1 * y2 + x2 * y3 + x3 * y1 - (x2 * y1 + x3 * y2 + x1 * y3);
    if(area > 0) {
        return 1;
    }
    else if(area < 0) {
        return -1;
    }
    return 0;
}

bool comp1(const Point p1, const Point p2){
    if(p1.y == p2.y){
        return p1.x < p2.x; 
    }
    return p1.y < p2.y;
}

int sqrDist(Point a, Point b)  {
    int dx = a.x - b.x, dy = a.y - b.y;
    return dx * dx + dy * dy;
}

int comp2(Point &p1, Point &p2){
    int order = -getSign(minY, p1, p2);
    if(order == 0){
        return sqrDist(minY, p1) < sqrDist(minY, p2);
    }
    return (order == -1);
}

vector<vector<int>> getHull(Point points[], int n){
    sort(points, points + n, comp1);
    minY = points[0];

    sort(points + 1, points + n, comp2);
    stack<Point> hull;
    hull.push(points[0]);

    int k = 0;
    while(k < n){
        if(!points[0].equals(points[k])){
            break;
        }
        ++k;
    }

    if(k == n){
        return {{points[0].x, points[0].y}};
    }

    hull.push(points[k]);
    for(int i = k; i < n; ++i){
        Point prev = hull.top(); hull.pop();
        while(hull.size() && getSign(hull.top(), prev, points[i]) <= 0){
            prev = hull.top(); hull.pop();
        }
        hull.push(prev);
        hull.push(points[i]);
    }

    vector<vector<int>> res;
    while(hull.size()){
        res.push_back({hull.top().x, hull.top().y});
        hull.pop();
    }

    reverse(res.begin(), res.end());
    return res;
}

int main(){
    ios::sync_with_stdio(0);
    cin.tie(0);

    int n, x, y;
    while(scanf("%d", &n) && n){
        Point points[n];
        for(int i = 0; i < n; ++i){
            scanf("%d%d", &x, &y);
            points[i].construct(x, y);
        }
        vector<vector<int>> hull = getHull(points, n);
        printf("%d\n", hull.size());
        for(auto& p : hull){
            printf("%d %d \n", p[0], p[1]);
        }
    }
    
    return 0;
}
