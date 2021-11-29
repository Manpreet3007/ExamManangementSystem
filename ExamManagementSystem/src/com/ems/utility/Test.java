package com.ems.utility;
import java.util.Scanner;

import com.ems.jdbc.JDBCConnect;


public class Test {

	
	public void createTest() {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter no of Questions");
		int noOfQuestions = Integer.parseInt(sc.nextLine());
		String testData[][] = new String[noOfQuestions][4];
		
		
		
		for(int i = 0; i< noOfQuestions;i++) {
			System.out.println("Eneter Question No "+ (i+1));
			 
			testData[i][0] = sc.nextLine();
			
			System.out.println("Enter 1 for mcq and 2 for numercial question");
			int typeOfQuestion = Integer.parseInt(sc.nextLine());
			if(typeOfQuestion == 1)
				testData[i][1] = "MCQ";
			else testData[i][1] = "Numerical";
			// if user selects mcs type question
			if(typeOfQuestion == 1) {
				System.out.println("Enter no of options");
				int noOfOptions = Integer.parseInt(sc.nextLine());
				System.out.println("Enter options for mcq type question");
				String commaSeparatedOptions = "";
				while(noOfOptions-- != 0)
					if(commaSeparatedOptions.equals(""))
						commaSeparatedOptions += sc.nextLine();
					else commaSeparatedOptions += "," + sc.nextLine();
				
				testData[i][2] = commaSeparatedOptions;
			}
			
			
			// Enter Correct Answer
			System.out.println("Enter Correct Answer");
			String correctAnswer = sc.nextLine();
			
			testData[i][3] = correctAnswer;
			
			
			
			
		
		}
		try {
		new JDBCConnect().createTest(testData);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
			
		
	}
	
	public void takeTest(String studentId, String testId) {
		
		Scanner sc = new Scanner(System.in);
		try {
		String testArray[][] = new JDBCConnect().takeTest(testId);
		String answers[] = new String[testArray.length];
		System.out.println("-----------------Instructions-----------------");
		System.out.println("1. For MCQ type question -  type option no and press enter to provide answer");
		System.out.print("2. For numerical type question, write answer and then press enter\n");
		System.out.print("3. To navigate to other Question, Type \"nav\" and press enter, select question no afterwards\n");
		System.out.print("4. To submit test type submit and press enter\n");
		
		int quesNo = 0;
		
		while(quesNo != testArray.length) {
			
			System.out.println("Q "+ (quesNo+1) +". " + testArray[quesNo][0]);
			if(testArray[quesNo][1].equals("MCQ")) {
				
				String options[] = testArray[quesNo][2].split(",");
				int optionNo = 0;
				while(optionNo != options.length) {
					System.out.println((optionNo+1)+". " + options[optionNo]);
					optionNo++;
				}
			}
			String input = sc.nextLine();
			if(input.equals("nav")) {
				int jumpToQuesNo = Integer.parseInt(sc.nextLine());
				quesNo = jumpToQuesNo - 1;
				continue;
			}else if(input.equalsIgnoreCase("submit")) {
				System.out.println("Are you sure you want to submit?? Press y for yes and n to continue..");
				if(sc.nextLine().equalsIgnoreCase("y")) {
					break;// write the code to save answered questions in database
				}else continue;
					
			}else {
				
				answers[quesNo] = input;
				
			
				
				
				
			
				
			}
			
			quesNo++;	
			
		}
		
		new JDBCConnect().saveTestAnswers(studentId, testId, answers);
		
		
		
		
		
		
		
		
		
		
		
		}catch(Exception ex) {
			ex.printStackTrace();
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
	}	
	
	
	
	
	
	
}
