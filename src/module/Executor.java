package module;

import execute.FunctionEx;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Executor {
    public void execute(final List<FunctionEx> functions) {
        final Map<String, FunctionEx> definedFunctions = new HashMap<>();
        functions.forEach(function -> definedFunctions.put(function.getName(), function));
        final FunctionEx mainFuntion = definedFunctions.get("main");
        mainFuntion.execute(null, definedFunctions, new LinkedList<>());
    }
}
