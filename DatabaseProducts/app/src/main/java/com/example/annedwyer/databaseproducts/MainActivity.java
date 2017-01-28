package com.example.annedwyer.databaseproducts;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {



    EditText user_Input;
    TextView records_TextView;
    Button btnAdd, btnDelete, btnExport;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = (Button)findViewById(R.id.add_Button);
        btnDelete = (Button)findViewById(R.id.delete_Button);
        user_Input = (EditText)findViewById(R.id.user_Input);
        records_TextView = (TextView)findViewById(R.id.records_TextView);

        dbHandler = new MyDBHandler(this, null, null, 1);
        if(dbHandler != null){
            Log.i("chk", "dbnotnull");
        }else{
            Log.i("chk", "dbnull");
        }
        try {
            printDatabase();
        }catch (Exception e){
            Log.i("exxxx", e.toString());
        }

    }

    public void printDatabase() {
        String dbString = dbHandler.databaseToString();
        records_TextView.setText(dbString);
        user_Input.setText("");
    }

    //Add a product to the database
    public void addButtonClicked(View view){
        Log.i("exxxx", "CLİCKED ADD BUTTON");
        String product = user_Input.getText().toString();
        Products p = new Products(product);
        dbHandler.addProduct(p);
        printDatabase();
    }

    //Delete a product to the database
    public void deleteButtonClicked(View view){
        Log.i("exxxx", "CLİCKED DELETE BUTTON");
        String inputText = user_Input.getText().toString();
        dbHandler.deleteProduct(inputText);
        printDatabase();
    }
/*
   //Write database to a csv file
    public void exportButtonClicked(View view){
        Log.i("exxxx", "CLİCKED EXPORT BUTTON");
        dbHandler.exportProductsDataToCSV();
    }
*/
}
