package goranpavlovic.mycontacts;

import java.io.Serializable;

public class Contact implements Serializable
{
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
