package model.musta.it.apiit.com.interactor;

import java.util.List;

/**
 * Created by musta on 23-Dec-17.
 */

public abstract class UseCase<T, Params> {

    protected abstract T buildUseCase(Params params);

    public T execute(Params params){
        return buildUseCase(params);
    }

}
