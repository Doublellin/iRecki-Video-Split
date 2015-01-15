using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;

public class ChooseTimePage : MonoBehaviour {

	public UIPopupList startYear;
	public UIPopupList startMonth;
	public UIPopupList startDay;
	public UIPopupList starTime;
	public UIPopupList starAMPM;
	public UIPopupList endTime;
	public UIPopupList endAMPM;

	public GameObject [] mode;

	public static string YEAR, MONTH, DAY, START_Time = "0", END_Time = "1", ID = "1";

	public GameObject nextButton;

	public static List<bool> canNotChoose = new List<bool>();

	public UILabel title;

	bool isTimeChooseError = false;

	void Awake(){
		ChooseTable.CHOOSE_ID_LIST.Clear ();
		UIEventListener.Get (nextButton).onClick = NextButton;

		startYear.value = "" + DateTime.Now.Year;
		startMonth.value = "" + DateTime.Now.Month;
		startDay.value = "" + DateTime.Now.Day;

		starAMPM.value = (DateTime.Now.AddHours(0).Hour > 12) ? "下午" : "上午";
		endAMPM.value = (DateTime.Now.AddHours(1).Hour > 12) ? "下午" : "上午";
		starTime.value = (DateTime.Now.AddHours(0).Hour > 12 ? DateTime.Now.AddHours(0).Hour-12 : DateTime.Now.AddHours(0).Hour)+ "";
		endTime.value = (DateTime.Now.AddHours(1).Hour > 12 ? DateTime.Now.AddHours(1).Hour-12 : DateTime.Now.AddHours(1).Hour)+ "";

		startYearTemp = startYear.value;
		startMonthTemp = startMonth.value;
		startDayTemp = startDay.value;
		starAMPMTemp = starAMPM.value;
		endAMPMTemp = endAMPM.value;
		starTimeTemp = starTime.value;
		endTimeTemp = endTime.value;

		startYear.AddItem ("" + DateTime.Now.AddYears(0).Year);
		startYear.AddItem ("" + DateTime.Now.AddYears(1).Year);
		startYear.AddItem ("" + DateTime.Now.AddYears(2).Year);
		startYear.AddItem ("" + DateTime.Now.AddYears(3).Year);
		startYear.AddItem ("" + DateTime.Now.AddYears(4).Year);
		startYear.AddItem ("" + DateTime.Now.AddYears(5).Year);
		startYear.AddItem ("" + DateTime.Now.AddYears(6).Year);

		for(int m = 1; m <= 12; m++){
			startMonth.AddItem("" + m);
		}
		for(int d = 1; d <= 31; d++){
			startDay.AddItem("" + d);
		}

		StartCoroutine(GetTimeIDStatus());
	}

	void NextButton(GameObject obj){
		if(ChooseTable.CHOOSE_ID_LIST.Count > 0){
			if(isTimeChooseError == false){
				Application.LoadLevel("UploadPage");
			}
		}
	}

	void Start () {
		string location = ChooseLocationPage.chooseLocation;
		CloseAllMode ();
		if(location == "Taipei"){
			mode[0].SetActive(true);
		}else if(location == "Hsinchu"){
			mode[1].SetActive(true);
		}
	}

	void CloseAllMode(){
		for(int i = 0; i < mode.Length; i++){
			mode[i].SetActive(false);
		}
	}

	void Update () {
		YEAR = startYear.value;
		MONTH = startMonth.value;
		DAY = startDay.value;
		string msg = "";
		foreach (string s in ChooseTable.CHOOSE_ID_LIST){
			msg += s + ",";
		}
		ID = msg;

		ChooseEvent ();
		Check ();
	}

