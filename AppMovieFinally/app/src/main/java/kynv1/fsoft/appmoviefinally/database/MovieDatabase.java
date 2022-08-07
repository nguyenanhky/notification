package kynv1.fsoft.appmoviefinally.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import kynv1.fsoft.appmoviefinally.model.Result;
import kynv1.fsoft.appmoviefinally.utils.Constance;

@Database(entities = {Result.class, kynv1.fsoft.appmoviefinally.model.reminder.Result.class},version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class MovieDatabase extends RoomDatabase {
    private static MovieDatabase instance;

    public static synchronized MovieDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),MovieDatabase.class, Constance.DATABASENAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract MovieDao movieDao();
    public abstract ReminderDao reminderDao();
}
