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
    private int mContactPosition;
    private ContactEditFragment mContactEditFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);

        mContactPosition = getIntent().getIntExtra(EXTRA, 0);

        mContactEditFragment = (ContactEditFragment) getFragmentManager().findFragmentById(R.id.contact_edit_container);

        if (mContactEditFragment == null) {
            mContactEditFragment = new ContactEditFragment();
            mContactEditFragment.setContactPosition(mContactPosition);
            getFragmentManager().beginTransaction()
                    .add(R.id.contact_edit_container, mContactEditFragment)
                    .commit();
        }
    }


}
