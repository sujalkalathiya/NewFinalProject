package algonquin.cst2335.newfinalproject.Sun.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LookupDAO {
    @Insert
    void insert(Lookup lookup);

    @Query("SELECT * FROM lookups")
    LiveData<List<Lookup>> getAllLookups();

    @Query("DELETE FROM lookups")
    void deleteAllLookups();

    @Delete
    void delete(Lookup lookup);

    @Update
    void update(Lookup lookup);

    @Query("DELETE FROM lookups")
    void deleteAll(); // This should have a proper Query annotation to avoid ambiguity

    @Query("SELECT * FROM lookups")
    List<Lookup> getAll(); // This should have a proper Query annotation to avoid ambiguity

    @Query("SELECT * FROM lookups WHERE id = :lookupId")
    Lookup getById(int lookupId);

    @Query("SELECT * FROM lookups WHERE sunrise IS NOT NULL AND sunset IS NOT NULL")
    List<Lookup> getAllSunriseAndSunsetLookups();
}
