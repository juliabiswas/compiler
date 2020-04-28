package scanner;

import java.io.*;

public class ScannerTester
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
            FileInputStream inStream = new FileInputStream(new File("scannerTestAdvanced.txt"));
            Scanner scan = new Scanner(inStream);

            while (scan.hasNext())
            {
                String token = scan.nextToken();
                System.out.print(token);
                if (!token.equals("")) //so that nothing is printed for an empty token
                    System.out.print("\n");
            }
        }
        catch (Exception e)
        {
            System.out.println("Exception while trying to read file");
        }
    }
}
