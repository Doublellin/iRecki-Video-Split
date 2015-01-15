using UnityEngine;
using System.Collections;
using Facebook.MiniJSON;

public class FirstPage : MonoBehaviour {

	public static string FB_ID = "";
	public static string FB_JSON_DATA = "";
	public static string FB_USER_NAME = "";
	public static Texture2D FB_USER_IMAGE;



	public GameObject buttonFB;

	bool isInit = false;

	void Awake(){
		// 可參考： https://developers.facebook.com/docs/unity/reference/current
		FB.Init (InitComplete); // 初始化
		UIEventListener.Get (buttonFB).onClick = ButtonFB;
	}

	// 初始化完成這個函示會被執行
	void InitComplete(){
		isInit = true;
	} 

	void ButtonFB(GameObject obj){
		if(isInit){
			// 可參考： https://developers.facebook.com/docs/facebook-login/permissions/v2.1
			FB.Login ("public_profile,user_birthday,email,user_friends",Login); // 登入，並設定要取得的資料
		}
	}
	
	void Login(FBResult result){ // 登入成功後會執行這個函式('方法)
		SignInPage.ID = "";
		FB_ID = FB.UserId;
		FB_JSON_DATA = result.Text;
		FB.API("me?fields=name", Facebook.HttpMethod.GET, UserCallBack);
	}
	
	void UserCallBack(FBResult result) {
		IDictionary dict = Json.Deserialize(result.Text) as IDictionary;
		FB_USER_NAME =dict ["name"].ToString(); // 取得用戶名稱
		StartCoroutine(GetImage());
	}
	
	IEnumerator GetImage () { // 取得用戶頭像
		WWW www = new WWW("https://graph.facebook.com/" + FB.UserId + "/picture?type=large"); 
		yield return www;
		FB_USER_IMAGE = www.texture;
		StartCoroutine(SignUp());
	}

	IEnumerator SignUp () { // 取得用戶頭像
		WWWForm f = new WWWForm ();
		f.AddField ("id",FB_ID);
		f.AddField ("IsFacebookUser", "true");
		f.AddField ("name", FB_USER_NAME);
		WWW www = new WWW("http://"+UploadPage.serverIP+":"+UploadPage.serverPort + "/SignUp", f);
		yield return www;
		Application.LoadLevel ("MainPage");
	}

}
