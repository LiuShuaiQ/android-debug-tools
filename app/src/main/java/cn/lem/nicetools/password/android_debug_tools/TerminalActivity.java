package cn.lem.nicetools.password.android_debug_tools;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TerminalActivity extends AppCompatActivity {
  private static final String TAG = "TerminalActivity";

  private TextView mTvLog;
  private LinearLayout mLlCommandLine;
  private EditText mEtCmd;

  private StringBuilder mLogString;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_terminal);
    mLogString = new StringBuilder();
    initView();

    mEtCmd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        String cmd = mEtCmd.getText().toString();
        if (TextUtils.isEmpty(cmd.trim())) {
          appendLogText("$: ");
        } else {
          ShellProxy.exec(cmd)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(s -> {
                Log.e(TAG, "执行命令成功--> " + s);
                appendLogText("$: " + s);
              }, throwable -> {
                Log.e(TAG, "执行命令失败--> " + throwable);
                appendLogText("$: " + "Error: " + throwable.getMessage());
              });
        }
        mEtCmd.setText("");
        return false;
      }
    });
  }

  private void appendLogText(String log) {
    mLogString.append(log);
    mTvLog.append(log);
    mTvLog.append("\n");
  }

  private void initView() {
    mTvLog = (TextView) findViewById(R.id.tv_log);
    mLlCommandLine = (LinearLayout) findViewById(R.id.ll_command_line);
    mEtCmd = (EditText) findViewById(R.id.et_cmd);
  }
}