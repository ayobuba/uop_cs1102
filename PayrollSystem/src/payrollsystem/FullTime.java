/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package payrollsystem;

// child of employee class
public class FullTime extends Employee {
    private double salary;
    private double overtime;

    public FullTime(int id, String name, double sal, double hourAndHalf, Vehicle vehicle) {
        super(id, name, vehicle);
        this.overtime = hourAndHalf;
        this.salary = sal;
}
    
    @Override
    public double calculatePay() {
        System.out.println("Full time employee.");
        return (this.getSalary() + this.getOvertime());
    }

    /**
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * @return the overtime
     */
    public double getOvertime() {
        return overtime;
    }

    /**
     * @param overtime the overtime to set
     */
    public void setOvertime(double overtime) {
        this.overtime = overtime;
    }
    
}
