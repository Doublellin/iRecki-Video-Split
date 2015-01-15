using UnityEngine;
using System.Collections;

public class MyCamera : MonoBehaviour {

	void Start () {
//		print (WebCamTexture.devices.Length);
	}

	void Update () {
	
	}


	WebCamTexture c;
	
	void OnEnable() {


		c = new WebCamTexture (WebCamTexture.devices[0].name);
//		c.requestedFPS = 1000;
//		c.requestedWidth = 160;
//		c.requestedHeight = 120;

		c.width = 160;
//		c.height = 120;


		renderer.material.mainTexture = c;
		c.Play ();
	}
	
	void OnDisable() {
		c.Stop ();
		GameObject.Destroy (c);
	}
}
