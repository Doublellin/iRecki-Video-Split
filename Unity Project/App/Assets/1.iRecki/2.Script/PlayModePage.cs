using UnityEngine;
using System.Collections;

public class PlayModePage : MonoBehaviour {

	public GameObject button1;

	public static int Mode = 0;

	void Awake(){
		UIEventListener.Get (button1).onClick = Button1;
	}

	void Button1(GameObject obj){
		Mode = 1;
		Application.LoadLevel ("UploadPage");
	}
	
	// Update is called once per frame
	void OnGUI () {
		if(MainPage.PATH_IMAGE != ""){
			GUILayout.Label ("Image : " + MainPage.PATH_IMAGE);
		}else{
			GUILayout.Label ("Video : " + MainPage.PATH_VIDEO);
		}
	}

	void Update(){
		if(Input.GetKeyDown(KeyCode.Escape)){
			Application.LoadLevel("MainPage");
		}
	}
}
