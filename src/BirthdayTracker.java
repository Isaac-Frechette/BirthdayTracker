import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.SpringLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;


public class BirthdayTracker extends Frame implements ActionListener, WindowListener
{
    int maxEntries = 100;     // Global variable to define the maximum size of the 3 arrays.
    int numberOfEntries = 0;  // Global variable to remember how many actual entries are currently in the 3 arrays.
    int currentEntry = 0;     // Global variable to remember which entry in the arrays we are currently focused on.
    
    // Declare the 3 arrays for storing the PC/IP data in memory - each has a maximum size of
	//         maxEntries (currently equal to 100 entries)

    FriendInfo[] friendInfo = new FriendInfo[maxEntries];
    
    Label lblFriendName, lblLikes, lblDislikes, lblDay, lblMonth, lblFind;
    TextField txtFriendName, txtLikes, txtDislikes, txtDay, txtMonth, txtFind;
    Button btnNew, btnSave, btnDel, btnFind, btnExit, btnFirst, btnPrev, btnNext, btnLast, btnSort, btnBinarySearch, btnDisplayList, btnMonthSearch;
    TextArea txtDisplayArea;
    String[] sortArray = new String[maxEntries];
    
    String dataFileName = "BirthdayTracker.txt";

    public static void main(String[] args)
    {
        Frame myFrame = new BirthdayTracker();
        myFrame.setSize(450,550);
        myFrame.setLocation(400, 200);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
    }

    public BirthdayTracker()
    {
        setTitle("Birthday Tracker");
        setBackground(Color.LIGHT_GRAY);

        SpringLayout myLayout = new SpringLayout();
        setLayout(myLayout);
        
	// Call the methods below to instantiate and place the various screen components
        LocateLabels(myLayout);
        LocateTextFields(myLayout);
        LocateButtons(myLayout);
        txtDisplayArea = LocateATextArea(myLayout, txtDisplayArea, 40, 270, 10, 50);

        this.addWindowListener(this);
    }

    //------------------------------------------------------------------------------------------
    // Method that manages the adding of multiple Labels to the screen.
    // Each line requests the LocateALabel method to instantiate, add and place a specific Label  
    public void LocateLabels(SpringLayout myLabelLayout)
    {
        lblFriendName = LocateALabel(myLabelLayout, lblFriendName, "Friend Name", 30, 25);
        lblLikes = LocateALabel(myLabelLayout, lblLikes, "Likes", 30, 50);
        lblDislikes = LocateALabel(myLabelLayout, lblDislikes, "Dislikes", 30, 75);
        lblDay = LocateALabel(myLabelLayout, lblDay, "Birthday - Day", 30, 100);
        lblMonth = LocateALabel(myLabelLayout, lblMonth, "Birthday - Month", 30, 125);
        lblFind = LocateALabel(myLabelLayout, lblFind, "Search", 30, 175);
    }

    public Label LocateALabel(SpringLayout myLabelLayout, Label myLabel, String  LabelCaption, int x, int y)
    {
        myLabel = new Label(LabelCaption);
        myLabel.setBackground(Color.BLUE);
        myLabel.setForeground(Color.WHITE);
        add(myLabel);        
        myLabelLayout.putConstraint(SpringLayout.WEST, myLabel, x, SpringLayout.WEST, this);
        myLabelLayout.putConstraint(SpringLayout.NORTH, myLabel, y, SpringLayout.NORTH, this);
        return myLabel;
    }

    public void LocateTextFields(SpringLayout myTextFieldLayout)
    {
        txtFriendName  = LocateATextField(myTextFieldLayout, txtFriendName, 20, 130, 25);
        txtLikes = LocateATextField(myTextFieldLayout, txtLikes, 20, 130, 50);
        txtDislikes = LocateATextField(myTextFieldLayout, txtDislikes, 20, 130, 75);
        txtDay = LocateATextField(myTextFieldLayout, txtDay, 20, 130, 100);
        txtMonth = LocateATextField(myTextFieldLayout, txtMonth, 20, 130, 125);
        txtFind = LocateATextField(myTextFieldLayout, txtFind, 20, 130, 175);
    }

    public TextField LocateATextField(SpringLayout myTextFieldLayout, TextField myTextField, int width, int x, int y)
    {
        myTextField = new TextField(width);
        add(myTextField);        
        myTextFieldLayout.putConstraint(SpringLayout.WEST, myTextField, x, SpringLayout.WEST, this);
        myTextFieldLayout.putConstraint(SpringLayout.NORTH, myTextField, y, SpringLayout.NORTH, this);
        return myTextField;
    }
    
