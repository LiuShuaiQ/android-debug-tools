package cn.lem.nicetools.password.android_debug_tools;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import cn.lem.nicetools.password.android_debug_tools.util.IpAddressUtil;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class NetworkInfoActivity extends AppCompatActivity {

  private TextView mTvNetworkInfo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_network_info);
    initView();
    initNetworkInfo();
  }

  private void initView() {
    mTvNetworkInfo = (TextView) findViewById(R.id.tv_network_info);
  }

  private void initNetworkInfo() {
    //ip info
    StringBuilder networkConfig = new StringBuilder();
    //dhcp info
    Context context = getApplicationContext();
    WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    WifiInfo wifiInfo = wm.getConnectionInfo();
    networkConfig.append("WiFi ssid: " + wifiInfo.getSSID());
    networkConfig.append("\n");
    networkConfig.append("WiFi bssid: " + wifiInfo.getBSSID());
    networkConfig.append("\n");
    networkConfig.append("WiFi ip: " + IpAddressUtil.intToInetAddress(wifiInfo.getIpAddress()).getHostAddress());
    networkConfig.append("\n\n");

    //dbcp info
    DhcpInfo dhcpInfo = wm.getDhcpInfo();
    networkConfig.append("dhcp ipAddress: " + IpAddressUtil.intToInetAddress(dhcpInfo.ipAddress).getHostAddress());
    networkConfig.append("\n");
    networkConfig.append("dhcp netmask: " + IpAddressUtil.intToInetAddress(dhcpInfo.netmask).getHostAddress());
    networkConfig.append("\n");
    networkConfig.append("dhcp gateway: " + IpAddressUtil.intToInetAddress(dhcpInfo.gateway).getHostAddress());
    networkConfig.append("\n");
    networkConfig.append("dhcp dns1: " + IpAddressUtil.intToInetAddress(dhcpInfo.dns1).getHostAddress());
    networkConfig.append("\n");
    networkConfig.append("dhc dns2: " + IpAddressUtil.intToInetAddress(dhcpInfo.dns2).getHostAddress());
    networkConfig.append("\n\n");

    try {
      List<NetworkInterface> neti = Collections.list(NetworkInterface.getNetworkInterfaces());
      for (NetworkInterface intf : neti) {
        intf.getDisplayName();
        networkConfig.append("网卡: " + intf.getDisplayName());
        networkConfig.append("\n");

        List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
        for (InetAddress addr : addrs) {
          if (!addr.isLoopbackAddress()) {
            String sAddr = addr.getHostAddress();
            networkConfig.append(sAddr);
            networkConfig.append("\n");
          }
        }
      }
    } catch (SocketException e) {
      e.printStackTrace();
    }

    mTvNetworkInfo.setText(networkConfig.toString());
  }
}