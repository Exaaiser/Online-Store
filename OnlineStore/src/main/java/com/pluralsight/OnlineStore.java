package com.pluralsight;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class OnlineStore {
    public static void main(String[] args) {
        // Creating our basic array list blablas
        Map<String, Product> productMap = new HashMap<>();

        // Creating our basic things for installation
        Map<String, Integer> cart = new HashMap<>();
        // We create cart Hashmap and these guy makes everything
        Scanner scanner = new Scanner(System.in);

        try {
            // We start our brick for file location and scanners
            File file = new File("src/main/resources/products.csv");
            Scanner fileScanner = new Scanner(file);
            fileScanner.nextLine(); // skip header row

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
                productMap.put(sku, product);  // Add product to the productMap
            }

            fileScanner.close(); //Closing scanner because We must IDK why xD
        } catch (FileNotFoundException e) {
            System.out.println("CSV file not found.");  // Basic catch block actually I did copy n paste from other projects
            return;
        }

        boolean running = true;
        //Creating our menu system
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart"); //We forgot the adding this guy and I try to fix problem why Its isn't appear at screen :KEKW:
            System.out.println("3. Exit");
            System.out.print("Your choice: ");// We want user can be able for choices

            String choice = scanner.nextLine(); // And we give String choice for at main menu user shall use numbers

            switch (choice) {//Creating switch statements, actually I like If statements more but when I discussed with AI I got If commands better

                case "1":  // Show available products
                    System.out.println("\n--- PRODUCTS ---");

                    for (Product product : productMap.values()) {
                        product.display();
                    }

                    System.out.print("Enter SKU to add to cart or 'back' to return: ");
                    String skuInput = scanner.nextLine().trim();  // User input for ID
                    if (skuInput.equalsIgnoreCase("Turkish League")) {
                        System.out.println("🧿 Mytic Kebap Power It was here!");
                        break; // hehehehehhehehe
                    }
                    if (!skuInput.equalsIgnoreCase("back")) {
                        Product selected = productMap.get(skuInput.toUpperCase());  // Get product by SKU
                        if (selected != null) {
                            //Add the selected product to the car, increasing quantity if already added
                            cart.put(skuInput.toUpperCase(), cart.getOrDefault(skuInput.toUpperCase(), 0) + 1);
                            System.out.println(selected.getName() + " added to cart.");
                        } else {
                            System.out.println("Product not found.");
                        }
                    }
                    break;

                case "2":  // Show the current our list
                    if (cart.isEmpty()) {
                        System.out.println("Your cart is empty.");
                        break;
                    }

                    System.out.println("\n--- YOUR CART ---");
                    double total = 0.0;
                    // Display each item in the cart along with its quantity and total price
                    for (String sku : cart.keySet()) {
                        Product p = productMap.get(sku);
                        int quantity = cart.get(sku);
                        double itemTotal = p.getPrice() * quantity;
                        System.out.printf("%s x%d - $%.2f\n", p.getName(), quantity, itemTotal);
                        total += itemTotal;  // For see total price of prodcuts
                    }
                    System.out.printf("Total: $%.2f\n", total); //We searched  all Internet for this symbols

                    System.out.println("1. Remove an item");
                    System.out.println("2. Check Out");
                    System.out.println("3. Back to Main Menu");
                    System.out.print("Your choice: ");
                    String subChoice = scanner.nextLine();
                    //Actually all of them are basic things I wanna explain that but I d take for ever I think

                    if (subChoice.equals("1")) {  // Remove an item from the cart
                        System.out.print("Enter SKU to remove from cart: "); //We used String based remove cause Int one would be problem
                        String removeSku = scanner.nextLine().trim().toUpperCase();
                        if (cart.containsKey(removeSku)) {
                            int currentQuantity = cart.get(removeSku); // Quantity thigs
                            if (currentQuantity > 1) {
                                cart.put(removeSku, currentQuantity - 1);  // Decrease the quantity by 1
                                System.out.println("One unit removed. Remaining: " + (currentQuantity - 1));
                            } else {
                                cart.remove(removeSku);  // Remove the product entirely if only one left
                                System.out.println("Product removed from cart.");
                            }
                        } else {
                            System.out.println("Product not found in cart.");
                        }

                    } else if (subChoice.equals("2")) {
                        // Generate receipt for checkout and
                        System.out.println("\n--- RECEIPT ---");
                        System.out.println("Thank you for your purchase!");
                        double receiptTotal = 0.0;
                        for (String sku : cart.keySet()) {
                            Product p = productMap.get(sku); // I feel very cool when I use little shortcuts like "p"
                            int quantity = cart.get(sku);
                            double itemTotal = p.getPrice() * quantity;
                            System.out.printf("%s x%d - $%.2f\n", p.getName(), quantity, itemTotal);
                            receiptTotal += itemTotal;
                        }
                        System.out.printf("Sales Total: $%.2f\n", receiptTotal);

                        // Prompt user for payment
                        System.out.print("Enter payment amount: $");
                        double payment = scanner.nextDouble();
                        scanner.nextLine();  // for leftovering

                        // Verify payment
                        if (payment >= receiptTotal) {
                            double change = payment - receiptTotal;
                            System.out.printf("Change Given: $%.2f\n", change);

                            // Print receipt with payment and change
                            System.out.println("\n--- SALES RECEIPT ---");
                            System.out.println("Order Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                            for (String sku : cart.keySet()) {
                                Product p = productMap.get(sku);
                                int quantity = cart.get(sku);
                                System.out.printf("%s x%d - $%.2f\n", p.getName(), quantity, p.getPrice() * quantity);
                            }
                            System.out.printf("Sales Total: $%.2f\n", receiptTotal);
                            System.out.printf("Amount Paid: $%.2f\n", payment);
                            System.out.printf("Change Given: $%.2f\n", change);

                            //Nobody can know we just copy paste "$%.2f"

                            // Generate receipt file
                            generateReceiptFile(receiptTotal, payment, change);

                            cart.clear();  // Clear the cart after checkout
                            System.out.println("Thank you for shopping with us!");
                            running = false;  // Exit after checkout
                        } else {
                            System.out.println("Insufficient payment. Please try again.");
                        }

                    }
                    break;

                case "3":  // Exit the program
                    System.out.println("Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }

        scanner.close();  // Close the scanner
    }

    // Method to generate receipt file and we spent lot of minutes here.
    private static void generateReceiptFile(double total, double paid, double change) {
        try {
            //  We creating filereader and string scanners
            String timestamp = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
            File receiptFile = new File("Receipts/" + timestamp + ".txt");
            receiptFile.getParentFile().mkdirs();
            // Actually we got "mkdirs" from Ai conversation  I remember these thing from GitBash, I can say just suprsing

            // Making receipt file for
            FileWriter writer = new FileWriter(receiptFile);
            writer.write("--- SALES RECEIPT ---\n");
            writer.write("Order Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
            writer.write("Sales Total: $" + total + "\n");
            writer.write("Amount Paid: $" + paid + "\n");
            writer.write("Change Given: $" + change + "\n");
            writer.close();  // Close the file writer
            System.out.println("Receipt saved to " + receiptFile.getAbsolutePath());
        } catch (IOException e) { // And our basic catch command
            System.out.println("Error generating receipt file: " + e.getMessage());
        }
    }
}
