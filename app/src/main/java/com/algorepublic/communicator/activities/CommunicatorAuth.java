package com.algorepublic.communicator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.algorepublic.communicator.Communicator;
import com.algorepublic.communicator.R;
import com.algorepublic.communicator.model.LabourModel;
import com.algorepublic.communicator.utils.Constants;
import com.algorepublic.communicator.utils.Util;
import com.google.gson.Gson;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by adilnazir on 26/07/2017.
 */

public class CommunicatorAuth extends BaseActivity {

    @BindView(R.id.edtUserName)
    EditText edtUserName;

    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindString(R.string.invalid_email)
    String InvalidEmail;
    @BindString(R.string.enter_email)
    String EmailRequired;
    @BindString(R.string.enter_pass)
    String PassRequired;
    @BindString(R.string.invalid_credentials)
    String invalidInput;
    private LabourModel LabourListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSubmit)
    void submit() {
        LoginClick();
//        intent = new Intent(this, CommunicatorMain.class);
//        startActivity(intent);
//        finish();
    }

    public void LoginClick() {
//        if (!isEmailValid(edtUserName.getText().toString())) {
//            edtUserName.requestFocus();
//            edtUserName.setError(InvalidEmail);
//            return;
//        }
        if (isContentNull(edtUserName.getText().toString())) {
            edtUserName.requestFocus();
            edtUserName.setError(EmailRequired);
            return;
        }
        if (isContentNull(edtPassword.getText().toString())) {
            edtPassword.requestFocus();
            edtPassword.setError(PassRequired);
            return;
        }
//        Util.hidekeyPad(getApplicationContext(), view);
        checkLogin();

    }

    private void checkLogin() {
        Gson gson = new Gson();
        LabourListModel = gson.fromJson(Communicator.db.getString(Constants.RESPONSE_GSON_LABOUR_LIST), LabourModel.class);
        for (int i = 0; i < LabourListModel.laboursList.size(); i++) {
            if (LabourListModel.laboursList.get(i).name.equalsIgnoreCase(edtUserName.getText().toString()) && LabourListModel.laboursList.get(i).employeeId.equalsIgnoreCase(edtPassword.getText().toString())) {
                ((BaseActivity) this).startApp();
                Communicator.db.putBoolean(Constants.IS_LOGIN, true);
                Communicator.db.putString(Constants.NAME, LabourListModel.laboursList.get(i).name);
                Communicator.db.putString(Constants.DESIGNATION, LabourListModel.laboursList.get(i).designation);
                Communicator.db.putString(Constants.CO_NUMBER, LabourListModel.laboursList.get(i).employeeId);
                return;
            }
        }
        Util.showToast(CommunicatorAuth.this, invalidInput);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isContentNull(String content) {
        //TODO: Replace this with your own logic
        return content.length() == 0;
    }
}
