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
public class Employer extends Worker {

    public static final int MAX_TABLE = 2; //Each employer's max table count.
    public int numOfTables = 0;

    public Employer() {
    }

    public Employer(String[] parameters) {
        this.name = parameters[0];
        this.salary = Double.parseDouble(parameters[1]);
        this.authorization = "employer";
        arrayListClass.employers.add(this);
        total_employer_count++;
    }

    public double updateSalary() {
        double bonus = numOfTables * this.salary / 10.0;
        return bonus;
    }

    public String getEmployerSalary() {
        String output = "";
        double newSalary = 0;
        for (Employer employer : arrayListClass.employers) {
            newSalary = employer.salary + employer.updateSalary();
            output += String.format("Salary for %s: %.1f\n", employer.name,
                    newSalary);
        }
        return output;
    }

}