    public void LocateButtons(SpringLayout myButtonLayout)
    {
        btnNew = LocateAButton(myButtonLayout, btnNew, "New", 320, 25, 80, 25);
        btnSave = LocateAButton(myButtonLayout, btnSave, "Save", 320, 50, 80, 25);
        btnDel = LocateAButton(myButtonLayout, btnDel, "Delete", 320, 75, 80, 25);
        btnFind = LocateAButton(myButtonLayout, btnFind, "Find", 320, 175, 80, 25);
        btnExit = LocateAButton(myButtonLayout, btnExit, "Exit", 320, 210, 80, 25);
        btnFirst = LocateAButton(myButtonLayout, btnFirst, "|<", 140, 210, 30, 25);
        btnPrev = LocateAButton(myButtonLayout, btnPrev, "<", 180, 210, 30, 25);
        btnNext = LocateAButton(myButtonLayout, btnNext, ">", 220, 210, 30, 25);
        btnLast = LocateAButton(myButtonLayout, btnLast, ">|", 260, 210, 30, 25);
        btnSort = LocateAButton(myButtonLayout, btnSort, "Sort", 40, 240, 100, 25);
        btnBinarySearch = LocateAButton(myButtonLayout, btnBinarySearch, "Binary Search", 140, 240, 120, 25);
        btnDisplayList = LocateAButton(myButtonLayout, btnDisplayList, "Display Record List", 260, 240, 150, 25);
        btnMonthSearch = LocateAButton(myButtonLayout, btnDisplayList, "Search by Month", 40, 450, 100, 25);
    }

    public Button LocateAButton(SpringLayout myButtonLayout, Button myButton, String  ButtonCaption, int x, int y, int w, int h)
    {    
        myButton = new Button(ButtonCaption);
        add(myButton);
	// Add an ActionListener to each button.
        myButton.addActionListener(this);
        myButtonLayout.putConstraint(SpringLayout.WEST, myButton, x, SpringLayout.WEST, this);
        myButtonLayout.putConstraint(SpringLayout.NORTH, myButton, y, SpringLayout.NORTH, this);
        myButton.setPreferredSize(new Dimension(w,h));
        return myButton;
    }
  
