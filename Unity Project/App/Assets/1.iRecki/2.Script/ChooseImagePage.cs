using UnityEngine;
using System.Collections;
using AndroidMediaBrowser;
using System.Collections.Generic;
using System.IO;

public class ChooseImagePage : MonoBehaviour {

	public GameObject prefab_Button_List_ChooseImage;
	public GameObject button_ChooseMore;

	public UIScrollView sv; 

	public static HashSet <string> PATH_IMAGE = new HashSet<string>();

	static bool sw = false;

	int fileMaxSize = 1000 * 1000; // 1 MB

	public GameObject error;

	static string path = "";

	public GameObject buttonErrorOk;

	void Awake () {
		if(path != ""){
//			PATH_IMAGE.Add(path);
			StartCoroutine(CheckFile2());
		}

		UIEventListener.Get (buttonErrorOk).onClick = ButtonErrorOk;
		UIEventListener.Get (button_ChooseMore).onClick = Button_ChooseMore;

		ImageBrowser.OnPicked += (image) =>	{
			if(sw){
				sw = false;
				path = image.Path;
//				isCheck = true;
//				step = "1";
//				
//				step = "E";
//				if(new FileStream(image.Path, FileMode.Open).Length < fileMaxSize){
//					PATH_IMAGE.Add(image.Path);
					Application.LoadLevel("ChooseImagePage");
//				}else{
//					error.SetActive(true);
//				}

			}
		};
		
	}

	void ButtonErrorOk(GameObject obj){
		error.SetActive(false);
	}

	bool isCheck;

	IEnumerator CheckFile2(){
//		sw = true;
		WWW w = new WWW ("file://" + path);

		yield return w;
		string errMsg = w.error;
		w.Dispose ();
		if(errMsg == "" || errMsg == null){
			if(new FileStream(path, FileMode.Open).Length < fileMaxSize){
				PATH_IMAGE.Add(path);
				path = "";
				Application.LoadLevel("ChooseImagePage");
			}else{
				error.SetActive(true);
			}
		}else{
			error.SetActive(true);
		}
	}



	void Start(){

		foreach(string path in PATH_IMAGE){
			GameObject g = Instantiate (prefab_Button_List_ChooseImage) as GameObject;
			Transform t = g.transform;
			t.parent = sv.transform;
			t.localScale = Vector3.one;
			t.localPosition = new Vector3 (142, y , 0);
			t.GetComponent<PrefabButtonListChooseImage>().path.text = path;
			y-= 60;
		}
	}

	int y = 190;

	void Button_ChooseMore(GameObject obj){
		sw = true;
		ImageBrowser.Pick();
	}



	void Update () {
		if(isCheck){
			isCheck = false;
			StartCoroutine(CheckFile2());
		}
	}

	string step = "0";

	void OnGUI(){
		GUILayout.Label (step);
		GUILayout.Label (""+sw);

		foreach(string s in PATH_IMAGE){
			GUILayout.Label("# " + s);
		}
	}


}
