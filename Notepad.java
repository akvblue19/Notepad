import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

class Notepad  implements ActionListener
{
	Frame mainFrame;	
	
	MenuBar mb;
	Menu fileMenu, editMenu, formatMenu, viewMenu, helpMenu;
	MenuItem fileItemNew, fileItemOpen, fileItemSave, fileItemSaveAs, fileItemExit, editItemUndo, editItemRedo, editItemCut, editItemCopy, editItemPaste, editItemDelete, editItemFind, editItemReplace, editItemGoto, editItemTimeDate, formatItemWordWrap, formatItemFont, viewItemStatusBar, helpItemViewHelp, helpItemAboutNotepad;
	
	TextArea inputTxt;
	
	String fileName = "";
	String saveInputText = "";
	String tempInputText = "";
	Notepad()
	{
		mainFrame = new Frame();	

		mainFrame.setSize(500, 500);
		mainFrame.setTitle("Notepad");
		mainFrame.setLayout(null);


		inputTxt = new TextArea();
		inputTxt.setBounds(10, 60, 480, 430);
	
		mb = new MenuBar();
		
		fileMenu = new Menu("File");	
		fileItemNew = new MenuItem("New");
		fileItemOpen = new MenuItem("Open");
		fileItemSave = new MenuItem("Save");
		fileItemSaveAs = new MenuItem("Save As");
		fileItemExit = new MenuItem("Exit");
		fileMenu.add(fileItemNew);	
		fileMenu.add(fileItemOpen);
		fileMenu.add(fileItemSave);
		fileMenu.add(fileItemSaveAs);
		fileMenu.add(fileItemExit);	
		
		editMenu = new Menu("Edit");
		editItemUndo = new MenuItem("Undo");
		editItemRedo = new MenuItem("Redo"); 
		editItemCut = new MenuItem("Cut");
		editItemCopy = new MenuItem("Copy");
		editItemPaste = new MenuItem("Paste");
		editItemDelete = new MenuItem("Delete"); 
		editItemFind = new MenuItem("Find");
		editItemReplace = new MenuItem("Replace");
		editItemGoto = new MenuItem("Goto");
		editItemTimeDate = new MenuItem("Time/Date");
		
		editMenu.add(editItemUndo);
		editMenu.add(editItemRedo);
		editMenu.add(editItemCut);
		editMenu.add(editItemCopy);
		editMenu.add(editItemPaste);
		editMenu.add(editItemDelete);
		editMenu.add(editItemFind);
		editMenu.add(editItemReplace);
		editMenu.add(editItemGoto);
		editMenu.add(editItemTimeDate);
		
		
		formatMenu = new Menu("Format");
		formatItemWordWrap = new MenuItem("Word Wrap");
		formatItemFont = new MenuItem("Font..");
		formatMenu.add(formatItemWordWrap);
		formatMenu.add(formatItemFont);
			
			
		viewMenu = new Menu("View");
		viewItemStatusBar = new MenuItem("Status Bar");
		viewMenu.add(viewItemStatusBar);
		
		helpMenu = new Menu("Help");
		helpItemViewHelp = new MenuItem("View Help");
		helpItemAboutNotepad = new MenuItem("About Notepad");
		helpMenu.add(helpItemViewHelp);
		helpMenu.add(helpItemAboutNotepad);	
		
		mb.add(fileMenu);
		mb.add(editMenu);
		mb.add(formatMenu);
		mb.add(viewMenu);
		mb.add(helpMenu);
		
		fileItemNew.addActionListener(this);
		fileItemOpen.addActionListener(this);
		fileItemSave.addActionListener(this);
		fileItemSaveAs.addActionListener(this);
		fileItemExit.addActionListener(this);
		
		mainFrame.setMenuBar(mb);
		
		mainFrame.add(inputTxt);
	
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
			
		mainFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}

		});
	}

	public void actionPerformed(ActionEvent e)
	{	
		if(e.getSource() == fileItemNew)
		{
			newMenu();
		}
		else if(e.getSource() == fileItemOpen)
		{
			open();
		}
		else if(e.getSource() == fileItemSave)
		{
			if(fileName.length() == 0)
				saveAs();
			else 
				save();
		}
		else if(e.getSource() == fileItemSaveAs)
		{
			saveAs();
		}
		else if(e.getSource() == fileItemExit)
		{
			JOptionPane.showMessageDialog(mainFrame, "Exit");
			System.exit(0);
		}
		else if(e.getSource() == formatItemFont)
		{
			
		}
	}	
	
	public void newMenu()
	{
		try
		{
			tempInputText = inputTxt.getText();
			if(!tempInputText.equals(saveInputText))
			{
				String[] options = {"Save", "Don't Save", "Cancel"};
				int check = JOptionPane.showOptionDialog(mainFrame, "Changes Made To The File", "choose", 0, 1, null, options, options[0]);	
				switch(check)
				{
					case 0:	
						if(fileName.length() == 0)
						{
							saveAs();
							JOptionPane.showMessageDialog(mainFrame, "Saved Succesfully");
						}
						else 
						{
							save();
							JOptionPane.showMessageDialog(mainFrame, "Saved Succesfully");
						}
						break;
					case 1:
						inputTxt.setText("");
						break;
					default:
				}	
			}  
			
			inputTxt.setText("");
			fileName = "";
		}
		catch(Exception ee)
		{
			System.out.println(ee);
		}
	}

	public void open()
	{
		tempInputText = inputTxt.getText();
		if(inputTxt.getText().length() != 0 && !tempInputText.equals(saveInputText))
		{
			newMenu();
			JOptionPane.showMessageDialog(mainFrame, "Select File To Open");
		}
		FileDialog fd = new FileDialog(mainFrame, "Open File", 0);
		fd.setVisible(true);
		try
		{
			fileName = fd.getDirectory() + fd.getFile();
			mainFrame.setTitle(fd.getFile());
			FileReader fis = new FileReader(fileName);
			BufferedReader bis = new BufferedReader(fis);	
			String data;
			String showTxt = "";
			while((data = bis.readLine()) != null)
			{
				showTxt = showTxt + data + "\n";
			}
			inputTxt.setText(showTxt);
			saveInputText = inputTxt.getText();
		}
		catch(Exception ee)
		{	
			System.out.println(ee);
		}
	}

	public void save()
	{
		try
			{
				PrintWriter out = new PrintWriter(fileName);
				String data = inputTxt.getText();
				out.println(data);
				out.close();
			}
			catch(Exception ee)
			{
				System.out.println(ee);
			}
	}	
	public void saveAs()
	{
		FileDialog fd = new FileDialog(mainFrame, "Save File", 1);
		fd.setVisible(true);
		fileName = fd.getDirectory() + fd.getFile();
		mainFrame.setTitle(fd.getFile());
		try
		{
			PrintWriter out = new PrintWriter(fileName);
			String data = inputTxt.getText();
			saveInputText = inputTxt.getText();	
			out.println(data);
			out.close();
		}
		catch(Exception ee)
		{
			System.out.println(ee);
		}
	}
	public void font()
	{
	
	}
	
	public static void main(String args[])
	{
		new Notepad();
	}
}