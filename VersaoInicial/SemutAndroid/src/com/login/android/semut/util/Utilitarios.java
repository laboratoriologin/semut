package com.login.android.semut.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Utilitarios {

	public static boolean hasConnection(Context contexto) {

		ConnectivityManager cm = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo netInfo = cm.getActiveNetworkInfo();

		if ((netInfo != null) && (netInfo.isConnectedOrConnecting()) && (netInfo.isAvailable()))
			return true;
		else
			return false;

	}

	/**
	 * Method gerarHash
	 * 
	 * @author Henrique Machado
	 * @param plainText
	 * @param algorithm
	 *            The algorithm to use like MD2, MD5, SHA-1, etc.
	 * @return String
	 * @throws NoSuchAlgorithmException
	 */
	public static String gerarHash(String plainText, String algorithm) {

		MessageDigest mdAlgorithm;

		StringBuffer hexString = new StringBuffer();

		try {

			mdAlgorithm = MessageDigest.getInstance(algorithm);

			mdAlgorithm.update(plainText.getBytes());

			byte[] digest = mdAlgorithm.digest();

			for (int i = 0; i < digest.length; i++) {

				plainText = Integer.toHexString(0xFF & digest[i]);

				if (plainText.length() < 2) {

					plainText = "0" + plainText;
				}

				hexString.append(plainText);
			}

		} catch (Exception e) {

		}

		return hexString.toString();
	}

	public static String getKeyMd5() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		return gerarHash(Constantes.KEY_SERVLET + dateFormat.format(cal.getTime()).toString(), "md5");
	}
}
