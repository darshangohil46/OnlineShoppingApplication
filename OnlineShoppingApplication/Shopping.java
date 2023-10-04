package OnlineShoppingApplication;

import java.util.*;
import java.sql.*;
import java.io.*;

class Login_complete {
    static Scanner sc = new Scanner(System.in);
    String nameOfItem;
    double priceOfItem;
    String catagory;

    Login_complete(String nameOfItem, double priceOfItem, String catagory) {
        this.nameOfItem = nameOfItem;
        this.priceOfItem = priceOfItem;
        this.catagory = catagory;
    }
}

public class Shopping {
    // all data from main file
    static String userName = "";
    static String myName = "";
    static String phone = "";

    public static void allDetails(String name, String username, String myphone) {
        userName = username;
        myName = name;
        phone = myphone;
    }

    static Connection con;
    static Scanner sc = new Scanner(System.in);
    // static String name = myName;
    // static String phonenum = phone;
    // static String username = userName;

    static FileWriter fw1;
    static BufferedWriter bw1;

    // for store shopping items
    static HashMap<Integer, Login_complete> grocery = new HashMap<>();
    static Hashtable<Integer, Login_complete> fashion = new Hashtable<>();
    static Hashtable<Integer, Login_complete> fruits_vegetables = new Hashtable<>();
    static Hashtable<Integer, Login_complete> electronics = new Hashtable<>();

    static double total_grocery_price = 0;
    static double total_fashion_price = 0;
    static double total_fruit_and_veg_price = 0;
    static double total_electronics_price = 0;

    // total amount
    public static double total_payable_amount = 0;
    // query for insert data in database
    static String cart_concat = "";
    static String sql_data_fetch = "select cart from shopping_app where username='" + userName + "';";
    static String url = "jdbc:postgresql://localhost:5432/postgres";
    static String insert_data = "";
    static String cart_fetch = "";

    // method for shopping
    public static void my_shopping() throws Exception {
        fw1 = new FileWriter("shopping_detail.txt", true);
        bw1 = new BufferedWriter(fw1);

        bw1.write("Name --> " + myName + "\t");
        bw1.write("User Name --> " + userName + "\t");
        bw1.write("Phone Number --> " + phone + "\n");
        bw1.flush();
        boolean b = true;
        while (b) {
            System.out.println("-----------------------------------");
            System.out.println("Enter 0 Back");
            System.out.println("Enter 1 Grocery");
            System.out.println("Enter 2 Fashion");
            System.out.println("Enter 3 Fruits and Vegetables");
            System.out.println("Enter 4 Electronics");
            System.out.println("-----------------------------------");

            System.out.print("Enter Your Choice: ");
            int n = sc.nextInt();
            Payment.done_payment = 0;
            switch (n) {
                case 1:
                    groceries();
                    break;
                case 2:
                    fashions();
                    break;
                case 3:
                    fruits_vegetable();
                    break;
                case 4:
                    electronic();
                    break;
                case 0:
                    b = false;
                    break;
                default:
                    System.out.println("Enter valid number...");
                    break;
            }
        }
    }

