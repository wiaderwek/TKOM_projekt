import model.data.TextPos;

import java.io.*;

public class Source {
    private String fileName;                        //nazwa pliku
    private Reader reader;                          //buffor pliku
    private TextPos currentPos;                     //obecna pozycja w pliku
    private char currentChar;                       //obecny znak
    private int etotal;                             //liczniki wszytskich bledow
    private boolean isAtEnd;                        //czy koniec pliku

    public Source(String fileName) {
        this.fileName = fileName;
        currentPos = new TextPos(1, 1);
        etotal = 0;
        isAtEnd = false;

        File file = new File(fileName);
        openFile(file);
    }

    protected void finalize() throws Throwable {
        System.out.println("Koniec analizy! Wykrytych błędów: " + etotal);
        reader.close();
        super.finalize();
    }

    private void openFile(File file) {
        try{
            InputStream inputStream = new FileInputStream(file);
            Reader inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            currentChar = (char) inputStream.read();
        } catch (FileNotFoundException e) {
            System.out.println("Fatal error! Cannot find the file!");
        } catch (IOException e) {
            System.out.println("Fatal error! Cannot load text from file!");
        }
    }

    public void error(TextPos errorPos) {
        ++etotal;
        System.out.println("Line: " + (errorPos.getLineNumber() + 1) + ", column: " + (errorPos.getCharNumber() + 1));
    }

    public void error(TextPos errorPos, String messaage) {
        error(errorPos);
        System.out.println(messaage);
    }

    public void nextChar(){
        try {
            int ch;
            if ((ch = reader.read()) == -1) {
                throw new IOException();
            }

            currentChar = (char) ch;
            currentPos.nextChar();
        } catch (IOException e) {
            currentChar = '\0';
            isAtEnd = true;
        }
    }

    public char getCurrentChar() {
        return currentChar;
    }

    public void nextLine() {
        nextChar();
        currentPos.nextLine();
    }

    public TextPos getCurrentPos() {
        return currentPos;
    }

    public boolean isAtEnd() {
        return isAtEnd;
    }
}
