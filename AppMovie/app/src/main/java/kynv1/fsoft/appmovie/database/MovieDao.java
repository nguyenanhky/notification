package kynv1.fsoft.appmovie.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import kynv1.fsoft.appmovie.model.Result;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Result result);

    @Delete
    void delete(Result result);

    @Query("SELECT * FROM MOVIETABLE")
    List<Result> getAll();

    @Query("SELECT * FROM MOVIETABLE WHERE id=:id")
    List<Result>checkMovie(Integer id);

    @Query("Select * from movieTable order by date")
    public List<Result> orderThetable();







}
