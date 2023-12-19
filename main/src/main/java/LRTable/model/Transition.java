package LRTable.model;

import java.util.List;

public class Transition {

    private final String grammarRule;
    private final int grammarRuleIndex;
    private final String lhs;
    private final List<String> rhs;
    private final int positionDot;

    public Transition(int ruleIndex, String lhs, List<String> rhs, int positionDot) {
        this.grammarRule = lhs + " -> " + String.join(" ", rhs);
        this.grammarRuleIndex = ruleIndex;
        this.lhs = lhs;
        this.rhs = rhs;
        this.positionDot = positionDot;
    }

    @Override
    public String toString() {
        StringBuilder rhsWithDot = new StringBuilder();
        for (int i = 0; i < rhs.size(); i++) {
            if (i == positionDot) {
                rhsWithDot.append(". ");
            }
            rhsWithDot.append(rhs.get(i)).append(" ");
        }
        if (positionDot == rhs.size()) {
            rhsWithDot.append(". ");
        }
        return lhs + " -> " + rhsWithDot.toString().trim();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Transition) {
            Transition value = (Transition) obj;
            return lhs.equals(value.lhs) && rhs.equals(value.rhs) && positionDot == value.positionDot;
        }
        return false;
    }

    public String getLhs() {
        return lhs;
    }

    public String getGrammarRule() {
        return grammarRule;
    }

    public int getGrammarRuleIndex() {
        return grammarRuleIndex;
    }

    public List<String> getRhs() {
        return rhs;
    }

    public int getPositionDot() {
        return positionDot;
    }
}
