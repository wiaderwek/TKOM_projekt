package execute;

import javafx.util.Pair;
import model.data.TokenType;

import java.util.*;

public class Effect extends Object {
    private Type type;
    private Map<String, Integer> params;
    private static Map<String, Pair<List<TokenType>, TokenType>> methods = new HashMap<>();

    static {
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

    public void setType(String type) {
        setType(Type.getType(type));
    }

    public Type getType() {
        return type;
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

    @Override
    public Value executeMethod(String name, List<Value> arguments) {
        Value value = new Value();
        switch (name) {
            case "setType" :
                setType((String) arguments.get(0).getValue());
                return null;
            case "setParam" :
                setParam((String) arguments.get(0).getValue(), (int) arguments.get(1).getValue());
                return null;
            case "getParamValue" :
                value.setValue(getParamValue((String) arguments.get(0).getValue()));
                value.setCalculated(true);
                break;
            default:
                break;
        }

        return value;
    }

}
