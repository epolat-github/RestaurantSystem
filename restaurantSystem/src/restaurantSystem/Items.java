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
public class Items {

    private String name;
    private double cost;
    public int amount;

    public Items() {
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public Items(String[] parameters) {
        this.name = parameters[0];
        this.cost = Double.parseDouble(parameters[1]);
        this.amount = Integer.parseInt(parameters[2]);
        arrayListClass.items.add(this);
    }

    public String stockStatus() {
        String output = "";
        String itemName;
        int itemCount;
        for (Items item : arrayListClass.items) {
            itemName = item.getName();
            itemCount = item.amount;
            output += String.format("%-10s %s\n", itemName + ":", itemCount);
        }

        return output;

    }
}
