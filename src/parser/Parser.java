package parser;

import ast.*;
import ast.Number;
import scanner.*;

import java.util.List;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * The Parser class parses statements using the following left-factored grammar:
 *  program → PROCEDURE id ( maybeparms ) ; stmt program | stmt .
 *  maybeparms → parms | ε
 *  parms → parms , id | id
 *  stmt → WRITELN ( expr ) ; | READLN ( id ) | BEGIN whilebegin
 *          | id := expr ; | IF cond THEN stmt | WHILE cond DO stmt
 *          | FOR id := expr TO expr DO stmt
 *  whilebegin → END ; | stmt whilebegin
 *  expr → term whileexpr
 *  whileexpr -> + term whileexpr | - factor whileexpr | ε
 *  term → factor whileterm
 *  whileterm → * factor whileterm | / factor whileterm | mod factor whileterm | ε
 *  factor → ( expr ) | - factor | num | id ( maybeargs ) | id
 *  maybeargs → args | ε
 *  args → args , expr | expr
 *  cond → expr relop expr
 *  relop → = | <> | < | > | <= | >=
 *
 * @author Julia Biswas
 * @version April 7, 2020
 */

public class Parser
{
    private Scanner scan;
    private String currToken;

    /**
     * Constructs a Parser object.
     *
     * @param scan the scanner that feeds in tokens to the parser
     */
    public Parser(Scanner scan)
    {
        this.scan = scan;
        currToken = scan.nextToken();
    }

    /**
     * Advances to the next token if the current token has the
     * expected value.
     *
     * @precondition a current token exists
     * @postcondition the token found has the expected value, the scanner moves
     *                to the next token; throws a ScanErrorException if the
     *                values aren't the same
     *
     * @throws IllegalArgumentException if the current token does not have the expected value
     *
     * @param expected the expected token
     */
    private void eat(String expected) throws IllegalArgumentException
    {
        if (currToken.equals(expected))
            currToken = scan.nextToken();
        else
            throw new IllegalArgumentException(expected + " was expected and " +
                    currToken + " was found.");
    }

    /**
     * Parses a program. The grammar for a program is
     *  program → PROCEDURE id ( maybeparms ) ; stmt program | stmt .
     *  maybeparms → parms | ε
     *  parms → parms , id | id
     *  (stmt is defined in the parseStatement() method)
     *
     * @return the program that has been parsed
     */
    public Program parseProgram()
    {
        List<ProcedureDeclaration> procedures = new ArrayList<ProcedureDeclaration>();

        while (currToken.equals("PROCEDURE"))
        {
            procedures.add(parseProcedure());
        }

        Statement stmt = parseStatement();
        eat(".");

        return new Program(procedures, stmt);
    }

    /**
     * Parses a procedure declaration.
     *
     * @return the procedure that has been parsed
     */
    public ProcedureDeclaration parseProcedure()
    {
        eat("PROCEDURE");

        String curr = currToken; //name of the procedure
        eat(currToken);

        eat("(");

        List<String> parms = new ArrayList<String>();

        while (!currToken.equals(")"))
        {
            parms.add(currToken);
            eat(currToken);

            if (currToken.equals(","))
                eat(",");
        }

        eat(")");
        eat(";");
        Statement stmt = parseStatement();

        return new ProcedureDeclaration(curr, stmt, parms);
    }

    /**
     * Parses a statement. The left-factored grammar for a statement is
     *  stmt → WRITELN ( expr ) ; | READLN ( id ) | BEGIN whilebegin
     *         | id := expr ; | IF cond THEN stmt | WHILE cond DO stmt
     *         | FOR id := expr TO expr DO stmt
     *  whilebegin → END ; | stmt whilebegin
     *  (expr defined in parseExpression() )
     *
     * @precondition the current token is the first token of a statement
     * @postcondition the entire statement has been parsed and its value
     *                has been returned
     *
     * @return the Statement object that has been parsed
     */
    public Statement parseStatement()
    {
        if (currToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            eat(";");
            return new Writeln(exp);
        }

        else if (currToken.equals("READLN"))
        {
            eat("READLN");
            eat("(");
            String var = currToken;

            try //asks user for input, waits for response before continuing
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                currToken = reader.readLine();
            }

            catch(Exception e)
            {
                System.out.println("Unable to read input.");
            }

            Number num = parseNumber();
            eat(")");
            eat(";");
            return new Readln(var, num);
        }

        else if (currToken.equals("IF"))
        {
            eat("IF");
            Condition cond = parseCondition();
            eat("THEN");
            Statement stmt = parseStatement();
            return new If(cond, stmt);
        }

        else if (currToken.equals("WHILE"))
        {
            eat("WHILE");
            Condition cond = parseCondition();
            eat("DO");
            Statement stmt = parseStatement();
            return new While(cond, stmt);
        }

