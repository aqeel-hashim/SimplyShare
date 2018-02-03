package model.musta.it.apiit.com.model;

import java.net.InetAddress;

/**
 * Created by musta on 20-Jan-18.
 */

public class WifiP2pInfo{

    /** Indicates if a p2p group has been successfully formed */
    public boolean groupFormed;

    /** Indicates if the current device is the group owner */
    public boolean isGroupOwner;

    /** Group owner address */
    public InetAddress groupOwnerAddress;

    public WifiP2pInfo() {
    }

    /** copy constructor */
    public WifiP2pInfo(WifiP2pInfo source) {
        if (source != null) {
            groupFormed = source.groupFormed;
            isGroupOwner = source.isGroupOwner;
            groupOwnerAddress = source.groupOwnerAddress;
        }
    }

    public WifiP2pInfo(boolean groupFormed, boolean isGroupOwner, InetAddress groupOwnerAddress) {
        this.groupFormed = groupFormed;
        this.isGroupOwner = isGroupOwner;
        this.groupOwnerAddress = groupOwnerAddress;
    }
}
