/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author User
 */
public class Tables {

    static final int MAX_TABLE_COUNT_TOTAL = 5;
    static final int MAX_ORDER_PER_TABLE = 5;
    public static int totalTableCount = 0;
    public int totalOrderCount = 0;

    private static int tableIDCount = 0;
    private int tableID;
    private int capacity;
    private boolean inService;
    private Employer creator = null;
    private Waiter waiter = null;

    public ArrayList<Orders> orders = new ArrayList<>();

    public int getCapacity() {
        return capacity;
    }

    public int getTableID() {
        return tableID;
    }

    public boolean isInService() {
        return inService;
    }

    public void setInService(boolean inService) {
        this.inService = inService;
    }

    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    public Waiter getWaiter() {
        return waiter;
    }

    public Tables() {
    }

    public String addTable(String[] parameters) {
        if (checkEmployer(parameters[0])) {
            if (checkTotalMax()) {
                if (checkEmployerMax()) {
                    this.capacity = Integer.parseInt(parameters[1]);
                    tableID = tableIDCount;
                    tableIDCount++;
                    this.inService = false;
                    arrayListClass.tables.add(this);
                    creator.numOfTables++;
                    creator.updateSalary();
                    totalTableCount++;
                    return "A new table has succesfully been added";
                } else {
                    return parameters[0]
                            + " has already created " + Employer.MAX_TABLE + " tables!";
                }
            } else {
                return "Not allowed to exceed max. number of tables, "
                        + MAX_TABLE_COUNT_TOTAL;
            }
        } else {
            return "There is no employer named " + parameters[0];
        }
    }

    private boolean checkEmployer(String name) {
        for (Employer employer : arrayListClass.employers) {
            if (employer.name.equalsIgnoreCase(name)) {
                this.creator = employer;
                return true;
            }
        }

        return false;
    }

    private boolean checkTotalMax() {
        return totalTableCount < MAX_TABLE_COUNT_TOTAL;
    }

    private boolean checkEmployerMax() {
        return (creator.numOfTables < Employer.MAX_TABLE);
    }

    public String checkOut(String[] parameters) {
        String waiterNameTemp = parameters[0];
        int tableIdTemp = Integer.parseInt(parameters[1]);
        Tables table_temp = null;
        Waiter waiter_temp = null;

        for (Waiter waiter : arrayListClass.waiters) {
            if (waiter.name.equalsIgnoreCase(waiterNameTemp)) {
                waiter_temp = waiter;
            }
        }

        for (Tables table : arrayListClass.tables) {
            if (table.inService == true && table.getTableID() == tableIdTemp
                    && table.waiter.name.equalsIgnoreCase(waiterNameTemp)) {
                table_temp = table;
            }
        }

        if (waiter_temp == null) {
            return "There is no waiter named " + waiterNameTemp;
        }

        if (table_temp == null) {
            return "This table is either not in service now or " + waiterNameTemp + " cannot "
                    + "be assigned this table!";
        }
        String output = calculateCheck(table_temp);
        table_temp.inService = false;
        table_temp.orders.clear();
        table_temp.totalOrderCount = 0;
        table_temp.waiter = null;
        waiter_temp.numOfTable--;

        return output;
    }

    private String calculateCheck(Tables table) {
        String output = "";
        double total = 0;
        String itemName;
        double itemPrice = 0;
        int itemAmount;
        double itemTotal;
        ArrayList<String> itemNames = new ArrayList<>();

        for (Orders order : table.orders) {
            for (Items item : order.orderItems) {
                itemNames.add(item.getName());
            }
        }

        HashSet<String> uniqueNames = new HashSet<>(itemNames);

        for (String names : uniqueNames) {
            itemName = names;
            itemAmount = Collections.frequency(itemNames, names);
            for (Items item : arrayListClass.items) {
                if (item.getName().equalsIgnoreCase(itemName)) {
                    itemPrice = item.getCost();
                }
            }
            itemTotal = itemPrice * itemAmount;
            total += itemTotal;

            output += String.format("%s: %.3f (x %d) %.3f $ \n", itemName,
                    itemPrice, itemAmount, itemTotal);
        }

        output += String.format("Total: %.3f $", total);

        return output;

    }

    public String getTableStatus() {
        String output = "";
        int tableId;

        for (Tables table : arrayListClass.tables) {
            tableId = table.tableID;
            if (table.inService == false) {
                output += "Table " + tableId + ": Free\n";
            } else {
                output += String.format("Table %d: Reserved (%s)\n",
                        tableId, table.waiter.name);
            }
        }

        return output;
    }

    public String getOrderStatus() {
        String output = "";

        for (Tables table : arrayListClass.tables) {
            output += "Table: " + table.tableID + "\n";
            output += "        " + table.orders.size() + " order(s)\n";
            for (Orders order : table.orders) {
                output += "                " + order.orderItems.size() + " item(s)\n";
            }
        }

        return output;
    }

}
