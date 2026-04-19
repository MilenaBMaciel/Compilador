package lexer;

import java.io.IOException;
import java.util.Hashtable;
import symbols.*;

public class Lexer {

    public static int line = 1;

    int curr = ' ';
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
        curr = System.in.read();
        if (curr != -1) {
            peek = (char) curr;
        }
    }

    boolean readch(char c) throws IOException {
        readch();
        if (curr == -1 || peek != c) {
            return false;
        }
        peek = ' ';
        return true;
    }

    public Token scan() throws IOException {
        for (;; readch()) {
            if (curr == -1) {
                return new Token(Tag.EOF);
            } else if (peek == ' ' || peek == '\t' || peek == '\r') {
                continue;
            } else if (peek == '\n') {
                line++;
            } else {
                break;
            }
        }

        // Comentários
        if (peek == '/') {
            readch();
            switch (peek) {
                case '/':
                    do {
                        readch();
                    } while (curr != -1 && peek != '\n');

                    if (peek == '\n') {
                        line++;
                    }
                    return scan();

                case '*':
                    char ant = '\0';
                    for (;;) {
                        readch();

                        if (curr == -1) {
                            return new Token(Tag.EOF);
                        }

                        if (peek == '\n') {
                            line++;
                        }

                        if (ant == '*' && peek == '/') {
                            readch();
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
                if (curr != -1 && peek == '=') {
                    readch();
                    return Word.le;
                } else if (curr != -1 && peek == '>') {
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
            } while (curr != -1 && Character.isDigit(peek));

            if (peek != '.') {
                return new Num(v);
            }

            float x = v;
            float d = 10;
            readch();

            if (curr == -1 || !Character.isDigit(peek)) {
                return new Num(v);
            }

            do {
                x = x + Character.digit(peek, 10) / d;
                d = d * 10;
                readch();
            } while (curr != -1 && Character.isDigit(peek));

            return new Real(x);
        }

        // Literais
        if (peek == '"') {
            StringBuffer b = new StringBuffer();
            readch();

            while (curr != -1 && peek != '"' && peek != '\n') {
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
            } while (curr != -1 && Character.isLetterOrDigit(peek));

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
