package goranpavlovic.mycontacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewContactActivity extends AppCompatActivity
{

    public static final String EXTRA = "CVA_Contact"; //the extra used to pass the contact to the view

    private int mColour;
    private Contact mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        // Retrieve the passed contact
        mContact = (Contact) getIntent().getSerializableExtra(EXTRA);

        // Get UI elements
        TextView contactNameTV = (TextView) findViewById(R.id.CVA_contact_name);
        RelativeLayout contactInfoLayout = (RelativeLayout) findViewById(R.id.CVA_contactinfo_header);

        // Set the contact information
        contactNameTV.setText(mContact.getName());

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point); // Populate the point with the display details

        int width = point.x;
        int height = point.y;

        // Set the image view layout
        contactInfoLayout.setLayoutParams(new LinearLayout.LayoutParams(width, (int) (width * (9.0 / 16.0))));

        //Toolbar setup
        Toolbar toolbar = (Toolbar) findViewById(R.id.CVA_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                int id = item.getItemId();
                if (id == R.id.CVA_edit_contact)
                {
                    Intent i = new Intent(ViewContactActivity.this, ContactEditActivity.class);
                    i.putExtra(ContactEditActivity.EXTRA, mContact);
                    startActivity(i);
                    return true;
                }
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu_contact_view);

        // Fetch the listview that holds the user fields
        ListView userFields = (ListView) findViewById(R.id.CVA_fields);
        userFields.setAdapter(new FieldsAdapter(mContact.emails, mContact.phoneNumbers));

        // Create the palette in order to work the theme to match the user image
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ernie);
        Palette palette = Palette.generate(bitmap);
        mColour = palette.getDarkVibrantSwatch().getRgb();
    }

    private class FieldsAdapter extends BaseAdapter
    {
        ArrayList<String> emails;
        ArrayList<String> phoneNumbers;

        FieldsAdapter(ArrayList<String> emails, ArrayList<String> phoneNumbers)
        {
            this.emails = emails;
            this.phoneNumbers = phoneNumbers;
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
                convertView = ViewContactActivity.this.getLayoutInflater().inflate(R.layout.contact_view_field_row, parent, false);
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
}
