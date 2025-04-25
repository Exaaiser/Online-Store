package com.pluralsight;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class OnlineStore {
    public static void main(String[] args) {
        // Creating our basic things for installation
        ArrayList<Product> productList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        // I added that after putting ''cart'' value
        ArrayList<Product> cart = new ArrayList<>();

        try { // We start our brick for file location and scanners
            File file = new File("src/main/resources/products.csv");
            Scanner fileScanner = new Scanner(file);
            fileScanner.nextLine(); //Skipping first line
            //Creating line split for better display If I want I can use patternQuotes
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");
                // We put our index values in []
                String sku = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                String department = parts[3];
                // And we give our product clas list here as a scanner I think
                Product product = new Product(sku, name, price, department);
                productList.add(product);
            }

            fileScanner.close(); //Closing scanner because We must IDK why xD
        } catch (FileNotFoundException e) {
            System.out.println("CSV file doesnt exist.");
            return;
            // Basic catch block actually I did copy n paste from other projects
        }

        while (true) { //Creating our menu system
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. SHOW PRODUCTS");
            System.out.println("2. VIEW CART"); //I forgot the adding this guy and I try to fix problem why Its isn't appear at screen :KEKW:
            System.out.println("3. EXIT");
            System.out.print("Your choices: "); // We want user can be able for choices
            int choice = scanner.nextInt(); // And we give int choice for at main menu user shall use numbers

            //Creating if n else statements, actually I like switch statements more but when I discussed with AI I got If commands better
            if (choice == 1) {
                System.out.println("\n--- PRODUCTS ---");
                for (Product product : productList) { // I really enjoy this guy I learned from CodeWars other people use that one for soccer game thing and I researched
                    product.display();
                }

                System.out.print("Enter SKU to add to cart or 'back' to return: ");
                scanner.nextLine();
                String skuInput = scanner.nextLine();

                //These command block Its more default one think creating string scanner for input
                if (skuInput.equalsIgnoreCase("EASTEREGG1")) {
                    System.out.println("\n NEVER GIVE UP - I SUPPORT YOU - YOU ARE NEVER ALONE \n");
                    continue;
                }

                if (!skuInput.equalsIgnoreCase("back")) {
                    boolean found = false; // I try Boolean found = false but It dosent work and I back ! statement
                    for (Product p : productList) {
                        if (p.getSku().equalsIgnoreCase(skuInput)) {
                            cart.add(p);
                            System.out.println(p.getName() + " added to cart.");
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Product not found.");
                    }
                }
            } else if (choice == 2) {
                if (cart.isEmpty()) {
                    System.out.println("Your cart is empty.");
                } else {
                    System.out.println("\n--- YOUR CART ---");
                    double total = 0;
                    for (int i = 0; i < cart.size(); i++) {  //No lies for here when I see int i = x I rush Ai AND i did again for understanding
                        Product p = cart.get(i);
                        System.out.print((i + 1) + ". ");
                        p.display();
                        total += p.getPrice();
                    } //I dont wanna deny that when I use shortcut terms like "p" I feel very cool!
                    System.out.printf("Total: $%.2f\n", total);

                    System.out.println("\n1. Remove a product");
                    System.out.println("2. Check Out");
                    System.out.println("3. Back to Main Menu");
                    System.out.print("Your choice: ");
                    int cartChoice = scanner.nextInt();
                    // Well firstly us tried int choice again (It took my 20m because I didnt understood why Its not working"

                    if (cartChoice == 1) {
                        System.out.print("Enter the number of the product to remove: ");
                        int removeIndex = scanner.nextInt(); //We search google for that and I learned removeIndex 's very usual as a term
                        if (removeIndex >= 1 && removeIndex <= cart.size()) {
                            Product removed = cart.remove(removeIndex - 1);
                            System.out.println(removed.getName() + " removed from cart.");
                        } else {
                            System.out.println("Invalid product number.");
                        }

                    } else if (cartChoice == 2) { //Every time I forget these symbols :(
                        System.out.printf("Checked out successfully! Total paid: $%.2f\n", total);
                        cart.clear();

                    } else if (cartChoice == 3) {
                        // Back to menu, do nothing
                    } else {
                        System.out.println("Invalid cart option.");
                    }
                }
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break; // Programı bitir
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}