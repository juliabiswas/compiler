package ast;

import environment.Environment;
import java.util.List;


/**
 * The Block class has objects that are created
 * from a list of statements. The class has a method
 * that can execute each of the statements in the
 * block.
 *
 * @author Julia Biswas
 * @version March 27, 2020
 */
public class Block extends Statement
{
    private List<Statement> stmts;

    /**
     * Constructs objects of the Block class.
     *
     * @param stmts the list of statements to be executed
     */
    public Block(List<Statement> stmts)
    {
        this.stmts = stmts;
    }

    /**
     * Executes the Block.
     *
     * @precondition a list of statements have been passed in to create a Block object
     * @postcondition the list of statements have been executed
     *
     * @param env the environment that the block is executed in
     */
    public void exec(Environment env)
    {
        for (Statement stmt: stmts)
            stmt.exec(env);
    }
}
