package com.hxgy.nurexcute.ui.frg;


import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.BuildKeyValueListAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.dto.UserResultMess;
import com.hxgy.nurexcute.ui.ExcuteContent;
import com.hxgy.nurexcute.ui.MainActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BloodSugar extends Fragment implements ExcuteContent {
    private Context context ;
    private PatientDTO patient ;
    private CheckBox fasting ;    //空腹
    private Spinner ProSpinner ;  //测试项目
    private EditText ResultValue ; //结果
    private EditText testtime ;   //测试时间
    private Button SaveData ;     //保存
    private boolean BloodListType=false ;   //下拉列表填充类型
    protected MainActivity main ;
    private Calendar cal = Calendar.getInstance() ;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.bloodsugar,null) ;
    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState) ;
        context = getActivity() ;
        patient = (PatientDTO)getArguments().getSerializable("patient") ;
        main = (MainActivity)getActivity() ;
        main.showExcute = false ;
        setRetainInstance(true) ;

    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState) ;
        ((MainActivity)getActivity()).EnableMenu() ;
        init() ;
        InitListValue() ;
    }
    private void init(){
        fasting = (CheckBox)getActivity().findViewById(R.id.fasting) ;
        ProSpinner = (Spinner)getActivity().findViewById(R.id.TestProjectValue) ;
        ResultValue = (EditText)getActivity().findViewById(R.id.ResultValue) ;
        testtime = (EditText)getActivity().findViewById(R.id.TestTimeValue) ;
        SaveData = (Button)getActivity().findViewById(R.id.BloodSave) ;
        Calendar cal = Calendar.getInstance() ;
        getActivity().setTitle(R.string.bed_btn_bloodsugar) ;
        //测试时间默认设置
        Date now = new Date() ;
        Calendar cl = Calendar.getInstance() ;
        DateFormat D1 = DateFormat.getTimeInstance() ;
        String Time = D1.format(now) ;
        testtime.setInputType(InputType.TYPE_NULL) ;
        testtime.setText(cl.get(Calendar.HOUR_OF_DAY)+":"+cl.get(Calendar.MINUTE)) ;
//      testtime.setText(Time);
        testtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 实例化一个TimePickerDialog的对象
                 * 第二个参数是一个TimePickerDialog.OnTimeSetListener匿名内部类，当用户选择好时间后点击done会调用里面的onTimeset方法
                 */
                Calendar calendar = Calendar.getInstance() ;
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY) ;
                int minute = calendar.get(Calendar.MINUTE) ;
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
        ResultValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    if(s.toString().contains(".")){
                        if(s.length()-1-s.toString().indexOf(".")>1){
                            int IndexRowid=s.toString().indexOf(".") ;
                            String fillString=s.toString().substring(0,IndexRowid)+"."+s.toString().substring(IndexRowid+1,IndexRowid+2) ;
                            ResultValue.setText(fillString) ;
                            UIHelper.ToastMessage(context,"血糖结果最多保留一位小数^_^") ;
                        }
                    }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        fasting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fasting.isChecked()){
                    BloodListType = true ;
                    InitListValue() ;
                }else{
                    BloodListType = false ;
                    InitListValue() ;
                }
            }
        });
        SaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(patient == null){
                    UIHelper.ToastMessage(context,"请选择病人");
                    return;
                }
                String FastingValue = "" ;
                //Get CheckBox value
                if(fasting.isChecked()){
                    FastingValue = "0" ;
                }else{
                    FastingValue = "1" ;
                }
                BuildKeyValueListAdapter buildkeyvalue = (BuildKeyValueListAdapter) ProSpinner.getSelectedItem() ;
                String ProjectValue = buildkeyvalue.getKey() ;
