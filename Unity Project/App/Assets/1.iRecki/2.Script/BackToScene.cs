using UnityEngine;
using System.Collections;

public class BackToScene : MonoBehaviour {

	public string sceneName;

	void Update () {
		if(Input.GetKeyDown(KeyCode.Escape)){
			Application.LoadLevel(sceneName);
		}
	}
}
