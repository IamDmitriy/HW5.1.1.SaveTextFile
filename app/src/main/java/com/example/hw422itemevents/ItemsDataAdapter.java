package com.example.hw422itemevents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ItemsDataAdapter extends BaseAdapter {

    private Context context;

    private List<ItemData> items;

    private LayoutInflater inflater;

    private View.OnClickListener btnDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            removeItemFromFile((Integer) view.getTag(), view.getContext());
        }
    };

    ItemsDataAdapter(Context context, List<ItemData> items) {
        this.context = context;

        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = items;
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    void addItem(ItemData item) {
        this.items.add(item);
        notifyDataSetChanged();
    }

    void removeItemFromFile(int position, Context context) {
        int sourceSizeList = getCount();
        items.remove(position);
        notifyDataSetChanged();

        File listFile = new File(context.getExternalFilesDir(null), "listFile.txt");
        if (!listFile.exists()) {
            Toast.makeText(context, context.getString(R.string.toast_file_not_found),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        FileReader fileReaderListFile;
        try {
            fileReaderListFile = new FileReader(listFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Scanner scannerListFile = new Scanner(fileReaderListFile);

        File temp = new File(context.getExternalFilesDir(null), "temp.txt");
        FileWriter fileWriterTemp = null;
        try {
            fileWriterTemp = new FileWriter(temp);

            for (int i = 0; i < sourceSizeList; i++) {
                if (i == position) continue;

                fileWriterTemp.append(scannerListFile.nextLine());
                if ((i + 1) != sourceSizeList) {
                    fileWriterTemp.append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileReaderListFile.close();

                if (fileWriterTemp != null) {
                    fileWriterTemp.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        temp.renameTo(listFile);

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ItemData getItem(int position) {
        if (position < items.size()) {
            return items.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_list_view, parent, false);
        }

        ItemData itemData = items.get(position);

        ImageView image = view.findViewById(R.id.icon);
        TextView title = view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        Glide.with(context)
                .load(itemData.getImage())
                .into(image);
        title.setText(itemData.getTitle());
        subtitle.setText(itemData.getSubtitle());
        btnDelete.setOnClickListener(btnDeleteListener);
        btnDelete.setTag(position);

        return view;
    }
}
