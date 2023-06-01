package com.example.projeto2.database;

import android.provider.BaseColumns;

public class MenuContract {
    private MenuContract() {} // Construtor privado para evitar instanciação da classe

    public static class MenuEntry implements BaseColumns {
        public static final String TABLE_NAME = "menu";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_HAS_GLUTEN = "has_gluten";
        public static final String COLUMN_CALORIES = "calories";
    }
}
