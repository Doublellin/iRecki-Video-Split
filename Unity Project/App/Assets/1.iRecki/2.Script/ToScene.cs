using UnityEngine;
using System.Collections;

public class ToScene : MonoBehaviour {

	public enum Scene 
	{
		FirstPage,
		MainPage,
		ChooseLocationPage,
		ChooseTimePage,
		ChooseModePage,
		UploadPage,
		ChooseImagePage,
		BuyPage,
		SignUpPage,
		SignInPage,
		MySpacePage,
		PointHistoryPage
	};

	public Scene SceneName;

	public bool useClick = true;
	public bool useBack;

	void OnClick () {
		if(useClick){
			Application.LoadLevel(SceneName.ToString());
		}
	}

	void Update () {
		if(useBack){
			if(Input.GetKeyDown(KeyCode.Escape)){
				Application.LoadLevel(SceneName.ToString());
			}
		}
	}
}
