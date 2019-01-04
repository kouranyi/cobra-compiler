public class Parser {

    // Recursive descent parser that inputs a C++Lite program and
    // generates its abstract syntax.  Each method corresponds to
    // a concrete syntax grammar rule, which appears as a comment
    // at the beginning of the method.

    Token token;          // current token from the input stream
    Lexer lexer;

    public Parser(Lexer ts) { // Open the C++Lite source program
        lexer = ts;                          // as a token stream, and
        token = lexer.nextToken();            // retrieve its first Token
    }



    void start(){
        program();
        match(TokenType.Eof);
    }

    //program -> declarations statements
    void program(){
        declarations();
        statements();
        System.out.println("No Syntax Errors");
    }
    //statements -> {statement}*
    void statements(){
        statement();
        while(token.type.equals(TokenType.Enter)|
              token.type.equals(TokenType.Print)|
              token.type.equals(TokenType.Identifier)|
              token.type.equals(TokenType.If)|
              token.type.equals(TokenType.From)) {

                 statement();
        }


    }
    //statement -> choiceStmnt | repetitionStmnt|assignmentStmnt|
    //               inputStmnt|printStmnt

    void statement(){
        if(token.type.equals(TokenType.Enter) ){
            match(TokenType.Enter);
            match(TokenType.Identifier);
        }else if(token.type.equals(TokenType.Print) ){
            match(TokenType.Print);
            match(TokenType.Identifier);
        }else if(token.type.equals(TokenType.Identifier)){
            match(TokenType.Identifier);
            match(TokenType.Assign);
            match(TokenType.Identifier);
        }else if (token.type.equals(TokenType.If) ){
            match(TokenType.If);
            match(TokenType.RightParen);
            exp();
            match(TokenType.LeftParen);
            match(TokenType.Execute);
            statements();

        }else if (token.type.equals(TokenType.From)) {

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
        }else
            error(token.type);

    }

    void exp(){
        operator1();
        operation1();
    }
    void operation1(){

        if( token.type.equals(TokenType.Greater) ){
            match(TokenType.Greater);
        }else if(token.type.equals(TokenType.GreaterEqual) ){
            match(TokenType.GreaterEqual);
        }else if(token.type.equals(TokenType.Less)){
            match(TokenType.Less);
        }else if(token.type.equals(TokenType.LessEqual)){
            match(TokenType.LessEqual);
        }else{
            return;
        }
        operator1();
        operation1();


    }
    void operator1(){
        operator2();
        operation2();

    }
    void operation2(){

        if( token.type.equals(TokenType.Minus) ){
            match(TokenType.Minus);
        }else if(token.type.equals(TokenType.Plus) ){
            match(TokenType.Plus);
        }else{
            return;
        }
        operator2();
        operation2();
    }
    void operator2(){
        operator3();
        operation3();
    }
    void operation3(){
        if( token.type.equals(TokenType.Multiply) ){
            match(TokenType.Multiply);
        }else if(token.type.equals(TokenType.Divide) ) {
            match(TokenType.Divide);
        }
        else{
            return;
        }
        operator3();
        operation3();
    }
    void operator3(){

        if(token.type.equals(TokenType.Identifier)){

            match(TokenType.Identifier);
        }else if (token.type.equals(TokenType.IntLiteral)){
            match(TokenType.IntLiteral);
        }
    }

    //declarations -> variableType variableName
    void declarations(){

        variableType();
        variableName();

    }
    void variableType(){
        if(token.type.equals(TokenType.Int) ){
            match(TokenType.Int);
        }else if(token.type.equals(TokenType.String) ){

            match(TokenType.String);


        }else if(token.type.equals(TokenType.Bool) ){

            match(TokenType.Bool);
        }else{
            error(token.type);
        }
    }
    void variableName(){
        character();
        while(token.type.equals(TokenType.CharLiteral)){
            character();
        }while(token.type.equals(TokenType.IntLiteral)){
            number();
        }
    }
    void character(){
        match(TokenType.CharLiteral);
    }
    void number(){
        match(TokenType.IntLiteral);
    }

   void numbers(){

        number();
       while(token.type.equals(TokenType.IntLiteral)) {
           number();
       }

   }






    private void match (TokenType t) {

        if (token.type.equals(t))
            token = lexer.nextToken();
        else
            error(t);

    }


    private void error(TokenType t){
        System.err.println("Syntax error: expecting: " + t
                + "; saw: " + token.value );
        System.exit(1);

    }


public static void main(String[] argv){
        Parser parser = new Parser(new Lexer("input.txt") );
        parser.start();

}


}

