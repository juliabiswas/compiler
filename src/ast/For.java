package ast;

import environment.Environment;

/**
 * The For class has objects that are created
 * from a variable, start value, end value,
 * and statement. The class has a method that
 * can execute the statement for the specified
 * number of iterations (start value to end value
 * but not including end).
 *
 * @author Julia Biswas
 * @version March 27, 2020
 */
public class For extends Statement
{
    private String var;
    private Expression start;
    private Expression end;
    private Statement stmt;

    /**
     * Constructs objects of the For class.
     *
     * @param var the variable for the for loop
     * @param start the start value for iterations
     * @param end the end value for iterations (exclusive ; up to but not including end)
     * @param stmt the statement that is executed for the specified number of iterations
     */
    public For(String var, Expression start, Expression end, Statement stmt)
    {
        this.var = var;
        this.start = start;
        this.end = end;
        this.stmt = stmt;
    }

    /**
     * Executes the for loop.
     *
     * @precondition a variable, start/end values, and a statement
     *               have been passed in to create a For object
     * @postcondition for each iteration, the statement has been executed
     *
     * @param env the environment that the for loop is executed in
     */
    public void exec(Environment env)
    {
        int startEval = start.eval(env);
        int endEval = end.eval(env);

        for (int i = startEval; i < endEval; i++)
        {
            env.setVariable(var, i); //saving the for loop variable to the value of i
            stmt.exec(env);
        }
    }
}