    // method for shopping groceries
    static void groceries() throws Exception {
        // JDBC
        con = DriverManager.getConnection(url, "postgres", "462005");

        fw1 = new FileWriter("shopping_detail.txt", true);
        bw1 = new BufferedWriter(fw1);

        grocery.put(1, new Login_complete("Solt 1 Kg", 25.00, "grocery"));
        grocery.put(2, new Login_complete("Almond 500 gm", 300.00, "grocery"));
        grocery.put(3, new Login_complete("Flour Of Wheat 1 kg", 45.00, "grocery"));
        grocery.put(4, new Login_complete("Sugar 1 Kg", 50.00, "grocery"));
        grocery.put(5, new Login_complete("Cottenseed Oil 1 Kg", 150.00, "grocery"));
        grocery.put(6, new Login_complete("Rise 1 Kg", 50.00, "grocery"));
        grocery.put(7, new Login_complete("Wheat 1 Kg", 35.00, "grocery"));
        grocery.put(8, new Login_complete("Flour Of Rise 1 kg", 60.00, "grocery"));

        boolean b = true;
        while (b) {
            System.out.println("0. Back");
            System.out.println("1. Solt 1 kg --> 25 Rupees");
            System.out.println("2. Almonds 500 gm --> 300 Rupees");
            System.out.println("3. Flour Of Wheat 1 kg --> 45 Rupees");
            System.out.println("4. Sugar 1 kg --> 50 Rupees");
            System.out.println("5. Cottenseed oil 1 kg --> 150 Rupees");
            System.out.println("6. Rise 1 kg --> 50 Rupees");
            System.out.println("7. Wheat 1 kg --> 35 Rupees");
            System.out.println("8. Flour Of Rise 1 kg --> 60 Rupees");

            boolean b1 = true;
            int flag = 0;

            while (b1) {
                System.out.print("Enter key value for shopping: ");
                int key = sc.nextInt();
                if (key == 0) {
                    b1 = false;
                    b = false;
                } else if (grocery.containsKey(key)) {
                    System.out.print("Enter Quntity: ");
                    int quntity = sc.nextInt();
                    if (quntity != 0) {
                        String name = grocery.get(key).nameOfItem;
                        double price = grocery.get(key).priceOfItem;

                        total_grocery_price = total_grocery_price + (price * quntity);
                        flag++;
                        Cart.myCart(name, price, quntity, "grocery");
                        // write in Shopping_detail file
                        bw1.write("-------------------------------\n");
                        bw1.write("Name Of Item: " + name + "\n");
                        bw1.write("Price Of Item: " + price + "\n");
                        bw1.write("Quntity: " + quntity + "\n");
                        bw1.flush();
                    }
                } else {
                    System.out.println("Please! Enter valid number...");
                }
            }
            if (flag > 0) {
                bw1.write("Total Order Of Grocery: " + flag + "\n");
                bw1.write("Total Payable Amount Of Grocery: " + total_grocery_price + "\n");
            }
            System.out.println();
            if (flag > 0) {
                System.out.println("Order add in cart.");
            }
            bw1.newLine();
            bw1.flush();
            // total_payable_amount += total_grocery_price;
            System.out.println("Groceries Amount: " + Shopping.total_grocery_price + " Rupees");
        }
    }

    // method for fashion shopping
    static void fashions() throws Exception {

        fw1 = new FileWriter("shopping_detail.txt", true);
        bw1 = new BufferedWriter(fw1);

        fashion.put(1, new Login_complete("Watch for Men", 1500.00, "fashion"));
        fashion.put(2, new Login_complete("T-Shirt for Men", 500.00, "fashion"));
        fashion.put(3, new Login_complete("Shirt", 350.00, "fashion"));
        fashion.put(4, new Login_complete("Sport shoes", 400.00, "fashion"));
        fashion.put(5, new Login_complete("Jeans", 500.00, "fashion"));
        fashion.put(6, new Login_complete("T-Shirt for Women", 450.00, "fashion"));
        fashion.put(7, new Login_complete("Watch for Women", 1000.00, "fashion"));
        fashion.put(8, new Login_complete("Smart Watch", 2500.00, "fashion"));

        boolean b = true;
        while (b) {
            System.out.println("0. Back");
            System.out.println("1. Watch for Men --> 1500 Rupees");
            System.out.println("2. T-shirt for Men --> 500 Rupees");
            System.out.println("3. Shirt  --> 350 Rupees");
            System.out.println("4. Sport shoes --> 400 Rupees");
            System.out.println("5. Jeans --> 500 Rupees");
            System.out.println("6. T-shirt for Women --> 450 Rupees");
            System.out.println("7. Watch for Women --> 1000 Rupees");
            System.out.println("8. Smart Watch --> 2500 Rupees");

            boolean b1 = true;
            int flag = 0;

            while (b1) {
                System.out.print("Enter key value for shopping: ");
                int key = sc.nextInt();
                if (key == 0) {
                    b1 = false;
                    b = false;
                } else if (fashion.containsKey(key)) {
                    System.out.print("Enter Quntity: ");
                    int quntity = sc.nextInt();
                    if (quntity != 0) {
                        String name = fashion.get(key).nameOfItem;
                        double price = fashion.get(key).priceOfItem;

                        total_fashion_price = total_fashion_price + (price * quntity);
                        flag++;
                        Cart.myCart(name, price, quntity, "fashion");
                        // write in Shopping_detail file
                        bw1.write("-------------------------------\n");
                        bw1.write("Name Of Item: " + name + "\n");
                        bw1.write("Price Of Item: " + price + "\n");
                        bw1.write("Quntity: " + quntity + "\n");
                        bw1.flush();
                    }
                } else {
                    System.out.println("Please! Enter valid number...");
                }
            }
            if (flag > 0) {
                bw1.write("Total Order Of Fashion: " + flag + "\n");
                bw1.write("Total Payable Amount Of Fashion: " + total_fashion_price + "\n");
            }
            System.out.println();
            if (flag > 0) {
                System.out.println("Order add in cart.");
            }
            bw1.newLine();
            bw1.flush();
            // total_payable_amount += total_fashion_price;
            System.out.println("Fashion Amount: " + Shopping.total_fashion_price + " Rupees");
        }
    }

