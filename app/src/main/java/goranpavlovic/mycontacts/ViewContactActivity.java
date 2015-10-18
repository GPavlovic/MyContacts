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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewContactActivity extends AppCompatActivity implements ContactViewFragment.Contract
{
    public static final String EXTRA = "CVA_Contact";

    private int mContactPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        if (getFragmentManager().findFragmentById(R.id.view_fragment_container) == null)
        {
            ContactViewFragment cvf = new ContactViewFragment();
            mContactPosition = getIntent().getIntExtra(EXTRA, 0);
            cvf.setContactPosition(mContactPosition);
            cvf.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction()
                    .add(R.id.view_fragment_container, cvf)
                    .commit();
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
