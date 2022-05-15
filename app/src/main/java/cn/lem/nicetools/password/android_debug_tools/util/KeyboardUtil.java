package cn.lem.nicetools.password.android_debug_tools.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 键盘实用工具
 */
public class KeyboardUtil {

  public static void reqFocusInEditText(EditText et) {
    et.setFocusable(true);
    et.setFocusableInTouchMode(true);
    //请求获得焦点
    et.requestFocus();
  }

  /**
   * 自动关闭软键盘
   */
  public static void showKeyboard(Context activity, EditText et) {
    InputMethodManager imm =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    if (imm != null) {
      imm.showSoftInput(et, 0);
    }
  }

  /**
   * 自动关闭软键盘
   */
  public static void closeKeyboard(Activity activity) {
    InputMethodManager imm =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    if (imm != null) {
      imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }
  }

  /**
   * 打开关闭相互切换
   */
  public static void hideKeyboard(Activity activity) {
    InputMethodManager imm =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    if (imm.isActive()) {
      if (activity.getCurrentFocus().getWindowToken() != null) {
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS);
      }
    }
  }
}
