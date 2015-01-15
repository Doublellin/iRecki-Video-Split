using UnityEngine;
using System.Collections;

public class SignUpPage : MonoBehaviour {

	public UIInput userName;
	public UIInput password;
	public UIInput name;
	public GameObject buttonSignUp;

	void Awake () {
		UIEventListener.Get (buttonSignUp).onClick = ButtonSignUp;
	}

	void ButtonSignUp(GameObject obj){
		StartCoroutine (SignUp ());
	}

	IEnumerator SignUp(){
		WWWForm f = new WWWForm ();
		f.AddField ("user_name", userName.value);
		f.AddField ("name", name.value);
		f.AddField ("password", password.value);
		f.AddField ("IsFacebookUser", "false");
		WWW w = new WWW ("http://"+UploadPage.serverIP + ":" + UploadPage.serverPort + "/SignUp", f);
		yield return w;

		if(w.text == "pass"){
			Application.LoadLevel("SignUpFinish");
		}
		w.Dispose();
	}

	void Update () {

	}
}
