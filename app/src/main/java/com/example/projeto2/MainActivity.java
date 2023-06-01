package com.example.projeto2;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeto2.database.MenuContract;
import com.example.projeto2.database.MenuDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<MenuItem> menuItems;
    private LinearLayout menuItemsLayout;
    private MenuDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuItemsLayout = findViewById(R.id.menuItemsLayout);
        databaseHelper = new MenuDatabaseHelper(this);

        // Carregar dados do cardápio (de forma simulada ou do banco de dados local)
        loadMenuItems();

        // Exibir os itens do cardápio na tela
        displayMenuItems();
    }

    private void loadMenuItems() {
        // Verificar a disponibilidade do serviço web remoto
        boolean isServiceAvailable = isServiceAvailable();

        if (isServiceAvailable) {
            // Carregar os dados do serviço web remoto
            menuItems = loadMenuItemsFromRemoteService();
            // Salvar os dados no banco de dados local
            saveMenuItemsToDatabase(menuItems);
        } else {
            // Carregar os dados do banco de dados local
            menuItems = loadMenuItemsFromDatabase();
            Toast.makeText(this, "Modo offline: exibindo dados salvos localmente", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isServiceAvailable() {
        // Verificar a disponibilidade do serviço web remoto
        // Implemente aqui a lógica para verificar se o serviço web remoto está disponível
        return false; // Altere para true se o serviço web estiver disponível
    }

    private List<MenuItem> loadMenuItemsFromRemoteService() {
        // Carregar os dados do serviço web remoto
        // Implemente aqui a lógica para carregar os dados do serviço web remoto
        return new ArrayList<>(); // Retorne a lista de itens do cardápio carregados do serviço web
    }

    private void saveMenuItemsToDatabase(List<MenuItem> menuItems) {
        // Salvar os dados no banco de dados local
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        // Excluir os registros existentes no banco de dados
        db.delete(MenuContract.MenuEntry.TABLE_NAME, null, null);

        // Inserir os novos registros no banco de dados
        for (MenuItem item : menuItems) {
            ContentValues values = new ContentValues();
            values.put(MenuContract.MenuEntry.COLUMN_NAME, item.getName());
            values.put(MenuContract.MenuEntry.COLUMN_DESCRIPTION, item.getDescription());
            values.put(MenuContract.MenuEntry.COLUMN_PRICE, item.getPrice());
            values.put(MenuContract.MenuEntry.COLUMN_HAS_GLUTEN, item.hasGluten());
            values.put(MenuContract.MenuEntry.COLUMN_CALORIES, item.getCalories());

            db.insert(MenuContract.MenuEntry.TABLE_NAME, null, values);
        }

        db.close();
    }

    private List<MenuItem> loadMenuItemsFromDatabase() {
        // Carregar os dados do banco de dados local
        List<MenuItem> menuItems = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + MenuContract.MenuEntry.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            int columnIndexName = cursor.getColumnIndex(MenuContract.MenuEntry.COLUMN_NAME);
            int columnIndexDescription = cursor.getColumnIndex(MenuContract.MenuEntry.COLUMN_DESCRIPTION);
            int columnIndexPrice = cursor.getColumnIndex(MenuContract.MenuEntry.COLUMN_PRICE);
            int columnIndexHasGluten = cursor.getColumnIndex(MenuContract.MenuEntry.COLUMN_HAS_GLUTEN);
            int columnIndexCalories = cursor.getColumnIndex(MenuContract.MenuEntry.COLUMN_CALORIES);

            do {
                if (columnIndexName >= 0 && columnIndexDescription >= 0 && columnIndexPrice >= 0
                        && columnIndexHasGluten >= 0 && columnIndexCalories >= 0) {
                    String name = cursor.getString(columnIndexName);
                    String description = cursor.getString(columnIndexDescription);
                    double price = cursor.getDouble(columnIndexPrice);
                    boolean hasGluten = cursor.getInt(columnIndexHasGluten) == 1;
                    int calories = cursor.getInt(columnIndexCalories);

                    MenuItem item = new MenuItem(name, description, price, hasGluten, calories);
                    menuItems.add(item);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return menuItems;
    }

    private void displayMenuItems() {
        for (MenuItem item : menuItems) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.menu_item_layout, null);

            TextView nameTextView = itemView.findViewById(R.id.nameTextView);
            TextView descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            TextView priceTextView = itemView.findViewById(R.id.priceTextView);
            TextView glutenTextView = itemView.findViewById(R.id.glutenTextView);
            TextView caloriesTextView = itemView.findViewById(R.id.caloriesTextView);

            nameTextView.setText(item.getName());
            descriptionTextView.setText(item.getDescription());
            priceTextView.setText("Preço: " + (item.getPrice() > 0 ? "R$" + item.getPrice() : "A consultar"));
            glutenTextView.setText("Glúten: " + (item.hasGluten() ? "Sim" : "Não"));
            caloriesTextView.setText("Calorias: " + item.getCalories());

            menuItemsLayout.addView(itemView);
        }
    }
}
