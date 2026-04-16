package lexer;

public class Tag {

    public static final int EOF = 65535,
            BASIC = 256,
            ID = 257,
            NUM = 258,
            REAL = 259,
            LITERAL = 260,
            CLASS = 261,
            IF = 262,
            ELSE = 263,
            DO = 264,
            WHILE = 265,
            REPEAT = 266,
            UNTIL = 267,
            READ = 268,
            WRITE = 269,
            AND = 270,
            OR = 271,
            NOT = 272,
            ASSIGN = 273, // :=
            LE = 274, // <=
            GE = 275, // >=
            NE = 276; // <>
}
