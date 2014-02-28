package com.example.signaturetest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());

				byte[] digestByteArray = md.digest();
				StringBuilder sb = new StringBuilder();
				for (byte b : digestByteArray) {
					sb.append(String.format("%02x", b & 0xff));
				}
				
				Log.d("SIGNATURE", "sha1 fingerprint :" + sb.toString());
				Log.d("SIGNATURE","KeyHash :" + Base64.encodeToString(digestByteArray, Base64.DEFAULT));
				
				md = MessageDigest.getInstance("MD5");
				md.update(signature.toByteArray());
				digestByteArray = md.digest();
				sb = new StringBuilder();
				for (byte b : digestByteArray) {
					sb.append(String.format("%02x", b & 0xff));
				}
				Log.d("SIGNATURE", "md5 fingerprint :" + sb.toString());
				
			}
		} catch (NameNotFoundException e) {
		} catch (NoSuchAlgorithmException e) {
		}

	}
}
