package symbols;

import java.util.*;
import inter.*;
import lexer.*;

public class Env {

    private Hashtable table;
    protected Env prev;

    public Env(Env n) {
        table = new Hashtable();
        prev = n;
    }

    public Id get(Token w) {
        for (Env e = this; e != null; e = e.prev) {
            Id found = (Id) (e.table.get(w));
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}
