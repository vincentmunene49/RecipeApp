package com.example.recipes.RoomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class Recipe_database:RoomDatabase() {

    abstract fun RecipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: Recipe_database? = null

        fun getDatabase(context: Context): Recipe_database {
            var tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
          synchronized(this){
              val instance = Room.databaseBuilder(
                  context.applicationContext,
                  Recipe_database::class.java,
                  "recipe_database"
              ).build()
              INSTANCE = instance
              return instance
          }



        }
    }
}