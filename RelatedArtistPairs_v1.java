/**
 *This class finds pairs of artists, in which each pair appears together on a user's list of favorite artists as many times as a given THRESHOLD.
 *Input: csv file (UTF-8 encoding), with each line represents a user's list of favorite artists.
 *Output:console output(stdout) of pairs of artists, which appear together in at least THRESHOLD number of times on different lists.

 * Here is a very straightforward algorithms to solve this combinatory problem. Here input data could be treated as a long table, 1000 rows(lines, users) x 50 columns(user's favorite artists). It was read into the memory by line/row from inputstream, so the memory consumption is minimal. I constructed a hashtable (implemented as HashMap) to store the paired-artists and their counts. The keysets are ordered binary combinations of artists names, which are sorted by their unicodes, and are generated on-the-fly from the input stream. The maximal number of the number combination is in the second power of column number(which is upbounded by 50), and linear with row number (here is 1000, but could potentially be very large). The theoretical memory requirement is 16M bytes. Because most combinations do not exist, the memory occupation for this hashtable was below 1M bytes. There are no other significant memory consumptions. In this implementation, all appeared artist binary combinations were counted before exporting those pairs which had reached predefined thresholds. This batch mode means a significant latency time, which measured the elapsed time before first pairs were printed. Because the access time complexity of Hashtable is O(1)(random access), the program were finished under 200 miniseconds for a wide range of threshold(from 10-120) running on machine of 2.5 GHz Intel Core i5, 4GB memory, 1600 MHz DDR3.
* Java and Javac version: 1.6.0_65
* compile: javac -d base_dir/  dir_of_java_sourcefile/RelatedArtistPair_v1.java * (This will generate the path/dir: base_dir/com/bioyinyang/knewton/ to store the compiled .class file if it doesnot exist or is not complete)
* run: java -classpath base_dir/ com.bioyinyang.knewton.RelatedArtistPair_v1 50
*      50 is the threshold arg, could be 1-1000, but over 120 will output nothing

* To visualize the input text file content, I wrote a program for  a scrollable GUI, which could be compiled and run in exactly the same way-
* to compile: javac -d base_dir/  dir_of_java_sourcefile/DisplayTestFile.java
* to run: java -classpath base_dir/ com.bioyinyang.knewton.DisplayTestFile
 



 *@author Mingsheng Zhang
 *@version 1.0
 *@since Jan 02, 2014
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
import java.util.Arrays;
import java.util.*;


public class RelatedArtistPairs_v1{

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

try {

while ((userFavorites = buf_in.readLine()) != null){
SortedSet<String> artistList = new TreeSet<String>( Arrays.asList(userFavorites.split("\\s*,\\s*")));
int lengthList = artistList.size();
String[] artistArray = artistList.toArray(new String[lengthList]); //change back to String[] for efficient indexing

for (int i=0; i< lengthList-1; i++){  //loop through first part of binary combinations
  for(int j=i+1; j< lengthList-1; j++){  //second part of binary combinations
  String pairName = artistArray[i] + "#_#" + artistArray[j];
  Integer count = artistPair_count.get(pairName);
  if (count == null){
   artistPair_count.put(pairName,1);
   }
  else {
  artistPair_count.put(pairName, count+1);
  }

  }  //endof inner for
 }   //endof outer for
}  //endof while
buf_in.close();
} //endof try block

catch(IOException e){
System.out.println("error while reading file: " + file_name);
System.out.println(e.getMessage());
}

long latencyEnd = System.currentTimeMillis();
for (Iterator<String> it = artistPair_count.keySet().iterator(); it.hasNext();){
  String pairName = it.next();
   Integer count = artistPair_count.get(pairName);
       if (count >= THRESHOLD) {
     String[] artistP = pairName.split("#_#");
     System.out.println(artistP[0]  + " , " + artistP[1]);
     }
       else {
      continue;
     }
} //end of for block

long endTime = System.currentTimeMillis();
long totalTime = endTime - startTime;
long latencyTime = latencyEnd-startTime;
System.out.println("total time: " + totalTime);
System.out.println("latency: " + latencyTime);
}  //end of main()
} // end of class

