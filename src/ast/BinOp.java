package ast;

import environment.Environment;

/**
 * The BinOp class has objects that are created
 * from an operator and two expressions. The class
 * also has a method to evaluate these binary operations.
 *
 * @author Julia Biswas
 * @version March 27, 2020
 */
public class BinOp extends Expression
{
    private String op;
    private Expression exp1;
    private Expression exp2;

    /**
     * Constructs objects of the BinOp class.
     *
     * @param op the operator used in the binary operation between two given expressions
     * @param exp1 the first given expression
     * @param exp2 the second given expression
     */
    public BinOp(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * Evaluates the binary operation.
     *
     * @precondition an operator and two expressions have been passed in
     *               to create a BinOp object
     * @postcondition the binary operation has been evaluated
     *
     * @param env the environment that the binary operation is evaluated in
     *
     * @return the value of the evaluated binary operation
     */
    public int eval(Environment env)
    {
        int eval1 = exp1.eval(env);
        int eval2 = exp2.eval(env);

        if (op.equals("*"))
            return eval1*eval2;

        else if (op.equals("/"))
            return eval1/eval2;

        else if (op.equals("+"))
            return eval1+eval2;

        else if (op.equals("-"))
            return eval1-eval2;

        else
            return eval1%eval2;
    }
}
