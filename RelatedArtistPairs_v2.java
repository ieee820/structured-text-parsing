/**
 *This class finds pairs of artists, in which each pair of artists appears together on a user's favorite list of artists as many times as a given THRESHOLD.
 *Input: csv file (UTF-8 encoding), with each line represents a user's list of favorite artists.
 *Output:console output(stdout) of pairs of artists, which appear together in at least THRESHOLD different lists.
 
 * This is an improved version of RelatedArtistPairs_v1.java. The major different is that here  when inserting each pairs into the Hashtable and update their counts, the program will check if it reached the threshold. If it does, it is output immediately, and this entry is removed from the table. So, we could save the memory occupation, but the biggest gain is to shorten the latency time. For example, when the threshold is set at 20, the first output was generated in 350 miniseconds, while in verion 1 (batch mode) was as long as 1400 miniseconds, over 60% shorter.
 
 *@author Mingsheng Zhang
 *@version 2.0
 *@since Jan 2, 2014
 */ 



package com.bioyinyang.knewton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.*;
import java.util.Arrays;



public class RelatedArtistPairs_v2{

private static final String file_name = "Artist_lists_small.txt";

public static void main(String[] args){

long startTime = System.currentTimeMillis();

int THRESHOLD = Integer.parseInt(args[0]);

String userFavorites;

FileInputStream f_in;
try {
f_in = new FileInputStream(file_name);
}
catch (FileNotFoundException e){
System.out.println("file not found: " + file_name);
return;
}

InputStreamReader stream_in = new InputStreamReader(f_in);
BufferedReader buf_in = new BufferedReader(stream_in);

HashMap<String, Integer> artistPair_count = new HashMap<String, Integer>();
HashSet<String> artistPairs_over_THRESHOLD = new HashSet<String>();//keep the record of pairs reaching the THRESHOLD


try {

while ((userFavorites = buf_in.readLine()) != null){
SortedSet<String> artistList = new TreeSet<String>( Arrays.asList(userFavorites.split("\\s*,\\s*")));
int lengthList = artistList.size();
String[] artistArray = artistList.toArray(new String[lengthList]); //change back to String[] for efficient indexing

for (int i=0; i< lengthList-1; i++){  //loop through first part of binary combinations
  for(int j=i+1; j< lengthList-1; j++){  //second part of binary combinations
  String pairName = artistArray[i] + "#_#" + artistArray[j];
  if (artistPairs_over_THRESHOLD.contains(pairName)){  //skip the pairs which have reached over THRESHOLD and been printed
      continue;
     }

Integer count = artistPair_count.get(pairName);
  if (count==null){
     artistPair_count.put(pairName,1);
   }
  else if (count==THRESHOLD-1){
     artistPair_count.remove(pairName);
     long latency = System.currentTimeMillis();
     long latencyTime = latency - startTime;
     
     System.out.println(artistArray[i] + " , " + artistArray[j]+" , "+latencyTime);
     artistPairs_over_THRESHOLD.add(pairName); //add() not put()
  }
  else{
     artistPair_count.put(pairName, count+1);
  } //end of if block
 
  }  //endof inner for
 }   //endof outer for
}  //endof while
buf_in.close();
} //endof try block

catch(IOException e){
System.out.println("error while reading file: " + file_name);
System.out.println(e.getMessage());
}
long endTime = System.currentTimeMillis();
long totalTime = endTime - startTime;
System.out.println("total time: " + totalTime);

 } //end of main()
} //end of class

