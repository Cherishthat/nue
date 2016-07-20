package com.hxgy.nurexcute.ui.frg;


import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.ui.ExcuteContent;
import com.hxgy.nurexcute.ui.MainActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class BloodSugar extends Fragment implements ExcuteContent {
    private Context context;
    private PatientDTO patient;
    private MainActivity main;
    private Spinner ProSpinner;  //测试项目
    private EditText testtime;   //测试时间
    private Calendar cal = Calendar.getInstance();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.bloodsugar,null);
    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context=getActivity();
        patient=(PatientDTO)getArguments().getSerializable("patient");
        main=(MainActivity)getActivity();
        setRetainInstance(true);

    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity)getActivity()).EnableMenu();
        init();
    }
    private void init(){
        ProSpinner=(Spinner)getActivity().findViewById(R.id.TestProjectValue) ;
        testtime=(EditText)getActivity().findViewById(R.id.TestTimeValue);
        Calendar cal = Calendar.getInstance();
        getActivity().setTitle(R.string.bed_btn_bloodsugar);
        //测试项目初始值设置
        String ProjectItem="早餐前,早餐后,午餐前,午餐后,晚餐前,晚餐后";
        String[] valueArray=ProjectItem.split(",");
        ArrayAdapter<String> Adapter;
        Adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,valueArray);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ProSpinner.setAdapter(Adapter);
        //测试时间默认设置
        Date now=new Date();
        Calendar cl=Calendar.getInstance();
        DateFormat D1=DateFormat.getTimeInstance();
        String Time=D1.format(now);
        testtime.setInputType(InputType.TYPE_NULL);
        testtime.setText(cl.get(Calendar.HOUR_OF_DAY)+":"+cl.get(Calendar.MINUTE));
//      testtime.setText(Time);
        testtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 实例化一个TimePickerDialog的对象
                 * 第二个参数是一个TimePickerDialog.OnTimeSetListener匿名内部类，当用户选择好时间后点击done会调用里面的onTimeset方法
                 */
                Calendar calendar=Calendar.getInstance();
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                        testtime.setText( hourOfDay + ":" + minute);
                    }
                }, hourOfDay, minute, true);
                timePickerDialog.show();
            }
        });



    }

    public void chart(){}
    public void search(){}
    public void excute(){}

}

