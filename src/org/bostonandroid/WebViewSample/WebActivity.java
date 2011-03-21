package org.bostonandroid.WebViewSample;

import android.app.Activity;
import android.os.Bundle;

import android.webkit.WebView;
import android.webkit.WebSettings;

import android.webkit.WebViewClient;

import android.view.KeyEvent;

import android.graphics.Bitmap;
import android.view.Window;

import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;
import android.content.ActivityNotFoundException;

public class WebActivity extends Activity {
  private WebView page;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
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

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && page().canGoBack()) {
      page().goBack();
      return true;
    } else
      return super.onKeyDown(keyCode, event);
  }

  private WebView page() {
    if (this.page == null) {
      this.page = (WebView)findViewById(R.id.webpage);
      WebSettings s = this.page.getSettings();
      s.setJavaScriptEnabled(true);
      this.page.setWebViewClient(new MyWebViewClient());
      this.page.setDownloadListener(new DefaultDownloadListener());
    }
    return this.page;
  }

  private class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView v, String url) {
      v.loadUrl(url);
      return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
      setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
      setProgressBarIndeterminateVisibility(false);
      removeElementById(view, "external-links");
    }
  }

  private class DefaultDownloadListener implements DownloadListener {
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
      Intent i = new Intent(Intent.ACTION_VIEW);
      i.setDataAndType(Uri.parse(url), mimetype);
      startActivity(i);
    }
  }

  private void removeElementById(WebView view, String id) {
    view.loadUrl("javascript:(function() {document.getElementById('"+id+"').style.display = 'none';})();");
  }
}