    // method for electronics shopping
    static void electronic() throws Exception {
        fw1 = new FileWriter("shopping_detail.txt", true);
        bw1 = new BufferedWriter(fw1);

        electronics.put(1, new Login_complete("Samsung Smart Phone", 20000.00, "electronic"));
        electronics.put(2, new Login_complete("Realme Smart Phone", 10000.00, "electronic"));
        electronics.put(3, new Login_complete("Vivo Smart Phone", 15000.00, "electronic"));
        electronics.put(4, new Login_complete("OnePlus Smart Phone", 30000.00, "electronic"));
        electronics.put(5, new Login_complete("Apple iPhone 14", 75000.00, "electronic"));
        electronics.put(6, new Login_complete("43-inch Samsung Smart TV", 35000.00, "electronic"));
        electronics.put(7, new Login_complete("32-inch Samsung Smart TV", 15000.00, "electronic"));
        electronics.put(8, new Login_complete("32-inch RealMe TV", 10000.00, "electronic"));
        electronics.put(9, new Login_complete("43-inch RealMe Smart TV", 25000.00, "electronic"));

        boolean b = true;
        while (b) {
            System.out.println("0. Back");
            System.out.println("1. Samsung Smart Phone --> 20000");
            System.out.println("2. Realme Smart Phone --> 10000");
            System.out.println("3. Vivo Smart Phone --> 15000");
            System.out.println("4. OnePlus Smart Phone --> 30000");
            System.out.println("5. Apple iPhone 14 --> 75000");
            System.out.println("6. 43-inch Samsung Smart TV --> 35000");
            System.out.println("7. 32-inch Samsung Smart TV --> 15000");
            System.out.println("8. 32-inch RealMe Smart TV --> 10000");
            System.out.println("9. 43-inch RealMe Smart TV --> 25000");

            boolean b1 = true;
            int flag = 0;

            while (b1) {
                System.out.print("Enter key value for shopping: ");
                int key = sc.nextInt();
                if (key == 0) {
                    b1 = false;
                    b = false;
                } else if (electronics.containsKey(key)) {
                    System.out.print("Enter Quntity: ");
                    int quntity = sc.nextInt();
                    if (quntity != 0) {
                        String name = electronics.get(key).nameOfItem;
                        double price = electronics.get(key).priceOfItem;

                        total_electronics_price = total_electronics_price + (price * quntity);
                        flag++;
                        Cart.myCart(name, price, quntity, "electronic");
                        // write in Shopping_detail file
                        bw1.write("-------------------------------\n");
                        bw1.write("Name Of Item: " + name + "\n");
                        bw1.write("Price Of Item: " + price + "\n");
                        bw1.write("Quntity: " + quntity + "\n");
                        bw1.flush();
                    }
                } else {
                    System.out.println("Please! Enter valid number...");
                }
            }
            if (flag > 0) {
                bw1.write("Total Order Of Electronics: " + flag + "\n");
                bw1.write("Total Payable Amount Of Electronics: " + total_electronics_price + "\n");
            }
            System.out.println();
            if (flag > 0) {
                System.out.println("Order add in cart.");
            }
            bw1.newLine();
            bw1.flush();
            // total_payable_amount += total_electronics_price;
            System.out.println("Electronics Amount: " + Shopping.total_electronics_price + " Rupees");
        }
    }

