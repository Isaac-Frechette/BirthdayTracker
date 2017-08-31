public class BirthdayRecord 
{
    private String FriendName = new String();   
    private String Likes = new String();
    private String Dislikes = new String();
    private String Day = new String();
    private String Month = new String();
    
    
    public BirthdayRecord()
    {    
        FriendName = "Friend_Name";
        Likes = "Friend_Likes";
        Dislikes = "Friend_Dislikes";
        Day = "Birthday_Day";
        Month = "Birthday_Month";
    }
 
    public BirthdayRecord(String name, String likes, String dislikes, String day, String month)
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
