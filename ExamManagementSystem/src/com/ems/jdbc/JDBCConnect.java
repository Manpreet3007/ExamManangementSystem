package com.ems.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class JDBCConnect {

	
	public boolean teacherLogin(String userId, String password) throws Exception {
		
		boolean isValidUser = false;
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "password");
		
		Statement st = con.createStatement();
		
		String query = "Select * from teachercreds where userId ='"+userId+"' and password = '"+ password+"';";
		System.out.print(query);
		
		ResultSet rs = st.executeQuery(query);
		
		if(rs.next()) {
			
			isValidUser =  true;
		}
		
		st.close();
		con.close();
		return isValidUser;
	}
	public boolean studentLogin(String userId, String password) throws Exception {
		
		boolean isValidUser = false;
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "password");
		
		Statement st = con.createStatement();
		
		String query = "Select * from studentcreds where username ='"+userId+"' and password = '"+ password+"';";
		System.out.print(query);
		
		ResultSet rs = st.executeQuery(query);
		
		if(rs.next()) {
			
			isValidUser =  true;
		}
		
		st.close();
		con.close();
		return isValidUser;
	}
	
	public boolean createTest(String [][]testData) throws Exception{
		
		
		Date dt = new Date();
		long timeOfTableCreation =  dt.getTime();
		String questionTableName = "test" +  timeOfTableCreation;
		String answersTableName =  "testScore" + timeOfTableCreation;
		System.out.println(questionTableName +"   "+ answersTableName);
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "password");
		
		Statement st = con.createStatement();
		
		String createTableQuery = "create table "+questionTableName+"(question varchar(200), typeofquestion varchar(10), options varchar(100), correctanswer varchar(100))";
		String answerTableQuery = "create table "+answersTableName+"(studentId varchar(50), ";
		
		int noOfQuestions = testData.length;
		for(int i = 1; i <= noOfQuestions; i++)
			answerTableQuery += "Answer"+i + " varchar(20),";
		
		answerTableQuery =  answerTableQuery.substring(0, answerTableQuery.length()-1) +");";
		
		System.out.println(answerTableQuery);
		
		
		System.out.print(createTableQuery);
		
		int updated = st.executeUpdate(createTableQuery);
		updated = st.executeUpdate(answerTableQuery);
		
		if(updated == 0) {
			int i =0;
			while(i < testData.length) {
				
				String insertQuery = "insert into "+ questionTableName+" values('"+testData[i][0]+"','" + testData[i][1]+"','"+testData[i][2] + "','"+ testData[i][3]+"');";
				System.out.println(insertQuery);
				int rowsUpdated = st.executeUpdate(insertQuery);
				i++;
			}	
			

			
		}
		
		
			
		st.close();
		con.close();
		
		
		return true;

		
	}
	
	public String[][] takeTest(String testId) throws Exception {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "password");
		
		
		
		String selectQuery = "select question,typeofquestion,options from "+testId;
		System.out.print(selectQuery);
		
		PreparedStatement st = con.prepareStatement(selectQuery,
                ResultSet.TYPE_SCROLL_SENSITIVE, 
            ResultSet.CONCUR_UPDATABLE);
		
		ResultSet rs = st.executeQuery(selectQuery);
		
		int size =0;
		if (rs != null) 
		{
		  rs.last();    // moves cursor to the last row
		  size = rs.getRow(); // get row id 
		}
		String testData[][] = new String[size][3];
		rs.beforeFirst(); int i = 0;
		
		
		while(rs.next()) {
			testData[i][0] = rs.getString(1);
			testData[i][1] = rs.getString(2);
			testData[i][2] = rs.getString(3);
			
			i++;
			
		}
		
		return testData;
		
		
		
		
	}
	
	public boolean saveTestAnswers(String studentId, String testId, String []answersOfTest) {
		
		Statement st = null;Connection con = null;
		boolean testSavedSuccess = true;
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "password");
		
		st = con.createStatement();
		
		String scoreTableName = "testScore"+ testId.substring(4);
		
		String insertAnswersQuery = "insert into "+ scoreTableName+" values('"+studentId +"',";
		
		for(int i =0; i< answersOfTest.length; i++)
			insertAnswersQuery += "'"+answersOfTest[i]+"',";
		
		insertAnswersQuery = insertAnswersQuery.substring(0, insertAnswersQuery.length() - 1)+")";
		
		System.out.println(insertAnswersQuery);
		
		st.executeUpdate(insertAnswersQuery);
		
		
		
		
		}catch(Exception ex) {
			
			testSavedSuccess = false;
			ex.printStackTrace();
			
		}
		try {
			st.close();
			con.close();
		}catch(Exception ex) {}
		
		return testSavedSuccess;
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
