package com.hxgy.nurexcute.api;

import com.example.comm.jason.RunServerMethod;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class ApiClient {

	public static void getServerVerCode(AsyncHttpResponseHandler handler){
		RunServerMethod.Get(handler,"App.Nure.API","VersonCode");
	}
	public static void getUser (String uid,String pwd,AsyncHttpResponseHandler handler) {
		RunServerMethod.Get(handler,"App.Nure.API","LogonCheck",uid,pwd);
	}
	
	public static void excuteOrder(String ordIDStr,String status,String userId,String depId,String disposeCode,String note,AsyncHttpResponseHandler handler){
		RunServerMethod.Get(handler,"App.Nure.API","ExeOrder",ordIDStr,status,userId,depId,disposeCode,note);
	}
	
	public static void getpatientList(String locid,String bedGroup,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","QueryPatientList",locid,"",bedGroup);
	}
	public static void GetPatientBetialByPatNo(String patNo,String ctloc, AsyncHttpResponseHandler handler){
		RunServerMethod.Get(handler,"App.Nure.API","GetPatientBetialByPatNo",patNo,ctloc);
	}
	public static void getPatientDetail(String admId ,String ctloc, AsyncHttpResponseHandler handler){
		RunServerMethod.Get(handler,"App.Nure.API","GetPatientDetail",admId,ctloc);
	}
	public static void getordertypeList(String groupid,String userId,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"web.DHCNurCom","GetQueryType",groupid,userId);
	}
	
	public static void getExcuteOrder(String admId, String sdate, String edate, String type, String userId , String loc ,String wardid,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","GetOrderInfoByPatNo",admId,sdate,edate,type,userId,loc,wardid);
	}
	
	public static void getOBSItem(String admId,String date, String time,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","GetOBSItems",admId,date,time);
	}
	public static void saveOBSItem(String admId,String date, String time,String values,String userId,AsyncHttpResponseHandler handler){
		RunServerMethod.Get(handler,"App.Nure.API","ExeOBS",admId,date,time,values,userId);
	}
	public static void GetOBSLineData(String admId,String obsId,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","GetOBSLineData",admId,obsId);
	}
	
	public static void GetWardMessage(String locid,String bgdate,String eddate,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","QueryShRecord",locid,bgdate,eddate);
	}
	//查出专业组当日输液
	public static void GetGetAllPatOrd(String locId ,String bedGroupId,String grpCode ,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","GetAllPatOrd", locId , bedGroupId, grpCode );
	}
	
	//查出当日输液
	public static void GetGetOrd(String admId ,AsyncHttpResponseHandler handler){
			RunServerMethod.GetRunQuery(handler,"App.Nure.API","GetOrd", admId );
		}
	//查出注射用药
	public static void GetInjectOrd(String admId ,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","GetInjectOrd", admId );
	}

	//查出病人有几管血
	public static void GetGetLabNo(String admId ,AsyncHttpResponseHandler handler){
			RunServerMethod.GetRunQuery(handler,"App.Nure.API","GetLabNo", admId );
		}
	//查询病人所有口服发药
	public static void GetGetPhaOrd(String admId ,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","GetPhaOrd", admId );
	}
	
	//查询病人所有口服发药
	public static void GetGetWHXROrd(String admId ,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","GetWHXROrd", admId );
	}
	
	//lis 
	public static void LabOritem(String barCode ,String admId,String userId,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","LabOritem", barCode,admId );
	}
	
	//包药机
	public static void KFYRelationOrd(String code ,String adm,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","KFYRelationOrd", code,adm );
	}
	
	//液体和雾化吸入
	public static void RelationOrd(String orderId ,String no,String adm,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","GetRelationOrd", orderId,no,adm );
	}
	
	//执行液体和雾化吸入
	public static void ExuteRelationOrd(String orderId ,String userId,String no,String admId,AsyncHttpResponseHandler handler){
		RunServerMethod.Get(handler,"App.Nure.API","ExcuteRelationOrd", orderId,no,userId, admId);
	}
	//包药机
	public static void ExuteKFY(String code ,String userId,String admId,AsyncHttpResponseHandler handler){
		RunServerMethod.Get(handler,"App.Nure.API","ExuteKFY", code,userId,admId );
	}
	
	//lis执行
	public static void ExuteLabOritem(String barCode,String admId,String userId,AsyncHttpResponseHandler handler){
		RunServerMethod.Get(handler,"App.Nure.API","ExuteLab", barCode,admId,userId );
	}
	
	public static void GetBarInfo(String admId,String barcode,String type ,AsyncHttpResponseHandler handler){
		RunServerMethod.Get(handler,"App.Nure.API","GetBarInfo",admId,barcode,type);
	}
	
	//报告
	public static void ExameReportOrder(String admId,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","QueryStudyByPaadmDR", admId );
	}
	public static void ExameGetReport(String ordId,AsyncHttpResponseHandler handler){
		RunServerMethod.Get(handler,"App.Nure.API","GetReport", ordId );
	}
	
	public static void GetApplyRetrun(String regNo , String admId , String displocrowid , String userlocid ,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","GetDispItms", regNo,admId, displocrowid,userlocid);
	}
	public static void GetPhaLoc(String hospid, AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","PhaLoc",hospid);
	}
	public static void GetAppRtNo(AsyncHttpResponseHandler handler){
		RunServerMethod.Get(handler,"App.Nure.API","GetAppRtNo");
	}
	
	public static void ApplyRt(String oedis , String prescno ,String pharowid ,String reqqty ,String reason ,String userid ,String locid, AsyncHttpResponseHandler handler){
		RunServerMethod.Get(handler,"App.Nure.API","ApplyRt",oedis,prescno,pharowid,reqqty,reason,userid,locid);
	}
	public static void GetRetReason(AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","GetRetReason");
	}
	
	public static void OrdEnterBase(AsyncHttpResponseHandler handler){
		RunServerMethod.Get(handler,"App.Nure.API","OrdEnterBase");
	}
	
	public static void LookUpItem(String code,String group,String admId ,String userId,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","LookUpItem",code,group,admId);
	}
	
	public static void GetArcDetial(String admId,String code,String dep ,AsyncHttpResponseHandler handler){
		RunServerMethod.Get(handler,"App.Nure.API","GetArcDetial",admId,code,dep);
	}
	
	public static void EnterOrder(String admId,String ordItemStr,String userId,String dep ,AsyncHttpResponseHandler handler){
		RunServerMethod.Post(handler,"App.Nure.API","EnterOrder",admId,ordItemStr,userId,dep);
	}
	
	public static void GetBedGroup(String ctlocId ,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","BedGroup",ctlocId);
	}
	
	public static void PatAdmEvent(String admId ,AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","PatAdmEvent",admId);
	}
	
	public static void ExeEvent(String adm,String userId,String type,String date,String time,AsyncHttpResponseHandler handler){
		RunServerMethod.Get(handler,"App.Nure.API","ExeEvent",adm,userId,type,date,time);
	}
	
	public static void QTRECTYP(AsyncHttpResponseHandler handler){
		RunServerMethod.GetRunQuery(handler,"App.Nure.API","QTRECTYP");
	}
	
	public static void DeleteEvent(String eventId,AsyncHttpResponseHandler handler){
		RunServerMethod.Get(handler,"App.Nure.API","DeleteEvent",eventId);
	}
	
	public static void SetSkinTest(String orderId,String userId,String flag,AsyncHttpResponseHandler handler){
		RunServerMethod.Get(handler,"App.Nure.API","SetSkinTest",orderId,userId,flag);
	}
	
}
