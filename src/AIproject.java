/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.*;
import java.util.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.GroupLayout.*;

/**
 *
 * @author Mike
 */
public class AIproject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
      
        SwingUtilities.invokeLater(new Runnable() {
            public void run (){
                
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                try {
                    new box().setVisible(true);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AIproject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
       
        
    }
    
}
