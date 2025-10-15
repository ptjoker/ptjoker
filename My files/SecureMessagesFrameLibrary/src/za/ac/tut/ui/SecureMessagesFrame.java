/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.File;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
/**
 *
 * @author MemaniV
 */
public class SecureMessagesFrame extends JFrame {
    //menu bar
    private JMenuBar menuBar;
    
    //menu 
    private JMenu fileMenu;
    
    //menu items
    private JMenuItem openFileMenuItem;
    private JMenuItem encryptFileMenuItem;
    private JMenuItem saveEncryptedFileMenuItem;
    private JMenuItem clearFileMenuItem;
    private JMenuItem exitFileMenuItem;
    
    //panels
    private JPanel headingPnl;
    private JPanel plainTextPnl;
    private JPanel encryptedTextPnl;
    private JPanel mainPnl;
    
    //label
    private JLabel headingLbl;
    
    //text area
    private JTextArea plainMsgTxtArea;
    private JTextArea encryptedMsgTxtArea;

    //text area
    private JScrollPane scrollablePlainMsgTxtArea;
    private JScrollPane scrollableEncryptedMsgTxtArea;
        
    public SecureMessagesFrame(){
        //initialise the frame
        setTitle("Secure Messages");
        setSize(50, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //create menu bar
        menuBar = new JMenuBar();
        
        //create menu 
        fileMenu = new JMenu("File");
        
        //create menu items
        openFileMenuItem = new JMenuItem("Open file...") ;
        openFileMenuItem.addActionListener(new OpenFileMenuItemListener());
        
        encryptFileMenuItem = new JMenuItem("Encrypt message...") ;
        encryptFileMenuItem.addActionListener(new EncryptFileMenuItemListener());
        
        saveEncryptedFileMenuItem = new JMenuItem("Save encrypted message...") ;
        saveEncryptedFileMenuItem.addActionListener(new SaveEncryptedFileMenuItemListener());
        
        clearFileMenuItem = new JMenuItem("Clear") ;
        clearFileMenuItem.addActionListener(new ClearFileMenuItemListener());

        exitFileMenuItem = new JMenuItem("Exit") ;
        exitFileMenuItem.addActionListener(new ExitFileMenuItemListener());
        
        //add menu items to menu
        fileMenu.add(openFileMenuItem);
        fileMenu.add(encryptFileMenuItem);
        fileMenu.add(saveEncryptedFileMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(clearFileMenuItem);
        fileMenu.add(exitFileMenuItem);
        
        //add menu to the menu bar
        menuBar.add(fileMenu);

        //create panels
        headingPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        plainTextPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        plainTextPnl.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "Plain message"));
        
        encryptedTextPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        encryptedTextPnl.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "Encrypted message"));
        
        mainPnl = new JPanel(new BorderLayout());
        
        //create label
        headingLbl = new JLabel("Message Encryptor");
        headingLbl.setForeground(Color.BLUE);
        headingLbl.setFont(new Font("SERIF", Font.BOLD + Font.ITALIC, 30));
        headingLbl.setBorder(new BevelBorder(BevelBorder.RAISED));
        
        //text areas
        plainMsgTxtArea = new JTextArea(10,30);
        plainMsgTxtArea.setEditable(false);
        
        encryptedMsgTxtArea = new JTextArea(10, 30);
        encryptedMsgTxtArea.setEditable(false);
        
        //make the text areas scrollable
        scrollablePlainMsgTxtArea = new JScrollPane(plainMsgTxtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableEncryptedMsgTxtArea = new JScrollPane(encryptedMsgTxtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //add components to panels
        headingPnl.add(headingLbl);
        plainTextPnl.add(scrollablePlainMsgTxtArea);
        encryptedTextPnl.add(scrollableEncryptedMsgTxtArea);
        
        mainPnl.add(headingPnl, BorderLayout.NORTH);
        mainPnl.add(plainTextPnl, BorderLayout.WEST);
        mainPnl.add(encryptedTextPnl, BorderLayout.EAST);
                 
        //add the menu bar to the frame
        setJMenuBar(menuBar);
        
        //add main panel to the frame
        add(mainPnl);
        
        //pack the components
        pack();
        
        //set resizable
        setResizable(false);
        
        //visible
        setVisible(true);
    }
    
    //anonymous class for opening a plain text file. It handles the Open file menu item.
    private class OpenFileMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) 
        {     
		//write your code here
            
            JFileChooser fc = new JFileChooser();
            
            File file = null;
            
            int result = fc.showOpenDialog(null);
            
            if (result == JFileChooser.APPROVE_OPTION)
            {
                    file = fc.getSelectedFile();
            }
            else
            {
                System.out.println("Failure trying to load file.");
            }
            
            
            try(BufferedReader reader = new BufferedReader(new FileReader(file))) 
            {
                 String data = reader.readLine();
                 String output  = "";
                 while (data != null)
                 {
                     output += data + "\n";    
                     data = reader.readLine();
                 }
                 reader.close();
                 plainMsgTxtArea.setText("");
                 plainMsgTxtArea.setText(output);
            }
            catch (IOException ex)
            {
               System.err.println(ex.getMessage());
            }
        }
    }

    //anonymous class for encrypting the message. It handles the Encrypt message menu item.
    private class EncryptFileMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) 
        {      
            //write your code here
            String textFromTxtArea = plainMsgTxtArea.getText();
            String[] characters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};  
            
            String encryptedText = "";
            textFromTxtArea = textFromTxtArea.toLowerCase();
            
            for (int i = 0; i < textFromTxtArea.length(); i++) 
            {
                char c = textFromTxtArea.charAt(i);
                String myc = String.valueOf(c);
                String encryptChar = "";
                
                if(Character.isWhitespace(c) || !Character.isLetter(c))
                {
                    encryptedText += myc;
                }

                else
                {    
                    for (int j = 0; j < characters.length; j++)
                    {

                        if (myc.equals(characters[j]))
                        {
                             encryptChar = characters[j + 3]; 
                             break;
                        }
                   }
                 
                 encryptedText += encryptChar;
                }  
            }
            
            encryptedMsgTxtArea.setText("");
            encryptedMsgTxtArea.setText(encryptedText);
        }
    }

    //anonymous class for saving encrypted message. It handles the Save encrypted message menu item.
    private class SaveEncryptedFileMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae)
        {    
            //write your code here 
            String data = encryptedMsgTxtArea.getText();
            
            try 
            {
               Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/EncryptedMessagesDB");
               
               String sql = "INSERT INTO Messages (Message) VALUES (?)";
               PreparedStatement ps = connect.prepareStatement(sql);
               ps.setString(1, data);
               ps.executeUpdate();
               ps.close();
               
                String message = "";
               String query = "SELECT * FROM Messages ORDER BY Id";
               Statement smt = connect.createStatement();
               ResultSet rs = smt.executeQuery(query);
               
               while(rs.next())
               {
                   message = rs.getString("Message");
               }
               rs.close();
               smt.close();
               
            encryptedMsgTxtArea.setText("");
            encryptedMsgTxtArea.setText(message);
            }
            catch (SQLException ex)
            {
              System.err.println(ex.getMessage());
            }
        }
    }
  
    //anonymous class for clearing the respective text areas. It handles the clear menu item
    private class ClearFileMenuItemListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae)
        {
            //write your code here  
            encryptedMsgTxtArea.setText("");
            plainMsgTxtArea.setText("");
        }
        
    }
    
    //anonymous class for exiting the application. It handles the exit menu item
    private class ExitFileMenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //write your code here
            System.exit(0);
        }
        
    }
}
