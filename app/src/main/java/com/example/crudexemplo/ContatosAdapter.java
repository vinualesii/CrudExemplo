package com.example.crudexemplo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ContatosAdapter extends BaseAdapter {

    private Context ctx;
    private List<Contato> lista;

    public ContatosAdapter(Context ctx2, List<Contato> lista2)
    {
        ctx = ctx2;
        lista = lista2;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Contato getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = null;

        if(convertView == null)
        {
            LayoutInflater inflater = ((Activity)ctx).getLayoutInflater();
            v = inflater.inflate(R.layout.item_lista, null);

        }else
        {
            v = convertView;
        }

        Contato c = getItem(position);

        TextView itemFullname = v.findViewById(R.id.itemFullname);
        TextView itemName = v.findViewById(R.id.itemName);
        TextView itemPassword = v.findViewById(R.id.itemPassword);
        TextView itemPhone = v.findViewById(R.id.itemPhone);
        TextView itemEmail = v.findViewById(R.id.itemEmail);

        itemFullname.setText(c.getFullname());
        itemName.setText(c.getUsername());
        itemPassword.setText(c.getPassword());
        itemPhone.setText(c.getPhone());
        itemEmail.setText(c.getEmail());

        return v;
    }
}
