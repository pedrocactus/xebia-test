package com.xebiatest.boxotop.app.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RatingBar;

import com.xebiatest.boxotop.app.BoxotopApp;
import com.xebiatest.boxotop.app.R;
import com.xebiatest.boxotop.app.events.SaveCommentEvent;
import com.xebiatest.boxotop.app.ui.utils.Utils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by pierrecastex on 28/09/2014.
 */
public class RatingDialog extends DialogFragment {
    public static String TAG = "rating_dialog_fragment";
    public static String TITLE = "title";
    public static String COMMENT = "comment";
    public static String RATING = "rating";


    @InjectView(R.id.rating_comment)
    EditText commentEditText;

    @InjectView(R.id.ratingbar_dialog)
    RatingBar ratingBar;

    @Inject
    EventBus eventBus;

    public RatingDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        BoxotopApp.injectMembers(this);
        String title = getArguments().getString(TITLE);
        String comment = getArguments().getString(COMMENT);
        float rating = getArguments().getFloat(RATING);
        AlertDialog.Builder b=  new  AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setPositiveButton(getString(R.string.rating_dialog_save),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                eventBus.post(new SaveCommentEvent(ratingBar.getRating(), commentEditText.getText().toString()));
                                dialog.dismiss();
                            }
                        }
                )
                .setNegativeButton(getString(R.string.rating_dialog_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Avoiding boilerplate and UnSaveCommentEventCreation to restore click enabling in the main fragment
                                eventBus.post(new SaveCommentEvent(-1, null));
                                dialog.dismiss();
                            }
                        }
                );

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.rating_dialog, null);
        ButterKnife.inject(this, view);
        // Show soft keyboard automatically
        commentEditText.requestFocus();
        commentEditText.setText(Utils.quoteToText(comment));
        ratingBar.setRating(rating);

        b.setView(view);
        return b.create();
    }


    public static RatingDialog newInstance(String title,float rating, CharSequence comment) {
        RatingDialog frag = new RatingDialog();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putCharSequence(COMMENT, comment);
        args.putFloat(RATING, rating);
        frag.setArguments(args);
        return frag;
    }

}
