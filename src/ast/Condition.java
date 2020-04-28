package ast;

import environment.Environment;

/**
 * The Condition class has objects that are conditions,
 * and it can evaluate these conditions as true or false.
 *
 * All conditions are of the following grammar:
 *  expr → term whileexpr
 *  whileexpr -> + term whileexpr | - factor whileexpr | ε
 *  term → factor whileterm
 *  whileterm → * factor whileterm | / factor whileterm | mod factor whileterm | ε
 *  factor → ( expr ) | - factor | num | id
 *  cond → expr relop expr
 *  relop → = | <> | < | > | <= | >=
 *
 * @author Julia Biswas
 * @version March 27, 2020
 */
public class Condition extends Expression
{
    private String relop;
    private Expression exp1;
    private Expression exp2;

    /**
     * Constructs objects of the Condition class
     * using the given relative operator and expressions.
     *
     * @param relop the relative operator used to compare the given expressions
     * @param exp1 the first given expression
     * @param exp2 the second given expression
     */
    public Condition(String relop, Expression exp1, Expression exp2)
    {
        this.relop = relop;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * Evaluates the condition by determining whether it is true or not.
     * Returns 1 if it is true and 0 if it is false.
     *
     * @precondition a relative operator and two expressions have been passed in
     *               to create a Condition object
     * @postcondition the condition has been evaluated
     *
     * @param env the environment for the evaluation of the condition
     *
     * @return 1 if the condition is true; otherwise,
     *         false
     */
    public int eval(Environment env)
    {
        int eval1 = exp1.eval(env);
        int eval2 = exp2.eval(env);

        boolean result = true;

        if (relop.equals("="))
            result = eval1 == eval2;

        else if (relop.equals("<>"))
            result = eval1 != eval2;

        else if (relop.equals("<"))
            result = eval1 < eval2;

        else if (relop.equals(">"))
            result = eval1 > eval2;

        else if (relop.equals("<="))
            result = eval1 <= eval2;

        else
            result = eval1 >= eval2;

        if (result)
            return 1;
        else
            return 0;

    }
}
