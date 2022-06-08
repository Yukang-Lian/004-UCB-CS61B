package lab11.graphs;


import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private int targetX;
    private int targetY;


    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        this.targetX = targetX;
        this.targetY = targetY;
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    private class Node {
        private int v;
        private int priority;

        public Node(int v) {
            this.v = v;
            this.priority = h(v);
        }
    }

    private class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.priority - o2.priority;
        }
    }

    /**
     * Estimate of the distance from v to the target.
     */
    private int h(int v) {
        int sourceX = maze.toX(v);
        int sourceY = maze.toY(v);
        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }

    /**
     * Finds vertex estimated to be closest to target.
     */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /**
     * Performs an A star search from vertex s.
     */
    private void astar(int s) {
        // TODO
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new NodeComparator());
        Node curNode = new Node(s);
        pq.add(curNode);
        while (!pq.isEmpty()) {
            curNode = pq.poll();
            for (int w : maze.adj(curNode.v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    distTo[w] = distTo[curNode.v] + 1;
                    edgeTo[w] = curNode.v;
                    announce();
                    if (w == t) {
                        return;
                    }
                    pq.add(new Node(w));
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

