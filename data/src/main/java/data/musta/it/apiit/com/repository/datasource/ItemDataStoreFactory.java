package data.musta.it.apiit.com.repository.datasource;

import android.content.Context;

/**
 * Created by musta on 23-Dec-17.
 */

public class ItemDataStoreFactory {
    private Context context;

    public ItemDataStoreFactory(Context context){
        this.context = context;
    }

    public ItemDataSource create(){
        return new ItemLocalDataStore(context);
    }
}
