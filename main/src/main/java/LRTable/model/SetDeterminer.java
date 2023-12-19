package LRTable.model;

import java.util.*;

public class SetDeterminer {

    public static Map<String, Set<String>> generateFollowSets(List<Tuple> G, List<String> T, List<String> nT) {
        Map<String, Set<String>> followSets = new HashMap<>();
        String epsilon = "#";
        boolean changed = true;

        for (String symbol : nT) {
            followSets.put(symbol, new HashSet<>());
        }

        while (changed) {
            changed = false;

            for (Tuple rule : G) {
                int ruleIndex = rule.getGrammarRuleIndex(); // Assuming the 0th element is the rule serial number
                String lhs = rule.getLhs();    // Assuming the 1st element is the left side
                String[] rhs = rule.getRhs().split(" ");  // Assuming the 2nd element is the right side

                for (int i = 0; i < rhs.length; i++) {
                    String symbol = rhs[i];

                    if (nT.contains(symbol)) {
                        String nextSymbol = null;

                        for (int j = i + 1; j < rhs.length; j++) {
                            nextSymbol = rhs[j];

                            if (nT.contains(nextSymbol)) {
                                if (!followSets.get(nextSymbol).contains(epsilon) && !nextSymbol.equals(lhs)) {
                                    followSets.get(nextSymbol).add(epsilon);
                                    changed = true;
                                }
                                break;
                            } else if (T.contains(nextSymbol)) {
                                if (!followSets.get(symbol).contains(nextSymbol)) {
                                    followSets.get(symbol).add(nextSymbol);
                                    changed = true;
                                }
                                break;
                            }
                        }

                        if (nextSymbol == null || i == rhs.length - 1) {
                            if (!lhs.equals(symbol)) {
                                for (String term : followSets.get(lhs)) {
                                    if (!followSets.get(symbol).contains(term)) {
                                        followSets.get(symbol).add(term);
                                        changed = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (!followSets.get(nT.iterator().next()).contains("#")) {
                followSets.get(nT.iterator().next()).add("#");
                changed = true;
            }
        }

        return followSets;
    }

}

