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
import javax.swing.JOptionPane;
import javax.swing.*;
import java.util.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.text.BadLocationException;
import javax.swing.GroupLayout.*;

public class box extends JFrame
        implements DocumentListener {
    
    private int r;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JTextArea textArea;
     
    private static final String COMMIT_ACTION = "commit";
    private static enum Mode { INSERT, COMPLETION };
    
    private Mode mode = Mode.INSERT;
     Map<String, Integer> Mapp;
     Iterator<String> tt;
     BKTree<String> bkTree = new BKTree<>(new Distance());
     
    public box () throws FileNotFoundException {
        super("Spell Checker");
        initComponents();
        JFileChooser fc = new JFileChooser();
       int returnVal = fc.showOpenDialog(null);
       File file = fc.getSelectedFile();
       Scanner in = new Scanner(file);
       
       
        
        
        
        while(in.hasNext()){
        bkTree.add(in.next());
    }
         
        textArea.getDocument().addDocumentListener(this);
         
        InputMap im = textArea.getInputMap();
        ActionMap am = textArea.getActionMap();
        im.put(KeyStroke.getKeyStroke("ENTER"), COMMIT_ACTION);
        am.put(COMMIT_ACTION, new CommitAction());
         
        
    }
     
     
    private void initComponents() {
        jLabel1 = new JLabel("Try typing");
         
        textArea = new JTextArea();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        textArea.setColumns(20);
        textArea.setLineWrap(true);
        textArea.setRows(5);
        textArea.setWrapStyleWord(true);
         
        jScrollPane1 = new JScrollPane(textArea);
         
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
         
        //Create a parallel group for the horizontal axis
        ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        //Create a sequential and a parallel groups
    SequentialGroup h1 = layout.createSequentialGroup();
        ParallelGroup h2 = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
        //Add a scroll panel and a label to the parallel group h2
    h2.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
        h2.addComponent(jLabel1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
         
    //Add a container gap to the sequential group h1
    h1.addContainerGap();
        // Add the group h2 to the group h1
    h1.addGroup(h2);
        h1.addContainerGap();
        //Add the group h1 to hGroup
    hGroup.addGroup(Alignment.TRAILING,h1);
        //Create the horizontal group
    layout.setHorizontalGroup(hGroup);
         
    //Create a parallel group for the vertical axis
        ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        //Create a sequential group
    SequentialGroup v1 = layout.createSequentialGroup();
        //Add a container gap to the sequential group v1
    v1.addContainerGap();
        //Add a label to the sequential group v1
    v1.addComponent(jLabel1);
        v1.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        //Add scroll panel to the sequential group v1
    v1.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
        v1.addContainerGap();
        //Add the group v1 to vGroup
    vGroup.addGroup(v1);
        //Create the vertical group
    layout.setVerticalGroup(vGroup);
        pack();
         
    }
    
    public void changedUpdate(DocumentEvent ev) {
    }
     
    public void removeUpdate(DocumentEvent ev) {
    }
     
    public void insertUpdate(DocumentEvent ev) {
        if (ev.getLength() != 1) {
            return;
        }
         
        int pos = ev.getOffset();
        String content = null;
        try {
            content = textArea.getText(0, pos + 1);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
         
        // Find where the word starts
        
        
        if (content.charAt(pos)!='*') {
            // Too few chars
            return;
        }int w;
        r=pos;
        
        for (w = r; w > 0; w--) {
            if (! Character.isLetter(content.charAt(w)) && content.charAt(w)!='*') {
                this.r = w;
                break;
            }
            
        }
         
        String prefix = content.toLowerCase().substring(w).replace('*', ' ').trim();
       
        Object[] numm = {1,2,3};
        int qq = (Integer) JOptionPane.showInputDialog(
                    null,
                    "Choose Distance",
                    "Levenshtein Distance",
                    JOptionPane.OK_OPTION,null,
                    numm ,"choose");
        Mapp = bkTree.query(prefix, qq);
        tt = Mapp.keySet().iterator();
        ArrayList<String> thi = new ArrayList<String>();
        while(tt.hasNext()){
    thi.add(tt.next());
    }
        if(thi.isEmpty()){mode = Mode.INSERT;}
        
        Object[] q = new Object[thi.size()];
        for(int i = 0;i< thi.size();i++){
            q[i]=thi.get(i);
        }
        
String s = (String)JOptionPane.showInputDialog(
                    null,
                    "Choose correct word",
                    "Spell Checker",
                    JOptionPane.YES_NO_OPTION,null,
                    q,"choose");
                    
                    
        
                    



        
            
            
                // A completion is found
                String completion = s;
                if(s==null){completion= "No Change";}
                // We cannot modify Document from within notification,
                // so we submit a task that does the change later
                SwingUtilities.invokeLater(
                        new CompletionTask(" --> "+completion, pos+1));
            
         
    }
     
   private class CompletionTask implements Runnable {
        String completion;
        int position;
         
        CompletionTask(String completion, int position) {
            this.completion = completion;
            this.position = position;
        }
         
        public void run() {
            textArea.insert(completion + "\n", position);
            textArea.setCaretPosition(position + completion.length());
            textArea.moveCaretPosition(position);
            mode = Mode.COMPLETION;
        }
    }
   private class CommitAction extends AbstractAction {
        public void actionPerformed(ActionEvent ev) {
            if (mode == Mode.COMPLETION) {
                int pos = textArea.getSelectionEnd();
                textArea.insert(" ", pos);
                textArea.setCaretPosition(pos + 1);
                mode = Mode.INSERT;
            } else {
                textArea.replaceSelection("\n");
            }
        }
    }
     
    }
    
    

