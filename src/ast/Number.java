package ast;

import environment.Environment;

/**
 * The Number class has objects that are created
 * from an integer. This class can also evaluate
 * itself by returning its numerical value.
 *
 * @author Julia Biswas
 * @version March 27, 2020
 */
public class Number extends Expression
{
    private int value;

    /**
     * Constructs objects of the Number class.
     *
     * @param value the numerical value of the object
     */
    public Number(int value)
    {
        this.value = value;
    }

    /**
     * Evaluates the number.
     *
     * @precondition an integer has been passed in to create a Number object
     * @postcondition the Number has been evaluated
     *
     * @param env the environment that the number is evaluated in
     *
     * @return the numerical value of the number
     */
    public int eval(Environment env)
    {
        return value;
    }
}
