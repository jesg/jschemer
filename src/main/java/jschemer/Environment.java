package jschemer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Environment {
    private Environment outer;
    private Map<Object, Object> inner;

    public Environment(List<Object> params, List<Object> args) {
        inner = new HashMap<Object, Object>();
        for (int i = 0; i < params.size(); i++) {
            inner.put(params.get(i), args.get(i));
        }
    }

    public Environment(List<Object> params, List<Object> args, Environment outer) {
        this(params, args);
        this.outer = outer;
    }

    public Environment() {
        this(Collections.emptyList(), Collections.emptyList());
    }

    public Map<Object, Object> find(Object var) {
        return inner.containsKey(var) ? inner : outer.find(var);
    }

    public void set(Object key, Object value) {
        inner.put(key, value);
    }

}
