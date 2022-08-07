package kynv1.fsoft.appmoviefinally.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import kynv1.fsoft.appmoviefinally.model.reminder.Result;
import kynv1.fsoft.appmoviefinally.utils.Constance;


@Dao
public interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Result result);

    @Delete
    void delete(Result result);


    @Query("SELECT * FROM reminder WHERE id=:id")
    List<Result> checkMovie(Integer id);

    @Query("SELECT * FROM " + Constance.TABLE_REMINDER)
    List<Result> getAll();

    @Query("Select * from reminder order by date")
     List<Result> orderThetable();

    @Query("SELECT * FROM reminder ORDER BY id  LIMIT 2")
    List<Result>getListLimitTwo();

}
