package ast;

import java.util.List;

import environment.Environment;

/**
 * A program is defined using the following grammar
 *  program → PROCEDURE id ( maybeparms ) ; stmt program | stmt .
 *  (maybeparms & stmt are defined in the Parser class)
 *
 * @author Julia Biswas
 * @version April 13, 2020
 */
public class Program extends Statement
{
    private List<ProcedureDeclaration> procedures;
    private Statement stmt;

    /**
     * Constructs objects of the Program class.
     *
     * @param procedures the list of declared procedures for the program
     * @param stmt the statement that follows the declared procedures
     *             in the program
     */
    public Program(List<ProcedureDeclaration> procedures, Statement stmt)
    {
        this.procedures = procedures;
        this.stmt = stmt;
    }

    /**
     * Executes the program by declaring all the procedures and then
     * executing the statement at the end.
     *
     * @precondition procedures and a statement have been passed in to create
     *               a Program object
     * @postcondition all the procedures have been declared and the statement
     *                has been executed
     *
     * @param env the environment in which the program will be executed in
     */
    public void exec(Environment env)
    {
        for (ProcedureDeclaration procDec : procedures)
            procDec.exec(env);

        stmt.exec(env);
    }

    /**
     * Takes in an output file name and then uses an Emitter to write code to the file with the given name
     * @param fileName
     */
    public void compile(String fileName)
    {

    }
}
