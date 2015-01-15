using UnityEngine;
using System.Collections;

public class SetTablePosition : MonoBehaviour {

	public GameObject next;

	public GameObject RootTaipei, RootHsinchu;

	public GameObject Taipei1, Taipei2;
	public GameObject Hsinchu1, Hsinchu2, Hsinchu3, Hsinchu4;
	
	// Use this for initialization
	void Start () {
		UIEventListener.Get (next).onClick = Next;

		UIEventListener.Get (Taipei1).onClick = Choose;
		UIEventListener.Get (Taipei2).onClick = Choose;

		UIEventListener.Get (Hsinchu1).onClick = Choose;
		UIEventListener.Get (Hsinchu2).onClick = Choose;
		UIEventListener.Get (Hsinchu3).onClick = Choose;
		UIEventListener.Get (Hsinchu4).onClick = Choose;

		if(SetLocation.Location == "Taipei"){
			RootTaipei.SetActive(true);
			RootHsinchu.SetActive(false);
			Taipei2.GetComponentInChildren<UILabel>().alpha = 0;
			Taipei1.GetComponent<UISprite>().color = new Color(1,0.9f,0.7f);
		}else if(SetLocation.Location == "Hsinchu"){
			RootTaipei.SetActive(false);
			RootHsinchu.SetActive(true);
			Hsinchu4.GetComponentInChildren<UILabel>().alpha = 0;
			Hsinchu3.GetComponentInChildren<UILabel>().alpha = 0;
			Hsinchu2.GetComponentInChildren<UILabel>().alpha = 0;
			Hsinchu1.GetComponent<UISprite>().color = new Color(1,0.9f,0.7f);
		}
	}

	public static string choose = "1";

	void Choose(GameObject obj){

		if(SetLocation.Location == "Taipei"){
			Taipei1.GetComponentInChildren<UILabel>().alpha = 0;
			Taipei2.GetComponentInChildren<UILabel>().alpha = 0;
			
			Taipei1.GetComponent<UISprite>().color = new Color(1,1,1);
			Taipei2.GetComponent<UISprite>().color = new Color(1,1,1);
			
			if(obj.name == "1"){
				Taipei1.GetComponentInChildren<UILabel>().alpha = 1;
				Taipei1.GetComponent<UISprite>().color = new Color(1,0.9f,0.7f);
				choose = "1";
			}else if(obj.name == "2"){
				Taipei2.GetComponentInChildren<UILabel>().alpha = 1;
				Taipei2.GetComponent<UISprite>().color = new Color(1,0.9f,0.7f);
				choose = "2";
			}
		}else if(SetLocation.Location == "Hsinchu"){
			Hsinchu1.GetComponentInChildren<UILabel>().alpha = 0;
			Hsinchu2.GetComponentInChildren<UILabel>().alpha = 0;
			Hsinchu3.GetComponentInChildren<UILabel>().alpha = 0;
			Hsinchu4.GetComponentInChildren<UILabel>().alpha = 0;
			
			Hsinchu1.GetComponent<UISprite>().color = new Color(1,1,1);
			Hsinchu2.GetComponent<UISprite>().color = new Color(1,1,1);
			Hsinchu3.GetComponent<UISprite>().color = new Color(1,1,1);
			Hsinchu4.GetComponent<UISprite>().color = new Color(1,1,1);
			
			if(obj.name == "1"){
				Hsinchu1.GetComponentInChildren<UILabel>().alpha = 1;
				Hsinchu1.GetComponent<UISprite>().color = new Color(1,0.9f,0.7f);
				choose = "1";
			}else if(obj.name == "2"){
				Hsinchu2.GetComponentInChildren<UILabel>().alpha = 1;
				Hsinchu2.GetComponent<UISprite>().color = new Color(1,0.9f,0.7f);
				choose = "2";
			}else if(obj.name == "3"){
				Hsinchu3.GetComponentInChildren<UILabel>().alpha = 1;
				Hsinchu3.GetComponent<UISprite>().color = new Color(1,0.9f,0.7f);
				choose = "3";
			}else if(obj.name == "4"){
				Hsinchu4.GetComponentInChildren<UILabel>().alpha = 1;
				Hsinchu4.GetComponent<UISprite>().color = new Color(1,0.9f,0.7f);
				choose = "4";
			}
		}





	}

	void Next(GameObject obj){
		Application.LoadLevel ("Main");
	}

	void Update () {
	}
}
