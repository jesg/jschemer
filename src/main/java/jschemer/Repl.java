package jschemer;

import java.util.Scanner;

import jschemer.lexer.SchemeLexer;
import jschemer.lexer.SchemeParser;

public class Repl {

    public static void main(String[] args) {
        Environment globalEnv = new Environment();
        addGlobals(globalEnv);
        SchemerRuntime runtime = new SchemerRuntime(globalEnv);
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        try {
            while (true) {
                System.out.print("> ");
                Object value = runtime.eval(new SchemeParser(new SchemeLexer(
                        scanner.next())).list());
                System.out.println("=> " + value);
            }
        } finally {
            scanner.close();
        }
    }

    public static void addGlobals(Environment globalEnv) {
        globalEnv.set("eq", Functions.eq());
        globalEnv.set("cons", Functions.cons());
        globalEnv.set("car", Functions.car());
        globalEnv.set("cdr", Functions.cdr());
        globalEnv.set("+", Functions.plus());
        globalEnv.set("-", Functions.minus());
        globalEnv.set("*", Functions.mult());
        globalEnv.set("/", Functions.div());

    }

}
