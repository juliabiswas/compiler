package ast;

import environment.Environment;

import java.util.List;

/**
 * The ProcedureDeclaration class is for declarations
 * of procedures which consist of a single statement
 * (usually a block) and can have parameters passed
 * into them. ProcedureDeclaration objects can be
 * executed, and this is done by making a symbol table
 * entry for the procedure.
 *
 * @author Julia Biswas
 * @version April 13, 2020
 */

public class ProcedureDeclaration extends Statement
{
    private String name;
    private Statement stmt;
    private List<String> parms;

    /**
     * Constructs objects of the ProcedureCall class.
     *
     * @param name the name of the procedure
     * @param stmt the statement that has been declared in the procedure
     * @param parms the parameters of the procedure
     */
    public ProcedureDeclaration(String name, Statement stmt, List<String> parms)
    {
        this.name = name;
        this.stmt = stmt;
        this.parms = parms;
    }

    /**
     * Retrieves the statement that is executed when the procedure is called.
     *
     * @return the procedure's statement
     */
    public Statement getStatement()
    {
        return stmt;
    }

    /**
     * Retrieves the parameters that are required to be passed in when calling
     * the procedure.
     *
     * @return the procedure's parameters
     */
    public List<String> getParms()
    {
        return parms;
    }

    /**
     * Executes the procedure by adding it to the symbol table.
     *
     * @param env the environment that the ProcedureDeclaration object is executed in
     */
    public void exec(Environment env)
    {
        env.setProcedure(name, this);
    }
}
