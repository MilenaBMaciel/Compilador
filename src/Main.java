
import java.io.IOException;
import lexer.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Lexer lexer = new Lexer();
        Token tok;

        System.out.println("TOKENS:");

        do {
            tok = lexer.scan();
            if (tok.tag == Tag.EOF) {
                System.out.println("EOF");
            } else {
                System.out.println(tok);
            }
        } while (tok.tag != Tag.EOF);

        System.out.println("\n\nTABELA DE SIMBOLOS:");

        for (String key : lexer.getWords().keySet()) {
            Word w = lexer.getWords().get(key);
            System.out.println(key + " => " + w.tag);
        }
    }
}
