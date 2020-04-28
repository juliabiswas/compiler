package ast;

import environment.Environment;

/**
 * The Readln class has objects that are created
 * from a variable name and a number. Readln objects
 * can be executed using a method in the class
 * that sets the variable with the given name to the
 * given value (that was passed in after the user
 * inputted it).
 *
 * @author Julia Biswas
 * @version March 27, 2020
 */
public class Readln extends Statement
{
    private String var;
    private Number num;

    /**
     * Constructs objects of the Readln class by setting
     * the variable with the given name to a number that
     * has been inputted by the user and passed into the
     * constructor.
     *
     * @param var the  name of the variable
     * @param num the value of the variable with the given name
     */
    public Readln(String var, Number num)
    {
        this.var = var;
        this.num = num;
    }

    /**
     * Executes READLN.
     *
     * @precondition a variable name and number have been passed in to create a Readln object
     * @postcondition sets the variable to the value that has been inputted by the user
     *
     * @param env the environment that READLN is executed in
     */
    public void exec(Environment env)
    {
        env.setVariable(var, num.eval(env));
    }
}
