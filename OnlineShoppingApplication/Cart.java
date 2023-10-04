package OnlineShoppingApplication;

import java.util.*;
import java.sql.*;

public class Cart {
    static Scanner sc = new Scanner(System.in);
    static int one_attmp = 0;

    static Connection con;
    static String url = "jdbc:postgresql://localhost:5432/postgres";
    static Hashtable<Integer, Cart> cart = new Hashtable<>();
    static Hashtable<Integer, Cart> cart2;
    static int key = 1;
    static double total_payable_amount;
    static double del_price = 0;

    static String data_of_cart = "";
    static int remove_once = 0;

    String name = null;
    String catagory = null;
    double price = 0;
    int quntity = 0;

    // all data from main file
    static String userName = "";
    static String myName = "";
    static String phoneNum = "";

    public static void allDetails(String name, String username, String phone) {
        userName = username;
        myName = name;
        phoneNum = phone;
    }

    Cart(String name, double price, int quntity, String catagory) {
        this.price = price;
        this.catagory = catagory;
        this.name = name;
        this.quntity = quntity;
    }

    public static void myCart(Object name, Object price, Object quntity, String catagory) {
        cart.put(key, new Cart((String) name, (double) price, (int) quntity, catagory));
        key++;
        // total_payable_amount = total;
        Shopping.total_payable_amount = Shopping.total_grocery_price + Shopping.total_fashion_price
                + Shopping.total_fruit_and_veg_price + Shopping.total_electronics_price;
        remove_once = 0;
    }

    public static void showCart() throws Exception {
        double removed_price = 0;
        con = DriverManager.getConnection(url, "postgres", "462005");
        cart2 = new Hashtable<>(cart);

        if (cart.isEmpty()) {
            System.out.println("Your cart is Empty.");
            return;
        } else {
            System.out.println();
            System.out.println("----Your Cart-----");
            System.out.println("*********************************************");
            for (Map.Entry<Integer, Cart> cart_detail : cart.entrySet()) {
                String values_by_key_name = cart_detail.getValue().name;
                double values_by_key_price = cart_detail.getValue().price;
                int values_by_key_quntity = cart_detail.getValue().quntity;
                String values_by_key_catagory = cart_detail.getValue().catagory;
                System.out.println(
                        cart_detail.getKey() + " --> Name: " + values_by_key_name + " | Price: " + values_by_key_price
                                + " | Catagory: " + values_by_key_catagory + " | Quntity: " + values_by_key_quntity);
            }

            System.out.println(
                    "Total patyable amount is: " + Shopping.total_payable_amount + " Rupees");
            System.out.println("*********************************************\n");

            // con = DriverManager.getConnection(url, "postgres", "462005");

            boolean b = true;
            while (b) {
                one_attmp++;
                System.out.print("Do you want to remove some items from cart? (Yes/No) ");
                String line = sc.next();
                sc.nextLine();
                if (line.equalsIgnoreCase("Yes")) {
                    data_of_cart = "";
                    remove_once = 0;
                    System.out.print("Enter key value: ");
                    int key_val = sc.nextInt();
                    if (cart.isEmpty()) {
                        System.out.println("Your cart is empty.");
                        return;
                    } else if (!cart.containsKey(key_val)) {
                        System.out.println("Enter valid key value...");
                        // return;
                    } else if (cart.containsKey(key_val)) {
                        System.out.print("Enter Quntity: ");
                        int user_quntity = sc.nextInt();
                        Cart c = cart.get(key_val);
                        String values_by_key_catagory = c.catagory;
                        int values_by_key_quntity = c.quntity;
                        double values_by_key_price = c.price;
                        String values_by_key_name = c.name;

                        removed_price = values_by_key_price;

                        if (user_quntity <= 0 || user_quntity > values_by_key_quntity) {
                            // user_quntity = 0;
                            System.out.println("Your Quntity is invalid...");
                            user_quntity = 0;
                        } else {
                            if (user_quntity == values_by_key_quntity) {
                                cart.remove(key_val);
                            } else {
                                cart.remove(key_val);
                                cart.put(key,
                                        new Cart(values_by_key_name, values_by_key_price,
                                                (values_by_key_quntity - user_quntity),
                                                values_by_key_catagory));
                                key++;
                            }
                        }

                        // update in catagory value
                        if (values_by_key_catagory.equalsIgnoreCase("grocery")) {
                            Shopping.total_grocery_price -= (removed_price * user_quntity);
                            Shopping.total_payable_amount = Shopping.total_payable_amount
                                    - removed_price * user_quntity;
                        } else if (values_by_key_catagory.equalsIgnoreCase("fashion")) {
                            Shopping.total_fashion_price -= (removed_price * user_quntity);
                            Shopping.total_payable_amount = Shopping.total_payable_amount
                                    - removed_price * user_quntity;
                        } else if (values_by_key_catagory.equalsIgnoreCase("electronic")) {
                            Shopping.total_electronics_price -= (removed_price * user_quntity);
                            Shopping.total_payable_amount = Shopping.total_payable_amount
                                    - removed_price * user_quntity;
                        } else if (values_by_key_catagory.equalsIgnoreCase("fruit_veg")) {
                            Shopping.total_fruit_and_veg_price -= (removed_price * user_quntity);
                            Shopping.total_payable_amount = Shopping.total_payable_amount
                                    - removed_price * user_quntity;
                        }
                        user_quntity = 0;
                        removed_price = 0;
                    }
                } else if (line.equalsIgnoreCase("No")) {
                    data_of_cart = "";
                    String update_cart = "update shopping_app set cart=? where (username=?);";
                    PreparedStatement ps = con.prepareStatement(update_cart);

                    // data insert into shopping_app (updated cart value is insert)
                    for (Map.Entry<Integer, Cart> cart_detail2 : cart2.entrySet()) {
                        String values_by_key_name = cart_detail2.getValue().name;
                        double values_by_key_price = cart_detail2.getValue().price;
                        int values_by_key_quntity = cart_detail2.getValue().quntity;
                        String values_by_key_catagory = cart_detail2.getValue().catagory;
                        data_of_cart += cart_detail2.getKey() + " --> Name: " + values_by_key_name + " | Price: "
                                + values_by_key_price + " | Quntity: " + values_by_key_quntity + " | Catagory: "
                                + values_by_key_catagory + "\n";

                    }

                    data_of_cart += "Total patyable amount is: " + Shopping.total_payable_amount + " Rupees\n";
                    ps.setString(1, data_of_cart);
                    ps.setString(2, userName);
                    ps.executeUpdate();
                    b = false;
                    return;
                } else {
                    System.out.println("Please! Enter (Yes/No)");
                }
            }
        }
    }
}
