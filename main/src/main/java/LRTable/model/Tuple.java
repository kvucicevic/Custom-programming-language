package LRTable.model;

import java.util.ArrayList;
import java.util.List;

public class Tuple {

    private int grammarRuleIndex;
    private String lhs;
    private String rhs;

    private List<Tuple> g = new ArrayList<>();
    private List<String> t = new ArrayList<>();
    private List<String> nT = new ArrayList<>();

    public Tuple(int grammarRuleIndex, String lhs, String rhs) {
        this.grammarRuleIndex = grammarRuleIndex;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Tuple(List<Tuple> g, List<String> t, List<String> nT) {
        this.g = g;
        this.t = t;
        this.nT = nT;
    }

    public int getGrammarRuleIndex() {
        return grammarRuleIndex;
    }

    public String getLhs() {
        return lhs;
    }

    public String getRhs() {
        return rhs;
    }

    public List<Tuple> getG() {
        return g;
    }

    public List<String> getT() {
        return t;
    }

    public List<String> getnT() {
        return nT;
    }
}
