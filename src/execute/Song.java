package execute;

import javafx.util.Pair;
import model.data.TokenType;

import java.util.*;

public class Song extends Object {
    private String songName;
    private int tempo;          //[BPM] 1-200
    private int length;         //[s] 1-300
    protected static Map<String, Pair<List<TokenType>, TokenType>> methods = new HashMap<>();

    static {
        methods.put("load", new Pair<>(Arrays.asList(TokenType.STRING), TokenType.VOID));
        methods.put("save", new Pair<>(Arrays.asList(TokenType.STRING), TokenType.VOID));
        methods.put("getTempo", new Pair<>(new ArrayList<>(), TokenType.INT));
        methods.put("getLength", new Pair<>(new ArrayList<>(), TokenType.INT));
        methods.put("modify", new Pair<>(Arrays.asList(TokenType.EFFECTS), TokenType.SONG));
    }

    public Song() {
        tempo = 0;
        length = 0;
        songName = "";
    }

    void load(String songName) {
        Random random = new Random();
        this.songName = songName;
        this.tempo = random.nextInt(200) + 1;
        this.length = random.nextInt(300) + 1;
    }

    int getTempo() {
        return tempo;
    }

    int getLength() {
        return getLength();
    }

    @Override
    public boolean hasMethod(String name) {
        if(methods.containsKey(name)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if(this == o) {
            return true;
        }

        if(!(o instanceof Song)) {
            return false;
        }

        Song song = (Song) o;
        if(this.tempo != song.tempo) {
            return false;
        } else if(this.songName != song.songName) {
            return false;
        } else if(this.length != song.getLength()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public TokenType getRetType(String name) {
        if(hasMethod(name)) {
            return  methods.get(name).getValue();
        }
        return null;
    }

    @Override
    public TokenType getObjType() {
        return TokenType.SONG;
    }

    @Override
    public List<TokenType> getArgumentsType(String name) {
        return methods.get(name).getKey();
    }
}
