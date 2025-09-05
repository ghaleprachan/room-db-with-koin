package app.prgghale.roomdb.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Update

/**
 * This contains common functions between all the dao*/
interface BaseDao<T> {

    /**
     * Insert single value on database*/
    @Insert(onConflict = REPLACE)
    suspend fun insert(value: T)

    /**
     * Insert list of data into database
     *
     * [values] is list of data to be inserted into databse*/
    @Insert(onConflict = REPLACE)
    suspend fun insert(values: List<T>)

    /**
     * Updates Table with new data
     *
     * [value] is updated data*/
    @Update(onConflict = REPLACE)
    suspend fun update(value: T)

    /**
     * Delete data from table*/
    @Delete
    suspend fun delete(value: T)

}