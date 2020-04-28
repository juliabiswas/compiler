package parser;

import scanner.Scanner;

import java.io.File;
import java.io.FileInputStream;

import environment.Environment;
import ast.Program;

public class ParserTester
{
    /**
     * Oversees the running of the program.
     *
     * @param args arguments from the command line
     */
    public static void main(String[] args)
    {
        try
        {
            FileInputStream inStream = new FileInputStream(new File("parserTest8.txt"));
            Scanner scan = new Scanner(inStream);

            Parser pars = new Parser(scan);
            Environment env = new Environment(null);

            while (scan.hasNext())
            {
                Program prog = pars.parseProgram();
                prog.exec(env);
            }
        }

        catch (Exception e)
        {
            System.out.println("Exception while trying to read file");
            e.printStackTrace();
        }
    }
}