        public TextArea LocateATextArea(SpringLayout myLayout, TextArea myTextArea, int x, int y, int w, int h)
    {    
        myTextArea = new TextArea(w,h);
        add(myTextArea);
        myLayout.putConstraint(SpringLayout.WEST, myTextArea, x, SpringLayout.WEST, this);
        myLayout.putConstraint(SpringLayout.NORTH, myTextArea, y, SpringLayout.NORTH, this);
        return myTextArea;
    }
        
        
    public void actionPerformed(ActionEvent e)
    {
        // BUTTON FIRST
        if(e.getSource() == btnFirst)
        {
            currentEntry = 0;
            displayEntry(currentEntry);
        }

        // BUTTON PREVIOUS
        if(e.getSource() == btnPrev)
        {
            if (currentEntry > 0){
                currentEntry--;
                displayEntry(currentEntry);
            }
        }

        // BUTTON NEXT
        if (e.getSource()== btnNext)
        {
            if (currentEntry < numberOfEntries - 1){
                currentEntry++;
                displayEntry(currentEntry);
            }
        }

        // BUTTON LAST
        if(e.getSource() == btnLast)
        {
            currentEntry = numberOfEntries - 1;
            displayEntry(currentEntry);
        }

        // BUTTON NEW
        if(e.getSource() == btnNew)
        {
            if (numberOfEntries < maxEntries - 1)
            {
                numberOfEntries++;
                currentEntry = numberOfEntries - 1;
                friendInfo[currentEntry] = new FriendInfo("","","","","");
                displayEntry(currentEntry);
            }
        }

        // BUTTON SAVE
        if(e.getSource() == btnSave)
        {
            saveEntry(currentEntry);
        }

        // BUTTON DELETE
        if(e.getSource()== btnDel)
        {
            for (int i = currentEntry; i < numberOfEntries - 1; i++)
            {
                friendInfo[i].setfriendInfo(friendInfo[i+1].getFriendName(), friendInfo[i+1].getLikes(), friendInfo[i+1].getDislikes(), friendInfo[i+1].getDay(), friendInfo[i+1].getMonth());
            }
            numberOfEntries--;
            if (currentEntry > numberOfEntries - 1)
            {
                currentEntry = numberOfEntries - 1;
            }
            displayEntry(currentEntry);
        }

        // BUTTON FIND
        if(e.getSource() == btnFind)
        {   
            if (!"".equals(txtFind.getText()))
            {
            boolean found = false;
            int i = 0;
            while (i < numberOfEntries && found == false)
            {
                if (friendInfo[i].getFriendName().equals( txtFind.getText()))
                {
                    found = true;
                }
                i++;
            }
            if (found) 
            {
                currentEntry = i - 1;
                displayEntry(currentEntry);
            }
            }
        }
        
        //SEARCH BY MONTH BUTTON
        if(e.getSource() == btnMonthSearch)
        {   
            if (!"".equals(txtFind.getText()))
            {
            SortButton();
            txtDisplayArea.setText("Searching " + txtFind.getText());
            
            if (txtFind.getText().equals("1"))
                txtDisplayArea.append("st");
            
            else if (txtFind.getText().equals("2"))
                txtDisplayArea.append("nd");
                    
            else if (txtFind.getText().equals("3"))
                txtDisplayArea.append("rd");
            else 
                txtDisplayArea.append("th");
            
            txtDisplayArea.append(" Month");
            
            txtDisplayArea.append("\n" + "---------------------------------------" + "\n");
            for (int i = 0; i < numberOfEntries; i++)
            {                
                if (friendInfo[i].getMonth().equals( txtFind.getText()))
                {
                    txtDisplayArea.append(sortArray[i] + "\n");
                }
            }
            }
        }
        
        // BUTTON EXIT
        if(e.getSource() == btnExit)
        {
            writeFile();
            System.exit(0);
        }
                   

        // BUTTON SORT -------------------------------------------
        if(e.getSource() == btnSort)
        {
            SortButton();
        }
        
        // BUTTON BINARY SEARCH -----------------------------------
        if(e.getSource() == btnBinarySearch)
        {
            if (!"".equals(txtFind.getText()))
            {
            SortButton();
            int result = Arrays.binarySearch(sortArray, 0, numberOfEntries, txtFind.getText());
            if (result >= 0)
                txtDisplayArea.append("\nBinary Search:\nResult Found at Position " + (result +1));
            else
               txtDisplayArea.append("\nBinary Search:\nNo Results Found");
            }
        }
        
        // BUTTON DISPLAY LIST -------------------------------------
        if(e.getSource() == btnDisplayList)
        {
            // Use .setText to clear the TextArea and add the field headings
            // Note:  \t - add a tab between entries,  \n - go to a new line.
            txtDisplayArea.setText("Name \t Likes \t Dislikes \t Bday/Day \t Bday/Month \n");
            // Use .append to add a line under the headings
            txtDisplayArea.append("----------------------------------------------------------------------------------------- \n");
            // Loop through the various records and add each one to a new line within the TextArea
            for(int i = 0; i < numberOfEntries; i++)
            {
                txtDisplayArea.append(friendInfo[i].getFriendName() + "\t" + friendInfo[i].getLikes() + "\t" + friendInfo[i].getDislikes() + "\t" + "\t" + friendInfo[i].getDay() + "\t" + "\t" + friendInfo[i].getMonth() + "\n");
            }
        }
    }
    
    private void SortButton()
    {
                    for(int i = 0; i < numberOfEntries; i++)
            {
                sortArray[i] = friendInfo[i].getFriendName();
            }
            // Sort the numberOfEntries in the sortArray
            Arrays.sort(sortArray, 0, numberOfEntries);
            // Display the sorted list in the textArea
            // Note:  \n - go to a new line in the TextArea
            txtDisplayArea.setText("Sorted Names:\n");
            txtDisplayArea.append("--------------------------\n");
            for(int i = 0; i < numberOfEntries; i++)
            {
                txtDisplayArea.append(sortArray[i] + "\n");
            }
    }
    
    
    
    
    
    // Manage responses to the various Window events


    public void windowClosing(WindowEvent we)
    {
        writeFile();
        System.exit(0);
    }

    public void windowIconified(WindowEvent we)
    {
    }

    public void windowOpened(WindowEvent we)
    {
        try
        {
        readFile(dataFileName);
        displayEntry(currentEntry);
        }
        catch (Exception e)
        {
            txtDisplayArea.append("\n" + "Error Reading File: " + e.getMessage());
        }
        
    }

    public void windowClosed(WindowEvent we)
    {
    }

    public void windowDeiconified(WindowEvent we)
    {
    }

    public void windowActivated(WindowEvent we)
    {
    }

    public void windowDeactivated(WindowEvent we)
    {
    }


    public void displayEntry(int i)
    {
        txtFriendName.setText(friendInfo[i].getFriendName());
        txtLikes.setText(friendInfo[i].getLikes());
        txtDislikes.setText(friendInfo[i].getDislikes());
        txtDay.setText(friendInfo[i].getDay());
        txtMonth.setText(friendInfo[i].getMonth());
    }

    

