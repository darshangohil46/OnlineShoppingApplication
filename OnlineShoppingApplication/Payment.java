package OnlineShoppingApplication;

import java.util.*;
import java.sql.*;
import java.io.*;

public class Payment extends Account_Detail {
    static Scanner sc = new Scanner(System.in);
    static Connection con;
    static String url = "jdbc:postgresql://localhost:5432/postgres";
    // static String user_name;
    static FileWriter fw1;
    static BufferedWriter bw1;

    static String data;
    static int order_placed;

    static String userName = "";
    static String myName = "";
    static String phoneNum = "";

    public static void allDetails(String name, String username, String phone) {
        userName = username;
        myName = name;
        phoneNum = phone;
    }

    static int done_payment = 0;

    public static void payment_details() throws Exception {
        fw1 = new FileWriter("payment_details.txt", true);
        bw1 = new BufferedWriter(fw1);
        System.out.println(userName);

        con = DriverManager.getConnection(url, "postgres", "462005");
        System.out.println("Your Amount is " + Shopping.total_payable_amount);

        boolean b = true;
        while (b) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("0. Exit");
            System.out.println("1. Pay from Debit card");
            System.out.println("2. Pay by UPI");
            System.out.println("3. Pay on Delivery");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.print("Enter your choice: ");
            switch (sc.nextInt()) {
                case 1:
                    debit_card();
                    b = false;
                    break;
                case 2:
                    upi_pay();
                    b = false;
                    break;
                case 3:
                    pay_on_delivery();
                    b = false;
                    break;
                case 0:
                    b = false;
                    break;
                default:
                    System.out.println("Enter valid number...");
                    break;
            }
        }

        order_placed = 0;
        if (done_payment != 0 && Shopping.total_payable_amount != 0) {
            String update_payment = "update shopping_app set payment=? where (username=?);";
            PreparedStatement ps2 = con.prepareStatement(update_payment);
            ps2.setString(1, "Done");
            ps2.setString(2, userName);
            ps2.executeUpdate();
            System.out.println();
            System.out.println("Your Order Placed Done...");
            order_placed = 1;
        }

