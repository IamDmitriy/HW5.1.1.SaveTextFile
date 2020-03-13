package com.example.hw422itemevents;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private static final String LIST_FILE_NAME = "listFile.txt";

    private ItemsDataAdapter adapter;
    private File listFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        Toolbar myToolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(myToolbar);
        listFile = new File(getExternalFilesDir(null), LIST_FILE_NAME);
        deleteFileList();

        ListView listView = findViewById(R.id.listView);
        adapter = new ItemsDataAdapter(this, null);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showItemData(i);
                return false;
            }
        });

    }

    private List<ItemData> generatedListContent() {
        List<ItemData> listContent = new ArrayList<>();

        listContent.add(new ItemData("Домашнее задание №1.3.1",
                "Мониторинг здоровья",
                true,
                R.drawable.health_app,
                this));

        listContent.add(new ItemData("Домашнее задание №3.3.1",
                "Адаптивная вёрстка",
                true,
                R.drawable.adaptive_layout_app,
                this));

        listContent.add(new ItemData("Домашнее задание №3.3.2",
                "Переключение языков",
                true,
                R.drawable.switch_language_app,
                this));

        return listContent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_open_notes:
                showToast(getString(R.string.open_notes));
                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add:
                addItemFromFile();
                return true;
            case R.id.action_create_file:
                createListFile();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteFileList() {
        if (listFile.exists()) {
            listFile.delete();
            showToast(getString(R.string.toast_file_deleted));
        } else {
            showToast(getString(R.string.toast_file_not_found));
        }

    }

    private void addItemFromFile() {

        if (!listFile.exists()) {
            showToast(getString(R.string.toast_file_not_found));
            return;
            //createListFile();
        }

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(listFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner;
        if (fileReader != null) {
            scanner = new Scanner(fileReader);
        } else {
            showToast(getString(R.string.toast_error));
            return;
        }

        for (int i = 0; i < adapter.getCount(); i++) {
            scanner.nextLine();
        }

        if (!scanner.hasNextLine()) {
            showToast(getString(R.string.toast_data_not_exist));
            return;
        }

        String[] input = scanner.nextLine().split(";");

        try {
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int imageId = Integer.valueOf(input[0]);
        String title = input[1];
        String subtitle = input[2];
        boolean checked = Boolean.valueOf(input[3]);

        ItemData itemData = new ItemData(getDrawable(imageId), title, subtitle, checked);

        adapter.addItem(itemData);

    }

    private void createListFile() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(listFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<ItemData> sourceList = generatedListContent();

        for (int i = 0; i < sourceList.size(); i++) {
            ItemData curItem = sourceList.get(i);

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(curItem.getImageId());
            stringBuilder.append(";");
            stringBuilder.append(curItem.getTitle());
            stringBuilder.append(";");
            stringBuilder.append(curItem.getSubtitle());
            stringBuilder.append(";");
            stringBuilder.append(curItem.isChecked());

            if ((i + 1) != sourceList.size()) {
                stringBuilder.append("\n");
            }


            try {
                writer.append(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showItemData(int position) {
        ItemData itemData = adapter.getItem(position);
        Toast.makeText(MainActivity.this,
                "Title: " + itemData.getTitle() + "\n" +
                        "Subtitle: " + itemData.getSubtitle() + "\n",
                Toast.LENGTH_SHORT).show();
    }
}
