package ast;

import environment.Environment;

/**
 * The Statement class is an abstract class whose descendents
 * are statements that can be executed.
 *
 * @author Julia Biswas
 * @version March 27, 2020
 */
public abstract class Statement
{
    /**
     * Executes the statement.
     *
     * @precondition a variable name and number have been passed in to create a Readln object
     * @postcondition sets the variable to the value that has been inputted by the user
     *
     * @param env the environment that the if statement is executed in
     */
    public abstract void exec(Environment env);
}
