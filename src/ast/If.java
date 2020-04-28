package ast;

import environment.Environment;

/**
 * The If class has objects that are created
 * from a condition and a statement. The class
 * has a method that can execute the statement
 * when the condition is true.
 *
 * @author Julia Biswas
 * @version March 27, 2020
 */
public class If extends Statement
{
    private Condition cond;
    private Statement stmt;

    /**
     * Constructs objects of the If class.
     *
     * @param cond the condition of the if statement
     * @param stmt the statement to be executed if the condition is true
     */
    public If(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }

    /**
     * Executes the if statement.
     *
     * @precondition a condition and statement have been passed in to create an If object
     * @postcondition if the condition is true, the statement has been executed
     *
     * @param env the environment that the if statement is executed in
     */
    public void exec(Environment env)
    {
        if (cond.eval(env) == 1)
            stmt.exec(env);
    }
}
