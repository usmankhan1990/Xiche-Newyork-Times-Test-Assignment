package helper;

import android.content.Context;
import android.graphics.Color;
import co.sspp.library.SweetAlertDialog;

public class UIView {

    private static final UIView ourInstance = new UIView();
    public static UIView getInstance() {
        return ourInstance;
    }

    /**
     * <p>This function display Progress Bar.</p>
     * @param context   - context of Activity or Fragment.
     * @param msg   - Message to display in Progress bar.
     */

    public SweetAlertDialog showProgressBar(Context context, String msg) {

        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(msg);
        pDialog.setCancelable(true);
        pDialog.show();
        return pDialog;

    }
}