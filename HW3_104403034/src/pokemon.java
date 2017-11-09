
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.*;

public class pokemon extends JFrame{
	private JPanel main = new JPanel();
	private JLabel img = new JLabel();
	private JPanel under = new JPanel(); //放name button1 button2 
	private JPanel button1 = new JPanel(); //第一排按鈕
	private JPanel button2 = new JPanel(); //第二排按鈕
	private JTextField name = new JTextField("set your nickname"); 
	private JButton givecandy = new JButton("Give Candy"); 
	private JButton open = new JButton("Open game");
	private JButton save = new JButton("Save");
	private JButton saveAs = new JButton("Save As");
	private JLabel info = new JLabel("New File");
	private JLabel candycount = new JLabel("0/25");
	private Icon[] icons = new ImageIcon[3];
	private int[] count = new int[3];  //糖果數 
	private Scanner scanner;
	private int s = 0,level = 0; //s:一次加多少,level:等級
	private String type="small",abs; //type:種類
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private File selectedFile;
	private PokeSerializable record,newSave;
	private JFileChooser fileChooser;
	public pokemon() {
		super("Pokemon");
		setLayout(new BorderLayout());
		add(main, BorderLayout.CENTER);
		under.setLayout(new GridLayout(4, 1));
		add(under, BorderLayout.SOUTH);
		button1.add(givecandy);
		button1.add(candycount);
		button2.add(open);
		button2.add(save);
		button2.add(saveAs);
		main.add(img);
		under.add(name);
		under.add(button1);
		under.add(button2);
		under.add(info);
		try{
			scanner = new Scanner(new File(this.getClass().getResource("/pokemon.txt").getPath()));//讀文字檔
			int k=0;
		    while(scanner.hasNext()){
		     icons[k] = new ImageIcon(getClass().getResource(scanner.next()));//存取圖片
		     if(k<=1){
		    	 count[k] = scanner.nextInt();//只有k=0,1才有值
		    	 }
		     k++;
		    }
		}catch(NoSuchElementException elementException){
	    	System.err.println("File improperly formed. Terminating.");
	    	scanner.close();
	    	System.exit(1);
	    }
	    catch(IllegalStateException stateException){
	    	System.err.println("Error reading from file. Terminating.");
	    	System.exit(1);
	    }
	    catch(FileNotFoundException fileNotFoundException){
	    	System.err.println("File Not Found");
	    	System.exit(1);
	    }
	    catch (IOException e) {
	    	System.err.println("Error!");
	    	System.exit(1);
		}
	    finally{
	    	if(input !=null)
	    		scanner.close();
	    }
		
		img.setIcon(icons[0]);
		main.add(img);
		givecandy.addActionListener(
		         new ActionListener() 
		         {
		            @Override
		            public void actionPerformed(ActionEvent e)
		            {
		            	s = s+1;  //一次+1  
		            	if(level==0){
		            		candycount.setText(s+"/"+count[level]);
		            		if(s==count[level]){
		            			JOptionPane.showMessageDialog(null, "Your monster is evolved!!!","Noticed",JOptionPane.INFORMATION_MESSAGE);
			            		level++;
			            		type = "medium";
			            		img.setIcon(icons[level]);
			            		s=0;   //歸零
		            		}
		            	}
		            	if(level==1){
		            		candycount.setText(s+"/"+count[level]);
		            		if(s==count[level]){
		            			JOptionPane.showMessageDialog(null, "Your monster is evolved!!!","Noticed",JOptionPane.INFORMATION_MESSAGE);
		            			JOptionPane.showMessageDialog(null, "Congratuation!!Your monster has evolved","Noticed",JOptionPane.INFORMATION_MESSAGE);
		            			level++;
		            			type = "large";
		            			img.setIcon(icons[level]);
		            		}
		            	}
		            	if(level==2){
		            		candycount.setText(s+"/"+count[level]);
		            	}
		            } 
		         } 
		      );
		open.addActionListener(
		         new ActionListener() 
		         {
		            @Override
		            public void actionPerformed(ActionEvent e)
		            {
		                fileChooser = new JFileChooser();
		                fileChooser.setFileSelectionMode(
		                  JFileChooser.FILES_AND_DIRECTORIES);
		                int result = fileChooser.showOpenDialog(pokemon.this);
		                selectedFile = fileChooser.getSelectedFile();
		                info.setText("File Load From : " + selectedFile.getAbsolutePath());		                
		                try{
		                	input = new ObjectInputStream(new FileInputStream(selectedFile));
		                	record = (PokeSerializable)input.readObject();
		                	name.setText(record.getNickname());
		                	s = record.getCandy();
		                	type = record.getMonster();
		                	switch(record.getMonster()){//設定等級
		        			case "small": //小火龍
		        				level = 0;
		        				break;
		        			case "medium": //火恐龍
		        				level = 1;
		        				break;
		        			case "large": //噴火龍
		        				level = 2;
		        				break;
		        			}
		                	candycount.setText(s+"/"+count[level]);
		                	img.setIcon(icons[level]);
		                	scanner.close();
		                }
		                catch (IOException e1) {
		    				System.err.println("Error!");
		    			}
		    			catch(ClassNotFoundException classNotFoundException){
		    				System.err.println("Class Not Found");	
		    		    }
		            } 
		         } 
		      );
		save.addActionListener(
		         new ActionListener() 
		         {
		            @Override
		            public void actionPerformed(ActionEvent e)
		            {
		            	record = new PokeSerializable(name.getText(),type,s);
		            	try{
		            		output = new ObjectOutputStream(new FileOutputStream(selectedFile));
		            		output.writeObject(record);
		    				output.close();
		    				info.setText("Save to : " + selectedFile.getAbsolutePath());
		            	}
		            	catch(NullPointerException e1){
							JOptionPane.showMessageDialog(pokemon.this, " Press Save As!");
						}
						catch(IOException e1) {
							System.err.println("Error!");
						}
		            } 
		         }
		      );
		saveAs.addActionListener(
		         new ActionListener()
		         {
		            @Override
		            public void actionPerformed(ActionEvent e)
		            {
		            	newSave = new PokeSerializable(name.getText(),type,s);
		            	try{
		            		JFileChooser jfile = new JFileChooser();
		            		int result = jfile.showSaveDialog(pokemon.this);
							if(result == JFileChooser.APPROVE_OPTION){
								File dir = jfile.getSelectedFile();
								FileOutputStream fileoutput = new FileOutputStream(dir);//將檔案(dir)與OutputStream串接
								output = new ObjectOutputStream(fileoutput);//串接
								output.writeObject(newSave);
								info.setText("New Save to : " + dir.getAbsolutePath());
								abs = dir.getAbsolutePath();
								fileoutput.close();
								output.close();
							}
						}
		    			catch(IOException e1) {
		    				System.err.println("Error!");
		    			}
		            } 
		         } 
		      );
	}
}
