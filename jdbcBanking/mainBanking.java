/*
 * Creating a Bank management system by using jdbc to perform  various operations such as add acount details ,deposit amount etc
 * @ Divya
 */
package com.jdbcBanking;
//importing required package
import java.util.Scanner;


public class mainBanking {
//creating main method
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Scanner obj = new Scanner(System.in);
		int cstId,cstAge,cstAccBal;
		String cstName,cstPhone,cstPswrd;
		int select =0;
		
		daoBanking dao = new daoBanking();
		dao.connect();
		
		main :
		while(select<2) {
			System.out.println("\t\t\t\t\t||>------------------WELCOME TO BANK MANAGEMENT SYSTEM----------------------------<||");
		System.out.println("\n1 - To Create a Bank Account \n2 - to login to existing Account \n3 - Exit");
		System.out.print("\nEnter Your choice : ");
		select = obj.nextInt();
		
		switch(select) {
		
		case 1->{	
			//creating custbanking class object and adding a new bank account details
	        custBanking bnk = new custBanking();
			System.out.print("\nEnter User Name : ");
			cstName = obj.next();
			bnk.cstName = cstName;
			System.out.print("Set Account Password : ");
			cstPswrd = obj.next();
			bnk.cstPassword = cstPswrd;
		    System.out.print("Enter Phone No : ");
			cstPhone = obj.next();
			bnk.cstPhone = cstPhone;
			System.out.print("Enter Age : ");
			cstAge = obj.nextInt();
			bnk.cstAge = cstAge;
			System.out.print("Enter Account Balance : ");
			cstAccBal = obj.nextInt();
			bnk.cstAccBal = cstAccBal;
			dao.addAccount(bnk);
		}
		
		
	
					case 2->{
						//login into the user's account
						System.out.print("\nEnter Account Holder Name : ");
						cstName = obj.next();
						System.out.print("Enter Account Password : ");
						cstPswrd = obj.next();
						
						// asking user for login credential
						int id = dao.bankLogin(cstName,cstPswrd);
						if(id>0) { 
							//if account details that is enter by the usr is correct then user can access its account
							System.out.println("\n********Login Success******!");
							int operation =0;
							
							inner:
							while(operation<5) {
								System.out.println("\t\t\t....................Enter your Choice......................... ");
								System.out.println("\n1 - ***To Print Account Detail \n2 - ***To Deposit Amount \n3 - ***To Withdraw Amount \n4 - ***To Change Your Pin \n5 - ***To Log Out.");
							    System.out.print("\n Press :  ");
								operation = obj.nextInt();
								
							//Different operations that can be perform
							   switch(operation) {
								
								// case 1 to print user account details
								case 1 ->{
									// calling getAccount method to access account details
									custBanking bnk= dao.getAccount(id);
									System.out.println("\nAccount No: "+bnk.cstId+"\nAccount Holder Name: "+bnk.cstName+"\nAge: "+bnk.cstAge+"\nPhone No: "+bnk.cstPhone+"\nAccount Balance: "+bnk.cstAccBal);
								}
								
								
								
								// depossiting amount in to the account  balance
								case 2->{
									int amt = 0;
									System.out.print("\nEnter Amount to Deposit : ");
									amt = obj.nextInt();
									
									// calling deposit method from user account 
									int rst = dao.deposit(id,amt);
									System.out.println("\n***Updated Account Balance : "+rst);
								}
								
								// 
								case 3->{
									//withdrawing amount from account balance
									int amt = 0;
									System.out.print("\nEnter Amount to WithDraw : ");
									amt = obj.nextInt();
									
									// Calling withdraw method from user account.
									int rst = dao.withDraw(id,amt);
									if(rst>0)
										System.out.println("***Updated Account Balance : "+rst);
									else
										System.out.println("Insufficient Account Balance.");
								}
				
								
							
								case 4 -> {
									//to change the existing pin and to create a new pin
									System.out.print("Enter Account Holder Name : ");
									cstName = obj.next();
									System.out.print("Enter Current Password : ");
									cstPswrd = obj.next();
									System.out.print("Enter New Password : ");
									String Pswrd = obj.next();
									
									// calling chnge password method 
									int rst = dao.changePswrd(id, cstName, cstPswrd, Pswrd);
									if(rst>0) {
										System.out.println("Invalid Password! \nOld Password and New Password can not be same");
									}
									else if(rst==0) {
										System.out.println("\nPassWord Successfully Changed. \nLogin Again with New Password!");
									}
									else if(rst== -1) {
										System.out.println("Invalid UserName or Password!!!");
									}
									else {
										System.out.println("\nInvalid Account!!!");
									}
									continue main;
								}
								
								}
							}
							System.out.println("\nSuccessfully Logged Out\n");
						}
						else if(id==0)
							System.out.println("\nIncorrect UserName or Password!!!");
						else
							System.out.println("\nAccount not Exist ! \nRegister Your Account !");
						
					}
					
					
				}
			}
		           
			     obj.close();        //closing Scanner object
     }   
}
