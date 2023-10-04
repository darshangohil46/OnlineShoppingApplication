import java.sql.*;
import java.util.*;

import OnlineShoppingApplication.*;

// javac Online_shopping_app.java
// java -cp "C:\Users\darshangohil\Desktop\postgresql-42.6.0.jar;." OnlineShopping

// create table shopping_app (id SERIAL PRIMARY KEY,
// name VARCHAR(20), username VARCHAR(20), phone VARCHAR(10), cart VARCHAR(2000), payment VARCHAR(500));

// create table cart_data(username VARCHAR(20),cart VARCHAR(2000), payable_amount VARCHAR(1000),payable_data VARCHAR(1000));

public class OnlineShopping extends Thread {
    static Connection con;
    static Scanner sc = new Scanner(System.in);
    static PreparedStatement ps;
    public static String name = "";
    public static String phoneNum = "";
    public static String userName = "";
    static String url = "jdbc:postgresql://localhost:5432/postgres";

    public static void main(String[] args) throws Exception {
        // creater database connection
        con = DriverManager.getConnection(url, "postgres", "462005");

        if (con != null) {
            System.out.println("----- WELCOME TO ONLINE SHOPPING APPLICATION -----");
        } else {
            System.out.println("404! CONNECTION ERROR...");
        }

        boolean b = true;
        while (b) {
            System.out.println("*******************************************");
            System.out.println("1. Register\t2. Login\t0. Exit");
            System.out.println("*******************************************");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1: {
                    register_app();
                    break;
                }
                case 2: {
                    boolean x = login_app();
                    if (x) {
                        String del_cart = "update shopping_app set cart=?, payment=? where username=?;";
                        PreparedStatement ps = con.prepareStatement(del_cart);
                        ps.setString(1, "");
                        ps.setString(2, "");
                        ps.setString(3, userName);
                        ps.executeUpdate();

                        boolean a = true;
                        while (a) {
                            System.out.println("---------------------------------------------");
                            System.out.println("0. Exit From Application\n1. Open Shopping App\n2. View Cart");
                            System.out.println(
                                    "3. Account Detail\n4. Show Payment Detail\n5. Delete Account Permanently");
                            System.out.println("---------------------------------------------");
                            System.out.print("Enter Your Choice: ");
                            switch (sc.nextInt()) {
                                case 1:
                                    // go for shopping
                                    Shopping.my_shopping();
                                    System.out.println("Your Cart is in Process...");
                                    Thread.sleep(5000);
                                    Cart.showCart();
                                    break;
                                case 2:
                                    // view and add into cart
                                    Cart.showCart();
                                    break;
                                case 3:
                                    Account_Detail.acc_details();
                                    break;
                                case 4:
                                    Payment.payment_details();
                                    break;
                                case 5:
                                    Delete_Permanent.delete_permanent();
                                    break;
                                case 0:
                                    if (Shopping.total_payable_amount == 0.0) {
                                        a = false;
                                    } else {
                                        System.out.println("Please! pay first...");
                                    }
                                    break;
                                default:
                                    System.out.println("Please! Enter valid number...");
                                    break;
                            }
                        }
                    }
                    break;
                }
                case 0: {
                    b = false;
                    break;
                }
                default: {
                    System.out.println("Enter valid...");
                    break;
                }
            }
        }
    }

    // register in application
    public static void register_app() throws Exception {
        System.out.print("Enter your Name: ");
        name = sc.next();
        boolean checkNum = phoneNumber(10);

        int generate_user_id = (int) (Math.random() * 100);
        userName = name + "_" + generate_user_id;
        System.out.println("Your User Name is: " + userName);

        if (checkNum) {
            // data insertion in tabel shopping_app
            String sql_insertion = "insert into shopping_app(name, username, phone) values(?,?,?)";
            ps = con.prepareStatement(sql_insertion);
            ps.setString(1, name);
            ps.setString(2, userName);
            ps.setString(3, phoneNum);
            int x = ps.executeUpdate();
            if (x > 0) {
                System.out.println("Registration is Complete.");

                Payment.allDetails(name, userName, phoneNum);
                Cart.allDetails(name, userName, phoneNum);
                Account_Detail.allDetails(name, userName, phoneNum);
                Shopping.allDetails(name, userName, phoneNum);
            } else {
                System.out.println("Registration is Fail.");
            }
        }
    }

    // this method for checking phone number.
    static boolean phoneNumber(int length) {
        boolean check_num = false;
        sc.nextLine();
        while (check_num != true) {
            System.out.print("Enter your number: ");
            phoneNum = sc.nextLine();
            try {
                if (phoneNum.length() == length) {
                    Long.parseLong(phoneNum);
                    check_num = true;
                } else {
                    System.out.println("Enter valid number.");
                    check_num = false;
                }
            } catch (Exception e) {
                System.out.println("Enter valid number.");
                check_num = false;
            }
        }
        return check_num;
    }

    // login in application
    public static boolean login_app() throws Exception {
        String sql = "select * from shopping_app";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.print("Enter Your user name: ");
        String given_userName = sc.next();
        System.out.print("Enter Your phone number: ");
        String given_phoneNum = sc.next();
        sc.nextLine();
        boolean b = false;

        while (rs.next()) {
            String name1 = rs.getString(2);
            String user = rs.getString(3);
            String phone = rs.getString(4);

            if (given_phoneNum.equals(phone) && given_userName.equals(user)) {
                name = name1;
                userName = user;
                phoneNum = phone;

                System.out.println("OTP Send On Your Mobile Number");
                Thread.sleep(3000);
                int generate_otp = (int) (Math.random() * 10000);
                System.out.println("Your OTP is: " + generate_otp);
                boolean x = true;
                while (x) {
                    System.out.print("Enter OTP: ");
                    int otp = sc.nextInt();
                    if (generate_otp == otp) {
                        System.out.println("Login Done.");

                        Payment.allDetails(name, userName, phoneNum);
                        Cart.allDetails(name, userName, phoneNum);
                        Account_Detail.allDetails(name, userName, phoneNum);
                        Shopping.allDetails(name, userName, phoneNum);
                        x = false;
                    }
                }
                b = true;
                break;
            }
        }

        if (!b) {
            System.out.println("Login Fail.");
        }
        return b;
    }
}
