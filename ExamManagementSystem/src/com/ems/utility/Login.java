package com.ems.utility;
import java .util.*;

import com.ems.jdbc.JDBCConnect;


public class Login {

	public static void main(String args[]) {
		
		
		System.out.println("Select 1 for Teacher login \n 2 for Student Login");
		Scanner sc = new Scanner(System.in);
		
		Login l = new Login();
		
		int choice = Integer.parseInt(sc.nextLine());
		if(choice == 1) {
			System.out.println("Enter UserId and Password");
			l.teacherLogin(sc.nextLine(),sc.nextLine());
		}else if(choice == 2) {
			System.out.println("Enter UserId and Password");
			l.studentLogin(sc.nextLine(),sc.nextLine());
		}
	}
	
	
	void teacherLogin(String userId, String password) {
		
		try {
		
			boolean isValidUser = new JDBCConnect().teacherLogin(userId, password);
			if(isValidUser) {
				System.out.println("\nWelcome to EMS System. Please slelect below to proceede further\n");
				System.out.println("Select 1 for creating test\n2 for checking score of previous tests");
				Scanner sc = new Scanner(System.in);
				int selection = Integer.parseInt(sc.nextLine());
				if(selection == 1) {
					
					new Test().createTest();
					
				}
				
			}
			else System.err.println("\nWrong Id or Password!! Please Input Correct credentials");
			
			
		}catch(Exception ex) {
			System.out.println("Some error occured while logging in");
		}	
		
	}
	void studentLogin(String userId, String password) {
		
		try {
		
			boolean isValidUser = new JDBCConnect().studentLogin(userId, password);
			if(isValidUser) {
				System.out.println("\nWelcome to EMS System. Please slelect below to proceede further\n");
				System.out.println("Select 1 for taking new Test\n2 for checking score of previous tests");
				Scanner sc = new Scanner(System.in);
				int selection = Integer.parseInt(sc.nextLine());
				if(selection == 1) {
					System.out.println("Enter test id of the test that you want to give");
					new Test().takeTest(userId, sc.nextLine());
					
				}
				
			}
			else System.err.println("\nWrong Id or Password!! Please Input Correct credentials");
			
			
		}catch(Exception ex) {
			System.out.println("Some error occured while logging in");
		}	
		
	}
	
	
	
	
	
}
