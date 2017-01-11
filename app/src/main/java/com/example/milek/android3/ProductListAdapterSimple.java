package com.example.milek.android3;

/**
 * Created by Milek on 2016-11-04.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.util.List;


public class ProductListAdapterSimple extends ArrayAdapter<ProductModel>{

    List<ProductModel> mylist;




    public ProductListAdapterSimple(Context _context, List<ProductModel> _mylist) {
        super(_context, R.layout.list_item, _mylist);

        this.mylist = _mylist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = new LinearLayout(getContext());
        String inflater = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
        convertView = vi.inflate(R.layout.list_item, parent, false);


        // Product object
        ProductModel product = getItem(position);
        //
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        txtTitle.setText(product.title);

        // show image
        ImageView img = (ImageView)convertView.findViewById(R.id.image);

        TextView txtDesc = (TextView) convertView.findViewById(R.id.opis);
        txtDesc.setText(Integer.toString(product.getId()));

        // download image
        img.setImageResource(product.img_url);

        return convertView;
    }


}

































