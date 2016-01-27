/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject;

/**
 *
 * @author Mike
 */
public class Distance {
    public int getDistance(String str1, String str2){
    
    
    int[][] distance = new int[str1.length() + 1][str2.length() + 1];        
 
        for (int i = 0; i <= str1.length(); i++)                                 
            distance[i][0] = i;                                                  
        for (int j = 1; j <= str2.length(); j++)                                 
            distance[0][j] = j;                                                  
 
        for (int i = 1; i <= str1.length(); i++)                                 
            for (int j = 1; j <= str2.length(); j++)                             
                distance[i][j] = Math.min(
                        distance[i - 1][j] + 1,                                  
                        Math.min(distance[i][j - 1] + 1,                                  
                        distance[i - 1][j - 1] + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1)));
 
        return distance[str1.length()][str2.length()];                           
    }      
    }

