import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WordNet {

    private String[] synset;
    private Map<String, ArrayList<Integer>> idOfNouns = new HashMap<String, ArrayList<Integer>>();
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }

        In in = new In(synsets);
        String[] arr = in.readAllLines();
        synset = new String[arr.length];

        Digraph G = new Digraph(arr.length);

        for (int id = 0; id < arr.length; ++id) {
            String s = arr[id];
            int i = Integer.parseInt(s.substring(0, s.indexOf(',')));
            s = s.substring(s.indexOf(',') + 1);
            s = s.substring(0, s.indexOf(','));

            synset[i] = s;

            String[] temp = s.split(" ");

            for (String noun : temp) {
                if (!idOfNouns.containsKey(noun)) {
                    idOfNouns.put(noun, new ArrayList<>());
                }

                idOfNouns.get(noun).add(i);
            }
        }

        in = new In(hypernyms);

        int cur = 0;

        while (!in.isEmpty()) {
            String[] id = in.readLine().split(",");
            for (int i = 1; i < id.length; ++i) {
                G.addEdge(Integer.parseInt(id[0]), Integer.parseInt(id[i]));
            }
            if (id.length > 1) {
                cur++;
            }
        }

        if (G.V() - cur != 1) {
            throw new IllegalArgumentException();
        }

        sap = new SAP(G);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return idOfNouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return idOfNouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        return sap.length(idOfNouns.get(nounA), idOfNouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        return synset[sap.ancestor(idOfNouns.get(nounA), idOfNouns.get(nounB))];
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");

    }
}