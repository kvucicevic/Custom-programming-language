package scanner;

import statements.CodeLine;

import java.io.*;

public class FileScan {

    public void readFile(String fileName){
        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            String line;
            String[] lines = new String[100];
            int i = 0;
            while((line = br.readLine()) != null){ /// LEVEL!! TO DETERMINE THE NUMBER OF TABS
                CodeLine currentLine = LineFactory.getInstance().generateLine(line);
                lines[i++] = currentLine.getJavaText();
                System.out.println(lines[i]);
            }

            writeJavaFile("javaCompFile.java", lines);

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

    void writeJavaFile(String fileName, String[] lines){
        File file = new File(fileName);

        FileWriter fw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter(file);
            pw = new PrintWriter(fw);

            pw.append("public class javaCompFile {\n\n\t");

            for(String line : lines) {
                if(line == null)
                    continue;
                System.out.println(line);
                pw.append(line);
            }

            pw.append("\n}");


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pw.flush();
            pw.close();
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
