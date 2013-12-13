package pt.up.fe.ssin.androidsecuremesh.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.conn.util.InetAddressUtils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

public class Storage {
	static {
	    Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
	}
	public static User myData = new User("me");
	public static ArrayList<Chat> chatsIn = new ArrayList<Chat>();
	public static ArrayList<User> users = new ArrayList<User>();
	public static Context ctx;
	
	//shamelessly stolen from http://stackoverflow.com/questions/6064510/how-to-get-ip-address-of-the-device
	public static String getIPAddress(boolean useIPv4) {
		WifiManager wifiMgr = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
		int ip = wifiInfo.getIpAddress();
		String ipAddress = Formatter.formatIpAddress(ip);
		return ipAddress;
	}

}
