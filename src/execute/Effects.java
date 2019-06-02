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
        methods.put("delete", new Pair<>(Arrays.asList(TokenType.INT), TokenType.VOID));
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

    public Effect getAt(int index) throws Exception {
        if(index < effects.size()){
            return effects.get(index);
        } else {
            throw new Exception("Index out of bound: " + index + ", list size: " + effects.size());
        }
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void delete(int index) throws Exception {
        if(index < effects.size()){
            effects.remove(index);
        } else {
            throw new Exception("Index out of bound: " + index + ", list size: " + effects.size());
        }
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
            try {
                if(getAt(i) != effects.getAt(i)) {
                    return false;
                }
            } catch (Exception e) {
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
    public Value executeMethod(String name, List<Value> arguments, Scope scope, String calee) {
        Value value = new Value();
        Value newObj = new Value();
        switch (name) {
            case "getAt" :
                try {
                    value.setValue(getAt((int) arguments.get(0).getValue()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                value.setCalculated(true);
                break;
            case "getCount" :
                value.setValue(getCount());
                value.setCalculated(true);
                break;
            case "add" :
                add((Effect) arguments.get(0).getValue());
                value = null;
                break;
            case "delete" :
                try {
                    delete(((int) arguments.get(0).getValue()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                value = null;
                break;
            default:
                break;
        }
        newObj.setValue(this);
        newObj.setCalculated(true);
        scope.setVariable(calee, newObj);
        return value;
    }
}
