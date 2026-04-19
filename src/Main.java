
import java.io.IOException;
import java.util.Map;
import lexer.Lexer;
import lexer.Tag;
import lexer.Token;
import lexer.Word;

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

        System.out.println("\nTABELA DE SIMBOLOS:");

        for (Map.Entry<String, Word> entry : lexer.getWords().entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue().tag);
        }
    }
}
