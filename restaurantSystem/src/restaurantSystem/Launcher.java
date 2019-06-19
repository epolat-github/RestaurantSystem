/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantSystem;

import java.io.FileNotFoundException;

/**
 *
 * @author User
 */
public class Launcher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Reader reader = new Reader();

        String setupFileName = "setup.dat"; //setup file for filling the stock.
        String commandsFileName = "commands.dat"; //commands fie for testing.

        try {
            reader.fileReader(setupFileName);
            reader.fileReader(commandsFileName);
        } catch (FileNotFoundException ex) {
            System.out.println("setup or commands file not found.");
        }

    }

}
