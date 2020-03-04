package com.example.hw422itemevents;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NotesActivity extends AppCompatActivity {
    private static final String NOTE_TEXT = "note_text";
    private static final String NOTE_TEXT_KEY = "MyNote";

    private EditText mInputNote;
    private Button mBtnSave;
    private SharedPreferences myNoteSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        initViews();

        getDataFromSharedPref();
    }

    private void initViews() {
        mInputNote = findViewById(R.id.inputNote);
        mBtnSave = findViewById(R.id.btnSave);

        myNoteSharedPref = getSharedPreferences(NOTE_TEXT, MODE_PRIVATE);

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String noteTxt = mInputNote.getText().toString();
                SharedPreferences.Editor myEditor = myNoteSharedPref.edit();
                myEditor.putString(NOTE_TEXT_KEY, noteTxt).apply();

                Toast.makeText(NotesActivity.this,
                        "Данные сохранены", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataFromSharedPref() {
        String noteTxt = myNoteSharedPref.getString(NOTE_TEXT_KEY, "");
        mInputNote.setText(noteTxt);
    }
}
