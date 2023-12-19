package LRTable;

import LRTable.model.State;
import LRTable.model.action.Action;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TableExporter {

    public static List<State> filterForUniqueStates(List<State> states) {
        return states.stream().filter(s -> !s.isCopy()).collect(Collectors.toList());
    }

    public static void exportLRTable(List<String> T, List<String> nT, List<State> states) {
        nT.remove("START");
        T.remove("$");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("LR_Table");

        // Columns - symbols
        String[] symbols = new String[T.size() + nT.size() + 2];
        symbols[0] = "";
        int index = 1;
        for (String t : T) {
            symbols[index++] = t;
        }
        for (String nt : nT) {
            symbols[index++] = nt;
        }
        symbols[index] = "#";

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < symbols.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(symbols[i]);
        }

        List<State> uniqueStates = filterForUniqueStates(states);
        Map<Integer, Integer> statesRowNo = new HashMap<>();

        // Rows - states
        int rowNumber = 1;
        for (State state : uniqueStates) {
            int stateNo = state.getOrderNumber();
            Row row = sheet.createRow(rowNumber);
            row.createCell(0).setCellValue("state " + stateNo);
            statesRowNo.put(stateNo, rowNumber);
            rowNumber++;
        }

        // Populate the table
        for (State state : uniqueStates) {
            int rowIndex = statesRowNo.get(state.getOrderNumber());

            for (Map.Entry<String, Action> entry : state.getActions().entrySet()) {
                int columnIndex = indexOf(symbols, entry.getKey());
                if (columnIndex != -1) {
                    Row row = sheet.getRow(rowIndex);
                    if (row == null) {
                        row = sheet.createRow(rowIndex);
                    }
                    Cell cell = row.createCell(columnIndex);
                    cell.setCellValue(entry.getValue().toString());
                }
            }
        }

        // Save to a file
        try (FileOutputStream fileOut = new FileOutputStream("LR_Table.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static int indexOf(String[] arr, String value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }
}

