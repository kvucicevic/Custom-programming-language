package scanner;

import lexer.LineFactory;
import lexer.line.Line;

import java.io.*;

public class FileScan {

    public void readFile(String fileName){
        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            String line;
            while((line = br.readLine()) != null){ /// LEVEL!! TO DETERMINE THE NUMBER OF TABS
                if(line.isEmpty())
                    continue;
                System.out.println("Current line is: " + line);
                Line specLine = LineFactory.getInstance().determineLine(line);
                if(specLine.getOptionFlag() == -1) {
                    System.out.println("Error");
                    return;
                }
                System.out.println(specLine);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fr.close();
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
