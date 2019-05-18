package model.data;

public class TextPos {
    //numerowane do 1
    private int lineNumber;
    private int charNumber;

    public TextPos() {
        this.lineNumber = 1;
        this.charNumber = 1;
    }

    public TextPos(int lineNumber, int charNumber) {
        this.lineNumber = lineNumber;
        this.charNumber = charNumber;
    }

    public TextPos(TextPos textPos) {
        this.lineNumber = textPos.getLineNumber();
        this.charNumber = textPos.getCharNumber();
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getCharNumber() {
        return charNumber;
    }

    public void nextChar() {
        ++charNumber;
    }

    public void nextLine() {
        charNumber = 1;
        ++lineNumber;
    }

    public boolean equals(TextPos textPos) {
        if(this.charNumber != textPos.getCharNumber()) {
            System.out.println("char: " + charNumber + " != " + textPos.getCharNumber());
            return false;
        } else if(this.lineNumber != textPos.getLineNumber()) {
            System.out.println("line: " + lineNumber + " != " + textPos.getLineNumber());
            return false;
        } else {
            return true;
        }
    }

    public String toString() {
        return  new String("Line: " + lineNumber + ", Column: " + charNumber);
    }
}