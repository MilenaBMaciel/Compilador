package lexer;

import java.io.IOException;
import java.util.Hashtable;

public class Lexer {

    public static int line = 1;
    char peek = ' ';
    Hashtable<String, Word> words = new Hashtable<>();

    void reserve(Word w) {
        words.put(w.lexeme, w);
    }

    public Lexer() {
        reserve(new Word("class", Tag.CLASS));
        reserve(new Word("do", Tag.DO));
        reserve(new Word("while", Tag.WHILE));

        reserve(new Word("else", Tag.ELSE));
        reserve(new Word("if", Tag.IF));

        reserve(new Word("and", Tag.AND));
        reserve(new Word("not", Tag.NOT));
        reserve(new Word("or", Tag.OR));

        reserve(new Word("repeat", Tag.REPEAT));
        reserve(new Word("until", Tag.UNTIL));

        reserve(new Word("read", Tag.READ));
        reserve(new Word("write", Tag.WRITE));

        reserve(Type.Float);
        reserve(Type.Int);
        reserve(Type.String);
    }

    void readch() throws IOException {
        peek = (char) System.in.read();
    }

    boolean readch(char c) throws IOException {
        readch();
        if (peek != c) {
            return false;
        }
        peek = ' ';
        return true;
    }

    public Token scan() throws IOException {
        for (;; readch()) {
            if (peek == ' ' || peek == '\t' || peek == '\r') {
                continue;
            } else if (peek == '\n') {
                line = line + 1;
            } else {
                break;
            }
        }

        if ((int) peek == -1) {
            return new Token(Tag.EOF);
        }

        // Comentários
        if (peek == '/') {
            readch();
            switch (peek) {
                case '/':
                    do {
                        readch();
                    } while (peek != '\n' && (int) peek != -1);

                    if (peek == '\n') {
                        line = line + 1;
                    }
                    return scan();

                case '*':
                    char ant = ' ';
                    for (;;) {
                        readch();

                        if ((int) peek == -1) {
                            return new Token(Tag.EOF);
                        }

                        if (peek == '\n') {
                            line = line + 1;
                        }

                        if (ant == '*' && peek == '/') {
                            return scan();
                        }

                        ant = peek;
                    }

                default:
                    return new Token('/');
            }
        }

        // Operadores
        switch (peek) {
            case ':':
                if (readch('=')) {
                    return Word.assign;
                } else {
                    return new Token(':');
                }

            case '<':
                readch();
                if (peek == '=') {
                    readch();
                    return Word.le;
                } else if (peek == '>') {
                    readch();
                    return Word.ne;
                } else {
                    return new Token('<');
                }

            case '>':
                if (readch('=')) {
                    return Word.ge;
                } else {
                    return new Token('>');
                }

            case '=':
                Token tokEq = new Token('=');
                readch();
                return tokEq;
        }

        // Números
        if (Character.isDigit(peek)) {
            int v = 0;
            do {
                v = 10 * v + Character.digit(peek, 10);
                readch();
            } while (Character.isDigit(peek));

            if (peek != '.') {
                return new Num(v);
            }

            float x = v;
            float d = 10;
            readch();

            if (!Character.isDigit(peek)) {
                return new Num(v);
            }

            do {
                x = x + Character.digit(peek, 10) / d;
                d = d * 10;
                readch();
            } while (Character.isDigit(peek));

            return new Real(x);
        }

        // Literais
        if (peek == '"') {
            StringBuffer b = new StringBuffer();
            readch();

            while (peek != '"' && peek != '\n' && (int) peek != -1) {
                b.append(peek);
                readch();
            }

            if (peek == '"') {
                readch();
            }

            return new Literal(b.toString());
        }

        // Identificadores e palavras reservadas
        if (Character.isLetter(peek)) {
            StringBuffer b = new StringBuffer();
            do {
                b.append(peek);
                readch();
            } while (Character.isLetterOrDigit(peek));

            String s = b.toString();
            Word w = words.get(s);

            if (w != null) {
                return w;
            }

            w = new Word(s, Tag.ID);
            words.put(s, w);
            return w;
        }

        Token tok = new Token(peek);
        readch();
        return tok;
    }

    public Hashtable<String, Word> getWords() {
        return words;
    }
}
