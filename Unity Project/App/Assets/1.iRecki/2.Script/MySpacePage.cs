using UnityEngine;
using System.Collections;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

public class MySpacePage : MonoBehaviour {

	public UIScrollView sv;
	public GameObject myData;

	IEnumerator Start () {

		string id, isFBuser;
		
		if(FirstPage.FB_ID != ""){
			id = FirstPage.FB_ID;
			isFBuser = "true";
		}else{
			id = SignInPage.ID;
			isFBuser = "false";
		}

		WWW w = new WWW ("http://" + UploadPage.serverIP + ":" + UploadPage.serverPort + "/UserDataSearch?id=" + id + "&isFBUser=" + isFBuser);
		yield return w;
		string json = w.text;
		w.Dispose ();
		JObject obj = JsonConvert.DeserializeObject<JObject> (json);

		JArray a = obj ["Data"] as JArray;

		for(int i = 0, y = 125; i < a.Count; i++, y -= 210){
			GameObject g = Instantiate(myData) as GameObject;
			g.transform.parent = sv.transform;
			g.transform.localScale = Vector3.one;
			g.transform.localPosition = new Vector3(0, y);
			MyData d = g.GetComponent<MyData>();
			d.buyTime.text = "";
			d.PlayTime.text = a[i]["start_play_time"].ToString();
			d.StopTime.text = a[i]["end_play_time"].ToString();
			d.buyMoney.text = "1";
			d.buyLocation.text = a[i]["location"].ToString();

			string chooseTable = "";

			for(int ID = 1; ID <= 6 ; ID++){
				if(a[i]["choose_table_id_"+ID].ToString() == "t"){
					chooseTable += ID+", ";
				}
			}


			d.buyTable.text = chooseTable;
		}
	}

	void Update () {
	
	}
}
