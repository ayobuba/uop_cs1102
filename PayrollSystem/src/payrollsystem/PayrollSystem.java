package payrollsystem;

import java.util.ArrayList;
import java.util.Scanner;

public class PayrollSystem {

    public static void main(String[] args) {
        ArrayList<Employee> arrEmp = new ArrayList<Employee> ();
        String varCont = "N";
        byte menuOption = ;
        do {
                menuOption = showMenu();
           switch (menuOption) {
               case 1:
                   FullTime ft;
                   ft = readFullTime();
                   addEmployee(arrEmp, ft); // add full time employee to arrayList
                   break;
               case 2:
                   PartTime pt;
                   pt = readNewPartTime();
                   addEmployee(arrEmp, pt); // add part time employee to arraylist
                   break;
               case 3:
                   calcPayroll(arrEmp);
                   break
               default:
                   break;
                
           } while (menuOption != 4);
        }
        
    public static FullTime readNewFullTime(){
        /* ****************************************************/
        /* this method CREATES and POPULATES Employee objects
        /* Parameters: none
        /* Return values: new FullTime
        /* ****************************************************/
        int id=0;
        String name=null;
        double sal=0.0;
        double hourAndHalf = 0.0; // overtime
        Scanner kbd = new Scanner(System.in);
        System.out.println("Enter id: ");       id = kbd.nextInt();
        System.out.append("\nEnter Name: ");    name = kdb.next();
        System.out.println("\nEnter Salary");   sal = kdb.nextDouble();
        System.out.println("\nEnter Bonus: ");  hourAndHalf = kbd.nextDouble();
        
        FullTime ft1 = null;
        ft1 = new FullTime(id, name, sal, hourAndHalf, getVehicle());
        
        return ft1;
    }
        
   public static PartTime readNewPartTime () {
        /* ****************************************************/
        /* this method CREATES and POPULATES Employee objects
        /* Parameters: none
        /* Return values: new FullTime
        /* ****************************************************/
       int id=0;
        String name=null;
        double sal=0.0;
        double hourAndHalf = 0.0; // overtime
        
        Scanner kbd = new Scanner(System.in);
        System.out.println("Enter id: ");       id = kbd.nextInt();
        System.out.append("\nEnter Name: ");    name = kbd.next();
        System.out.println("\nEnter Salary");   sal = kbd.nextDouble();
        System.out.println("\nEnter Bonus: ");  hoursWorked = kbd.nextDouble();
        
        PartTime v1 = getVehicle;
        PartTime pt1 = null;
        pt1 = new PartTime(id, name, rate, v1);
        
        return pt1;
   }
   
   public static byte showMenu () {
       byte menuOption = 0;
       Scanner kdb = new Scanner(System.in);
       
       System.out.println(""
               + "/* *****************************************************************/"
               + "\n/* 1. Add FullTime                                               */"
               + "\n/* 2. Add PartTime                                               */"
               + "\n/* 3. Calculate Payroll                                          */"
               + "\n/* 4. Exit                                                       */"
               + "\n/* ***************************************************************/");
       System.out.println("Input: ");                menuOption = kbd.nextByte();
       
       return menuOption;
   }
   
   public static Vehicle getVehicle() {
       /* 
        * creates and returns a Vehicle object if "Y". Else returns null
        */
       Scanner kbd = new Scanner(System.in);
       String hasVehicle = "N";
       
       System.out.print("\nDoes this employee have a vehicle? Y/N : \nInput : ");
       hasVehicle = kbd.next();
       
       if (hasVehicle.equalsIgnoreCase("Y")) {
           // create and populate object vehicle
           System.out.print("\nEnter plate number: "); String auxPlate = kbd.next();
           System.out.print("\nEnter vehicle color: "); String auxColor = kbd.next();
           return (new Vehicle(auxPlate, auxColor));
       }
       else { // employee does not have a vehicle
           return (null);
       }
   }
           
   public static void addEmployee(ArrayList<Employee> pArrEmp, Employee pEmp) {
       // this method adds one employee e, to the arrayList arrEmp 
       pArrEmp.add(pEmp);
              
   }
   
   public static void calcPayroll(ArrayList<Employee> pArrEmp) {
       double totalCompanyPay = 0.0;
       double individualPay;
       
       // calculate salary - manipulate array only
       for (int i=0; i<pArrEmp.size; i++) {
           System.out.println("\n*********************************\n");
           individualPay = (pArrEmp.get(i)).calculatePay();
           Vehicle v = (pArrEmp.get(i)).getVehicle();
           String hasVehicle;
           
           // check if employee has vehicle
           if (v == null)
               hasVehicle = "No";
           else
               hasVehicle = "Yes";
           
           System.out.println("Employee Name: " + (pArrEmp.get(i)).getName());
           System.out.println("Has Vehicle: " + hasVehicle);
           
           if (v != null) {
               System.out.println("Plate Number: " + v.getPlateNumber());
               System.out.println("Color" + v.getColor());
           }
           
           System.out.println("Take Home Pay: " + individualPay);
           
           totalCompanyPay = totalCompanyPay + individualPay;
          
       }
       System.out.print("------------------------\nTotal payroll of the company: " + totalCompanyPay + "\n----------------");
   }
    }
        


