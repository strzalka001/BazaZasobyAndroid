package com.example.milek.android3;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    List products;
    ListView lvProducts;
    TextView pole;
    Button add, delete, edit, next;
    ProductAdapter db;
    ProductListAdapterSimple AdapterListy;
    private static final String DEBUG_TAG = "SqLite";

    ProductModel product;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = (Button) findViewById(R.id.buttonAdd);
        delete = (Button) findViewById(R.id.buttonDelete);
        edit = (Button) findViewById(R.id.buttonEdit);
        next = (Button) findViewById(R.id.buttonNext);

        products = new ArrayList();
         db = new ProductAdapter(this);
        db.open();

        Log.d(DEBUG_TAG, "Database creating...");
/*
        db.insertProduct(new ProductModel(R.string.pies,R.drawable.pies, R.string.pies_opis));
        db.insertProduct(new ProductModel(R.string.pies,R.drawable.pies, R.string.pies_opis));
        db.insertProduct(new ProductModel(R.string.chomik,R.drawable.chomik, R.string.chomik_opis));
        db.insertProduct(new ProductModel(R.string.rybka,R.drawable.rybka, R.string.rybka_opis));
        db.insertProduct(new ProductModel(R.string.papuga,R.drawable.papuga, R.string.papuga_opis));
*/



        // populate data

       // products.add(new Product(R.string.kot, R.drawable.kot, R.string.kot_opis));
       // products.add(new Product(R.string.pies,R.drawable.pies, R.string.pies_opis));
       // products.add(new Product(R.string.chomik,R.drawable.chomik, R.string.chomik_opis));
       // products.add(new Product(R.string.rybka,R.drawable.rybka, R.string.rybka_opis));
       // products.add(new Product(R.string.papuga,R.drawable.papuga, R.string.papuga_opis));

        Cursor cursor = db.getAllTodos();
        List<ProductModel> pom =new ArrayList<ProductModel>();
        if (cursor.moveToFirst()) {
            do {
                ProductModel item = new ProductModel();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setDesc(Integer.parseInt(cursor.getString(3)));
                item.setTitle(Integer.parseInt(cursor.getString(1)));
                item.setImg_url(Integer.parseInt(cursor.getString(2)));

                // Adding contact to list
                pom.add(item);
            } while (cursor.moveToNext());
        }
        //
        AdapterListy = new ProductListAdapterSimple(this, pom);
        lvProducts = (ListView) findViewById( R.id.list_products);
        pole = (TextView) findViewById(R.id.textView4);
        lvProducts.setAdapter(AdapterListy);
        lvProducts.setClickable(true);



        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 product =(ProductModel) lvProducts.getItemAtPosition(position);

               // Toast.makeText(getApplicationContext(),product.getDesc(),Toast.LENGTH_LONG).show();
               pole.setText(product.getDesc());

               delete.setEnabled(true);
            }
        });


        addListenerOnButtonAdd();
        addListenerOnButtonDelete();
        addListenerOnButtonEdit();
        addListenerOnButtonNext();
    }




    public void addListenerOnButtonAdd() {
        final Context context = this;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                db.insertProduct(new ProductModel(R.string.kot,R.drawable.kot, R.string.kot_opis));
                AdapterListy.add(new ProductModel(R.string.kot,R.drawable.kot, R.string.kot_opis));

                db.insertProduct(new ProductModel(R.string.pies,R.drawable.pies, R.string.pies_opis));
                AdapterListy.add(new ProductModel(R.string.pies,R.drawable.pies, R.string.pies_opis));

                db.insertProduct(new ProductModel(R.string.chomik,R.drawable.chomik, R.string.chomik_opis));
                AdapterListy.add(new ProductModel(R.string.chomik,R.drawable.chomik, R.string.chomik_opis));

                db.insertProduct(new ProductModel(R.string.rybka,R.drawable.rybka, R.string.rybka_opis));
                AdapterListy.add(new ProductModel(R.string.rybka,R.drawable.rybka, R.string.rybka_opis));

                db.insertProduct(new ProductModel(R.string.papuga,R.drawable.papuga, R.string.papuga_opis));
                AdapterListy.add(new ProductModel(R.string.papuga,R.drawable.papuga, R.string.papuga_opis));
                Toast.makeText(getApplicationContext(),"dodaje",Toast.LENGTH_LONG).show();
                AdapterListy.notifyDataSetChanged();

            }
        });
    }



    public void addListenerOnButtonDelete() {
        final Context context = this;
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                db.deleteProduct(product.getId());

                AdapterListy.remove(product);
                AdapterListy.notifyDataSetChanged();

            }
        });
    }


    public void addListenerOnButtonEdit() {
        final Context context = this;
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Cursor cur = db.getAllTodos();

                cur.moveToFirst();

                int id = Integer.parseInt(cur.getString(0));
                int ty = Integer.parseInt(cur.getString(1));
                int ob = Integer.parseInt(cur.getString(2));
                int op = Integer.parseInt(cur.getString(3));


                db.updateProduct(new ProductModel(id, R.string.zmiana, ob, op));
                AdapterListy.remove(new ProductModel(id, ty, ob, op));
                AdapterListy.notifyDataSetChanged();
                AdapterListy.add(new ProductModel(id, R.string.zmiana, ob, op));
                AdapterListy.notifyDataSetChanged();

            }
        });
    }




    public void addListenerOnButtonNext() {
        final Context context = this;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
               Intent intent = new Intent(context, Main2Activity.class);
                startActivity(intent);

            }
        });
    }








}