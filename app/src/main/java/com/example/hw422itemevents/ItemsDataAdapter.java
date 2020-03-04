package com.example.hw422itemevents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ItemsDataAdapter extends BaseAdapter {

    private Context context;

    private List<ItemData> items;

    private LayoutInflater inflater;

    private View.OnClickListener btnDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            removeItem((Integer) view.getTag());
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

    // Удаляет элемент списка.
    void removeItem(int position) {
        items.remove(position);
        notifyDataSetChanged();
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
