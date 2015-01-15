using UnityEngine;
using System.Collections;
using AndroidMediaBrowser;

public class MainPage : MonoBehaviour {

	public UILabel userName;
	public UITexture userImage;

//	public GameObject buttonImage;
	public GameObject buttonVideo;

	public static string PATH_IMAGE = "";
	public static string PATH_VIDEO = "";

	public UILabel point;
	public static int POINT;



	void Awake () {

		StartCoroutine (GetPoint());
		PATH_VIDEO = "";
		ChooseImagePage.PATH_IMAGE.Clear ();

		userName.text = FirstPage.FB_USER_NAME;
		userImage.mainTexture = FirstPage.FB_USER_IMAGE;

		UIEventListener.Get (buttonVideo).onClick = ButtonVideo;

		#if UNITY_ANDROID
		VideoBrowser.OnPicked += (video) =>	{
			PATH_VIDEO = video.Path;
			Application.LoadLevel("ChooseLocationPage");
		};
		#endif
	}
	
	void ButtonVideo(GameObject obj){
		#if UNITY_ANDROID
		VideoBrowser.Pick();
		#endif
	}



	IEnumerator GetPoint(){

		string id, isFBuser;

		if(FirstPage.FB_ID != ""){
			id = FirstPage.FB_ID;
			isFBuser = "true";
		}else{
			id = SignInPage.ID;
			isFBuser = "false";
		}

		WWWForm f = new WWWForm ();
		f.AddField ("id", id);
		f.AddField ("IsFacebookUser", isFBuser);
		WWW w = new WWW ("http://" + UploadPage.serverIP + ":" + UploadPage.serverPort+"/GetPoint", f);
		yield return w;
		point.text = w.text;
		POINT = int.Parse (w.text);
	}

	void OnGUI(){
		GUILayout.Label (UploadPage.serverIP + ":" + UploadPage.serverPort);
	}

}
