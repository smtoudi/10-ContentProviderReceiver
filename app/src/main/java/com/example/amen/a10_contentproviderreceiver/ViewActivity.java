package com.example.amen.a10_contentproviderreceiver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewActivity extends AppCompatActivity {

    /**
     * Widoki, zbindowane używając ButterKnife'a.
     */
    @BindView(R.id.id)private TextView id;
    @BindView(R.id.name)private TextView name;
    @BindView(R.id.surname)private TextView surname;
    @BindView(R.id.phone)private TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);

        if (getIntent().hasExtra("contact_name")) {
            id.setText(getIntent().getStringExtra("contact_id"));
            name.setText(getIntent().getStringExtra("contact_name"));
            surname.setText(getIntent().getStringExtra("contact_surname"));
            phone.setText(getIntent().getStringExtra("contact_phone"));
        }
    }
}
