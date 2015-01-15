using UnityEngine;
using System.Collections;
using System;
using System.Net;
using System.Net.Sockets;
using System.IO;
using System.Threading;

public class Main : MonoBehaviour {

	public Camera mainCmera;
	string ServerURL = "";

	void Awake(){
		ServerURL = "http://"+ServerIP+":"+HttpServerPort;
	}

	void Start () {

		StartCoroutine(Download());
		InvokeRepeating("UploadCameraImage", 0, 1);
		InvokeRepeating("Refresh", 0, 5);

		new Thread (Ready).Start ();

		print (SetTablePosition.choose);
	}

	int nowImage = 0;
	int nowImage2 = 0;

	void ChangeImage(){

		if(SetLocation.Location == "Taipei"){
			if(status == 0){
				nowImage = nowImage2;
				mainCmera.orthographicSize = 0.14f;
				if(SetTablePosition.choose == "1"){
					mainCmera.transform.position = new Vector3(-0.2439658f,0,-10);
				}else if(SetTablePosition.choose == "2"){
					mainCmera.transform.position = new Vector3(0.2439658f,0,-10);
				}
				nowImage += 1;
			}else if(status == 1 || status == 2){
				if(SetTablePosition.choose == "1"){
					nowImage += 1;
				}else if(SetTablePosition.choose == "2"){
					nowImage += 2;
				}
				mainCmera.orthographicSize = 0.28f;
				mainCmera.transform.position = new Vector3(0,0,-10);
			}
		}else if(SetLocation.Location == "Hsinchu"){
			if(status == 0){
				nowImage = nowImage2;
				mainCmera.orthographicSize = 0.14f;
				if(SetTablePosition.choose == "1"){
					mainCmera.transform.position = new Vector3(-0.2439658f,0.14f,-10);
				}else if(SetTablePosition.choose == "2"){
					mainCmera.transform.position = new Vector3(0.2439658f,0.14f,-10);
				}else if(SetTablePosition.choose == "3"){
					mainCmera.transform.position = new Vector3(-0.2439658f,-0.14f,-10);
				}else if(SetTablePosition.choose == "4"){
					mainCmera.transform.position = new Vector3(0.2439658f,-0.14f,-10);
				}
				nowImage += 1;
			}else if(status == 1 || status == 2){
				if(SetTablePosition.choose == "1"){
					nowImage += 1;
				}else if(SetTablePosition.choose == "2"){
					nowImage += 2;
				}else if(SetTablePosition.choose == "3"){
					nowImage += 3;
				}else if(SetTablePosition.choose == "4"){
					nowImage += 4;
				}	
				mainCmera.orthographicSize = 0.28f;
				mainCmera.transform.position = new Vector3(0,0,-10);
				
			}
		}

		nowImage2++;
		status++;

		if(status > 2) status = 0;
		if(nowImage >= temp.Length) nowImage = 0;
		if(nowImage2 >= temp.Length) nowImage2 = 0;

		ScreenImageCamera.transform.position = mainCmera.transform.position;
		ScreenImageCamera.orthographicSize = mainCmera.orthographicSize;
		
		g.renderer.material.mainTexture = temp[nowImage];
		if(temp[nowImage].width > temp[nowImage].height){
			g.transform.localScale = new Vector3 ((float)temp[nowImage].width/(float)temp[nowImage].height, 1);
		}else{
			g.transform.localScale = new Vector3 (1, (float)temp[nowImage].height/(float)temp[nowImage].width);
		}
	}
	
	static string playNow = "";

	void Refresh(){
		StartCoroutine(Refresh2());
	}

	IEnumerator Refresh2(){
		WWW www = new WWW (ServerURL+"/PlayNow?choose_table_id="+SetTablePosition.choose);
		yield return www;
		if(www.text != playNow){
			Application.LoadLevel("Main");
		}		
		playNow = www.text;
	}

	bool canUploadCameraImage = true;

	void UploadCameraImage(){
		if(canUploadCameraImage){
			StartCoroutine(UploadCameraImage2());
		}
	}

