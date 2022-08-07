package kynv1.fsoft.appmoviefinally.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import kynv1.fsoft.appmoviefinally.model.Result;
import kynv1.fsoft.appmoviefinally.utils.Constance;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Result result);

    @Delete
    void delete(Result result);

    @Query("SELECT * FROM "+ Constance.TABLE_NAME)
    List<Result> getAll();

    @Query("SELECT * FROM table_movie WHERE id=:id")
    List<Result>checkMovie(Integer id);

    @Query("SELECT COUNT(*) FROM table_movie ")
    int getCount();

    @Query("SELECT * FROM table_movie WHERE original_title LIKE '%' || :name || '%' ")
    List<Result>SearchResult(String name);

}
