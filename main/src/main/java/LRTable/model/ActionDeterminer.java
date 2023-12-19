package LRTable.model;

import LRTable.model.action.Accept;
import LRTable.model.action.Reduce;
import LRTable.model.action.Shift;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ActionDeterminer {

    public static ActionDeterminer instance = null;

    private ActionDeterminer() {
    }

    public static ActionDeterminer getInstance() {
        if(instance == null)
            instance = new ActionDeterminer();
        return instance;
    }

    public static State findStateByOrderNumber(List<State> states, int prevStateNo) {
        for (State s : states) {
            if (s.getOrderNumber() == prevStateNo) {
                return s;
            }
        }
        return null;
    }

    public static void determineShiftActions(List<State> states) {
        for (int i = 1; i < states.size(); i++) {
            State s = states.get(i);
            int prevStateNo = s.getPrevStateOrderNumber();
            String symbol = s.getGotoSymbol();

            int currStateNo = s.getOrderNumber();
            if (s.isCopy()) {
                currStateNo = s.getCopiedFromStateNumber();
            }

            State currState = findStateByOrderNumber(states, currStateNo);
            State prevState = findStateByOrderNumber(states, prevStateNo);
            if (prevState != null) {
                prevState.getActions().put(symbol, new Shift(currState.getOrderNumber()));
            }
        }
    }

    public static void determineAcceptAction(List<State> states) {
        for (State s : states) {
            for (Transition t : s.getTransitions()) {
                if (t.getLhs().equals("START") && t.getPositionDot() == t.getRhs().size()) {
                    s.getActions().put("#", new Accept());
                    return;
                }
            }
        }
    }

    public static void determineReduceActions(List<State> states, List<Tuple> grammar, Map<String, Set<String>> followSets) {
        for (State s : states) {
            for (Transition t : s.getTransitions()) {
                if (t.getPositionDot() == t.getRhs().size()) {
                    Tuple rule = grammar.get(t.getGrammarRuleIndex());
                    String symbol = t.getLhs();
                    for (String followSymbol : followSets.get(symbol)) {
                        s.getActions().put(followSymbol, new Reduce(grammar.indexOf(rule), rule.getLhs(), rule.getRhs()));
                    }
                }
            }
        }
    }

    public static void determineActions(List<State> states, List<Tuple> grammar, Map<String, Set<String>> followSets) {
        determineShiftActions(states);
        determineReduceActions(states, grammar, followSets);
        determineAcceptAction(states);
    }
}

