package cn.lem.nicetools.password.android_debug_tools;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  private Button mBtnTerminal;
  private Button mBtnOpenWifiDebug;
  private Button mBtnNetworkInfo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
    mBtnTerminal.setOnClickListener(v -> {
      startActivity(new Intent(this, TerminalActivity.class));
    });
    mBtnOpenWifiDebug.setOnClickListener(v -> {
      startActivity(new Intent(this, AdbTcpSettingActivity.class));
    });
    mBtnNetworkInfo.setOnClickListener(v -> {
      startActivity(new Intent(this, NetworkInfoActivity.class));
    });
  }

  private void initView() {
    mBtnTerminal = (Button) findViewById(R.id.btn_terminal);
    mBtnOpenWifiDebug = (Button) findViewById(R.id.btn_open_wifi_debug);
    mBtnNetworkInfo = (Button) findViewById(R.id.btn_network_info);
  }
}