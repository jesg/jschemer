package jschemer.lexer;

import java.util.LinkedList;
import java.util.List;

public class SchemeParser extends Parser {

    public SchemeParser(Lexer input) {
        super(input);
    }

    public List<Object> list() {
        match(SchemeLexer.LBRACK);
        List<Object> localList = elements();
        match(SchemeLexer.RBRACK);

        return localList;
    }

    private List<Object> elements() {
        List<Object> localList = new LinkedList<Object>();

        while (lookahead.type != SchemeLexer.RBRACK) {
            localList.add(element());
        }
        return localList;
    }

    private Object element() {
        Object result;
        if (lookahead.type == SchemeLexer.SYMBOL) {
            result = lookahead.text;
            match(SchemeLexer.SYMBOL);
        } else if (lookahead.type == SchemeLexer.NUMBER) {
            result = Double.parseDouble(lookahead.text);
            match(SchemeLexer.NUMBER);
        } else if (lookahead.type == SchemeLexer.LBRACK) {
            result = list();
        } else {
            throw new Error("expecting symbol, number, or list; found "
                    + lookahead);
        }
        return result;
    }

}
