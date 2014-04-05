package jschemer;

import java.util.List;

public abstract class Functions {

    public static Closure eq() {
        return new Closure() {
            public Object call(List<Object> args) {
                return args.get(0).equals(args.get(1));
            }
        };
    }

    public static Closure cons() {
        return new Closure() {

            public Object call(List<Object> args) {
                List<Object> list = (List<Object>) args.get(1);
                list.add(0, args.get(0));
                return list;
            }
        };
    }

    public static Closure car() {
        return new Closure() {

            public Object call(List<Object> args) {
                List<Object> list = (List<Object>) args.get(0);
                return ((List<Object>) args.get(0)).get(0);
            }
        };
    }

    public static Closure cdr() {
        return new Closure() {

            public Object call(List<Object> args) {
                List<Object> list = (List<Object>) args.get(0);
                if (list.isEmpty())
                    return list;
                else
                    return list.subList(1, list.size());
            }
        };
    }

    public static Closure plus() {
        return new Closure() {
            public Object call(List<Object> args) {
                Double result = (Double) args.get(0);
                for (Object arg : args.subList(1, args.size())) {
                    result += (Double) arg;
                }
                return result;
            }
        };
    }

    public static Closure minus() {
        return new Closure() {
            public Object call(List<Object> args) {
                Double result = (Double) args.get(0);
                for (Object arg : args.subList(1, args.size())) {
                    result -= (Double) arg;
                }
                return result;
            }
        };
    }

    public static Closure mult() {
        return new Closure() {
            public Object call(List<Object> args) {
                Double result = (Double) args.get(0);
                for (Object arg : args.subList(1, args.size())) {
                    result *= (Double) arg;
                }
                return result;
            }
        };
    }

    public static Closure div() {
        return new Closure() {
            public Object call(List<Object> args) {
                return ((Double) args.get(0)) / ((Double) args.get(1));
            }
        };
    }
}
