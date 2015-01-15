using UnityEngine;
using System.Collections;

public class SetLocation : MonoBehaviour {

	public GameObject next;

	public UIPopupList p;

	public static string Location = "Hsinchu";

	// Use this for initialization
	void Start () {
		UIEventListener.Get (next).onClick = Next;

		 // "/mnt/sdcard/1.jpg"
	}

	void Next(GameObject obj){
		Application.LoadLevel ("SetTablePosition");
	}
	
	// Update is called once per frame
	void Update () {

		if(p.value == "台北市"){
			Location = "Taipei";
		}else if(p.value == "新竹市"){
			Location = "Hsinchu";
		}


	}
}
