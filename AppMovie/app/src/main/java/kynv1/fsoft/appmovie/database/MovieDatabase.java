package kynv1.fsoft.appmovie.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import kynv1.fsoft.appmovie.model.Result;

@Database(entities = {Result.class},version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class MovieDatabase extends RoomDatabase {
    public static MovieDatabase instance;

    public static synchronized MovieDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),MovieDatabase.class,"movieDatabase")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract MovieDao movieDao();

}
