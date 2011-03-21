package org.bostonandroid.WebViewSample;

import android.app.Activity;
import android.os.Bundle;

import android.webkit.WebView;
import android.webkit.WebSettings;

public class WebActivity extends Activity {
  private WebView page;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    if (savedInstanceState == null)
      page().loadUrl("http://bostonandroid.org/");
    else
      page().restoreState(savedInstanceState);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    page().saveState(outState);
  }

  private WebView page() {
    if (this.page == null) {
      this.page = (WebView)findViewById(R.id.webpage);
      WebSettings s = this.page.getSettings();
      s.setJavaScriptEnabled(true);
    }
    return this.page;
  }
}
