package OnlineShoppingApplication;

import java.util.*;
import java.sql.*;

public class Account_Detail extends Thread {
    static Connection con;
    static Scanner sc = new Scanner(System.in);
    static String url = "jdbc:postgresql://localhost:5432/postgres";

    // all data from main file
    static String userName = "";
    static String myName = "";
    static String phoneNum = "";

    public static void allDetails(String name, String username, String phone) {
        userName = username;
        myName = name;
        phoneNum = phone;
    }

    public static void acc_details() throws Exception {
        con = DriverManager.getConnection(url, "postgres", "462005");
        // con2 = DriverManager.getConnection(url, "postgres", "462005");
        if (con != null) {
            System.out.println("YOUR ACCOUNT DETAILS");
        } else {
            System.out.println("404! Error...");
        }

        // data fetch from shopping_app table
        String sql_details1 = "select * from shopping_app where username = '" + userName + "';";
        PreparedStatement ps = con.prepareStatement(sql_details1);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Id: " + rs.getInt(1));
            System.out.println("Name: " + rs.getString(2));
            System.out.println("User Name: " + rs.getString(3));
            System.out.println("Phone Number: " + rs.getString(4));
            System.out.println("Recenty Added In Cart");
            if (rs.getString(5) == null || rs.getString(5) == "") {
                System.out.println("Your Cart is Empty.");
            } else {
                System.out.println(rs.getString(5));
            }
        }

        // data fetch from cart_data named table
        String sql_details2 = "select * from cart_data where username = ?;";
        PreparedStatement ps2 = con.prepareStatement(sql_details2);
        ps2.setString(1, userName);
        ResultSet rs2 = ps2.executeQuery();
        while (rs2.next()) {
            System.out.println("==========================================================");
            System.out.print("Your Old Orders in Cart\n" + rs2.getString(2));
            System.out.println("Your Transaction Details\n" + rs2.getString(3));
            System.out.println("Payment Data\n" + rs2.getString(4));
            System.out.println("==========================================================");
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
