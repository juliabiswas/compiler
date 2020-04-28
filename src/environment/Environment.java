package environment;

import ast.ProcedureDeclaration;

import java.util.HashMap;
import java.util.Map;

/**
 * The environment in which the parser runs. It stores the values
 * of all the variables.
 *
 * @author Julia Biswas
 * @version April 13, 2020
 */
public class Environment
{
    private Map<String, Integer> vars;
    private Map<String, ProcedureDeclaration> procedures;
    private Environment parent;

    /**
     * Constructs objects of the Environment class.
     *
     * @param parent the parent of the environment; its value is null
     *               if the environment is the global environment
     */
    public Environment(Environment parent)
    {
        this.parent = parent;
        this.vars = new HashMap<String, Integer>();
        this.procedures = new HashMap<String, ProcedureDeclaration>();
    }

    /**
     * Associates the given variable name with the given value.
     * If set variable is called on an environment that hasn't
     * already had the variable declared, it will set the value of
     * the variable in the global environment. //***idk if this is right
     *
     * @precondition to set the variable in the current environment,
     *               it must have already been declared in the current
     *               environment; if it hasn't, the variable will be stored
     *               in the global environment
     * @postcondition the variable is set to the new value
     *
     * @param variable the name of the variable
     * @param value the value of the variable
     */
    public void setVariable(String variable, int value)
    {
        if (parent != null && vars.containsKey(variable))
            vars.replace(variable, value);

        else if (parent == null) //this is the global environment
            vars.put(variable, value);

        else //child environment that doesn't include the variable
            parent.setVariable(variable, value);
    }

    /**
     * Retrieves the value associated with the given variable.
     *
     * @precondition the variable is already stored in the variable map
     * @postcondition the variable's value has been returned
     *
     * @param variable the name of the variable whose value will
     *                 be returned
     *
     * @return the value of the variable with the given name
     */
    public int getVariable(String variable)
    {
        if (vars.containsKey(variable))
            return vars.get(variable);
        else
            return parent.getVariable(variable);
    }

    /**
     * Associates the given variable name with the given value
     * in the current environment.
     *
     * @precondition a variable and value have been passed in
     * @postcondition a variable has been created with the given value
     *
     * @param variable the name of the variable
     * @param value the value of the variable
     */
    public void declareVariable(String variable, int value)
    {
        vars.put(variable, value);
    }

    /**
     * Associates the given procedure name with the given statement
     * in the global environment.
     *
     * @precondition a procedure name and declaration have been passed in
     * @postcondition a procedure has been added to the procedures map
     *                with the given declaration as its value
     *
     * @param name the name of the procedure
     * @param proDec the ProcedureDeclaration object for the procedure
     */
    public void setProcedure(String name, ProcedureDeclaration proDec)
    {
        procedures.put(name, proDec);
    }

    /**
     * Retrieves the ProcedureDeclaration object associated with the given
     * procedure name in the global environment.
     *
     * @precondition a procedure with the given name already exists in
     *               the global environment
     * @postcondition the ProcedureDeclaration object associated with the
     *                given procedure name has been returned
     *
     * @param name the name of the procedure that will be returned
     *
     * @return the declared procedure
     */
    public ProcedureDeclaration getProcedure(String name)
    {
        return procedures.get(name);
    }
}

