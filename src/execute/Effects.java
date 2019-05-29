package execute;

import javafx.util.Pair;
import model.data.TokenType;

import java.lang.Object;
import java.util.*;

public class Effects extends execute.Object{
    List<Effect> effects;
    static Map<String, Pair<List<TokenType>, TokenType>> methods = new HashMap<>();

    static {
        methods.put("add", new Pair<>(Arrays.asList(TokenType.EFFECT), TokenType.VOID));
        methods.put("getCount", new Pair<>(new ArrayList<>(), TokenType.INT));
        methods.put("getAt", new Pair<>(Arrays.asList(TokenType.INT), TokenType.EFFECT));
    }

    public Effects() {
        effects = new ArrayList<>();
    }

    public void add(Effect effect) {
        effects.add(effect);
    }

    public int getCount() {
        return effects.size();
    }

    public Effect getAt(int index) {
        return effects.get(index);
    }

    public List<Effect> getEffects() {
        return effects;
    }

    @Override
    public boolean hasMethod(String name) {
        if(methods.containsKey(name)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof Effects)) {
            return false;
        }

        Effects effects = (Effects) o;
        if(effects.getCount() != getCount()) {
            return false;
        }

        for(int i = 0; i <getCount(); ++i) {
            if(getAt(i) != effects.getAt(i)) {
                return false;
            }
        }
        return true;
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
        return TokenType.EFFECTS;
    }

    @Override
    public List<TokenType> getArgumentsType(String name) {
        return methods.get(name).getKey();
    }

    @Override
    public Value executeMethod(String name, List<Value> arguments) {
        Value value = new Value();
        switch (name) {
            case "getAt" :
                value.setValue(getAt((int) arguments.get(0).getValue()));
                value.setCalculated(true);
                break;
            case "getCount" :
                value.setValue(getCount());
                value.setCalculated(true);
                break;
            case "add" :
                add((Effect) arguments.get(0).getValue());
                return null;
            default:
                break;
        }
        return value;
    }
}
