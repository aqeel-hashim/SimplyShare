package model.musta.it.apiit.com.model;

import model.musta.it.apiit.com.interactor.OnItemReceived;
import model.musta.it.apiit.com.repository.FileInputRetreiver;
import model.musta.it.apiit.com.repository.ServerRequest;

/**
 * Created by musta on 20-Jan-18.
 */

public class ConnectionManager implements FileInputRetreiver{

    private ServerRequest serverRequest;
    private OnItemReceived onItemReceived;

    public ConnectionManager(ServerRequest serverRequest, OnItemReceived onItemReceived) {
        serverRequest.addFileInputRetreiver(this);
        this.serverRequest = serverRequest;
        this.onItemReceived = onItemReceived;
    }

    public void connect(String ip, int port){
        serverRequest.connect(ip, port);
    }

    public void send(Item item){
        serverRequest.send(null, item);
    }

    @Override
    public void dataRecieved(byte[] data, Item itemDescription) {
        onItemReceived.itemReceived(itemDescription);
    }
}
