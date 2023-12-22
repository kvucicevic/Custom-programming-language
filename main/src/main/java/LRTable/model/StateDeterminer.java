package LRTable.model;

import java.util.*;

public class StateDeterminer {
    private static List<State> states = new ArrayList<>();

    public static boolean isAlreadyDeterminedState(int oldStateNumber, String nextSymbol) {
        for (State state : states) {
            if (state.getPrevStateOrderNumber() == oldStateNumber && state.getGotoSymbol().equals(nextSymbol)) {
                return true;
            }
        }
        return false;
    }

    public static Integer ledIntoAnExistingState(State newState) {
        for (State s : states) {
            if (s.equals(newState)) {
                return s.getOrderNumber();
            }
        }
        return null;
    }

    public static void generateStateZero(List<Tuple> grammar) {
        State stateZero = new State(0, 0, "");

        for (Tuple rule : grammar) {
            String[] rhs = rule.getRhs().split(" ");
            Transition transition = new Transition(rule.getGrammarRuleIndex(), rule.getLhs(), List.of(rhs), 0);
            stateZero.addTransition(transition);
        }
        states.add(stateZero);
    }

    public static void addTransitionsOne(State currState, State newState, String nextSymbol) {
        for (Transition t : currState.getTransitions()) {
            if (t.getPositionDot() <= t.getRhs().size() - 1 && t.getRhs().get(t.getPositionDot()).equals(nextSymbol)) {
                Transition newT = new Transition(t.getGrammarRuleIndex(), t.getLhs(), t.getRhs(), t.getPositionDot() + 1);
                newState.addTransition(newT);

                String newNextSymbol = (t.getPositionDot() < t.getRhs().size() - 1) ? t.getRhs().get(newT.getPositionDot()) : null;
                addTransitionsTwo(newState, newNextSymbol);
            }
        }
    }

    public static void addTransitionsTwo(State newState, String newNextSymbol) {
        if(newState.getAddedNTPhaseTwo().contains(newNextSymbol)){
            return;
        }
        newState.addAddedNTPhaseTwo(newNextSymbol);

        if (newNextSymbol == null || Character.isLowerCase(newNextSymbol.charAt(0))) {
            return;
        }

        for (State state : states) {
            if (state.getOrderNumber() == 0) {
                for (Transition t : state.getTransitions()) {
                    if (t.getLhs().equals(newNextSymbol)) {
                        Transition newT = new Transition(t.getGrammarRuleIndex(), t.getLhs(), t.getRhs(), t.getPositionDot());
                        newState.addTransition(newT);

                        String newNewNextSymbol = (t.getPositionDot() <= t.getRhs().size() - 1) ? t.getRhs().get(t.getPositionDot()) : null;
                        addTransitionsTwo(newState, newNewNextSymbol);
                    }
                }
            }
        }
    }



    public static List<State> generateStates() {

        int index = 0;
        while (index != states.size()) {

            State state = states.get(index);
            if (state.isCopy()) {
                index += 1;
                continue;
            }

            for (Transition transition : state.getTransitions()) {
                if (transition.getPositionDot() <= transition.getRhs().size() - 1) {
                    String nextSymbol = transition.getRhs().get(transition.getPositionDot());

                    if (isAlreadyDeterminedState(state.getOrderNumber(), nextSymbol)) {
                        continue;
                    }

                    State newState = new State(states.size(), state.getOrderNumber(), nextSymbol);

                    if (!newState.alreadyHasTransForNonTerminal(nextSymbol)) {
                        addTransitionsOne(state, newState, nextSymbol);
                    }

                    Integer copyOf = ledIntoAnExistingState(newState);
                    if (copyOf != null) {
                        newState.stateIsACopy(copyOf);
                    }

                    states.add(newState);
                }
            }

            index += 1;
        }

        return states;
    }

    public static List<State> getStates() {
        return states;
    }
}
