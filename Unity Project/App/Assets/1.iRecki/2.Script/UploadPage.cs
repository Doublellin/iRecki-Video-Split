using UnityEngine;
using System.Collections;
using System.Collections.Specialized;
using System.IO;
using System.Threading;
using System.Net;
using System;

public class UploadPage : MonoBehaviour {

	public static string serverIP = "219.85.221.13";
//	public static string serverIP = "192.168.1.103";
//		public static string serverIP = "54.69.109.145";
	public static string serverPort = "8080";

	public UILabel uploadNumber;
	public GameObject buttonUpload;
	public GameObject noPoint;
	public UILabel point;
	public UILabel uiP;
	public UILabel uiSpeed;


	string serverURL = "";
	string status = "wait";	
	string w = "";
	string x = "";
	int uploadCount = 0;
	int uploadCount2 = 0;
	int fileLength = 0;
	int uploadSpeedKB = 0;

	void Awake () {
		UIEventListener.Get (buttonUpload).onClick = ButtonUpload;
		point.text = "1";
		noPoint.SetActive(false);
		string id, isFBuser;
		
		if(FirstPage.FB_ID != ""){
			id = FirstPage.FB_ID;
			isFBuser = "true";
		}else{
			id = SignInPage.ID;
			isFBuser = "false";
		}

		serverURL = "http://" + serverIP + ":" + serverPort + "/UploadFile?" +
				"isFBUser="+isFBuser + "&" +
				"id=" + id + "&" +
				"location=" + ChooseLocationPage.chooseLocation + "&" +
				"playMode=" + ChooseModePage.MODE+ "&" +
				"year=" + ChooseTimePage.YEAR +"&" +
				"month=" + ChooseTimePage.MONTH +"&" +
				"day=" + ChooseTimePage.DAY +"&" +
				"startHour=" + ChooseTimePage.START_Time +"&" +
				"endHour="+ ChooseTimePage.END_Time + "&" +
				"chooseTableID="+ChooseTimePage.ID;

		uiP.gameObject.SetActive (false);
		uiSpeed.gameObject.SetActive (false);
	}

	void ButtonUpload(GameObject obj){
		if(MainPage.POINT > 0){
			status = "upload";
			new Thread (UploadFilesToRemoteUrl).Start();
			InvokeRepeating("UploadSpeedControl", 0, 1f);
			uiP.text = "00.0";
			uiSpeed.text = "0";
			uiP.gameObject.SetActive (true);
			uiSpeed.gameObject.SetActive (true);
		}else{
			noPoint.SetActive(true);
		}
		uploadNumber.GetComponent<UIPanel> ().Refresh ();
	}

	IEnumerator UP(){

		string fileUrl;

		if (MainPage.PATH_IMAGE != "") {
			fileUrl = MainPage.PATH_IMAGE;
		}else{
			fileUrl = MainPage.PATH_VIDEO;
		}

		FileStream fs = new FileStream (fileUrl, FileMode.Open, FileAccess.Read);
		byte [] b = new byte[fs.Length];
		fs.Read (b, 0, b.Length);

		WWWForm f = new WWWForm ();
		f.AddBinaryData ("file", b);

		WWW ww = new WWW (serverURL, f);

		status = "upload";

		yield return ww;

		w = ww.error;

		status = "finish";

	}

	void Update () {
		if(status == "wait"){
			uploadNumber.text = "請按上傳鍵";
		}else if(status == "upload"){
			if(uploadCount != 0 && fileLength != 0){
				float p = ((float)uploadCount/(float)fileLength) * 100;
				x = uploadCount + " , " + fileLength + " , " + p + " , " + p.ToString("0.0");
				uploadNumber.text = "上傳中... ";
				uiP.text = p.ToString("00.0");
				uiSpeed.text = "" + uploadSpeedKB;
			}
		}else if(status == "finish"){
			uploadNumber.text = "上傳完成";
			uiP.gameObject.SetActive (false);
			uiSpeed.gameObject.SetActive (false);
		}else if(status == "error"){
			uploadNumber.text = "上傳失敗";
		}
	}

	void UploadSpeedControl(){
		uploadSpeedKB = (uploadCount-uploadCount2)/1000;
		uploadCount2 = uploadCount;
	}

	void UploadFilesToRemoteUrl(){
		if (MainPage.PATH_VIDEO != "") {
			UploadVideo();
		}else{
			StartCoroutine(UploadImg());
		}
	}

	void UploadVideo(){
		try{
			status = "upload";
			string fileUrl = MainPage.PATH_VIDEO;			
			string url = serverURL + "&type=Video";
			string file = fileUrl;
			string boundary = "----------------------------" + DateTime.Now.Ticks.ToString("x");

			HttpWebRequest httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
			httpWebRequest.ContentType = "multipart/form-data; boundary=" + boundary;
			httpWebRequest.Method = "POST";
			httpWebRequest.KeepAlive = true;			
			httpWebRequest.Credentials = CredentialCache.DefaultCredentials;
			httpWebRequest.AllowWriteStreamBuffering = false;

			byte[] boundarybytes = System.Text.Encoding.ASCII.GetBytes("\r\n--" + boundary + "\r\n");
			string headerTemplate = "Content-Disposition: form-data; name=\"{0}\";filename=\"{1}\"\r\n Content-Type: application/octet-stream\r\n\r\n";
			string header = string.Format(headerTemplate, "file", file);
			byte[] headerbytes = System.Text.Encoding.UTF8.GetBytes(header);
			httpWebRequest.ContentLength = new FileInfo(file).Length + headerbytes.Length + (boundarybytes.Length * 2) + 2;
			Stream requestStream = httpWebRequest.GetRequestStream();
			requestStream.Write(boundarybytes, 0, boundarybytes.Length);
			requestStream.Write(headerbytes, 0, headerbytes.Length);
			FileStream fileStream = new FileStream(file, FileMode.Open, FileAccess.Read);
			byte[] buffer = new byte[4096];
			int bytesRead = 0;
			uploadCount = 0;
			fileLength = (int) fileStream.Length;
			while ((bytesRead = fileStream.Read(buffer, 0, buffer.Length)) != 0){
				requestStream.Write(buffer, 0, bytesRead);
				requestStream.Flush();
				uploadCount+=buffer.Length;
			}
			boundarybytes = System.Text.Encoding.ASCII.GetBytes("\r\n--" + boundary + "--\r\n");
			requestStream.Write(boundarybytes, 0, boundarybytes.Length);
			requestStream.Close();
			fileStream.Close();
			status = "finish";
		}catch(Exception e){
			status = "error";
		}
	}

	IEnumerator UploadImg(){

		status = "upload";

		WWWForm wf = new WWWForm ();
		int i = 1;

		foreach(string path in ChooseImagePage.PATH_IMAGE){
			FileStream fs = new FileStream(path, FileMode.Open);
			byte [] b = new byte[fs.Length];
			fs.Read(b, 0, b.Length);
			wf.AddBinaryData("file"+i, b, WWW.EscapeURL(path));
			i++;
		}

		WWW w = new WWW (serverURL + "&type=Image", wf);
		yield return w;
		if(w.text == "pass"){
			uploadNumber.text = "上傳完成";
		}else if(w.text == "error"){
			uploadNumber.text = "上傳錯誤 : " + w.error;
		}
		status = "finish";
	}

	void OnGUI(){
		GUILayout.Label (""+uploadCount);
		GUILayout.Label (x);
		GUILayout.Label (ChooseTimePage.START_Time);
		GUILayout.Label (ChooseTimePage.END_Time);
	}

}
