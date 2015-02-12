package com.example.lauramarra.checkboxsqlite;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    MyCustomAdapter dataAdapter = null;
    TextView textView, posit;
    MyDBHandler dbHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //textView = (TextView) findViewById(R.id.bdText);
        //posit = (TextView) findViewById(R.id.posit);

        //Generate list View from ArrayList
        displayListView();

        //checkButtonClick();

    }

    private void displayListView() {

        //Array list of classes
        ArrayList<ItemMateria> materiaList = new ArrayList<ItemMateria>();
        ItemMateria itemList = new ItemMateria("1","Calculo I",false);
        materiaList.add(itemList);
        itemList = new ItemMateria("2","Fisica I",false);
        materiaList.add(itemList);
        itemList = new ItemMateria("3","Fisica Experimental I",false);
        materiaList.add(itemList);
        itemList = new ItemMateria("4","Computacao I",false);
        materiaList.add(itemList);
        itemList = new ItemMateria("5","Quimica I",false);
        materiaList.add(itemList);

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this, R.layout.materia_info, materiaList);
        ListView listView = (ListView) findViewById(R.id.listView1);

        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                ItemMateria item = (ItemMateria) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + item.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    public class MyCustomAdapter extends ArrayAdapter<ItemMateria> {

        private ArrayList<ItemMateria> listMaterias;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<ItemMateria> countryList) {
            super(context, textViewResourceId, countryList);
            this.listMaterias = new ArrayList<ItemMateria>();
            this.listMaterias.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
            CheckBox classOk;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Context context = getContext();
            dbHandler = new MyDBHandler(context, null, null, 1);
            final ItemMateria itemMat = listMaterias.get(position);

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.materia_info, null);


                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                holder.classOk = (CheckBox) convertView.findViewById(R.id.itemCheck);
                convertView.setTag(holder);

                holder.classOk.setOnClickListener( new View.OnClickListener() {

                    public void onClick(View v) {

                        CheckBox cb = (CheckBox) v;

                        Toast.makeText(getApplicationContext(), itemMat.name + ": Cursando", Toast.LENGTH_LONG).show();

                        if (cb.isChecked()) {
                            MateriaDB materia = new MateriaDB(itemMat.getCode(), itemMat.getName(), "Em andamento");
                            dbHandler.addMateria(materia);

                        } else {
                            if (dbHandler.itemExists()) {
                                dbHandler.delMateria(itemMat.getCode());
                            }
                        }
                        //printDatabase();
                    }

                });

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        //ItemMateria itemMateria = (ItemMateria) cb.getTag();
                        //itemMateria.setSelected(cb.isChecked());

                        Toast.makeText(getApplicationContext(), cb.getText() + ": Concluída", Toast.LENGTH_LONG).show();

                        if(cb.isChecked()) {
                            if(dbHandler.itemExists()){
                                dbHandler.delMateria(itemMat.getCode());
                            }
                            MateriaDB materia = new MateriaDB(itemMat.getCode(), itemMat.getName(), "Concluida");
                            dbHandler.addMateria(materia);

                        }
                        else{
                            if(dbHandler.itemExists()){
                                dbHandler.delMateria(itemMat.getCode());
                            }
                            MateriaDB materia = new MateriaDB(itemMat.getCode(), itemMat.getName(), "Em andamento");
                            dbHandler.addMateria(materia);

                        }
                        //printDatabase();
                    }
                });

            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.code.setText(" (" +  itemMat.getCode() + ")");
            holder.name.setText(itemMat.getName());
            holder.name.setChecked(itemMat.isSelected());
            holder.name.setTag(itemMat);

            return convertView;

        }

    }

/*
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        textView.setText(dbString);
    }


    private void checkButtonClick() {
        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<ItemMateria> countryList = dataAdapter.listMaterias;
                for(int i=0;i<countryList.size();i++){
                    ItemMateria country = countryList.get(i);
                    if(country.isSelected()){
                        responseText.append("\n" + country.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });

    }
*/
}
