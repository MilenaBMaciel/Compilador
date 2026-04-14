package lexer;

public class Word extends Token{
    public String lexeme = "";
    public Word(String s, int tag){
        super(tag);
        lexeme = s;
    }
    public static final Word
    and = new Word("&&", Tag.AND),
    or = new Word("||", Tag.OR),
    eqequal = new Word("==", Tag.EQEQUAL),
    dif = new Word("<>", Tag.DIF),
    lesseq = new Word("<=", Tag.LESSEQ),
    greatereq = new Word(">=", Tag.GREATEREQ),
    True = new Word("true", Tag.TRUE),
    False = new Word("false", Tag.FALSE);
}