        else if (currToken.equals("FOR"))
        {
            eat("FOR");

            String var = currToken;
            eat(currToken);
            eat(":=");

            Expression start = parseExpression();

            eat("TO");

            Expression end = parseExpression();

            eat("DO");
            Statement stmt = parseStatement();
            return new For(var, start, end, stmt);
        }

        else if (currToken.equals("BEGIN"))
        {
            eat("BEGIN");
            List<Statement> stmts = new ArrayList<Statement>();

            while (!currToken.equals("END"))
                stmts.add(parseStatement());

            eat("END");
            eat(";");

            return new Block(stmts);
        }

        else //if currToken is id
        {
            String var = currToken;
            eat(currToken);
            eat(":=");
            Expression exp = parseExpression();
            eat(";");
            return new Assignment(var, exp);
        }
    }

     /**
     * Advances to the next token and retrieves the value
     * of the parsed integer.
     *
     * @precondition the current token is an integer
     * @postcondition the number token has been eaten and its
     *                value has been returned
     *
     * @return a Number object with the value of the parsed integer
     */
    private Number parseNumber()
    {
        int value = Integer.parseInt(currToken);
        eat(currToken);
        return new Number(value);
    }

    /**
     * Parses a condition. The grammar for a condition is
     *  cond → expr relop expr
     *  relop → = | <> | < | > | <= | >=
     *  (the grammar for an expression is in the description of  parseExpression())
     *
     * @precondition the current token is the first token of a condition
     * @postcondition the condition has been parsed and its value
     *                has been returned
     *
     * @return the value of the parsed condition
     */
    private Condition parseCondition()
    {
        Expression exp1 = parseExpression();

        String relop = currToken;
        eat(currToken);

        Expression exp2 = parseExpression();

        return new Condition(relop, exp1, exp2);
    }

    /**
     * Parses a factor. The grammar for a factor is
     *  factor -> (expr) |  -factor | num | id
     *
     * @precondition the current token is the first token of a factor
     * @postcondition the factor has been parsed and its value
     *                has been returned
     *
     * @return the value of the parsed factor
     */
    public Expression parseFactor()
    {
        if (scan.isLetter(currToken.charAt(0)))
        {
            String curr = currToken;
            eat(currToken);

            if(currToken.equals("("))
            {
                eat("(");

                List<Expression> parms = new ArrayList<Expression>();

                while (!currToken.equals(")"))
                {
                    Expression parm = parseExpression();
                    parms.add(parm);

                    if (currToken.equals(","))
                        eat(",");
                }

                eat(")");
                return new ProcedureCall(curr, parms);
            }

            else
                return new Variable(curr);
        }

        else if (!currToken.equals("-") && !currToken.equals("(") && !currToken.equals(")"))
        {
            return parseNumber();
        }

        else if (currToken.equals("-"))
        {
            eat(currToken);
            return new BinOp("*", new Number(-1), parseFactor());
        }

        else
        {
            eat(currToken);
            Expression exp = parseExpression();
            eat(currToken);
            return exp;
        }
    }

    /**
     * Parses a term. The left-factored grammar for a term is
     *  term → factor whileterm
     *  whileterm → * factor whileterm | / factor whileterm | mod factor whileterm | ε
     *  (factor defined in description of parseFactor())
     *
     * @precondition the current token is the first token of a term
     * @postcondition the term has been parsed and its value
     *                has been returned
     *
     * @return the value of the parsed term
     */
    private Expression parseTerm()
    {
        Expression exp = parseFactor();

        while (currToken.equals("*") || currToken.equals("/") || currToken.equals("mod"))
        {
            if (currToken.equals("*"))
            {
                eat("*");
                exp =  new BinOp("*", exp, parseFactor());
            }

            else if (currToken.equals("/"))
            {
                eat("/");
                exp = new BinOp("/", exp, parseFactor());
            }

            else
            {
                eat("mod");
                exp = new BinOp("%", exp, parseFactor());
            }
        }

        return exp;
    }

    /**
     * Parses an expression. The left-factored grammar for an expression is
     *  expr → term whileexpr
     *  whileexpr -> + term whileexpr | - factor whileexpr | ε
     *  (term & factor defined in description of parseTerm() & parseFactor())
     *
     * @precondition the current token is the first token of an expression
     * @postcondition the expression has been parsed and its
     *                value has been returned
     *
     * @return the value of the parsed expression
     */
    private Expression parseExpression()
    {
        Expression exp = parseTerm();

        while (currToken.equals("+") || currToken.equals("-"))
        {
            if (currToken.equals("+"))
            {
                eat("+");
                exp = new BinOp("+", exp, parseExpression());
            }

            else
            {
                eat("-");
                exp = new BinOp("-", exp, parseExpression());
            }
        }

        return exp;
    }
}

