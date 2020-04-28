package ast;

import environment.Environment;

import java.util.List;

/**
 * The ProcedureCall class is for objects
 * that are made up of a name and list of parameters
 * (if needed to call the respective procedure). Objects
 * of the ProcedureCall class call their respective
 * procedures when evaluated and return the value of
 * their procedure if the procedure returns a value.
 *
 * @author Julia Biswas
 * @version April 13, 2020
 */
public class ProcedureCall extends Expression
{
    private String name;
    private List<Expression> parms;

    /**
     * Constructs objects of the ProcedureCall class.
     *
     * @param name the name of the procedure that is called
     * @param parms the parameters passed into the procedure
     */
    public ProcedureCall(String name, List<Expression> parms)
    {
        this.name = name;
        this.parms = parms;
    }

    /**
     * Evaluates the procedure call by evalating and declaring
     * the passed in values and then running the procedure.
     *
     * @precondition the name of the procedure and a list of
     *               parameters (if required) have been passed in
     * @postcondition the procedure call has been evaluated
     *
     * @param env the environment that the ProcedureCall is evaluated in
     *
     * @return the value returned by the call to the procedure;
     *         if there is no return value, 0 is returned
     */
    public int eval(Environment env)
    {
        Environment childEnv = new Environment(env);

        ProcedureDeclaration proDec = env.getProcedure(name);

        List<String> formalArgs = proDec.getParms();

        for (int i = 0; i < parms.size(); i++)
        {
            int val = parms.get(i).eval(env);
            childEnv.declareVariable(formalArgs.get(i), val);
        }

        childEnv.declareVariable(name, 0); //0 is the default value

        proDec.getStatement().exec(childEnv);

        return childEnv.getVariable(name);
    }
}
