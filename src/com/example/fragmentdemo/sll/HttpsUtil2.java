package com.example.fragmentdemo.sll;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import com.example.fragmentdemo.MainApplication;

import android.content.Context;

public class HttpsUtil2 {

	public static HttpsURLConnection getHttpsURLConnection(Context mcontext,
			String urlpath, String method) throws Exception {
		// Setup connection
		//
		URL url = new URL(urlpath);
		HttpsURLConnection urlConnection = (HttpsURLConnection) url
				.openConnection();
		// urlConnection.setRequestProperty("Cookie",
		// CookieUtil.getCookieString(mcontext));
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);
		urlConnection.setRequestProperty("Content-Type", "binary/octet-stream");
		urlConnection.setRequestMethod(method);
		// Set SSL Socket Factory for this request
		SSLContext sslContext = getSSLContext();

		urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());
		// ��������֤��
		urlConnection.setHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				// if ("222.76.241.234".equals(hostname)) {
				return true;
				// } else {
				// return false;
				// }
			}
		});
		return urlConnection;

	}

	private static SSLContext getSSLContext() throws Exception {

		InputStream inputStream = MainApplication.getInstance()
				.getApplicationContext().getResources().getAssets()
				.open("client.p12");

		TrustManager[] trustManagers = prepareTrustManager(MainApplication
				.getInstance().getApplicationContext().getResources()
				.getAssets().open("server.cer"));
		KeyManager[] keyManagers = prepareKeyManager(inputStream,
				"Y9rha019eyZiZ94Bjs/KnOau3ij27IEM");

		SSLContext sslContext = SSLContext.getInstance("TLS");
		// SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
		sslContext.init(keyManagers, new TrustManager[] { new MyTrustManager(
				chooseTrustManager(trustManagers)) }, new SecureRandom());
		return sslContext;

	}

	private static TrustManager[] prepareTrustManager(
			InputStream... certificates) {
		if (certificates == null || certificates.length <= 0)
			return null;
		try {
			CertificateFactory certificateFactory = CertificateFactory
					.getInstance("X.509");
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(null);
			int index = 0;
			for (InputStream certificate : certificates) {
				String certificateAlias = Integer.toString(index++);
				keyStore.setCertificateEntry(certificateAlias,
						certificateFactory.generateCertificate(certificate));
				try {
					if (certificate != null)
						certificate.close();
				} catch (IOException e)

				{
				}
			}
			TrustManagerFactory trustManagerFactory = null;

			trustManagerFactory = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(keyStore);

			TrustManager[] trustManagers = trustManagerFactory
					.getTrustManagers();

			return trustManagers;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private static KeyManager[] prepareKeyManager(InputStream bksFile,
			String password) {
		try {
			if (bksFile == null || password == null)
				return null;

			KeyStore clientKeyStore = KeyStore.getInstance("PKCS12");
			clientKeyStore.load(bksFile, password.toCharArray());
			KeyManagerFactory keyManagerFactory = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			keyManagerFactory.init(clientKeyStore, password.toCharArray());
			return keyManagerFactory.getKeyManagers();

		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static X509TrustManager chooseTrustManager(
			TrustManager[] trustManagers) {
		for (TrustManager trustManager : trustManagers) {
			if (trustManager instanceof X509TrustManager) {
				return (X509TrustManager) trustManager;
			}
		}
		return null;
	}

	private static class MyTrustManager implements X509TrustManager {
		private X509TrustManager defaultTrustManager;
		private X509TrustManager localTrustManager;

		public MyTrustManager(X509TrustManager localTrustManager)
				throws NoSuchAlgorithmException, KeyStoreException {
			TrustManagerFactory var4 = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			var4.init((KeyStore) null);
			defaultTrustManager = chooseTrustManager(var4.getTrustManagers());
			this.localTrustManager = localTrustManager;
		}

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {

		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			try {
				defaultTrustManager.checkServerTrusted(chain, authType);
			} catch (CertificateException ce) {
				localTrustManager.checkServerTrusted(chain, authType);
			}
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}
	}

}
