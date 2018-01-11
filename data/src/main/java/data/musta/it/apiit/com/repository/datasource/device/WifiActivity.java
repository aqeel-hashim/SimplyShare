package data.musta.it.apiit.com.repository.datasource.device;

/**
 * Created by musta on 11-Jan-18.
 */

public interface WifiActivity {
    static final String TAG = WifiActivity.class.getCanonicalName();
    void setIsWifiP2pEnabled(boolean isWifiP2pEnabled);
    void resetData();
}
