package network.callbacks;

import android.os.Bundle;

public interface ResponseCallBacks {

    void onSuccessResponse(Bundle msg);

    void onFailureResponse(Bundle msg);
}

