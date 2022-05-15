package cn.lem.nicetools.password.android_debug_tools;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import cn.lem.nicetools.password.android_debug_tools.util.IpAddressUtil;
import cn.lem.nicetools.password.android_debug_tools.util.RxUtil;
import io.reactivex.rxjava3.functions.Consumer;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class AdbTcpSettingActivity extends AppCompatActivity {

  private TextView mTvTcpPort;
  private TextView mTvIp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_adb_tcp_setting);
    initView();
    initInfo();
  }

  private void initView() {
    mTvTcpPort = (TextView) findViewById(R.id.tv_tcp_port);
    mTvIp = (TextView) findViewById(R.id.tv_ip);
  }

  private void initInfo() {
    ShellProxy.exec(" getprop service.adb.tcp.port ")
        .compose(RxUtil.rxSingleSchedulerHelper())
        .subscribe(new Consumer<String>() {
          @Override public void accept(String s) throws Throwable {
            mTvTcpPort.setText("ADB TCP port: " + s);
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Throwable {
            mTvTcpPort.setText("ADB TCP port get Error: " + throwable.getMessage());
          }
        });

    // ip info
    //StringBuilder networkConfig = new StringBuilder();
    //try {
    //  List<NetworkInterface> neti = Collections.list(NetworkInterface.getNetworkInterfaces());
    //  for (NetworkInterface intf : neti) {
    //    intf.getDisplayName();
    //    networkConfig.append("网卡: " + intf.getDisplayName());
    //    networkConfig.append("\n");
    //
    //    List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
    //    for (InetAddress addr : addrs) {
    //      if (!addr.isLoopbackAddress()) {
    //        String sAddr = addr.getHostAddress();
    //        networkConfig.append(sAddr);
    //        networkConfig.append("\n");
    //      }
    //    }
    //    networkConfig.append("\n\n");
    //  }
    //} catch (SocketException e) {
    //  e.printStackTrace();
    //}
    //mTvIp.setText(networkConfig.toString());

    Context context = getApplicationContext();
    WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    String ip = IpAddressUtil.intToInetAddress(wm.getConnectionInfo().getIpAddress()).getHostAddress();
    mTvIp.setText("IP: " + ip);
  }
}