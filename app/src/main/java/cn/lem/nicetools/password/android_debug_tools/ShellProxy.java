package cn.lem.nicetools.password.android_debug_tools;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShellProxy {

  public static Single<String> exec(String cmd) {
    return Single.create((SingleOnSubscribe<String>) emitter -> {
      try {
        Process p = Runtime.getRuntime().exec(cmd);
        StringBuffer result = new StringBuffer();
        InputStream is = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null) {
          result.append(line + "\n");
        }
        p.waitFor();
        is.close();
        reader.close();
        p.destroy();
        System.out.println("执行输出结果: " + result.toString());
        emitter.onSuccess(result.toString());
      } catch (IOException e) {
        e.printStackTrace();
        emitter.onError(e);
      }
    });
  }
}
