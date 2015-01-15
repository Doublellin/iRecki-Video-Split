using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class ChooseTable : MonoBehaviour {

	public static string [] id;

	void Start () {
		v.SetActive(false);
		x.SetActive(false);
	}

	bool isChoose = false;

	public GameObject v;
	public GameObject x;

	public static HashSet<string> CHOOSE_ID_LIST = new HashSet<string>();

	void Update(){
		if(ChooseTimePage.canNotChoose.Count > 0){
			if(ChooseTimePage.canNotChoose[int.Parse(name)-1] == false){
				x.SetActive(false);
			}else{
				CHOOSE_ID_LIST.Clear();
				x.SetActive(true);
				v.SetActive(false);
				isChoose = false;
			}
		}
	}

	void OnClick () {
		if(ChooseTimePage.canNotChoose.Count > 0){
			if(ChooseTimePage.canNotChoose[int.Parse(name)-1] == false){
				isChoose = !isChoose;			
				if(isChoose){
					v.SetActive(true);
					CHOOSE_ID_LIST.Add(name);
				}else{
					v.SetActive(false);
					CHOOSE_ID_LIST.Remove(name);
				}
			}
		}
	}
}
