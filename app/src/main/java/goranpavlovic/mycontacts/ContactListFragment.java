package goranpavlovic.mycontacts;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends ContractFragment<ContactListFragment.Contract>
{
    ContactList mContacts;
    ContactAdapter mAdapter;

    public ContactListFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_list, container, false);

        mContacts = ContactList.getInstance();

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

        ListView ContactList = (ListView) v.findViewById(R.id.contact_list_view);
        mAdapter = new ContactAdapter(mContacts);
        ContactList.setAdapter(mAdapter);

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
                ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
                if (firstVisibleItem > previousFirstItem)
                {
                    ab.hide();
                }
                else if (firstVisibleItem < previousFirstItem)
                {
                    ab.show();
                }

                previousFirstItem = firstVisibleItem;
            }
        });

        ContactList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (getContract() != null)
                {
                    getContract().selectedPosition(position);
                }
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_contact_list, menu);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        mAdapter.notifyDataSetChanged();
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

    private class ContactAdapter extends ArrayAdapter<Contact>
    {
        ContactAdapter(ArrayList<Contact> contacts)
        {
            super(getActivity(), R.layout.contact_list_row, R.id.contact_row_name, contacts);
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

    public interface Contract
    {
        public void selectedPosition(int position);
    }
}
