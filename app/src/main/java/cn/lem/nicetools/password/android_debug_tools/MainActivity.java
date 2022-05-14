package cn.lem.nicetools.password.android_debug_tools;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  private Button mBtnTerminal;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
    mBtnTerminal.setOnClickListener(v -> {
      startActivity(new Intent(this, TerminalActivity.class));
    });
  }

  private void initView() {
    mBtnTerminal = (Button) findViewById(R.id.btn_terminal);
  }
}