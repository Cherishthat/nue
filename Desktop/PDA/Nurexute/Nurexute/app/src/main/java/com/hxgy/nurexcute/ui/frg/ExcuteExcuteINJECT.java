package com.hxgy.nurexcute.ui.frg;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.ExcuteExcuteGetOrdAdapter;
import com.hxgy.nurexcute.adapter.ExcuteExcuteRelationOrdAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.AllPatOrdDTO;
import com.hxgy.nurexcute.dto.RelationOrdDTO;
import com.hxgy.nurexcute.dto.ResultMessDTO;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;



public class ExcuteExcuteINJECT extends ExcuteExcuteMainA {

    @Override
    public void init(){
        getActivity().setTitle(R.string.menu_right_bar_inject);
    }

    @Override
    public void getData() {
        super.getData();
        if(patient!=null) {
            main.setSupportProgressBarIndeterminateVisibility(true);

            ApiClient.GetInjectOrd(patient.getAdm(),new OrdHander(this.context));
        }
    }

    @Override
    public void exuteBar(String barCode) {
        String [] strArr=BARCODE.split("-");
        String ordId=strArr[0]+"||"+strArr[1];
        String no=strArr[2];

        main.setSupportProgressBarIndeterminateVisibility(true);
        ApiClient.ExuteRelationOrd(ordId, CurUser.userId, no, patient.getAdm(),new ExuteRelationOrdHander(this.context,ordId,no,patient.getAdm()));
    }
    @Override
    public void showBarData(String barCode){

        String [] strArr=barCode.split("-");
        String ordId=strArr[0]+"||"+strArr[1];
        String no=strArr[2];
        main.setSupportProgressBarIndeterminateVisibility(true);
        ApiClient.RelationOrd(ordId,no,patient.getAdm(),new RelationOrdHander(this.context,barCode));

    }

    class OrdHander extends  AsyncHttpResponseHandler{
        Context context =null;
        public OrdHander(Context context) {
            this.context =context;
        }
        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);
            Gson gson = new Gson();
            try {
                List<AllPatOrdDTO> p = gson.fromJson(result,new TypeToken<List<AllPatOrdDTO>>(){}.getType() );
                ExcuteExcuteGetOrdAdapter adapter = new ExcuteExcuteGetOrdAdapter(context, p);
                lv.setAdapter(adapter);
            }catch(Exception ex) {
                UIHelper.ToastMessage(context, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable throwable, String arg1) {
            super.onFailure(throwable, arg1);
            UIHelper.ToastFailMessage( this.context, throwable);

        }
        @Override
        public void onFinish() {
            if(main!=null){

                main.setSupportProgressBarIndeterminateVisibility(false);
            }
        }
    }
    class ExuteRelationOrdHander extends  AsyncHttpResponseHandler{
        Context context =null;
        String ordId,no,admId;
        public ExuteRelationOrdHander(Context context,String ordId,String no,String admId) {
            this.context =context;
            this.ordId=ordId;
            this.no=no;
            this.admId=admId;
        }
        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);
            try {
                Gson gson = new Gson();
                List<ResultMessDTO> p = gson.fromJson(result,new TypeToken<List<ResultMessDTO>>(){}.getType() );
                if( p.get(0).getCode().equals("0")) {
                    UIHelper.ToastMessage(context, R.string.WaitExcuteList_sucess);
                    getData();
                }else{
                    UIHelper.ToastMessage(context, R.string.WaitExcuteList_failure);
                    UIHelper.ToastMessage(context, p.get(0).getMessage());
                }
            }catch (Exception ex) {
                UIHelper.ToastMessage(context, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable throwable, String arg1) {
            super.onFailure(throwable, arg1);
            UIHelper.ToastFailMessage( this.context, throwable);


        }
        @Override
        public void onFinish() {
            if(main!=null){
                main.setSupportProgressBarIndeterminateVisibility(false);
            }
        }
    }
    class RelationOrdHander extends  AsyncHttpResponseHandler{
        Context context =null;
        String barcode="";
        public RelationOrdHander(Context context,String barcode) {
            this.context =context;
            this.barcode=barcode;
        }
        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);
            Gson gson = new Gson();
            try {
                List<RelationOrdDTO> p = gson.fromJson(result,new TypeToken<List<RelationOrdDTO>>(){}.getType() );
                ExcuteExcuteRelationOrdAdapter adapter = new ExcuteExcuteRelationOrdAdapter(context, p);
                lv.setAdapter(adapter);
                if(p.size()>0){
                    showBtn(this.barcode);
                }
            }catch (Exception ex) {
                UIHelper.ToastMessage(context, ex.getMessage());
            }
        }

        @Override
        public void onFailure(Throwable throwable, String arg1) {
            super.onFailure(throwable, arg1);
            UIHelper.ToastFailMessage( this.context, throwable);

        }
        @Override
        public void onFinish() {
            if(main!=null){
                main.setSupportProgressBarIndeterminateVisibility(false);
            }
        }
    }

}
