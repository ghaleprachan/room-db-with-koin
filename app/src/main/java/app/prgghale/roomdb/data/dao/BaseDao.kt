package app.prgghale.roomdb.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Update

/**
 * This contains common functions between all the dao*/
interface BaseDao<T> {

    @Insert(onConflict = REPLACE)
    fun insert(value: T)

    @Update(onConflict = REPLACE)
    fun update(value: T)

    @Delete
    fun delete(value: T)

}