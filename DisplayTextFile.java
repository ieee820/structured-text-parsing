/**

 * This is the a program to view content of a large text file: a scrollable panel.
 * Input: any text file  
 *Output: any GUI with scrollable bars

 *@author Mingsheng Zhang
 *@version 1.0
 *@since Jan 2, 2014
 */ 



package com.bioyinyang.knewton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class DisplayTextFile{
 

private final static String file_name = "Artist_lists_small.txt";

public static void main(String[] args){

String str_line, text;
text="";
JFrame.setDefaultLookAndFeelDecorated(false);
JFrame frame = new JFrame("Artist_lists_small.txt");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

frame.setLayout(new FlowLayout());



FileInputStream f_in;
try{
 f_in = new FileInputStream(file_name);
}
catch(FileNotFoundException e){
System.out.println("file not found: " + file_name);
return;
}

InputStreamReader stream_in  = new InputStreamReader(f_in);
BufferedReader br = new BufferedReader(stream_in);



try{
while((str_line=br.readLine()) != null)
{
text = str_line + "\n\n" +  text;
}
br.close();
}
catch(IOException e)
{
System.out.println("Error opening file:  " + file_name);
System.out.println(e.getMessage());
return;
}




JTextArea textArea = new JTextArea(text,50,50);
//textArea.setPreferredSize(new Dimension(100,100));



JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
textArea.setLineWrap(true);

frame.add(scrollPane);
frame.pack();
frame.setVisible(true);
}
}
