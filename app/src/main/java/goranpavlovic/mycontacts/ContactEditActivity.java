package goranpavlovic.mycontacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactEditActivity extends AppCompatActivity
{
    public static final String EXTRA = "CEA_Contact";

    private Contact mContact;

    // UI elements
    private EditText mEditNameET;
    private LinearLayout mPhoneNumberSection;
    private LinearLayout mEmailSection;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);

        int position = getIntent().getIntExtra(EXTRA, 0);
        mContact = ContactList.getInstance().get(position);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.CEA_toolbar);
        toolbar.setTitle(getResources().getString(R.string.edit_contact));
        toolbar.setNavigationIcon(R.drawable.ic_checkmark);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Set the contact name
                mContact.setName(mEditNameET.getText().toString());
                // Update phone numbers
                mContact.phoneNumbers = getSectionValues(mPhoneNumberSection);
                // Update email addresses
                mContact.emails = getSectionValues(mEmailSection);

                // Show toast
                Toast.makeText(ContactEditActivity.this, "Contact updated", Toast.LENGTH_LONG).show();

                finish();
            }
        });

        // Set the contact name EditText field
        mEditNameET = (EditText) findViewById(R.id.CEA_edit_contact_name);
        mEditNameET.setText(mContact.getName());

        // Populate the phone number section
        mPhoneNumberSection = (LinearLayout) findViewById(R.id.CEA_phone_number_section_layout);
        for (int i = 0; i < mContact.phoneNumbers.size(); i++)
        {
            EditText phoneNumberET = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            phoneNumberET.setLayoutParams(lp);
            phoneNumberET.setText(mContact.phoneNumbers.get(i));
            mPhoneNumberSection.addView(phoneNumberET);
        }

        // Populate the email section
        mEmailSection = (LinearLayout) findViewById(R.id.CEA_email_section_layout);
        for (int i = 0; i < mContact.emails.size(); i++)
        {
            EditText emailET = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            emailET.setLayoutParams(lp);
            emailET.setText(mContact.emails.get(i));
            mEmailSection.addView(emailET);
        }

        // Set the add new button click logic
        TextView addNewPhoneNumber = (TextView) findViewById(R.id.CEA_add_new_phone_number);
        TextView addNewEmail = (TextView) findViewById(R.id.CEA_add_new_email);

        addNewPhoneNumber.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addToSection(R.id.CEA_phone_number_section_layout, "");
            }
        });

        addNewEmail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addToSection(R.id.CEA_email_section_layout, "");
            }
        });
    }

    private void addToSection(int sectionId, String value)
    {
        LinearLayout section = (LinearLayout) findViewById(sectionId);
        EditText ET = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ET.setLayoutParams(lp);
        ET.setText(value);
        section.addView(ET);
    }

    private ArrayList<String> getSectionValues(LinearLayout section)
    {
        ArrayList<String> newValues = new ArrayList<String>();
        for (int i = 0; i < section.getChildCount(); i++)
        {
            EditText newValue = (EditText) section.getChildAt(i);
            newValues.add(newValue.getText().toString());
        }

        return newValues;
    }
}
