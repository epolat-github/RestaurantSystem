/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantSystem;

/**
 *
 * @author User
 */
public class Waiter extends Worker {

    public static final int MAX_TABLE = 3;
    public int numOfTable = 0;
    public int orderCount = 0;
    Tables table;

    public Waiter() {
    }

    public Waiter(String[] parameters) {
        this.name = parameters[0];
        this.salary = Double.parseDouble(parameters[1]);
        this.authorization = "waiter";
        arrayListClass.waiters.add(this);
        total_waiter_count++;
    }

    public double updateSalary() {
        double bonus = orderCount * this.salary * 5.0 / 100.0;
        return bonus;
    }

    public String getWaiterSalary() {
        String output = "";
        double newSalary;
        for (Waiter waiter : arrayListClass.waiters) {
            newSalary = waiter.salary + waiter.updateSalary();
            output += String.format("Salary for %s: %.1f\n", waiter.name,
                    newSalary);
        }
        return output;
    }

}
