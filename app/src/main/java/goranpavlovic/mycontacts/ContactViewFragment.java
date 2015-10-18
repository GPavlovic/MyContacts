package goranpavlovic.mycontacts;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dbco.gpavlovic.material.RatioLayout;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactViewFragment extends ContractFragment<ContactViewFragment.Contract>
{
    private int mColour;
    private int mContactPosition;
    private Contact mContact;
    // UI
    private TextView mContactNameTV;
    private FieldsAdapter mUserFieldsAdapter;

    public ContactViewFragment()
    {
        // Required empty public constructor
    }

    public void setContactPosition(int contactPosition)
    {
        mContactPosition = contactPosition;
        if (mUserFieldsAdapter != null)
        {
            mContact = ContactList.getInstance().get(contactPosition);
            mUserFieldsAdapter.setContact(mContact);
            updateUI();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_view, container, false);

        // Retrieve the passed contact
        mContact = ContactList.getInstance().get(mContactPosition);

        // Get UI elements
        mContactNameTV = (TextView) v.findViewById(R.id.CVA_contact_name);

        //Toolbar setup
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.CVA_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                int id = item.getItemId();
                if (id == R.id.CVA_edit_contact)
                {
                    if (getContract() != null)
                    {
                        getContract().selectedEdit(mContactPosition);
                    }
                    return true;
                }
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu_contact_view);

        // Fetch the listview that holds the user fields
        ListView userFields = (ListView) v.findViewById(R.id.CVA_fields);
        mUserFieldsAdapter = new FieldsAdapter(mContact);
        userFields.setAdapter(mUserFieldsAdapter);

        // Create the palette in order to work the theme to match the user image
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ernie);
        Palette palette = Palette.generate(bitmap);
        mColour = palette.getDarkVibrantSwatch().getRgb();

        updateUI();

        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        updateUI();
    }

    private void updateUI()
    {
        mContactNameTV.setText(mContact.getName());
        mUserFieldsAdapter.notifyDataSetChanged();
    }

    private class FieldsAdapter extends BaseAdapter
    {
        ArrayList<String> emails;
        ArrayList<String> phoneNumbers;

        FieldsAdapter(Contact contact)
        {
            setContact(contact);
        }

        public void setContact(Contact contact)
        {
            this.emails = contact.emails;
            this.phoneNumbers = contact.phoneNumbers;
        }

        @Override
        public int getCount()
        {
            return emails.size() + phoneNumbers.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            // Create the view that we want to get
            if (convertView == null)
            {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.contact_view_field_row, parent, false);
            }
            // Get the value
            String value = (String) getItem(position);
            // Set the text in the view to the value
            TextView valueTV = (TextView) convertView.findViewById(R.id.contact_view_row_value);
            valueTV.setText(value);
            // Set the image conditionally
            ImageView valueIV = (ImageView) convertView.findViewById(R.id.contact_view_field_icon);
            if (isFirst(position))
            {
                if (isEmail(position))
                {
                    valueIV.setImageResource(R.drawable.ic_email_white);
                }
                else
                {
                    valueIV.setImageResource(R.drawable.ic_call_white);
                }
            }

            valueIV.setColorFilter(mColour);

            // Return the view
            return convertView;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public Object getItem(int position)
        {
            if (isEmail(position))
            {
                return emails.get(position - phoneNumbers.size());
            }
            else
            {
                return phoneNumbers.get(position);
            }
        }

        //region Helper Methods
        private boolean isEmail(int position)
        {
            if (position > phoneNumbers.size() - 1)
            {
                return true;
            }
            return false;
        }

        private boolean isFirst(int position)
        {
            if ((position == 0) || position == phoneNumbers.size())
            {
                return true;
            }
            return false;
        }

        //endregion
    }

    public interface Contract
    {
        void selectedEdit(int position);
    }

}
