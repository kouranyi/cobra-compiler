// Recursive descent parser
public class Parser {


    Token token;          // current token
    Lexer lexer;

    public Parser(Lexer ts) {
        lexer = ts;
        token = lexer.nextToken();            // retrieve first Token
    }

    public static void main(String[] argv) {
        Parser parser = new Parser(new Lexer("input.txt"));
        parser.start();

    }

    void start() {
        program();
        System.out.println("No Syntax Errors :) ");
    }

    //program -> {declarations} statements
    void program() {
        declarations();
        statements();

    }
    //statement -> choiceStmnt | repetitionStmnt|assignmentStmnt|
    //               inputStmnt|printStmnt

    //statements -> {statement}*
    void statements() {
        statement();
        while (token.type.equals(TokenType.Enter) |
                token.type.equals(TokenType.Print) |
                token.type.equals(TokenType.Identifier) |
                token.type.equals(TokenType.If) |
                token.type.equals(TokenType.From)) {

            statement();
        }


    }

    void statement() {
        if (token.type.equals(TokenType.Enter)) {
            match(TokenType.Enter);
            match(TokenType.Identifier);
        } else if (token.type.equals(TokenType.Print)) {
            match(TokenType.Print);
            match(TokenType.Identifier);
        } else if (token.type.equals(TokenType.Identifier)) {
            match(TokenType.Identifier);
            match(TokenType.Assign);
            exp();
        } else if (token.type.equals(TokenType.If)) {
            match(TokenType.If);
            match(TokenType.RightParen);
            exp();
            match(TokenType.LeftParen);
            match(TokenType.Execute);
            statements();

        } else if (token.type.equals(TokenType.From)) {

            match(TokenType.From);
            match(TokenType.Identifier);
            match(TokenType.Assign);
            exp();
            match(TokenType.To);
            exp();
            match(TokenType.Step);
            numbers();
            match(TokenType.Execute);
            statements();
        } else
            error(token.type);

    }

    void exp() {
        operator1();
        operation1();
    }

    void operation1() {

        if (token.type.equals(TokenType.Greater)) {
            match(TokenType.Greater);
        } else if (token.type.equals(TokenType.GreaterEqual)) {
            match(TokenType.GreaterEqual);
        } else if (token.type.equals(TokenType.Less)) {
            match(TokenType.Less);
        } else if (token.type.equals(TokenType.LessEqual)) {
            match(TokenType.LessEqual);
        } else {
            return;
        }
        operator1();
        operation1();


    }

    void operator1() {
        operator2();
        operation2();

    }

    void operation2() {

        if (token.type.equals(TokenType.Minus)) {
            match(TokenType.Minus);
        } else if (token.type.equals(TokenType.Plus)) {
            match(TokenType.Plus);
        } else {
            return;
        }
        operator2();
        operation2();
    }

    void operator2() {
        operator3();
        operation3();
    }

    void operation3() {
        if (token.type.equals(TokenType.Multiply)) {
            match(TokenType.Multiply);
        } else if (token.type.equals(TokenType.Divide)) {
            match(TokenType.Divide);
        } else {
            return;
        }
        operator3();
        operation3();
    }

    void operator3() {

        if (token.type.equals(TokenType.Identifier)) {

            match(TokenType.Identifier);
        } else if (token.type.equals(TokenType.IntLiteral)) {
            match(TokenType.IntLiteral);
        }
    }

    //declarations -> variableType variableName
    void declarations() {

        //one or more declaration
        variableType();
        variableName();
        while (token.type.equals(TokenType.Int) |
                token.type.equals(TokenType.String) |
                token.type.equals(TokenType.Bool)) {
            variableType();
            variableName();
        }


    }

    void variableType() {
        if (token.type.equals(TokenType.Int)) {
            match(TokenType.Int);
        } else if (token.type.equals(TokenType.String)) {

            match(TokenType.String);


        } else if (token.type.equals(TokenType.Bool)) {

            match(TokenType.Bool);
        } else {
            error(token.type);
        }
    }

    void variableName() {
        character();
        while (token.type.equals(TokenType.Identifier)) {
            character();
        }
        while (token.type.equals(TokenType.IntLiteral)) {
            number();
        }
    }

    void character() {
        match(TokenType.Identifier);
    }

    void number() {
        match(TokenType.IntLiteral);
    }

    void numbers() {

        number();
        while (token.type.equals(TokenType.IntLiteral)) {
            number();
        }

    }

    private void match(TokenType t) {

        if (token.type.equals(t))
            token = lexer.nextToken();
        else
            error(t);

    }

    private void error(TokenType t) {
        System.err.println("Syntax error: expecting: " + t
                + " found: " + token.value);
        System.exit(1);

    }


}

