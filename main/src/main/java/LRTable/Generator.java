package LRTable;

import LRTable.model.ActionDeterminer;
import LRTable.model.SetDeterminer;
import LRTable.model.State;
import LRTable.model.Tuple;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static LRTable.model.StateDeterminer.*;

public class Generator {
    public static void main(String[] args) {
        try {
            List<String> fileData = Files.readAllLines(Paths.get("main/src/main/java/LRTable/gramatika.txt"));

            // Grammar - list of rules (tuple: index, lhs, rhs)
            Tuple grammarData = importGrammar(fileData);

            List<Tuple> G = grammarData.getG();
            List<String> T = grammarData.getT();
            List<String> nT = grammarData.getnT();

            generateStateZero(G);
            List<State> states = generateStates();

            // follow set
            Map<String, Set<String>> followSets = SetDeterminer.generateFollowSets(G, T, nT);

            // actions
            ActionDeterminer.determineActions(states, G, followSets);

            // export table to excel
            TableExporter.exportLRTable(T, nT, states);
//            for(Tuple t : G){
//                System.out.println("Terminal:" + t);
//            }
//            for(String key : followSets.keySet()){ // dobar
//                System.out.println("Follow: " + key);
//            }
//            for(State s : getStates()){
//                System.out.println("Stejt: " + s);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Tuple importGrammar(List<String> fileData) {
        List<Tuple> G = new ArrayList<>();
        List<String> T = new ArrayList<>();
        List<String> nT = new ArrayList<>();

        for (int i = 0; i < fileData.size(); i++) {
            String line = fileData.get(i).trim().replace("\n", "");
            String[] sides = line.split(" -> ");

            String lhs = sides[0].trim();
            String rhs = sides[1].trim();

            if (!nT.contains(lhs)) {
                nT.add(lhs);
            }

            String[] rhsSymbols = rhs.split(" ");
            for (String symbol : rhsSymbols) {
                if (Character.isUpperCase(symbol.charAt(0))) {
                    if (!nT.contains(symbol)) {
                        nT.add(symbol);
                    }
                } else {
                    if (!T.contains(symbol)) {
                        T.add(symbol);
                    }
                }
            }

            Tuple rule = new Tuple(i, lhs, rhs);
            if (!G.contains(rule)) {
                G.add(rule);
            }
        }

        T.add("$");

        return new Tuple(G, T, nT);
    }
}

