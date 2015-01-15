using UnityEngine;
using System.Collections;

public class ChooseModePage : MonoBehaviour {

	public static int MODE = 0;

	public Texture2D [] t;
	public UITexture target;
	public UIPopupList p;

	void Update () {
		if(p.value == "指定平板播放"){
			target.mainTexture = t[0];
			MODE = 0;
		}else if(p.value == "獨立播放"){
			target.mainTexture = t[1];
			MODE = 1;
		}else if(p.value == "整體播放"){
			target.mainTexture = t[2];
			MODE = 2;
		}else if(p.value == "跳格播放"){
			target.mainTexture = t[3];
			MODE = 3;
		}

	}
}
