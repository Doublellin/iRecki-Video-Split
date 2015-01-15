using UnityEngine;
using System.Collections;

public class SetMode : MonoBehaviour {

	public GameObject next;
	
	// Use this for initialization
	void Start () {
		UIEventListener.Get (next).onClick = Next;
	}
	
	void Next(GameObject obj){
		Application.LoadLevel ("SetTablePosition");
	}

	
	// Update is called once per frame
	void Update () {
	
	}
}