	IEnumerator UploadCameraImage2(){
		if(c!=null){
			canUploadCameraImage = false;

			Texture2D t = new Texture2D(c.width, c.height);
			t.SetPixels(c.GetPixels());
			t.Apply();

			WWWForm f = new WWWForm ();
			f.AddBinaryData ("file", t.EncodeToJPG(), "1.jpg");
			f.AddField ("id", SetTablePosition.choose);
			f.AddField ("location", SetLocation.Location);
			WWW w = new WWW (ServerURL + "/UploadCameraImage" , f);
			yield return w;
			w.Dispose ();
			GameObject.Destroy (t);

			canUploadCameraImage = true;
		}
	}

	WebCamTexture c;

	void OnEnable() {
#if !UNITY_EDITOR
		c = new WebCamTexture (WebCamTexture.devices[1].name, 80,60, 1);
		c.Play ();
#endif
	}
	


	bool sw = false;

	void Update(){

		if(canPlay){
			canPlay = false;
			if(encode == "mp4"){
				InvokeRepeating ("Run",0,5);
			}
			InvokeRepeating("UploadScreenImage", 0, 1);
			s5 = "Is Play";
		}



		if(canPlay2){
			gg.GetComponent<MediaPlayerCtrl> ().Play ();
		}

		if(canPlay3){
			canPlay3 = false;
			InvokeRepeating("ChangeImage", 0, 2);
		}



		if(Input.GetKeyDown(KeyCode.Escape)){
			Application.LoadLevel("SetTablePosition");
		}
	}

	public Camera ScreenImageCamera;

	public RenderTexture rt;

	void UploadScreenImage(){
		if(canUpload){
			StartCoroutine (UploadScreenImage2());
//			UploadScreenImage2();
		}
	}

	bool canUpload = true;

	IEnumerator UploadScreenImage2(){
		canUpload = false;
		RenderTexture.active = rt;
		Texture2D t2d = new Texture2D(rt.width, rt.height);
		t2d.ReadPixels(new Rect(0, 0, rt.width, rt.height), 0, 0);
		t2d.Apply();


//		Application.CaptureScreenshot ("1.png");

		WWWForm f = new WWWForm ();
		f.AddBinaryData("file", t2d.EncodeToJPG ());
		f.AddField ("id", SetTablePosition.choose);
		f.AddField ("location", SetLocation.Location);
		WWW w = new WWW (ServerURL + "/UploadScreenImage" , f);
		yield return w;
		w.Dispose ();

		GameObject.Destroy (t2d);

		canUpload = true;
	}

	public GameObject renderTextureObj;

	bool isDownloadFinish = false;

	int status = 0;

	void Run(){

		status++;

		if(status > 1){
			status = 0;
		}

		if(SetLocation.Location == "Taipei"){
			if(status == 0){
				mainCmera.orthographicSize = 0.28f;
				mainCmera.transform.position = new Vector3(0,0,-10); 
			}else if(status == 1){
				mainCmera.orthographicSize = 0.14f;
				if(SetTablePosition.choose == "1"){
					mainCmera.transform.position = new Vector3(-0.2439658f,0,-10);
				}else if(SetTablePosition.choose == "2"){
					mainCmera.transform.position = new Vector3(0.2439658f,0,-10);
				}
			}else if(status == 2){
				
			}
		}else if(SetLocation.Location == "Hsinchu"){
			if(status == 0){

					mainCmera.orthographicSize = 0.28f;
					mainCmera.transform.position = new Vector3(0,0,-10);

			}else if(status == 1){

					mainCmera.orthographicSize = 0.14f;
					if(SetTablePosition.choose == "1"){
						mainCmera.transform.position = new Vector3(-0.2439658f,0.14f,-10);
					}else if(SetTablePosition.choose == "2"){
						mainCmera.transform.position = new Vector3(0.2439658f,0.14f,-10);
					}else if(SetTablePosition.choose == "3"){
						mainCmera.transform.position = new Vector3(-0.2439658f,-0.14f,-10);
					}else if(SetTablePosition.choose == "4"){
						mainCmera.transform.position = new Vector3(0.2439658f,-0.14f,-10);
					}

			}else if(status == 2){
				
			}
		}


		ScreenImageCamera.transform.position = mainCmera.transform.position;
		ScreenImageCamera.orthographicSize = mainCmera.orthographicSize;

	}

//	string ServerIP = "54.69.109.145";
	string ServerIP = "219.85.221.13";
	int HttpServerPort = 8080;
	int TcpServerPort = 18081;