//                String ProjectValue=ProSpinner.getSelectedItem().toString(); //Get project name value
                String ResultData = ResultValue.getText().toString() ;          //Get Result value
                String testtimeValue = testtime.getText().toString() ;          //Get test time value
                String RegNo = patient.getPatNo() ;
                String PatName = patient.getName() ;
                String PAAdm = patient.getAdm() ;
                String WardDR = patient.getctlocDesc() ;
                String BedNo = patient.getBedNo() ;
                String Project = ProjectValue ;    //测试项目
                String IsEmpty = FastingValue ;   //空腹
                String Result = ResultData ;       //结果
                String TestTime = testtimeValue ;  //测试时间
                String Tester = CurUser.userId ;
                String DataSource = "PDA" ;
                Map<String,String> map=new HashMap<String,String>() ;
                map.put("RegNo",RegNo) ;
                map.put("PatName",PatName) ;
                map.put("PAAdm",PAAdm) ;
                map.put("WardDR",WardDR) ;
                map.put("BedNo",BedNo) ;
                map.put("Project",Project) ;
                map.put("IsEmpty",IsEmpty) ;
                map.put("Result",Result) ;
                map.put("TestTime",TestTime) ;
                map.put("Tester",Tester) ;
                map.put("DataSource",DataSource) ;
                Gson gson = new Gson() ;
                String Values=gson.toJson(map) ;
                ApiClient.SaveBloodSugar(Values,new SaveBloodSugar(context)) ;

            }

        });
    }
    class SaveBloodSugar extends AsyncHttpResponseHandler{
        Context context = null ;
        public SaveBloodSugar(Context context){
            this.context = context ;
        }
        @Override
        public void onSuccess(String result){
            super.onSuccess(result) ;
            Gson gson = new Gson() ;
            try{
                List<UserResultMess> list = gson.fromJson(result,new TypeToken<List<UserResultMess>>(){}.getType()) ;
                UserResultMess mess = list.get(0) ;
                if(mess.getCode().equals("0")){
                    UIHelper.ToastMessage(context,"保存成功!") ;
                }else{
                    UIHelper.ToastMessage(context,"保存失败!") ;
                }
            }catch (Exception e){
                UIHelper.ToastMessage(context,e.getMessage()) ;
            }
        }
    }
    public void InitListValue(){
        String Values = "" ;
        try{
           HashMap<String,String> map = new HashMap<String, String>() ;
           map.put("MHTCode","GLU") ;
            Gson gson=new Gson() ;
            Values=gson.toJson(map) ;
            // TODO: 2016/8/2
        } catch (Exception e){
            e.printStackTrace() ;
        }
        ApiClient.GetBloodListValue(Values,new GetListHandler(context));
    }
    class GetListHandler extends AsyncHttpResponseHandler{
        Context context = null ;
        public GetListHandler(Context context){
            this.context = context ;
        }
        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);
            try {
                String resultValue = result ;
                /**
                 *去掉json串中的多余的中括号
                 */
                if(result.substring(0,1).equals("[") ){
                    resultValue = result.substring(1,result.length()-1) ;
                }
                ArrayList<BuildKeyValueListAdapter> listadapter = new ArrayList<BuildKeyValueListAdapter>() ;
                //定义实体类用Gson解析串
                Gson gson = new Gson() ;
                ProjectItem ReValue = gson.fromJson(resultValue,ProjectItem.class) ;
                List<Data> list = ReValue.getData() ;
                //得到Data对象的List，用Iterator遍历List
                Iterator<Data> ir = list.listIterator() ;
                while(ir.hasNext()){
                    Data t = ir.next() ;
                    String RtnItemCode = t.getItemCode() ;
                    String RtnItemName = t.getItemName() ;
                    String RtnItemType = t.getItemType() ;
                    if(BloodListType){
                        if(RtnItemType.equals("0")||(RtnItemType.equals("All"))){
                            listadapter.add(new BuildKeyValueListAdapter(RtnItemCode,RtnItemName)) ;
                        }
                    }else{
                        if(RtnItemType.equals("1")||(RtnItemType.equals("All"))){
                            listadapter.add(new BuildKeyValueListAdapter(RtnItemCode,RtnItemName)) ;
                        }
                    }
                }
                ArrayAdapter<BuildKeyValueListAdapter> keyAdapter=new ArrayAdapter<BuildKeyValueListAdapter>(context,android.R.layout.simple_spinner_item,listadapter) ;
                keyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) ;
                ProSpinner.setAdapter(keyAdapter) ;
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
    }
    public void chart(){}
    public void search(){}
    public void excute(){}
    public class ProjectItem{
        List<Data> data ;
        public void setData(List<Data> data){
            this.data=data ;
        }
        public List<Data> getData(){
            return data ;
        }
    }
    public class Data{
        String ItemCode ;
        String ItemName ;
        String ItemType ;
        public void setItemCode(String itemcode){
            this.ItemCode = itemcode ;
        }
        public String getItemCode() {
            return ItemCode ;
        }
        public void setItemName(String itemname)
        {
            this.ItemName = itemname ;
        }
        public String getItemName()
        {
            return ItemName ;
        }
        public void setItemtype(String itemtype){
            this.ItemType = itemtype ;
        }
        public String getItemType(){
            return ItemType;
        }
    }

}

