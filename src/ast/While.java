package ast;

import environment.Environment;

/**
 * The While class has objects that are created
 * from a condition and a statement. The class
 * has a method that can execute the statement
 * while the condition is true.
 *
 * @author Julia Biswas
 * @version March 27, 2020
 */
public class While extends Statement
{
    private Condition cond;
    private Statement stmt;

    /**
     * Constructs objects of the While class.
     *
     * @param cond the condition of the while loop
     * @param stmt the statement that is executed while the condition is true
     */
    public While(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }

    /**
     * Executes the while statement.
     *
     * @precondition a condition and statement have been passed in to create a While object
     * @postcondition while the condition is true, the statement is executed
     *
     * @param env the environment that the while loop is executed in
     */
    public void exec(Environment env)
    {
        while (cond.eval(env) == 1)
            stmt.exec(env);
    }
}
