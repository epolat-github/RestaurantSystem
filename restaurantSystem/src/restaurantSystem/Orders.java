/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantSystem;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Orders {

    static final int MAX_ITEMS_PER_ORDER = 10; //Max 10 items per order.

    ArrayList<Items> orderItems = new ArrayList<>();
    private ArrayList<String> itemsTemp = new ArrayList<>();

    private String waiter_temp;
    private int customer_num_temp;

    private Tables table;

    public Orders() {
    }

    //gives order if all conditions are appropriate
    public String giveOrder(String[] parameters) {
        this.waiter_temp = parameters[0];
        this.customer_num_temp = Integer.parseInt(parameters[1]);

        if (!findTable()) {
            return "There is no appropriate table for this order!\n";
        }

        if (!checkWaiterExist()) {
            return "There is no waiter named " + waiter_temp + "\n";
        }

        if (!checkWaiterMaxTable(this.table.getWaiter())) {
            return "Not allowed to service max. number of tables, " + Waiter.MAX_TABLE + "\n";
        }

        System.out.format("Table (= ID %d) has been taken into service\n", table.getTableID());
        this.table.getWaiter().numOfTable++;
        this.table.totalOrderCount++;
        this.table.setInService(true);
        this.table.getWaiter().orderCount++;

        return orderItems(parameters[2].split(":"));
    }

    public String addOrder(String[] parameters) {
        this.waiter_temp = parameters[0];

        switch (checkTable(Integer.parseInt(parameters[1]))) {
            case 0: {
                this.table.totalOrderCount++;
                this.table.getWaiter().orderCount++;
                return orderItems(parameters[2].split(":"));

            }
            case 1: {
                return "Not allowed to exceed max number of orders!";

            }
            case 2: {
                return "This table is either not in service now or " + this.waiter_temp + " cannot "
                        + "be assigned this table!";
            }
            default: {
                return "problem";
            }
        }
    }

    //Checks the table's condition
    private int checkTable(int tableID) {
        for (Tables table_temp : arrayListClass.tables) {
            if (table_temp.getTableID() == tableID
                    && table_temp.isInService() == true
                    && this.waiter_temp.equalsIgnoreCase(table_temp.getWaiter().name)) {
                this.table = table_temp;
                if (checkTableMaxOrder() == false) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
        return 2;
    }

    private boolean checkTableMaxOrder() {
        return this.table.totalOrderCount < Tables.MAX_ORDER_PER_TABLE;
    }

    private boolean findTable() {
        for (Tables table_temp : arrayListClass.tables) {
            if (this.customer_num_temp <= table_temp.getCapacity()
                    && table_temp.isInService() == false) {
                this.table = table_temp;
                return true;
            }
        }

        return false;
    }

    private boolean checkWaiterExist() {
        for (Waiter waiter : arrayListClass.waiters) {
            if (this.waiter_temp.equalsIgnoreCase(waiter.name)) {
                this.table.setWaiter(waiter);
                return true;
            }
        }
        return false;
    }

    private boolean checkWaiterMaxTable(Waiter waiter) {
        return waiter.numOfTable != Waiter.MAX_TABLE;
    }

    private String orderItems(String[] items) {

        splitOrders(items);
        return sellItems();

    }

    private void splitOrders(String[] items) {
        for (String order : items) {
            String[] temp = order.split("-");
            for (int i = 0; i < (Integer.parseInt(temp[1])); i++) {
                this.itemsTemp.add(temp[0]);
            }
        }
    }

    private String sellItems() {
        String output = "";
        String orderName_temp;
        for (int i = 0; i < itemsTemp.size(); i++) {
            orderName_temp = itemsTemp.get(i);
            boolean flag = true;
            for (Items item : arrayListClass.items) {
                if (orderName_temp.equalsIgnoreCase(item.getName())) {
                    flag = false;
                    if (item.amount > 0) {
                        output += ("Item " + orderName_temp + " added into order\n");
                        orderItems.add(item);
                        item.amount--;
                    } else {
                        output += ("Sorry! No " + orderName_temp + " in the stock!\n");
                    }
                }
            }
            if (flag) {
                output += ("Unknown item " + orderName_temp + "\n");
            }
        }
        this.table.orders.add(this);
        return output;
    }

}