        if (!Cart.cart.isEmpty() && done_payment != 0) {
            Cart.cart.clear();
            Cart.cart2.clear();
            Shopping.total_payable_amount = 0;
            Shopping.total_grocery_price = 0;
            Shopping.total_fashion_price = 0;
            Shopping.total_fruit_and_veg_price = 0;
            Shopping.total_electronics_price = 0;
        }
    }

    static void debit_card() throws Exception {
        con = DriverManager.getConnection(url, "postgres", "462005");

        if (done_payment != 0) {
            System.out.println("Your payment already done.");
            return;
        } else if (Shopping.total_payable_amount != 0) {
            dataFill();
            System.out.println("Amount is " + Shopping.total_payable_amount + " Rupees");
            System.out.println("OTP Send on : " + phoneNum);

            boolean b = true;
            while (b) {
                int otp = (int) (Math.random() * 10000);
                Thread.sleep(3000);
                System.out.println("Your OTP is " + otp);
                System.out.print("Enter OTP: ");
                int input_otp = sc.nextInt();
                if (otp == input_otp) {
                    System.out.println("Payment By Debit Card Done");
                    System.out.println(Shopping.total_payable_amount + " Rupees payment done");
                    b = false;
                }
            }

            bw1.write("User Name --> " + userName);
            bw1.write("Total Pay: " + Shopping.total_payable_amount);
            bw1.newLine();
            System.out.println("Total pay: " + Shopping.total_payable_amount);
            bw1.write("Payment is done by Debit Card.");
            done_payment++;
            Thread.sleep(3000);
            System.out.print("Payment is done by Debit Card.");
            bw1.newLine();
            bw1.write("-------------------------------------------\n");
            bw1.flush();

            System.out.println("Thanks For Online Payment\n");
            pay_amount();
        } else {
            System.out.println("Please! Add Some Items In Cart.");
        }
    }

    static void upi_pay() throws Exception {
        con = DriverManager.getConnection(url, "postgres", "462005");

        if (done_payment != 0) {
            System.out.println("Your payment already done.");
            return;
        } else if (Shopping.total_payable_amount != 0) {
            String default_pin = "123456";
            System.out.print("Enter Your UPI Id(name@bank_name): ");
            String upi_id = sc.next();
            sc.nextLine();

            int max_attm = 3;
            int attmp = 0;
            while (attmp < max_attm) {
                System.out.print("Enter Your UPI Pin: ");
                String upi_pin = sc.nextLine();
                if (upi_pin.equals(default_pin)) {
                    System.out.println("Payment Done From " + upi_id);
                    System.out.println(Shopping.total_payable_amount + " Rupees payment done");
                    break;
                } else {
                    System.out.println("Payment Fail. Please try again.");
                    System.out.println("You have only " + attmp + " attemp.");
                    attmp++;
                }
            }
            bw1.write("User Name --> " + userName);
            bw1.newLine();
            bw1.write("UPI ID: " + upi_id + "\n");
            bw1.write("Total Pay: " + Shopping.total_payable_amount + "\n");
            bw1.write("Payment is done by UPI.");
            bw1.newLine();
            done_payment++;
            Thread.sleep(3000);
            System.out.println("Payment is done by UPI.");
            data = "Payment By UPI.\nUPI Id: " + upi_id + "\n";
            bw1.write("-------------------------------------------\n");
            bw1.flush();

            System.out.println("Thanks For Online Payment\n");
            pay_amount();
        } else {
            System.out.println("Please! Add Some Items In Cart.");
        }
    }

    static void pay_on_delivery() throws Exception {
        con = DriverManager.getConnection(url, "postgres", "462005");

        if (done_payment != 0) {
            System.out.println("Your payment already done.");
            return;
        } else if (Shopping.total_payable_amount != 0) {
            System.out.print("Enter Your address: ");
            sc.nextLine();
            String address = sc.nextLine();
            System.out.println(
                    "Address :: " + address + "\n" + Shopping.total_payable_amount + " Rupees pay on this address.");

            bw1.write("USer Name --> " + userName);
            bw1.newLine();
            bw1.write("Total Pay: " + Shopping.total_payable_amount);
            bw1.newLine();
            bw1.write("Address: " + address);
            bw1.newLine();
            bw1.write("Pay at delivery time.");
            bw1.newLine();
            done_payment++;
            Thread.sleep(3000);
            System.out.println("Pay at delivery time.");
            bw1.write("-------------------------------------------\n");
            bw1.flush();

            data = "Pay on Delivery." + address;
            pay_amount();
        } else {
            System.out.println("Please! Add Some Items In Cart.");
        }
    }

    // write payment details in database payable_amount
    static String ins_in_payable_amo = "insert into cart_data(username,cart,payable_amount,payment_data)values(?,?,?,?);";

    static void pay_amount() throws Exception {
        con = DriverManager.getConnection(url, "postgres", "462005");
        PreparedStatement ps = con.prepareStatement(ins_in_payable_amo);

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT NOW()");
        String date_time = "";
        Thread.sleep(3000);
        if (rs.next()) {
            date_time = rs.getString(1);
            System.out.println(date_time);
        }

        ps.setString(1, userName);
        ps.setString(2, Cart.data_of_cart);
        ps.setString(3, date_time + "\nAmount: " + Shopping.total_payable_amount);
        ps.setString(4, data);
        ps.executeUpdate();
        System.out.println("Thank You");
        System.out.println("Visit Again");
    }

    // data fill in file and shopping_app table
    static void dataFill() throws Exception {
        System.out.print("Enter Name: ");
        String name = sc.next();
        sc.nextLine();
        System.out.print("Enter Last Valid date(MM/YY): ");
        String date = sc.next();
        sc.nextLine();
        String num = "";
        boolean b1 = true;
        while (b1) {
            System.out.print("Enter Card Number(16 Digits): ");
            num = sc.next();
            sc.nextLine();
            if (checkNum(16, num)) {
                b1 = false;
            }
        }
        boolean b2 = true;
        String cvv = "";
        while (b2) {
            System.out.print("Enter CVV Number(3 Digits): ");
            cvv = sc.next();
            sc.nextLine();
            if (checkNum(3, cvv)) {
                b2 = false;
            }
        }

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT NOW()");
        String date_time = "";
        if (rs.next()) {
            date_time = rs.getString(1);
            System.out.println(date_time);
        }

        data = ("Name On Card: " + name + "\nDate: " + date + "\nCard Number: " + num + "\nCVV Number: " + cvv + "\n");
        bw1.write("User Name --> " + userName + "\n");
        bw1.write(data);
        bw1.flush();
    }

    // for checking number of credit and debit card and check cvv number.
    static boolean checkNum(int length, String num) {
        boolean check_num = true;
        // sc.nextLine();
        while (check_num) {
            try {
                if (num.length() == length) {
                    Long.parseLong(num);
                    return true;
                } else {
                    System.out.println("Enter valid number.");
                    return false;
                }
            } catch (Exception e) {
                System.out.println("Enter valid number.");
                return false;
            }
        }
        return true;
    }
}