package jp.os.kz.android.regza_app;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;

public class RegzaRecTimerActivity extends Activity {

    /*static final private int BACK_ID = Menu.FIRST;
    static final private int CLEAR_ID = Menu.FIRST + 1;*/

    Intent implicitIntent;
    CharSequence exSubject="";
    CharSequence exText="";
    CharSequence exStream="";
    CharSequence exTemplate="";
    CharSequence exTitle="";
    CharSequence exUid="";
    private EditText mEditor;
    Button button1;

    public RegzaRecTimerActivity(){
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        implicitIntent = (Intent) getIntent();

        if (Intent.ACTION_SEND.equals(implicitIntent.getAction())) {
            Bundle extras = implicitIntent.getExtras();
            if (extras != null) {
                exSubject = extras.getCharSequence(Intent.EXTRA_SUBJECT);
                exText = extras.getCharSequence(Intent.EXTRA_TEXT);
                exStream = extras.getCharSequence(Intent.EXTRA_STREAM);
                exTemplate = extras.getCharSequence(Intent.EXTRA_TEMPLATE);
                exTitle = extras.getCharSequence(Intent.EXTRA_TITLE);
                exUid = extras.getCharSequence(Intent.EXTRA_UID);
            }
            startEntryActivity();
        }

        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                button1_onClick();
            }
        });

    }

    /**
     * Called when the activity is about to start interacting with the user.
     */
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    /**
     * Called when your activity's options menu needs to be created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu, menu);

        // We are going to create two menus. Note that we assign them
        // unique integer IDs, labels from our string resources, and
        // given them shortcuts.
    	/*
        menu.add(0, BACK_ID, 0, R.string.back).setShortcut('0', 'b');
        menu.add(0, CLEAR_ID, 0, R.string.clear).setShortcut('1', 'c');
		*/
        return true;
    }

    /**
     * Called right before your activity's option menu is displayed.
     */

    /*
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        // Before showing the menu, we need to decide whether the clear
        // item is enabled depending on whether there is text to clear.
        menu.findItem(CLEAR_ID).setVisible(mEditor.getText().length() > 0);

        return true;
    }
     */

    /**
     * Called when a menu item is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                //item.setIcon(android.R.drawable.ic_menu_manage);
                startActivity(new Intent(this, MyPreferences.class));
                return true;
		/*
        case BACK_ID:
            finish();
            return true;
        case CLEAR_ID:
            mEditor.setText("");
            return true;
        */
        }
        return false;
        /*
        return super.onOptionsItemSelected(item);
        */
    }

    /**
     * A call-back for when the user presses the back button.
     */
    OnClickListener mBackListener = new OnClickListener() {
        public void onClick(View v) {
            finish();
        }
    };

    /**
     * A call-back for when the user presses the clear button.
     */
    OnClickListener mClearListener = new OnClickListener() {
        public void onClick(View v) {
            mEditor.setText("");
        }
    };

    //button1 が押された時の動作
    private void button1_onClick(){
        startEntryActivity();
    }

    private void startEntryActivity(){
        Intent explicitIntent = new Intent(RegzaRecTimerActivity.this,EntryActivity.class);
        explicitIntent.putExtra("EXTRA_SUBJECT", exSubject);
        explicitIntent.putExtra("EXTRA_TEXT", exText);
        explicitIntent.putExtra("EXTRA_STREAM", exStream);
        explicitIntent.putExtra("EXTRA_TEMPLATE", exTemplate);
        explicitIntent.putExtra("EXTRA_TITLE", exTitle);
        explicitIntent.putExtra("EXTRA_UID", exUid);
        startActivity( explicitIntent );
    }

}