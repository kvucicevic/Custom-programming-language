package LRTable.model.action;

public class Reduce extends Action{

    private final int ruleIndex;
    private final String rule;
    private final String lhs;
    private final String rhs;

    public Reduce(int ruleIndex, String lhs, String rhs) {
        super("Reduce");
        this.ruleIndex = ruleIndex;
        this.lhs = lhs;
        this.rhs = rhs;
        this.rule = lhs + " -> " + rhs;
    }

    @Override
    public String toString() {
        return "r" + ruleIndex;
    }
}
