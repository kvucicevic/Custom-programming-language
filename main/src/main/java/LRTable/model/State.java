package LRTable.model;

import LRTable.model.action.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State {

    private final int orderNumber;
    private final int prevStateOrderNumber;
    private final String gotoSymbol;
    private List<Transition> transitions;
    private boolean isCopy;
    private Integer copiedFromStateNumber;
    private final Map<String, Action> actions;
    private final List<String> addedNTphaseTwo;

    public State(int orderNumber, int prevStateOrderNumber, String gotoSymbol) {
        this.orderNumber = orderNumber;
        this.prevStateOrderNumber = prevStateOrderNumber;
        this.gotoSymbol = gotoSymbol;
        this.transitions = new ArrayList<>();
        this.isCopy = false;
        this.copiedFromStateNumber = null;
        this.actions = new HashMap<>();
        this.addedNTphaseTwo = new ArrayList<>();
    }

    public void addTransition(Transition transition) {
        transitions.add(transition);
    }

    public boolean alreadyHasTransForNonTerminal(String nonTerminal) {
        for (Transition t : transitions) {
            if (t.getLhs().equals(nonTerminal)) {
                return true;
            }
        }
        return false;
    }

    public void stateIsACopy(int copiedFromStateNumber) {
        this.isCopy = true;
        this.copiedFromStateNumber = copiedFromStateNumber;
        this.transitions = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State) {
            State value = (State) obj;
            return gotoSymbol.equals(value.gotoSymbol) && transitions.equals(value.transitions);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stateString = new StringBuilder("State " + orderNumber + " = goto(" + prevStateOrderNumber + ", " + gotoSymbol + ") ");
        if (isCopy) {
            stateString.append("copy of State ").append(copiedFromStateNumber);
        }
        stateString.append("\n");

        StringBuilder transitionsString = new StringBuilder();
        if (!transitions.isEmpty()) {
            for (Transition t : transitions) {
                transitionsString.append("\t").append(t).append("\n");
            }
        }

        StringBuilder actionsString = new StringBuilder();
        if (!actions.isEmpty()) {
            actionsString.append("\n\tActions:\n");
            for (Map.Entry<String, Action> entry : actions.entrySet()) {
                actionsString.append("\tfor ").append(entry.getKey()).append(": ").append(entry.getValue().getName()).append(" ").append(entry.getValue()).append("\n");
            }
        }

        return stateString.toString() + transitionsString.toString() + actionsString.toString();
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getPrevStateOrderNumber() {
        return prevStateOrderNumber;
    }

    public String getGotoSymbol() {
        return gotoSymbol;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public boolean isCopy() {
        return isCopy;
    }

    public Integer getCopiedFromStateNumber() {
        return copiedFromStateNumber;
    }

    public Map<String, Action> getActions() {
        return actions;
    }

    public List<String> getAddedNTphaseTwo() {
        return addedNTphaseTwo;
    }
}
