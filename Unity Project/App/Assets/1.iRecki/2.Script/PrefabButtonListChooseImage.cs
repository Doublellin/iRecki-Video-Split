using UnityEngine;
using System.Collections;

public class PrefabButtonListChooseImage : MonoBehaviour {

	public GameObject button_ChooseImage;
	public UILabel path;

	void Start () {
		UIEventListener.Get (button_ChooseImage).onClick = Button_ChooseImage;
	}

	void Button_ChooseImage(GameObject obj){
		ChooseImagePage.PATH_IMAGE.Remove (path.text);
		Application.LoadLevel("ChooseImagePage");
	}

}


