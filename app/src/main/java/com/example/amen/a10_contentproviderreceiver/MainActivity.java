package com.example.amen.a10_contentproviderreceiver;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    // Definiujemy uri, w taki sam sposób jak w poprzedniej aplikacji.
    private String URI = "content://com.example.amen.internaldatabasecontactsapplication.controler.ContactsProvider/contacts";
    // Definiujemy elementy widoku i cursor jako pola
    @BindView(R.id.layout)
    private LinearLayout layout;
    private CursorLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // uruchamiamy loadera (asynchroniczne wywołanie)
        getLoaderManager().initLoader(1, null, this);
    }

    /**
     * Tworzymy loadera który ma zwrócić nam dane z content providera.
     * @param id
     * @param args
     * @return
     */


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        loader = new CursorLoader(this,
                Uri.parse("content://com.example.amen.internaldatabasecontactsapplication.controler.ContactsProvider/contacts"),
                null, null, null, null);
        return loader;
    }


    public void insertElement() {
        ContentValues values = new ContentValues();
        values.put("contact", URI);
        getContentResolver().insert(Uri.parse(URI), values);
    }

    /**
     * Po pobraniu wywołuje się onLoadFinished - metoda powinna załadować elementy
     * widoku. Tutaj ładowanie elementów z kursora do listy, a następnie wyświetlenie ich
     * jako buttonów.
     * @param loader
     * @param data
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        final Cursor cursor = data;

        cursor.moveToFirst();
//        layout.removeAllViews();
        for (int i = 0; i < cursor.getCount(); i++) {
            final Button b = new Button(this);
            //
            final String id = String.valueOf(cursor.getInt(0));
            final String name = cursor.getString(1);
            final String surname = cursor.getString(2);
            final String phone = cursor.getString(3);

            b.setText(name + " " + surname);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, ViewActivity.class);
                    i.putExtra("contact_id", id);
                    i.putExtra("contact_name", name);
                    i.putExtra("contact_surname", surname);
                    i.putExtra("contact_phone", phone);

                    startActivity(i);
                }
            });
            layout.addView(b);
            cursor.moveToNext();
        }
    }

    /**
     * Wywoływany przez androida - jeśli nasz loader się zresetuje, to dane są niedostępne
     * (trzeba je załadować ponownie).
     * @param loader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