	void Check(){

		int starTime2;
		if(starAMPM.value == "下午"){
			starTime2 = int.Parse(starTime.value) + 12;
		}else{
			starTime2 = int.Parse(starTime.value);
		}
		int endTime2;
		if(endAMPM.value == "下午"){
			endTime2 = int.Parse(endTime.value) + 12;
		}else{
			endTime2 = int.Parse(endTime.value);
		}

		string chooseStartDate = startYear.value + "-" + startMonth.value + "-" + startDay.value + " " + starTime2 + ":00";
		string chooseEndDate = startYear.value + "-" + startMonth.value + "-" + startDay.value + " " + endTime2 + ":00";

		int TimeStatus = DateTime.Compare (DateTime.Parse (chooseStartDate), DateTime.Now.AddHours(-1));
		int TimeEndStatus = DateTime.Compare (DateTime.Parse (chooseEndDate), DateTime.Parse (chooseStartDate).AddHours(1));

		title.text = "選擇時間";
		isTimeChooseError = false;

		if(TimeStatus == -1){
			title.text = "[FF0000]不可以選擇過去的時間";
			isTimeChooseError = true;
		}
		if(TimeEndStatus == -1){
			isTimeChooseError = true;
			if(title.text == "[FF0000]不可以選擇過去的時間"){
				title.text += "\n[FF0000]開始與結束時間錯誤";
			}else{
				title.text = "[FF0000]開始與結束時間錯誤";
			}
		}
	}


	string startYearTemp, startMonthTemp, startDayTemp, starAMPMTemp, endAMPMTemp, starTimeTemp, endTimeTemp;

	void ChooseEvent(){

		if(startYearTemp != startYear.value){
			startMonth.value = "" + DateTime.Now.Month;
			startDay.value = "" + DateTime.Now.Day;
			StartCoroutine(GetTimeIDStatus());
		}

		if(startMonthTemp != startMonth.value){
			startDay.Clear();
			int day = 1;
			while(true){
				try{
					DateTime.Parse(startYear.value+"-"+startMonth.value+"-"+day);
					startDay.AddItem("" + day);
					day++;
				}catch(Exception e){
					day--;
					break;
				}
			}
			startDay.value = "1";
			StartCoroutine(GetTimeIDStatus());
		}

		if(startDayTemp != startDay.value){
			StartCoroutine(GetTimeIDStatus());
		}
		if(starAMPMTemp != starAMPM.value){
			StartCoroutine(GetTimeIDStatus());
		}
		if(endAMPMTemp != endAMPM.value){
			StartCoroutine(GetTimeIDStatus());
		}
		if(starTimeTemp != starTime.value){
			StartCoroutine(GetTimeIDStatus());
		}
		if(endTimeTemp != endTime.value){
			StartCoroutine(GetTimeIDStatus());
		}

		startYearTemp = startYear.value;
		startMonthTemp = startMonth.value;
		startDayTemp = startDay.value;
		starAMPMTemp = starAMPM.value;
		endAMPMTemp = endAMPM.value;
		starTimeTemp = starTime.value;
		endTimeTemp = endTime.value;
	}


	IEnumerator GetTimeIDStatus(){

		if(ChooseLocationPage.chooseLocation != null){

			int StartPMTime = 0;
			int EndPMTime = 0;

			if(starAMPM.value == "下午"){
				StartPMTime = int.Parse(starTime.value) + 12;
			}else{
				StartPMTime = int.Parse(starTime.value);
			}

			if(endAMPM.value == "下午"){
				EndPMTime = int.Parse(endTime.value) + 12;
			}else{
				EndPMTime = int.Parse(endTime.value);
			}

			START_Time = StartPMTime + "";
			END_Time = EndPMTime + "";
			
			WWWForm f = new WWWForm ();
			f.AddField ("day", startYear.value+"-"+startMonth.value+"-"+startDay.value);
			f.AddField ("start_time", StartPMTime + ":00");
			f.AddField ("end_time", EndPMTime + ":00");
			f.AddField ("location", ChooseLocationPage.chooseLocation);
			WWW w = new WWW ("http://" + UploadPage.serverIP + ":" + UploadPage.serverPort+"/GetTimeIDStatus", f);
			yield return w;
			canNotChoose.Clear();
			foreach(string s in w.text.Split(',')){
				canNotChoose.Add(bool.Parse(s));
			}

//			foreach(bool b in canNotChoose){
//				print (canNotChoose.Count+" : "+b);
//			}
		}
	}

//	void Time(){
//		int i = UIPopupList.current.items.IndexOf (UIPopupList.current.value);
//		START_Time = i.ToString();
//		END_Time = (i + 1).ToString();
////		print (UIPopupList.current.items.IndexOf(UIPopupList.current.value));
//	}
}
