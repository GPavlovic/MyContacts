package goranpavlovic.mycontacts;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactEditFragment extends Fragment
{
    private Contact mContact;

    // UI elements
    private EditText mEditNameET;
    private LinearLayout mPhoneNumberSection;
    private LinearLayout mEmailSection;
    private int mContactPosition;

    public void setContactPosition(int contactPosition)
    {
        mContactPosition = contactPosition;
    }

    public ContactEditFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_edit, container, false);

        mContact = ContactList.getInstance().get(mContactPosition);

        // Toolbar
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.CEA_toolbar);
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
                Toast.makeText(getActivity(), "Contact updated", Toast.LENGTH_LONG).show();

                getActivity().finish();
            }
        });

        // Set the contact name EditText field
        mEditNameET = (EditText) v.findViewById(R.id.CEA_edit_contact_name);
        mEditNameET.setText(mContact.getName());

        // Populate the phone number section
        mPhoneNumberSection = (LinearLayout) v.findViewById(R.id.CEA_phone_number_section_layout);
        for (int i = 0; i < mContact.phoneNumbers.size(); i++)
        {
            EditText phoneNumberET = new EditText(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            phoneNumberET.setLayoutParams(lp);
            phoneNumberET.setText(mContact.phoneNumbers.get(i));
            mPhoneNumberSection.addView(phoneNumberET);
        }

        // Populate the email section
        mEmailSection = (LinearLayout) v.findViewById(R.id.CEA_email_section_layout);
        for (int i = 0; i < mContact.emails.size(); i++)
        {
            EditText emailET = new EditText(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            emailET.setLayoutParams(lp);
            emailET.setText(mContact.emails.get(i));
            mEmailSection.addView(emailET);
        }

        // Set the add new button click logic
        TextView addNewPhoneNumber = (TextView) v.findViewById(R.id.CEA_add_new_phone_number);
        TextView addNewEmail = (TextView) v.findViewById(R.id.CEA_add_new_email);

        addNewPhoneNumber.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addToSection(v, R.id.CEA_phone_number_section_layout, "");
            }
        });

        addNewEmail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addToSection(v, R.id.CEA_email_section_layout, "");
            }
        });

        return v;
    }

    private void addToSection(View v, int sectionId, String value)
    {
        LinearLayout section = (LinearLayout) v.findViewById(sectionId);
        EditText ET = new EditText(getActivity());
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
