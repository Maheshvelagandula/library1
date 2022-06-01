package revature;
import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.util.Properties;
public class Bookitems {
	Scanner sc=new Scanner(System.in);
	public Connection getConnection()  {
		FileInputStream si=null;
		try {
			si = new FileInputStream("jdbc.prop");
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		}
		Properties P=new Properties();
		try {
			P.load(si);
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}
		String url =(String)P.get("url");
		String username =(String)P.get("username");
		String password =(String)P.get("password");
		Connection con=null;
		try {
			con=DriverManager.getConnection(url,username,password);	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	public void Register() {
		Connection con=getConnection();
		System.out.println("___Registeration Page___");
		System.out.println("enter username");
		String username=sc.next();
		System.out.println("enter password");
		String password=sc.next();
		System.out.println("enter role");
		String role=sc.next();
	String s1="select * from librarydetails where username=?";
	String s2="insert into librarydetails (username,password,role) values (?,?,?);";
	try {
		PreparedStatement stmt=con.prepareStatement(s1);
		stmt.setString(1, username);
		ResultSet rs=stmt.executeQuery();
		if (rs.next()) {
			System.out.println("username"+username+"already exists");
		}
		else {
			stmt=con.prepareStatement(s2);
			stmt.setString(1, username);
			stmt.setString(2, password);	
			stmt.setString(3, role);
			stmt.executeUpdate();
			System.out.println("_____Registration sucessfull_____");
		}	
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	}
	public void Login() {
		String role=null;
		Connection con=getConnection();
		System.out.println("Welcome to the login page");
		System.out.println("enter username");
		String username=sc.next();
		System.out.println("enter password");
		String password=sc.next();
		String s1="select * from librarydetails where username=? AND password=?;";
		try {
			PreparedStatement stmt=con.prepareStatement(s1);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs=stmt.executeQuery();
			if(rs.next()) {
				role=rs.getString(3);
			System.out.println("********************");
			System.out.println("login success");
			if(role.equals("admin")) {
				System.out.println("____admin___");
				Adminboard();
			}
			else if(role.equals("librarian")) {
				System.out.println("____librarian___");
				Librarianboard();	
			}
			else {
				System.out.println("____user____");
				Userboard();
			}	
		}
			else {
				System.out.println("***************");
				System.out.println("login failed");
				System.out.println("invalid username and password");
			}
	}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void Userboard() {
		System.out.println("\n\n____viewing books____\n");
		Books();
	}
	public void Adminboard() {
		System.out.println("1.Add librarian into library");
		System.out.println("2.view librarians into library");
		System.out.println("3.Delete librarians into library");
		System.out.println("\n enter your choice");
		int a =sc.nextInt();
		String s="y";
		while(s.equals("y")) {
			switch(a){
				case 1:
					Addlibrarian();
					break;
				case 2:
					viewlibrarian();
					break;
				case 3:
					deletelibrarian();
					break;
				default:
					System.out.println("enter valid choice");			
		}
			System.out.println("do you wish to continue:y/n");
			s=sc.next();
	}
	}
	public void Addlibrarian() {
		Connection con=getConnection();
		String s1="insert into librarian (lid,lname,lphoneno) values (?,?,?);";
		try {
		PreparedStatement stmt=con.prepareStatement(s1);
		System.out.println("enter id");
		int id=sc.nextInt();
		System.out.println("enter name");
		String name=sc.next();
		System.out.println("enter phoneno");
		int phoneno=sc.nextInt();
		stmt.setInt(1,id);
		stmt.setString(2, name);
		stmt.setInt(3, phoneno);
		stmt.executeUpdate();
		System.out.println("librarian added successfully");	
	}
		catch( Exception e) {
			e.printStackTrace();
		}
	}
	public void viewlibrarian() {
		Connection con= getConnection();
		String s1="select * from librarian;";
		PreparedStatement stmt;
		try {
			stmt=con.prepareStatement(s1);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		}
	public void deletelibrarian() {
		Connection con=getConnection();
		String s1="delete from librarian where lid=?;";
		try {
			PreparedStatement stmt=con.prepareStatement(s1);
			System.out.println("enter id");
			int id=sc.nextInt();
			stmt.setInt(1, id);
			stmt.executeUpdate();
			System.out.println("deleted sucessfully");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void Librarianboard() {
		System.out.println("1.ADD BOOKS");
		System.out.println("2.BOOKS");
		System.out.println("3.REMOVE BOOKS");
		System.out.println("4.ISSUE BOOKS");
		System.out.println("5.UPDATE BOOKS");
		System.out.println("ENTER YOUR CHOICE");
		int a =sc.nextInt();
		String s="y";
		while(s.equals("y")) {
			switch(a) {
			case 1:
				Addbooks();
				break;
			case 2:
				Books();
				break;
			case 3:
				Removebooks();
				break;
			case 4:
				Issuebooks();
				break;
			case 5:
				Updatebooks();
				break;
			default:
				System.out.println("enter valid choice");
			}
			System.out.println("do you wish to continue y/n");
			s=sc.next();
		}
	}
	public void Addbooks() {
		Connection con=getConnection();
		String s1="insert into Books (bookid,bookname,bookprice) values (?,?,?);";
		try {
			PreparedStatement stmt=con.prepareStatement(s1);
			System.out.println("enter bookid");
			int id=sc.nextInt();
			System.out.println("enter bookname");
			String name=sc.next();
			System.out.println("enter bookprice");
			int price=sc.nextInt();
			stmt.setInt(1, id);
			stmt.setString(2, name);
			stmt.setInt(3, price);
			stmt.executeUpdate();
			System.out.println("books added successfully");
			System.out.println("Add More books? (y/n): ");
			String more = sc.next();
			if(more.equals("y"))
			{
				Addbooks();
			}
			else
			{
				Librarianboard();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void Books() {
		Connection con=getConnection();
		String s1="select * from Books;";
		try {
			PreparedStatement stmt=con.prepareStatement(s1);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString(1)+""+rs.getString(2)+""+rs.getString(3));
			}
		}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		public void Removebooks() {
			Connection con=getConnection();
			String s1="delete from Books where bookid=?;";
			try {
				PreparedStatement stmt=con.prepareStatement(s1);
				System.out.println("enter bookid");
				int id=sc.nextInt();
				stmt.setInt(1, id);
				stmt.executeUpdate();
				System.out.println("book deleted successfully");
				System.out.println("delete More books? (y/n): ");
				String more = sc.next();
				if(more.equals("y"))
				{
					Removebooks();
				}
				else
				{
					Librarianboard();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			public void Issuebooks() {
				Connection con=getConnection();
				String s1="insert into Issuebooks (bookid,bookname,username) values (?,?,?);";
				try {
					PreparedStatement stmt=con.prepareStatement(s1);
					System.out.println("enter bookid");
					int id=sc.nextInt();
					System.out.println("enter bookname");
					String name=sc.next();
					System.out.println("enter username");
					String username=sc.next();
					stmt.setInt(1, id);
					stmt.setString(2, name);
					stmt.setString(3, username);
					stmt.executeUpdate();
					System.out.println("books issued successfully");
					System.out.println("issue More books? (y/n): ");
					String more = sc.next();
					if(more.equals("y"))
					{
						Issuebooks();
					}
					else
					{
						Librarianboard();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			public void Updatebooks() {
				Connection con=getConnection();
				String s1="update Books set bookid=?,bookname=?,bookprice=? where id=?;";
				try {
					PreparedStatement stmt=con.prepareStatement(s1);
					System.out.println("enter bookid");
					int id=sc.nextInt();
					System.out.println("enter newbookid");
					int newid=sc.nextInt();
					System.out.println("enter newbookname");
					String newname=sc.next();
					System.out.println("enter newbookprice");
					int newprice=sc.nextInt();
					stmt.setInt(1, newid);
					stmt.setString(2, newname);
					stmt.setInt(3, newprice);
					stmt.setInt(4, id);
					stmt.executeUpdate();
					System.out.println("books updated successfully");
					System.out.println("update More books? (y/n): ");
					String more = sc.next();
					if(more.equals("y"))
					{
						Updatebooks();
					}
					else
					{
						Librarianboard();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
}
	

