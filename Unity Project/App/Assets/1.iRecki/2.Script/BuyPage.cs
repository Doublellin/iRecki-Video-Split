using UnityEngine;
using System.Collections;

public class BuyPage : MonoBehaviour {

	public GameObject button_Buy;
	public UILabel number;

	void Awake () {
		UIEventListener.Get (button_Buy).onClick = Button_Buy;
	}

	void Button_Buy (GameObject obj) {

		string id, isFBuser;
		
		if(FirstPage.FB_ID != ""){
			id = FirstPage.FB_ID;
			isFBuser = "true";
		}else{
			id = SignInPage.ID;
			isFBuser = "false";
		}

		string url 
			= "ItemName="+WWW.EscapeURL("iRecki " + number.text + " $ Stored Value")
			+"&TotalAmount="+number.text
			+"&TradeDesc="+WWW.EscapeURL("iRecki Stream Service")
			+"&ChoosePayment=Credit" 
			+"&ID="+WWW.EscapeURL(id)
			+"&IsFacebookUser="+isFBuser;


		url = "http://" + UploadPage.serverIP + ":" + UploadPage.serverPort + "/TradeOrder?"+url;

		Application.OpenURL (url);

		Application.LoadLevel ("MainPage");
	}

}
