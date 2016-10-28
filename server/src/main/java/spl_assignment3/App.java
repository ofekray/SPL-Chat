package spl_assignment3;

import java.io.IOException;
import java.util.Scanner;

import reactor.Reactor;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Enter the number for your desired server model");
    	System.out.println("1.Reactor Server      2.ThreadPerClient Server");
    	String line = sc.nextLine();
    	int num;
    	try
    	{
    		num = Integer.parseInt(line);
    		if (num == 1)
    		{
    			Reactor.mainRun(args);
    		}
    		else
    		{
    			if (num == 2)
    			{
    				try {
    					TPC.MessagingServer.mainRun(args);
    				} catch (NumberFormatException | IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    			else
    			{
    				System.out.println("You did not enter a valid number...");
    			}
    		}
    	}
    	catch (Exception ex)
    	{
    		System.out.println("You did not enter a number...");
    	}
    }
}
