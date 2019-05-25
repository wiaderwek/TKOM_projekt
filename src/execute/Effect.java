package execute;

import javafx.util.Pair;
import model.data.TokenType;

import java.util.*;

public class Effect extends Object {
    private Type type;
    private Map<String, Integer> params;
    private static Map<String, Pair<List<TokenType>, TokenType>> methods = new HashMap<>();

    static {
        methods.put("add", new Pair<>(new ArrayList<>(), TokenType.STRING));
        methods.put("setType", new Pair<>(Arrays.asList(TokenType.STRING), TokenType.VOID));
        methods.put("setParam", new Pair<>(Arrays.asList(TokenType.STRING, TokenType.INT), TokenType.VOID));
        methods.put("getParamValue", new Pair<>(Arrays.asList(TokenType.STRING), TokenType.INT));
    }

    public enum Type {
        SHORTEN("Shorten"),
        EXTEND("Extend"),
        RISE("Rise");

        private String typeName;
        private Type(String typeName) {
            this.typeName = typeName;
        }

        public static Type getType(String name) {
            for(Type type : values()) {
                if(type.typeName.equals(name)) {
                    return type;
                }
            }

            return null;
        }
    }

    public void setType(Type type) {
        this.type = type;
        params = new HashMap<>();
        params.put("value", 0);
    }

    public String getType() {
        return type.toString();
    }

    public void setParam(String name, int value) {
        if(params.containsKey(name)) {
            params.compute(name, (k, v)-> value);
        } else {
            throw new IllegalArgumentException("There in no such parameter [" + name + "] in " + getType() + " type");
        }
    }

    public int getParamValue(String name) {
        if(params.containsKey(name)) {
            return params.get(name);
        } else {
            throw new IllegalArgumentException("There in no such parameter [" + name + "] in Rise type");
        }
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if(this == o) {
            return true;
        }

        if(!(o instanceof Effect)) {
            return false;
        }

        Effect effect = (Effect) o;
        if(this.type != effect.type) {
            return false;
        } else if(!this.params.equals(effect.params)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean hasMethod(String name) {
        if(methods.containsKey(name)) {
            return true;
        }
        return false;
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
        return TokenType.EFFECT;
    }

    @Override
    public List<TokenType> getArgumentsType(String name) {
        return methods.get(name).getKey();
    }

}
