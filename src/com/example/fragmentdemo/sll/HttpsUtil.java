package com.example.fragmentdemo.sll;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;

public class HttpsUtil {

	private static final String KEY_STORE_TYPE_BKS = "BKS";

	private static final String KEY_STORE_TYPE_P12 = "PKCS12";

	private static final String SCHEME_HTTPS = "https";

	private static final int HTTPS_PORT = 13801;

	public static final String KEY_STORE_CLIENT_PATH = "client.p12";

	private static final String KEY_STORE_TRUST_PATH = "client.truststore";

	public static final String KEY_STORE_PASSWORD = "Y9rha019eyZiZ94Bjs/KnOau3ij27IEM";

	private static final String KEY_STORE_TRUST_PASSWORD = "Y9rha019eyZiZ94Bjs/KnOau3ij27IEM";

	private static KeyStore keyStore;

	private static KeyStore trustStore;

	public static DefaultHttpClient getSslHttpClient(Context pContext) {
		DefaultHttpClient httpsClient = new DefaultHttpClient();
		try {
			// ����������Ҫ��֤�Ŀͻ���֤��
			keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
			// �ͻ������εķ�������֤��
			trustStore = KeyStore.getInstance(KEY_STORE_TYPE_BKS);
			InputStream ksIn = pContext.getResources().getAssets()
					.open(KEY_STORE_CLIENT_PATH);
			InputStream tsIn = pContext.getResources().getAssets()
					.open(KEY_STORE_TRUST_PATH);
			try {
				keyStore.load(ksIn, KEY_STORE_PASSWORD.toCharArray());
				trustStore.load(tsIn, KEY_STORE_TRUST_PASSWORD.toCharArray());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					ksIn.close();
				} catch (Exception ignore) {
					ignore.printStackTrace();
				}
				try {
					tsIn.close();
				} catch (Exception ignore) {
					ignore.printStackTrace();
				}
			}
			SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore,
					KEY_STORE_PASSWORD, trustStore);
			socketFactory
					.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme sch = new Scheme(SCHEME_HTTPS, socketFactory, HTTPS_PORT);
			httpsClient.getConnectionManager().getSchemeRegistry()
					.register(sch);
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return httpsClient;
	}

}