    // method for fruits and vegetables shopping
    static void fruits_vegetable() throws Exception {
        fw1 = new FileWriter("shopping_detail.txt", true);
        bw1 = new BufferedWriter(fw1);

        fruits_vegetables.put(1, new Login_complete("Carrot 1 kg", 50.00, "fruit_veg"));
        fruits_vegetables.put(2, new Login_complete("Onion 1 kg", 20.00, "fruit_veg"));
        fruits_vegetables.put(3, new Login_complete("Tomato 1 kg", 30.00, "fruit_veg"));
        fruits_vegetables.put(4, new Login_complete("Potato 1 kg", 20.00, "fruit_veg"));
        fruits_vegetables.put(5, new Login_complete("Orange 1 kg", 70.00, "fruit_veg"));
        fruits_vegetables.put(6, new Login_complete("Green Chilli 1 kg", 80.00, "fruit_veg"));
        fruits_vegetables.put(7, new Login_complete("Apple 1 kg", 200.00, "fruit_veg"));
        fruits_vegetables.put(8, new Login_complete("Water Melon 1 kg", 50.00, "fruit_veg"));

        boolean b = true;
        while (b) {
            System.out.println("0. Back");
            System.out.println("1. Carrot 1 kg --> 50 Rupees");
            System.out.println("2. Onion 1 kg --> 20 Rupees");
            System.out.println("3. Tomato 1 kg --> 30 Rupees");
            System.out.println("4. Potato 1 kg --> 20 Rupees");
            System.out.println("5. Orange 1 kg --> 70 Rupees");
            System.out.println("6. Green Chilli 1 kg --> 80 Rupees");
            System.out.println("7. Apple 1 kg --> 200 Rupees");
            System.out.println("8. Water Melon 1 kg --> 50 Rupees");

            boolean b1 = true;
            int flag = 0;

            while (b1) {
                System.out.print("Enter key value for shopping: ");
                int key = sc.nextInt();
                if (key == 0) {
                    b1 = false;
                    b = false;
                } else if (fruits_vegetables.containsKey(key)) {
                    System.out.print("Enter Quntity: ");
                    int quntity = sc.nextInt();
                    if (quntity != 0) {
                        String name = fruits_vegetables.get(key).nameOfItem;
                        double price = fruits_vegetables.get(key).priceOfItem;

                        total_fruit_and_veg_price = total_fruit_and_veg_price + (price * quntity);
                        flag++;
                        Cart.myCart(name, price, quntity, "fruit_veg");
                        // write in Shopping_detail file
                        bw1.write("-------------------------------\n");
                        bw1.write("Name Of Item: " + name + "\n");
                        bw1.write("Price Of Item: " + price + "\n");
                        bw1.write("Quntity: " + quntity + "\n");
                        bw1.flush();
                    }
                } else {
                    System.out.println("Please! Enter valid number...");
                }
            }
            if (flag > 0) {
                bw1.write("Total Order Of Fruits & Veg: " + flag + "\n");
                bw1.write("Total Payable Amount Of Fruits & Veg: " + total_fruit_and_veg_price + "\n");
            }
            System.out.println();
            if (flag > 0) {
                System.out.println("Order add in cart.");
            }
            bw1.newLine();
            bw1.flush();
            // total_payable_amount += total_fruit_and_veg_price;
            System.out.println("Fruits & Vegetables Amount: " + Shopping.total_fruit_and_veg_price + " Rupees");
        }
    }
}
