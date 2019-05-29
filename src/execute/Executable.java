package execute;

import java.lang.Object;
import java.util.Map;

public interface Executable {
    Value execute(final Scope scope, final Map<String, FunctionEx> functions);
}
