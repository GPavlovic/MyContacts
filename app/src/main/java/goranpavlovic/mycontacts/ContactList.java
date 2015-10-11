package goranpavlovic.mycontacts;

import java.util.ArrayList;

public class ContactList extends ArrayList<Contact>
{
    public static ContactList _contactList = null;

    private ContactList(){}

    public static ContactList getInstance()
    {
        if (_contactList == null)
        {
            _contactList = new ContactList();
        }
        return _contactList;
    }
}
