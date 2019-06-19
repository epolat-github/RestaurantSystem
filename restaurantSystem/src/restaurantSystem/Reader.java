/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class Reader {

    String line;
    String[] command;
    String[] parameters;

    public void fileReader(String name) throws FileNotFoundException {
        String path = getClass().getResource("exampleInputs/" + name).getPath();
        Scanner read = new Scanner(new File(path));

        while (read.hasNextLine()) {
            line = read.nextLine();
            command = line.split(" ");
            checkCommand(command[0]);
        }
    }

    private void checkCommand(String command) {
        switch (command) {
            case ("add_item"): {
                parameters = this.command[1].split(";");
                Items item = new Items(parameters);
                break;
            }

            case ("add_employer"): {
                parameters = this.command[1].split(";");
                if (Worker.total_employer_count < Worker.MAX_EMPLOYER_COUNT) {
                    Employer employer = new Employer(parameters);
                }
                break;
            }

            case ("add_waiter"): {
                parameters = this.command[1].split(";");
                if (Worker.total_waiter_count < Worker.MAX_WAITER_COUNT) {
                    Waiter waiter = new Waiter(parameters);
                }
                break;
            }

            case ("create_table"): {
                System.out.println("PROGRESSING COMMAND: create_table");
                parameters = this.command[1].split(";");
                Tables table = new Tables();
                System.out.println(table.addTable(parameters));
                break;
            }
            case ("new_order"): {
                System.out.println("PROGRESSING COMMAND: new_order");
                parameters = this.command[1].split(";");
                Orders order = new Orders();
                String output = order.giveOrder(parameters);

                System.out.print(output);
                break;
            }
            case ("add_order"): {

                System.out.println("PROGRESSING COMMAND: add_order");
                parameters = this.command[1].split(";");
                Orders order = new Orders();
                String output = order.addOrder(parameters);
                System.out.print(output);
                break;
            }

            case ("check_out"): {
                System.out.println("PROGRESSING COMMAND: check_out");
                parameters = this.command[1].split(";");
                Tables table = new Tables();
                System.out.println(table.checkOut(parameters));
                break;
            }

            case ("stock_status"): {
                System.out.println("PROGRESSING COMMAND: stock_status");
                Items item = new Items();
                System.out.print(item.stockStatus());
                break;
            }

            case ("get_table_status"): {
                System.out.println("PROGRESSING COMMAND: get_table_status");
                Tables table = new Tables();
                System.out.print(table.getTableStatus());
                break;
            }

            case ("get_order_status"): {
                System.out.println("PROGRESSING COMMAND: get_order_status");
                Tables table = new Tables();
                System.out.print(table.getOrderStatus());
                break;
            }

            case ("get_employer_salary"): {
                System.out.println("PROGRESSING COMMAND: get_employer_salary");
                Employer employer = new Employer();
                System.out.print(employer.getEmployerSalary());
                break;
            }
            case ("get_waiter_salary"): {
                System.out.println("PROGRESSING COMMAND: get_waiter_salary");
                Waiter waiter = new Waiter();
                System.out.print(waiter.getWaiterSalary());
                break;
            }
        }
    }
}
