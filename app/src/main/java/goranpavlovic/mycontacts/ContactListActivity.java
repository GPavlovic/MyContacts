package goranpavlovic.mycontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ContactListActivity extends AppCompatActivity
        implements ContactListFragment.Contract, ContactViewFragment.Contract
{
    private ContactListFragment mListFragment;
    private ContactViewFragment mViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        mListFragment = (ContactListFragment) getFragmentManager().findFragmentById(R.id.list_fragment_container);

        if (mListFragment == null)
        {
            mListFragment = new ContactListFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.list_fragment_container, mListFragment)
                    .commit();
        }

        mViewFragment = (ContactViewFragment) getFragmentManager().findFragmentById(R.id.view_fragment_container);

        if (mViewFragment == null && findViewById(R.id.view_fragment_container) != null)
        {
            mViewFragment = new ContactViewFragment();
            mViewFragment.setContactPosition(0);
            getFragmentManager().beginTransaction()
                    .add(R.id.view_fragment_container, mViewFragment)
                    .commit();
        }

    }

    @Override
    public void selectedPosition(int position)
    {
        if (mViewFragment == null)
        {
            Intent i = new Intent(this, ViewContactActivity.class);
            i.putExtra(ViewContactActivity.EXTRA, position);
            startActivity(i);
        }
        else
        {
            mViewFragment.setContactPosition(position);
        }
    }

    @Override
    public void selectedEdit(int position)
    {
        Intent i = new Intent(this, ContactEditActivity.class);
        i.putExtra(ContactEditActivity.EXTRA, position);
        startActivity(i);
    }
}
