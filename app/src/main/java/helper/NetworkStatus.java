package helper;

import android.content.Context;
import co.sspp.library.SweetAlertDialog;
import network.callbacks.ResponseCallBacks;

public abstract class NetworkStatus implements ResponseCallBacks {

    private boolean isProgrssDialogVisible;
    private UIView uiViewInstance = UIView.getInstance();
    SweetAlertDialog sweetAlertDialog;

    protected NetworkStatus(boolean isProgrssDialogVisible) {
        this.isProgrssDialogVisible = isProgrssDialogVisible;
    }

    /**
     * <p>This method shows progress bar on Network call start</p>
     *  @param context   - Activity context
     *  @param msg   - Message to display in Progress bar
     */

    public void onStart(Context context, String msg) {
        if (isProgrssDialogVisible) {
            sweetAlertDialog =  uiViewInstance.showProgressBar(context, msg);
        }
    }

    /**
     * <p>This method hides progress bar on Network call completion</p>
     */

    public void onComplete() {
        if (isProgrssDialogVisible) {
            sweetAlertDialog.dismiss();
        }
    }
}

