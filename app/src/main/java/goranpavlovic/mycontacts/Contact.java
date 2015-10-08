package goranpavlovic.mycontacts;

import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable
{
    public ArrayList<String> emails;
    public ArrayList<String> phoneNumbers;
    private String mName;

    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        mName = name;
    }
}
