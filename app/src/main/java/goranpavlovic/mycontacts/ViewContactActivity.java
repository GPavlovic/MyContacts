package goranpavlovic.mycontacts;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewContactActivity extends AppCompatActivity {

    public static final String EXTRA = "CVA_Contact"; //the extra used to pass the contact to the view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        // Retrieve the passed contact
        Contact contact = (Contact)getIntent().getSerializableExtra(EXTRA);

        // Get UI elements
        TextView contactNameTV = (TextView)findViewById(R.id.CVA_contact_name);
        RelativeLayout contactInfoLayout = (RelativeLayout)findViewById(R.id.CVA_contactinfo_header);

        // Set the contact information
        contactNameTV.setText(contact.getName());

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point); // Populate the point with the display details

        int width = point.x;
        int height = point.y;

        // Set the image view layout
        contactInfoLayout.setLayoutParams(new RelativeLayout.LayoutParams(width, (int)(width * (9.0/16.0))));

        //Toolbar setup
        Toolbar toolbar = (Toolbar)findViewById(R.id.CVA_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.CVA_edit_contact){
                    return true;
                }
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu_contact_view);
    }
}
