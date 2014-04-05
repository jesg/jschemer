package jschemer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SchemerRuntime {
    private Environment globalEnv;

    public SchemerRuntime(Environment globalEnv) {
        this.globalEnv = globalEnv;
    }

    public Object eval(Object exp) {
        return eval(exp, globalEnv);
    }

    public Object eval(Object expression, final Environment env) {
        if (String.class.isInstance(expression)) { // variable reference
            return env.find(expression).get(expression);
        } else if (!List.class.isInstance(expression)) { // constant literal
            return expression;
        } else {
            final List<Object> list = (List<Object>) expression;
            if (list.isEmpty())
                return list;
            Object first = list.get(0);
            if (List.class.isInstance(first)) {
                Object value = eval(first);
                list.set(0, value);
                return eval(list);
            }

            if ("quote".equals(first)) {
                return list.get(1);
            } else if ("if".equals(first)) {
                Object second = list.get(1);
                return eval(
                        (Boolean) eval(second, env) ? list.get(2) : list.get(3),
                        env);
            } else if ("set!".equals(first)) {
                Object key = list.get(1);
                Object value = list.get(2);
                env.find(key).put(key, eval(value, env));
            } else if ("define".equals(first)) {
                Object second = list.get(1);
                env.set(second, eval(list.get(2), env));
            } else if ("lambda".equals(first)) { // (lambda (arg1 arg2...) expr1 expr2...)
                final Object second = list.get(1);
                return new Closure() {
                    public Object call(final List<Object> args) {
                        Object result = Collections.emptyList();
                        final Environment localEnv = new Environment(
                                (List<Object>) second, args, env);

                        for (Object expr : list.subList(2, list.size())) {
                            result = eval(expr, localEnv);
                        }
                        return result;
                    }
                };
            } else {
                LinkedList<Object> exprs = new LinkedList<Object>();
                for (Object exp : (List<Object>) expression) {
                    exprs.add(eval(exp, env));
                }
                Closure closure = (Closure) exprs.pop();
                return closure.call(exprs);
            }
            return Collections.emptyList();
        }
    }
}
