import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class SAP {
    private Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null || G.V() == 0) {
            throw new IllegalArgumentException();
        }
        this.G = new Digraph(G);
    }
    private int length(BreadthFirstDirectedPaths bfsA, BreadthFirstDirectedPaths bfsB) {
        int minDist = G.V();
        for (int c = 0; c < G.V(); ++c) {
            if (bfsA.hasPathTo(c) && bfsB.hasPathTo(c)) {
                minDist = Math.min(minDist, bfsA.distTo(c) + bfsB.distTo(c));
            }
        }
        return (minDist == G.V() ? -1 : minDist);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return length(new BreadthFirstDirectedPaths(G, v), new BreadthFirstDirectedPaths(G, w));
    }

    private int ancestor(BreadthFirstDirectedPaths bfsA, BreadthFirstDirectedPaths bfsB) {
        int minDist = length(bfsA, bfsB);
        for (int c = 0; c < G.V(); ++c) {
            if (bfsA.hasPathTo(c) && bfsB.hasPathTo(c) && bfsA.distTo(c) + bfsB.distTo(c) == minDist) {
                return c;
            }
        }
        return -1;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return ancestor(new BreadthFirstDirectedPaths(G, v), new BreadthFirstDirectedPaths(G, w));
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v != null && w != null && (!v.iterator().hasNext() || !w.iterator().hasNext())) {
            return -1;
        }
        return length(new BreadthFirstDirectedPaths(G, v), new BreadthFirstDirectedPaths(G, w));
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v != null && w != null && (!v.iterator().hasNext() || !w.iterator().hasNext())) {
            return -1;
        }
        return ancestor(new BreadthFirstDirectedPaths(G, v), new BreadthFirstDirectedPaths(G, w));
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("in.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        List<Integer> v = new ArrayList<>(), w = new ArrayList<>();
        v.add(3);
        StdOut.printf("length = %d\n", sap.length(v, w));
    }
}


