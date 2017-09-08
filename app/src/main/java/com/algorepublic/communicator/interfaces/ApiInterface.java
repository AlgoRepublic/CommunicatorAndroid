package com.algorepublic.communicator.interfaces;


import com.algorepublic.communicator.model.ActListModel;
import com.algorepublic.communicator.model.ActSchedule2Model;
import com.algorepublic.communicator.model.ActTypeListModel;
import com.algorepublic.communicator.model.LabourModel;
import com.algorepublic.communicator.utils.Constants;

import retrofit.Call;
import retrofit.http.GET;

public interface ApiInterface {

    @GET(Constants.GET_LABOUR_LIST)
    Call<LabourModel> getLabourModel();

    @GET(Constants.GET_FCT_ACTIVITY_SCHEDULE_2)
    Call<ActSchedule2Model> getActSchedule();

    @GET(Constants.GET_ACT_LIST)
    Call<ActListModel> getActList();

    @GET(Constants.GET_ACT_TYPE_LIST)
    Call<ActTypeListModel> getActTypeList();

//    @GET(Constants.GET_WORK_PLACE_LIST)
//    Call<ActSchedule2Model> getWorkPlaceList();
//
//    @GET(Constants.GET_WORK_PLACE_TYPE_LIST)
//    Call<ActSchedule2Model> getWorkPlaceTypeList();

}