    public void saveEntry(int i)
    {
        friendInfo[i].setfriendInfo(txtFriendName.getText(),txtLikes.getText(),txtDislikes.getText(),txtDay.getText(),txtMonth.getText()); 
        writeFile();
    }

    public void readFile(String fileName)
    {
        // Try to read in the data and if an exception occurs go to the Catch section 
        try
        {
            // Set up various streams for reading in the content of the data file.
            FileInputStream fstream = new FileInputStream(fileName);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            int i = 0;    // i is used as the line counter
            String line;  // line is used to temporarily store the line read in from the data file
            
            // Read a line from the data file into the buffer and then check whether
            //      it is null.  The while loop continues until a line read in is null.
                       if ((br.readLine()) == null)
                        {
                            txtDisplayArea.setText("No Data Found on Disk!");
                        }
            
            while ((line = br.readLine()) != null)
            {
                // Split the line of data (from the text file) and put each entry into the
				//                                             temporary array - temp[]
                String[] temp = line.split(",");
                // Save each entry into its respective PCDataRecord object.
                friendInfo[i] = new FriendInfo(temp[0],temp[1],temp[2],temp[3],temp[4]); 
                i++;  // Increment i so we can keep a count of how many entries have been read in.
            }

            numberOfEntries = i;   // Set numberOfEntries equal to i, to remember how many entries are now in the array 
            br.close();            // Close the BufferedReader
            in.close();            // Close the DataInputStream
            fstream.close();       // Close the FileInputStream

        }
        catch (Exception e)
        {
            System.err.println("Error Reading File: " + e.getMessage());
        }
    }

    
    // Write the data back out to the data file - one line at a time
    // Note: You may wish to use a different data file name while initially
    //       developing, so as not to accidently corrupt your input file.
    public void writeFile()
    {
        // Try to print out the data and if an exception occurs go to the Catch section 
        try
        {
            // After testing has been completed, replace the hard-coded filename: "Dislikes_New.txt"
            //       with the parameter variable: fileName 
            // Set up a PrintWriter for printing the array content out to the data file.
            PrintWriter printFile = new PrintWriter(new FileWriter("BirthdayTracker.txt")); 
            
            // Print out each line of the array into your data file.
            // Each line is printed out in the format:  FriendName,Likes,Dislikes
            for(int i = 0; i < numberOfEntries; i++)
            {
                printFile.println(friendInfo[i].getFriendName() + "," + friendInfo[i].getLikes() + "," + friendInfo[i].getDislikes() + "," + friendInfo[i].getDay() + "," + friendInfo[i].getMonth());
            }

            // Close the printFile (and in so doing, empty the print buffer)
             printFile.close();
        }
        catch (Exception e)
        {
            // If an exception occurs, print an error message on the console.
            System.err.println("Error Writing File: " + e.getMessage());
        }
    }
  
}


class FriendInfo 
{
    private String FriendName = new String();   
    private String Likes = new String();
    private String Dislikes = new String();
    private String Day = new String();
    private String Month = new String();
    
    
    public FriendInfo()
    {    
        FriendName = "Friend_Name";
        Likes = "Friend_Likes";
        Dislikes = "Friend_Dislikes";
        Day = "Birthday_Day";
        Month = "Birthday_Month";
    }
 
    public FriendInfo(String name, String likes, String dislikes, String day, String month)
    {    
        FriendName = name;
        Likes = likes;
        Dislikes = dislikes;
        Day = day;
        Month = month;
    }


    /** --------------------------------------------------------
    * Purpose: A method that will allow the calling class to set the
    *          3 properties - Name/ID/IP_Address - all at the one time.
    * @param   PC_Name, PC_ID and IP_Address.
    * @returns nothing (void).
    * ----------------------------------------------------------
    */    
   public void setfriendInfo(String name, String likes, String dislikes, String day, String month)
    {    
        FriendName = name;
        Likes = likes;
        Dislikes = dislikes;
        Day = day;
        Month = month;
    }

    public String getFriendName()
    {
        return FriendName;
    }

    public void setFriendName(String FriendName)
    {
        this.FriendName = FriendName;
    }

    public String getLikes()
    {
        return Likes;
    }

    public void setLikes(String Likes)
    {
        this.Likes = Likes;
    }

    public String getDislikes()
    {
        return Dislikes;
    }

    public void setDislikes(String Dislikes)
    {
        this.Dislikes = Dislikes;
    }

    public String getDay()
    {
        return Day;
    }

    public void setDay(String Day)
    {
        this.Day = Day;
    }

    public String getMonth()
    {
        return Month;
    }

    public void setMonth(String Month)
    {
        this.Month = Month;
    }

}