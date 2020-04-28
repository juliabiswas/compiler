package ast;

import environment.Environment;

/**
 * The Variable class has objects that are created
 * from a variable name. The class also has a method
 * to evaluate the variables (retrieve their values).
 *
 * @author Julia Biswas
 * @version March 27, 2020
 */
public class Variable extends Expression
{
    private String name;

    /**
     * Constructs objects of the Variable class.
     *
     * @param name the name of the variable
     */
    public Variable(String name)
    {
        this.name = name;
    }

    /**
     * Evaluates the variable by retrieving its value.
     *
     * @precondition a variable name has been passed in to create a Variable object
     * @postcondition the variable has been evaluated
     *
     * @param env the environment that the variable is evaluated in
     *
     * @return the value of the variable
     */
    public int eval(Environment env)
    {
        return env.getVariable(name);
    }
}