	string path = "", encode = "", startTime = "", endTime = "";


	public GameObject m, img;

	Texture2D [] temp;

	IEnumerator Download(){

		s = "Download...";

		WWW www = new WWW (ServerURL+"/PlayNow?choose_table_id="+SetTablePosition.choose);
		yield return www;

		s = www.error;

		playNow = www.text;
		
		string [] d = www.text.Split(';');
		www.Dispose ();
		
		for(int i=0; i < d.Length-1; i++){
			path = d[0];
			encode = d[1];
			startTime = d[2];
			endTime = d[3];
		}

		String urlPath;

		if(encode == "mp4"){
			path = ServerURL + "/" + path + "/1." + encode;
			s4 = path;

			www = new WWW (path);
			yield return www;
			
			s2 = www.error;
			File.WriteAllBytes (Application.persistentDataPath +"/1.mp4" , www.bytes);
			www.Dispose ();
			s = "Save Finish";
			MediaPlayerCtrl.m_strFileName = "file://" + Application.persistentDataPath +"/1.mp4";			
			gg = Instantiate (m) as GameObject;
			gg.transform.localScale = Vector3.one;
			gg.transform.position = new Vector3 (0,0,-9.13f);
		}else{
//			if(encode.IndexOf(",") != -1){
//				print (encode);
//				if(Directory.Exists(Application.persistentDataPath+"/Temp")){
//					Directory.Delete(Application.persistentDataPath+"/Temp");
//				}
//				Directory.CreateDirectory(Application.persistentDataPath+"/Temp");
				
			string [] e = encode.Split(',');

			temp = new Texture2D[e.Length];
			s4 = "";
			for(int i=0; i < e.Length; i++){
				urlPath = ServerURL + "/" + path + "/" + ( i + 1 ) + "." + e[i];
				s4 += urlPath + "\n";
				www = new WWW (urlPath);
				yield return www;
				temp[i] = www.texture;
				www.Dispose ();
			}
//			}
			g = Instantiate (img) as GameObject;
			g.transform.position = new Vector3 (0,0,-9f);
			g.renderer.material.mainTexture = temp[0];
			s = "Save Finish";
		}				
		isDownloadFinish = true;
	}
	GameObject g;

	GameObject gg;

	bool canPlay = false;
	bool canPlay2 = false;
	bool canPlay3 = false;
//	Socket socket;

	void Ready(){
		Socket socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
		socket.Connect(ServerIP, TcpServerPort); 
		NetworkStream stream = new NetworkStream(socket);
		StreamWriter sw = new StreamWriter(stream);
		StreamReader sr = new StreamReader(stream);
		sw.AutoFlush = true;

		while(!isTcpClose){
			try{
				Thread.Sleep(1000);
			}catch(Exception e){
			}
			if(isDownloadFinish){
//				for(int i = 0; i < 3; i++){
				sw.WriteLine(SetLocation.Location+","+SetTablePosition.choose);
//				}
				break;
			}
		}

//		for(int i = 0; i < 1000; i++){
			string st = sr.ReadLine();
			s6 += st;
//		}




		canPlay = true;

		if(encode == "mp4"){
			canPlay2 = true;
		}else{
			canPlay3 = true;
		}

		sw.Close();
		stream.Close();
		socket.Close();	
		


	}

	bool isTcpClose = false;

	void OnDisable(){
		// Camera
		c.Stop ();
		GameObject.Destroy (c);
		// TCP Socket
//		isTcpClose = true;
//		if(socket != null){
//			socket.Close();
//		}
	}

	string s = "No";
	string s2 = "No";
	string s3 = "No";
	string s4 = "No";
	string s5 = "No";
	string s6 = "No ";

	public GUISkin gs;

	void OnGUI () {
		GUI.skin = gs;

		GUILayout.Label (s4);
		GUILayout.Label (s);
		GUILayout.Label (s2);
		GUILayout.Label (s3);
		GUILayout.Label (s5);
		GUILayout.Label (s6);
	}
}
