package LRTable.model.action;

public class Shift extends Action{

    private final int intoState;

    public Shift(int intoState) {
        super("Shift");
        this.intoState = intoState;
    }

    @Override
    public String toString() {
        return "s" + intoState;
    }
}
