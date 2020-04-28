package ast;

import environment.Environment;

/**
 * The Writeln class has objects that are created
 * from an expression. Writeln objects can be executed
 * using a method in the class that prints out the
 * evaluated expression.
 *
 * @author Julia Biswas
 * @version March 27, 2020
 */
public class Writeln extends Statement
{
    private Expression exp;

    /**
     * Constructs objects of the Writeln class.
     *
     * @param exp the expression that will be printed
     */
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }

    /**
     * Executes WRITELN.
     *
     * @precondition an expression has been passed in to create a Writeln object
     * @postcondition the value of the expression has been printed
     *
     * @param env the environment that WRITELN is executed in
     */
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));
    }
}
