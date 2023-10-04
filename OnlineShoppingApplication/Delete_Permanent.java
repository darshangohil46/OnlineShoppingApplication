package OnlineShoppingApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Delete_Permanent {
    static Scanner sc = new Scanner(System.in);
    static String url = "jdbc:postgresql://localhost:5432/postgres";

    public static void delete_permanent() throws Exception {
        System.out.print("Do You Really Want To Delete Account? Enter (Yes/No) ");
        String input = sc.nextLine();
        while (true) {
            if (input.equalsIgnoreCase("yes")) {
                Connection con = DriverManager.getConnection(url, "postgres", "462005");
                System.out.print("Enter Your Phone Number: ");
                String phone = sc.nextLine();
                System.out.print("Enter Your User Name: ");
                String userName = sc.nextLine();

                String sql = "delete from shopping_app where username = ? and phone = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, userName);
                ps.setString(2, phone);
                int x = ps.executeUpdate();
                if (x > 0) {
                    System.out.println("Your Account Deletion Done.");
                } else {
                    System.out.println("Account Deletion Process Fail.");
                }
                System.exit(0);
            } else if (input.equalsIgnoreCase("no")) {
                break;
            } else {
                System.out.println("Enter (Yes/No)");
            }
        }
    }
}