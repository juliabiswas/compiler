package ast;

import environment.Environment;

/**
 * The Expression class is an abstract class whose descendents
 * are expressions that can be evaluated.
 *
 * @author Julia Biswas
 * @version March 27, 2020
 */
public abstract class Expression
{
    /**
     * Evaluates the expression.
     *
     * @precondition the Expression object has been created (this applies
     *               to its concrete subclasses)
     * @postcondition the expression has been evaluated
     *
     * @param env the environment that the expression is evaluated in
     *
     * @return the evaluated value
     */
    public abstract int eval(Environment env); //**IDK IF IM ALLOWED TO CHANGE IT TO OBJECT
}
