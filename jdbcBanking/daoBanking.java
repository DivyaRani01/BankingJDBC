/**
 * @author Divya
 * Creating a BANK MANAGEMENT SYSTEM using jdbc and data access object(DAO) And performing operations on it.
 * controller
 */

package com.jdbcBanking;
import java.sql.*;



public class daoBanking {
	
	Connection con = null;                                        //global object
	 
//(1)creating a method to connect vd database
	public void connect() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost : 3306/custBanking";
		String username="root";
		String password ="Divya123";
		//getting connection
		con=DriverManager.getConnection(url,username,password);
	};
//(2)creating a method to add account in database	
    public void addAccount(custBanking cst) throws Exception {
    	String query = ("insert into databank(cstName,cstPassword,cstPhone,cstAge,cstAccBal) values(?,?,?,?,?)");
    	//preparing statement from con object
    	PreparedStatement psmt= con.prepareStatement(query);
		//adding values to ? fields
		psmt.setString(1,cst.cstName); 
		psmt.setString(2,cst.cstPassword);
		psmt.setString(3,cst.cstPhone);
		psmt.setInt(4,cst.cstAge); 
		psmt.setInt(5,cst.cstAccBal); 
		
		int count = psmt.executeUpdate();
		System.out.println("\n Your Account Created Successfully");
		System.out.println(count+" rows affected.");
		
	
    }
 //(3)creating method to set the details and print the details
    public custBanking getAccount(int cstId) throws Exception {
		custBanking cst = new custBanking();
		cst.cstId = cstId;
		String query = "select * from databank where cstId = "+cstId;
		Statement st = con.createStatement();
		ResultSet set = st.executeQuery(query);
		set.next();
		
		// using bank data 
		cst.cstName = set.getString(2);
		cst.cstPassword=set.getString(3);
		cst.cstPhone = set.getString(4);
		cst.cstAge = set.getInt(5);
		cst.cstAccBal = set.getInt(6);
		return cst;
	}
 //(4)creating method to login in the bank database   
     public int bankLogin(String cstName,String cstPswrd) throws Exception{
		
		Statement st= con.createStatement();
		ResultSet set = st.executeQuery("select * from databank where cstName = '"+cstName+"'");
		
		if(set.next()) {
			
			String cstPassword = set.getString(3);
			if(cstPassword.equals(cstPswrd)) {
				int cstId = set.getInt(1);
				return cstId;
			}
			else {
				return 0;
			}
		}
		else {
			return -1;
		}
	}
 //(5)creating a method to deposit amount in bank account     
     public int deposit(int cstId,int cstAmount) throws Exception {
 		Statement st = con.createStatement();
 		
 		//taking details via account id
 		ResultSet set = st.executeQuery("select * from databank where cstId= "+cstId);
 		set.next();      
 		int bal = set.getInt(6);
 		    //depositing money in user's account balance 
 			bal += cstAmount;
 			Statement stm = con.createStatement();
 			
 			// updating account balance of user's
 			int amount = stm.executeUpdate("update databank set cstAccBal ="+bal+" where cstId ="+cstId);
 			return bal;
 	}
 	
    
//(6)creating a method to withdraw amount from account 
	  public int withDraw (int cstId,int cstAmount) throws Exception {
		Statement st = con.createStatement();
		
		//taking user's data
		ResultSet set = st.executeQuery("select * from databank where cstId= "+cstId);
		set.next();
		int bal = set.getInt(6);
		if(bal>cstAmount) {
			//withdrawing amount
			bal -= cstAmount;
			Statement wdrawSt = con.createStatement();
			
			// UPDATING USER ACCOUNT BALANCE
			int amount = wdrawSt.executeUpdate("update databank set cstAccBal ="+bal+" where cstId ="+cstId);
			return bal;
		}
		else {
			return 0;
		}
	}
//(7)creating method to change the pin or password of the account 	
      public int changePswrd(int cid,String cstName,String cstPswrd,String pswrd)throws Exception{
		
		Statement st = con.createStatement();
		ResultSet set = st.executeQuery("select * from  databank where cstName = '"+cstName+"'");
		
			// checking username
			if(set.next()) {
			
				String cstPassword = set.getString(3);
				
				// veryfying password
				if(cstPassword.equals(cstPswrd)) {
					if(cstPassword.equals(pswrd)) {
						return 1;
					}
					else {
					Statement st1 = con.createStatement();
					st1.executeUpdate("update databank set cstPassword ="+pswrd+" where cstId ="+cid);
					return 0;
					}
				}
				else {
					return -1;
				}
			}
			else {
				return -2;
			}
	}
	
	
}
