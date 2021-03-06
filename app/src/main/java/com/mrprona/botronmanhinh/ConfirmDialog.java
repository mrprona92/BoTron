package com.mrprona.botronmanhinh;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Project : TRS Social
 * Author :DANGLV
 * Date : 31/05/2016
 * Time : 14:40
 * Description :
 */


public class ConfirmDialog extends SCBaseDialog {

    //Matching UI
    @BindView(R.id.cbTopRight)
    CheckBox cbTopRight;

    @BindView(R.id.cbTopLeft)
    CheckBox cbTopLeft;

    @BindView(R.id.cbBotRight)
    CheckBox cbBotRight;

    @BindView(R.id.cbBotLeft)
    CheckBox cbBotLeft;

    @BindView(R.id.txtTopRight)
    TextView txtTopRight;

    @BindView(R.id.txtTopLeft)
    TextView txtTopLeft;

    @BindView(R.id.txtBotRight)
    TextView txtBotRight;

    @BindView(R.id.txtBotLeft)
    TextView txtBotLeft;


    public interface ConfirmDialogListener {
        void onSelect(int indexButton, boolean[] listCheck);
    }

    private int mTitleId, mMessageId, mConfirmTextId, mCancelTextId;
    private String mMessageString;
    private String mTitleString;
    private int mConfirmBtnResId;
    private boolean[] listConer;

    public int getmCancelTextColor() {
        return mCancelTextColor;
    }

    public void setmCancelTextColor(int mCancelTextColor) {
        this.mCancelTextColor = mCancelTextColor;
    }

    private int mCancelTextColor;


    public int getmCancelBtnResId() {
        return mCancelBtnResId;
    }

    public void setmCancelBtnResId(int mCancelBtnResId) {
        this.mCancelBtnResId = mCancelBtnResId;
    }

    private int mCancelBtnResId;

    private Button mRightButton;
    private Button mLeftButton;

    public int getTitleId() {
        return mTitleId;
    }

    public void setTitleId(int titleId) {
        this.mTitleId = titleId;
    }

    public int getMessageId() {
        return mMessageId;
    }

    public void setMessageId(int messageId) {
        this.mMessageId = messageId;
    }

    public int getConfirmTextId() {
        return mConfirmTextId;
    }

    public void setConfirmTextId(int confirmTextId) {
        this.mConfirmTextId = confirmTextId;
    }

    public int getCancelTextId() {
        return mCancelTextId;
    }

    public void setCancelTextId(int cancelTextId) {
        this.mCancelTextId = cancelTextId;
    }

    public String getMessageString() {
        return mMessageString;
    }

    public void setMessageString(String messageString) {
        this.mMessageString = messageString;
    }

    public String getTitleString() {
        return mTitleString;
    }

    public void setTitleString(String titleString) {
        this.mTitleString = titleString;
    }

    public int getConfirmBtnResId() {
        return mConfirmBtnResId;
    }

    public void setConfirmBtnResId(int confirmBtnResId) {
        this.mConfirmBtnResId = confirmBtnResId;
    }

    public ConfirmDialog(Context context, int themeResId, boolean[] list4Coner) {
        super(context);
        listConer = list4Coner;
    }

    public ConfirmDialog(Context context) {
        super(context);
    }

    private ConfirmDialogListener mListener;

    public void setOnConfirmDialogListener(ConfirmDialogListener confirmDialogListener) {
        mListener = confirmDialogListener;
    }

    public void setupViewWithTitle(int titleId, int messageId, int confirmText, int cancelText, final ConfirmDialogListener listener) {
        this.mListener = listener;
        this.mMessageId = messageId;
        this.mTitleId = titleId;
        this.mConfirmTextId = confirmText;
        this.mCancelTextId = cancelText;
        this.mTitleString = "";
        this.mMessageString = "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);
        ButterKnife.bind(this);
        mRightButton = (Button) findViewById(R.id.btnSettle);
        mLeftButton = (Button) findViewById(R.id.btnActionCancel);
        if (mConfirmBtnResId > 0) mRightButton.setBackgroundResource(mConfirmBtnResId);
        if (mCancelBtnResId > 0) mLeftButton.setBackgroundResource(mCancelBtnResId);
        if (mCancelTextColor > 0) mLeftButton.setTextColor(mCancelTextColor);


        String[] fiilliste = mActivity.getResources().getStringArray(R.array.setting_round);

        txtTopRight.setText(fiilliste[0]);
        txtTopLeft.setText(fiilliste[1]);
        txtBotRight.setText(fiilliste[2]);
        txtBotLeft.setText(fiilliste[3]);

        cbTopRight.setChecked(listConer[0]);
        cbTopLeft.setChecked(listConer[1]);
        cbBotRight.setChecked(listConer[2]);
        cbBotLeft.setChecked(listConer[3]);

        if (mTitleId > 0) {
            ((TextView) findViewById(R.id.lblTitle)).setText(mTitleId);
        } else if (mTitleString != null && !mTitleString.equals("")) {
            ((TextView) findViewById(R.id.lblTitle)).setText(mTitleString);
        } else
            (findViewById(R.id.lblTitle)).setVisibility(View.GONE);


        if (mCancelTextId > 0) ((Button) findViewById(R.id.btnActionCancel)).setText(mCancelTextId);

        if (mConfirmTextId > 0) mRightButton.setText(mConfirmTextId);

        findViewById(R.id.btnActionCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialog.this.dismiss();
                if (mListener != null) {
                    mListener.onSelect(0, listConer);
                }
            }
        });

        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialog.this.dismiss();
                if (mListener != null) {
                    listConer[0] = cbTopRight.isChecked();
                    listConer[1] = cbTopLeft.isChecked();
                    listConer[2] = cbBotRight.isChecked();
                    listConer[3] = cbBotLeft.isChecked();

                    mListener.onSelect(1, listConer);
                }
            }
        });
    }
}
