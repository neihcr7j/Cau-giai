import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns) {
        String worstNoun = "";
        int worstDist = -1;

        for (String noun : nouns) {
            int currentDist = 0;
            for (String other : nouns) {
                currentDist += wordNet.distance(noun, other);
            }

            if (currentDist > worstDist) {
                worstDist = currentDist;
                worstNoun = noun;
            }
        }

        return worstNoun;
    }
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
