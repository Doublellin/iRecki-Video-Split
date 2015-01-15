using UnityEngine;
using System.Collections;

public class SignInPage : MonoBehaviour {

	public UIInput id;
	public UIInput password;
	public GameObject buttonSignIn;

	public static string ID = "";
	
	void Awake () {
		UIEventListener.Get (buttonSignIn).onClick = ButtonSignIn;
	}
	
	void ButtonSignIn(GameObject obj){
		StartCoroutine (SignIn ());
	}
	
	IEnumerator SignIn(){
		string tempID = id.value;

		WWWForm f = new WWWForm ();
		f.AddField ("id", tempID);
		f.AddField ("password", password.value);
		f.AddField ("IsFacebookUser", "false");
		WWW w = new WWW ("http://"+UploadPage.serverIP + ":" + UploadPage.serverPort + "/SignIn", f);
		yield return w;
		
		if(w.text == "pass"){
			FirstPage.FB_ID = "";
			ID = tempID;
			Application.LoadLevel("MainPage");
		}
		w.Dispose();
	}
	
	void Update () {
		
	}
}
