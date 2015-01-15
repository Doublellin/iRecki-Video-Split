using UnityEngine;
using System.Collections;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

public class PointHistoryPage : MonoBehaviour {

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

#if UNITY_EDITOR
		id = "997791446913790";
		isFBuser = "true";
#endif
		
		WWW w = new WWW ("http://" + UploadPage.serverIP + ":" + UploadPage.serverPort + "/GetTrade?id=" + id + "&isFBUser=" + isFBuser);
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
			MyTrade d = g.GetComponent<MyTrade>();

			if(a[i]["payment_type"].ToString() == "Credit_CreditCard"){
				d.payment_type.text = "信用卡"; // 付款方式
			}

			if(a[i]["rtn_msg"].ToString() == "Succeeded"){
				d.rtn_msg.text = "成功";
			}else{
				d.rtn_msg.text = "失敗";
			}

			d.trade_amt.text = a[i]["trade_amt"].ToString() + " $"; // 付款 $
			d.merchant_trade_no.text = a[i]["merchant_trade_no"].ToString(); // 
			d.trade_no.text = a[i]["trade_no"].ToString(); // 
			d.trade_date.text = a[i]["trade_date"].ToString(); //

			d.payment_date.text = a[i]["payment_date"].ToString(); // 付款 Date

		}
	}
	
	void Update () {
		
	}

}
