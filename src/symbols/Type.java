package symbols;

import lexer.*;

public class Type extends Word {

    public int width = 0;

    public Type(String s, int tag, int w) {
        super(s, tag);
        width = w;
    }

    public static final Type Int = new Type("int", Tag.BASIC, 4),
            Float = new Type("float", Tag.BASIC, 8),
            String = new Type("string", Tag.BASIC, 0);

    public static boolean numeric(Type p) {
        return p == Type.Int || p == Type.Float;
    }

    public static Type max(Type p1, Type p2) {
        if (!numeric(p1) || !numeric(p2)) {
            return null;
        } else if (p1 == Type.Float || p2 == Type.Float) {
            return Type.Float;
        } else {
            return Type.Int;
        }
    }
}
