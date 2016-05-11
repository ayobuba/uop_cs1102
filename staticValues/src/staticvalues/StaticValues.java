import java.io.*;

public class staticValues
{
    static int num1 = 0;
    int num2;
    
	public static void main(String a[]) throws Exception
	{
    	staticValues s1 = new staticValues();
    	s1.num1 = 5;
    	s1.num2 = 10;
    
    	staticValues s2 = new staticValues();
    
    	staticValues s3 = new staticValues();
    	s3.num1 = 15;
    	s3.num2 = 15;
    	
    	System.out.println("s1 num1=" + s1.num1 + " num2= "+s1.num2);
    	System.out.println("s2 num1=" +s2.num1+ " num2= " +s2.num2);
    	System.out.println("s3 num1=" +s3.num1+" num2= "+s3.num2);			
	}
	
}
