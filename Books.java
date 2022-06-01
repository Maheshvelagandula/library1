package revature;
import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.util.Properties;
public class Books {
	public static void main(String args[]) {
		Scanner sc =new Scanner(System.in);
		String s="y";
		while(s.equals("y")) {
			System.out.println("1.Register");
			System.out.println("2.Login");
			System.out.println("\n enter your choice:");
			Bookitems bi = new Bookitems();
			int a=sc.nextInt();
			switch(a) {
			case 1:
				bi.Register();
				break;
			case 2:
				bi.Login();
				break;
			default:
				System.out.println("enter valid choice:");
				
			}
			System.out.println("Do you wish to continue: y/n");
			s=sc.next();
		}
		sc.close();
	}

}
