package com.algorepublic.communicator.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.algorepublic.communicator.Communicator;
import com.algorepublic.communicator.R;
import com.algorepublic.communicator.fragments.ActSchedule2Fragment;
import com.algorepublic.communicator.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommunicatorMain extends BaseActivity {

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvDesignation)
    TextView tvDesignation;

    @BindView(R.id.tvCoNumber)
    TextView tvCoNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicator_main);
        ButterKnife.bind(this);
        setUserInfo();
        callFragment(R.id.container, ActSchedule2Fragment.newInstance(), null);
    }

    private void setUserInfo() {
        tvName.setText(Communicator.db.getString(Constants.NAME));
        tvDesignation.setText(Communicator.db.getString(Constants.DESIGNATION));
        tvCoNumber.setText(Communicator.db.getString(Constants.CO_NUMBER));
    }
}
