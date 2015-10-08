package goranpavlovic.mycontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ContactListActivity extends AppCompatActivity
{
    ArrayList<Contact> mContacts = new ArrayList<Contact>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        mContacts = new ArrayList<Contact>();

        // Add temp test contact
        Contact temp;

        for (int i = 0; i < 30; i++)
        {
            temp = new Contact();
            temp.setName("John Doe" + i);
            temp.emails = new ArrayList<String>();
            temp.phoneNumbers = new ArrayList<String>();
            temp.emails.add("fake" + i + "@fake.com");
            temp.emails.add("fake" + i + "@fake2.com");
            temp.phoneNumbers.add("519" + "-" + i);
            temp.phoneNumbers.add("529" + "-" + i);
            mContacts.add(temp);
        }

        ListView ContactList = (ListView) findViewById(R.id.contact_list_view);
        ContactList.setAdapter(new ContactAdapter(mContacts));

        ContactList.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            int previousFirstItem = 0;
            boolean isHidden = false;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if (firstVisibleItem > previousFirstItem)
                {
                    getSupportActionBar().hide();
                }
                else if (firstVisibleItem < previousFirstItem)
                {
                    getSupportActionBar().show();
                }

                previousFirstItem = firstVisibleItem;
            }
        });

        ContactList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Contact selectedContact = mContacts.get(position);

                Intent i = new Intent(ContactListActivity.this, ViewContactActivity.class);
                i.putExtra(ViewContactActivity.EXTRA, selectedContact);
                startActivity(i);
            }
        });
    }

    private class ContactAdapter extends ArrayAdapter<Contact>
    {
        ContactAdapter(ArrayList<Contact> contacts)
        {
            super(ContactListActivity.this, R.layout.contact_list_row, R.id.contact_row_name, contacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            convertView = super.getView(position, convertView, parent);

            Contact contact = getItem(position);

            TextView nameTV = (TextView) convertView.findViewById(R.id.contact_row_name);
            nameTV.setText(contact.getName());
            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
