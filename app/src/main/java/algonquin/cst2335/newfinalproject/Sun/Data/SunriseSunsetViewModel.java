package algonquin.cst2335.newfinalproject.Sun.Data;// SunriseSunsetViewModel.java
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SunriseSunsetViewModel extends AndroidViewModel {

    private final LookupDAO lookupDAO;
    private final LiveData<List<Lookup>> allLookups;

    public SunriseSunsetViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(application);
        lookupDAO = database.lookupDAO();
        allLookups = lookupDAO.getAllLookups();
    }

    public void insert(Lookup lookup) {
        AppDatabase.databaseWriteExecutor.execute(() -> lookupDAO.insert(lookup));
    }

    public LiveData<List<Lookup>> getAllLookups() {
        return allLookups;
    }

    public void clearAllLookups() {
        AppDatabase.databaseWriteExecutor.execute(() -> lookupDAO.deleteAll());
    }

    public void updateLookup(Lookup lookup) {
        AppDatabase.databaseWriteExecutor.execute(() -> lookupDAO.update(lookup));
    }

    public LiveData<List<Lookup>> getLookups() {
        return allLookups;
    }
}
