package ast;

import environment.Environment;

/**
 * The Assignment class has objects that are created
 * from a variable name and a value. To execute these
 * assignments, the class has a method that sets
 * the variable with the given name to the given value.
 *
 * @author Julia Biswas
 * @version March 27, 2020
 */
public class Assignment extends Statement
{
    private String var;
    private Expression exp;

    /**
     * Constructs objects of the Assignment class.
     *
     * @param var the name of the variable
     * @param exp the expression that is the value of the variable
     */
    public Assignment(String var, Expression exp)
    {
        this.var = var;
        this.exp = exp;
    }

    /**
     * Executes the assignment statement.
     *
     * @precondition a variable name and expression have been passed in
     *               to create an Assignment object
     * @postcondition the variable has been assigned the value of the evaluated expression
     *
     * @param env the environment that the assignment statement is executed in
     */
    public void exec(Environment env)
    {
        env.setVariable(var, exp.eval(env));
    }
}
