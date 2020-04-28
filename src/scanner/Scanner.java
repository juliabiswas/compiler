
package scanner;

import java.io.*;

/**
 * Scanner is a simple scanner that can recognize three different types of tokens:
 * numbers, identifiers, and operands. The regular expressions for each are the following:
 *      number := digit(digit)*
 *      identifier := letter(letter | digit)*
 *      operand := ['=' '+' '-' '*' '/' '%' '(' ')']
 *
 * It does so by looking one character ahead to determine which type of token the current
 * character is.
 *
 * @author  Julia Biswas
 * @version February 6, 2020
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;

    /**
     * Constructs a Scanner object by taking in an InputStream object
     * for input. It sets the end-of-file flag and then reads in the tokens
     * until the end of the file. Program ends if a token is found that
     * doesn't match any of the regular expressions.
     * Usage: FileInputStream inStream = new FileInputStream(new File("scannerfile.txt"));
     *        Scanner scan = new Scanner(inStream);
     *
     * @precondition an input stream has been created and fed into the constructor
     * @postcondition a Scanner object is created and the tokens have been read in,
     *                assuming that no ScanErrorExceptions occurred
     *
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }

    /**
     * Constructs a Scanner object by taking in a given input string.
     * It sets the end-of-file flag and then reads in the tokens contained in
     * the input string until the end of the file is reached. Program ends
     * if a token is found that doesn't match any of the regular expressions.
     * Usage: Scanner scan = new Scanner(input_string);
     *
     * @precondition an input string has been created and fed into the constructor
     * @postcondition a Scanner object is created and the tokens have been read in,
     *                assuming that no ScanErrorExceptions occurred
     *
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }

    /**
     * Retrieves the next char.
     *
     * @precondition the next char is either a value or an end of file flag
     * @postcondition currentChar has been set to the char value of the next char
     */
    private void getNextChar()
    {
        try
        {
            int curr = in.read(); //getting the next char
            if (curr == -1 || curr == '.') // . signifies the end of file
                eof = true;
            else
                currentChar = (char)curr;
        }

        catch (IOException e)
        {
            System.out.println("Exception while getting next char");
            System.exit(-1);
        }
    }

    /**
     * Takes in a value representing the expected char and
     * compares the input value and the currentChar. Advances
     * the input stream if they are the same; if not, throws
     * a ScanErrorException.
     *
     * @precondition a char has been read (a currentChar exists)
     * @postcondition the char found is compared to the expected value and
     *                if they are the same, the input stream is advanced;
     *                throws a ScanErrorException if they aren't the same
     *
     * @throws ScanErrorException if the currentChar doesn't match the expected char value
     *
     * @param expected the expected char value
     */
    private void eat(char expected) throws ScanErrorException
    {
        if (currentChar == expected)
            getNextChar();
        else
        {
            throw new ScanErrorException("Illegal character - expected " + expected +
                                            " and found " + currentChar);
        }
    }

    /**
     * Determines whether or not there is a next char
     * (whether or not the end of the file has been reached).
     *
     * @precondition an input stream or string has been
     *
     * @return true if there is a next char; otherwise,
     *         false
     */
    public boolean hasNext()
    {
        return !eof;
    }

    /**
     * Retrieves the next token in the input stream.
     *
     * @precondition getNextChar() has already been called
     * @postcondition a string representing the lexeme found
     *                or "." if the end of the file has been reached
     *
     * @throws ScanErrorException if the token doesn't match
     * the regular expression of a number, identifier, or operand
     *
     * @return a String that represents the lexeme that was found;
     *         "END" if the input stream is at the end of the file
     */
    public String nextToken()
    {
        String token = "";

        try
        {
            while (isWhiteSpace(currentChar) && hasNext())
                eat(currentChar);

            if (eof)
                token += ".";
            else if (isDigit(currentChar))
                token += scanNumber();
            else if (isLetter(currentChar))
                token += scanIdentifier();
            else
            {
                token += scanOperand(); //scanOperand throws an error if the token is not an operand

                //support for comments in this format: (// comment)
                if (token.equals("/") && currentChar == '/')
                {
                    while (currentChar != '\n' && hasNext())
                        eat(currentChar);
                    token = nextToken();
                }

                //support for comments in this format: (* comment *)
                else if (token.equals("(") && currentChar == '*')
                {
                    eat(currentChar);
                    char prevChar = ' ';
                    while (!(prevChar == '*' && currentChar == ')') && hasNext())
                    {
                        //System.out.println("hello" + currentChar);//****
                        prevChar = currentChar;
                        eat(currentChar);
                    }
                    eat(currentChar);
                    token = nextToken();
                }

            }
        }

        catch (ScanErrorException e)
        {
            System.out.println("Unrecognized token: it doesn't match " +
                                    "any of the given regular expressions."); //error message

            /*
            try
            {
                eat(currentChar);
            }
            catch(ScanErrorException eatE) //to deal with the possible error in eat()
            {
                System.exit(-1);
            }
            */

            System.exit(-1);
        }
        return token;
    }

    /**
     * Examines input char to see if it is a digit according to
     * the following regular expression: digit := [0-9].
     *
     * @precondition a char has been read in
     * @postcondition a boolean has been returned indicating whether
     *                the char passed in was a digit or not
     *
     * @param input the char that will be determined to be a digit or not
     *
     * @return true if the char is a digit; otherwise,
     *         false
     */
    public static boolean isDigit (char input)
    {
        return input>='0' && input <= '9';
    }

    /**
     * Examines input char to see if it is a digit according to
     * the following regular expression: letter := [a-z A-Z].
     *
     * @precondition a char has been read in
     * @postcondition a boolean has been returned indicating whether
     *                the char passed in was a letter or not
     *
     * @param input the char that will be determined to be a letter or not
     *
     * @return true if the char is a letter; otherwise,
     *         false
     */
    public static boolean isLetter (char input)
    {
        return (input >= 'a' && input <= 'z') ||(input>= 'A' && input <= 'Z');
    }

    /**
     * Examines input char to see if it is a digit according to
     *      * the following regular expression: letter := [' ' "\t' '\r' '\n'].
     *
     * @precondition a char has been read in
     * @postcondition a boolean has been returned indicating whether
     *                the char passed in was a white space or not
     *
     * @param input the char that will be determined to be a white space or not
     *
     * @return true if the char is a white space; otherwise,
     *         false
     */
    public static boolean isWhiteSpace (char input)
    {
        return input == ' ' || input == '\t' || input == '\r' || input == '\n';
    }

    /**
     * Scans the input and returns a String representing the number lexeme found in the input
     * stream. If no number is recognized, a ScanErrorException is thrown.
     *
     * Numbers are defined by the following regular expression: digit(digit)* .
     *
     * @precondition the currentChar is a digit
     * @postcondition a String representing the lexeme found is returned; if the
     *                token doesn't match the regular expression of a number,
     *                a ScanErrorException is thrown
     *
     * @throws ScanErrorException if the token scanned in doesn't match
     *                            the regular expression of a number
     */
    private String scanNumber() throws ScanErrorException
    {
        String number = "";

        while (isDigit(currentChar) && hasNext()) //first char is already determined to be a digit
        {
            number += currentChar;
            eat(currentChar);
        }

        return number;
    }

    /**
     * Scans the input and returns a String representing the identifier lexeme found in the input
     * stream. If no identifier is recognized, a ScanErrorException is thrown.
     *
     * Identifiers are defined by the following regular expression: letter (letter | digit)* .
     *
     * @precondition the currentChar is a letter
     * @postcondition a String representing the lexeme found is returned; if the
     *                token doesn't match the regular expression of an identifier,
     *                a ScanErrorException is thrown
     *
     * @throws ScanErrorException if the token scanned in doesn't match
     *                            the regular expression of an identifier
     */
    private String scanIdentifier() throws ScanErrorException
    {
        String identifier = "";

        while ((isLetter(currentChar) || isDigit(currentChar)) &&
                hasNext()) //first char is already determined to be a letter
        {
            identifier += currentChar;
            eat(currentChar);
        }

        return identifier;
    }

    /**
     * Scans the input and returns a String representing the operand lexeme found in the input
     * stream. If no operand is recognized, a ScanErrorException is thrown.
     *
     * Operands are defined by the following regular expression: ['=' '+' '-' '*' '/' '%' '(' ')'] .
     *
     * @precondition the current char is not a white space
     * @postcondition a String representing the lexeme found is returned; if the
     *                token doesn't match the regular expression of an operand,
     *                a ScanErrorException is thrown
     *
     * @throws ScanErrorException if the token scanned in doesn't match
     *                            the regular expression of an operand
     */
    private String scanOperand() throws ScanErrorException
    {
        String operand = "";

        if (currentChar == '=' || currentChar == '+' || currentChar == '-' || currentChar == '*' ||
                currentChar == '/' || currentChar == '%' || currentChar == '(' ||
                currentChar == ')' || currentChar == ';' || currentChar == '<' ||
                currentChar == '>' || currentChar == ':' || currentChar == ',')
        {
            operand += currentChar;
            eat(currentChar);
        }

        if ((operand.equals("<") || operand.equals(">") ||
                operand.equals(":")) && currentChar == '=') //to check for <= >= and :=
        {
            operand += currentChar;
            eat(currentChar);
        }

        else if (operand.equals("<") && currentChar == '>') //to check for <>
        {
            operand += currentChar;
            eat(currentChar);
        }

        if (operand.equals(""))
            throw new ScanErrorException("Unrecognized token: it doesn't match " +
                                            "any of the given regular expressions.");

        return operand;
    }
}

