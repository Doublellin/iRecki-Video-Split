using UnityEngine;
using System.Collections;
using System.IO;

public class ChooseLocationPage : MonoBehaviour {

	public static string chooseLocation;

	public UIPopupList p;

	int fileMaxSize = 200 * 1000 * 1000; // Video Max Size = 200 MB
	
	public GameObject error;

	void Awake () {
#if !UNITY_EDITOR
		if(MainPage.PATH_VIDEO != ""){
			if(new FileStream(MainPage.PATH_VIDEO, FileMode.Open).Length < fileMaxSize){
				error.SetActive(false);
			}else{
				error.SetActive(true);
			}
		}
#endif
	}

	public GameObject [] mode;

	void Update () {

		CloseAllMode();

		if(p.value == "臺北"){
			mode[0].SetActive(true);
			chooseLocation = "Taipei";
		}else if(p.value == "新竹"){
			mode[1].SetActive(true);
			chooseLocation = "Hsinchu";
		}

	}

	void CloseAllMode(){
		for(int i = 0; i < mode.Length; i++){
			mode[i].SetActive(false);
		}
	}

	void OnGUI(){
		GUILayout.Label ("Msg : " + p.value);
	}
}
