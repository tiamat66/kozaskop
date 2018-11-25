package com.robic.zoran.moonstalker.rest;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.robic.zoran.moonstalker.MainActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("WeakerAccess")
public class HTTP extends AsyncTask<String, Void, String>
{
  protected       String                  url               = "";
  protected       File                    destinationFile   = null;
  protected       OutputStream            destinationStream = null;
  protected       String                  params            = "";
  protected       HashMap<String, String> headers           = new HashMap<>();
  protected       GetCompleteEvent        onComplete        = null;
  protected       GetCompleteEvent        onFail            = null;
  protected       GetCompleteEvent        onErrorConnection = null;
  protected       boolean                 completeInThread  = false;
  protected       boolean                 logging           = true;
  protected       int                     responseCode      = 0;
  protected       Exception               serverException   = null;
  protected       String                  responseMessage   = "";
  public          AtomicInteger           progress          = new AtomicInteger(0);
  public volatile boolean                 throttled         = false;
  public          StringComposer          diag              = null;  // detail diagnostics HTML

  public HTTP(String url, GetCompleteEvent evt)
  {
    super();
    this.url = url;
    this.onComplete = evt;
  }

  public HTTP(String url, GetCompleteEvent evt, boolean logging)
  {
    super();
    this.url = url;
    this.onComplete = evt;
    this.logging = logging;
  }

  public HTTP(String url, File file, GetCompleteEvent evt)
  {
    super();
    this.url = url;
    this.destinationFile = file;
    this.onComplete = evt;
  }

  public HTTP(String url, OutputStream file, GetCompleteEvent evt)
  {
    super();
    this.url = url;
    this.destinationStream = file;
    this.onComplete = evt;
  }

  public HTTP params(String p)
  {
    params = p;
    return this;
  }

  public HTTP headers(String k, String v)
  {
    this.headers.put(k, v);
    return this;
  }

  private void diag(String label, String dataFormat, Object... args)
  {
    if (diag != null)
      diag.trParam(label, dataFormat, args);
  }

  private void diag(Exception e)
  {
    if (diag != null) {
      StringWriter b = new StringWriter();
      e.printStackTrace(new PrintWriter(b));
      diag.trParam("Exception", "<pre>%s</pre>", b);
    }
    if (onErrorConnection != null)
      onErrorConnection.complete(this, null);
  }

  public void setOnFail(GetCompleteEvent onFail)
  {
    this.onFail = onFail;
  }

  public HTTP setOnErrorConnection(GetCompleteEvent e)
  {
    onErrorConnection = e;
    return this;
  }

  public HTTP onFail(final MainActivity act)
  {
    setOnFail(new GetCompleteEvent()
    {
      @Override public void complete(HTTP http, String s)
      {
//        act.progressOff();
      }
    });
    return this;
  }

  private void backgroundReadStream(InputStream in, OutputStream out) throws IOException
  {
    byte[] buffer = new byte[4096]; // Or whatever constant you feel like using
    int    size;
    while ((size = in.read(buffer)) > 0) {
      out.write(buffer, 0, size);
      progress.addAndGet(size);
      if (isCancelled()) break;
      if (throttled) try { Thread.sleep(99); } catch (InterruptedException ignored) { }
    }
  }

  @Override
  protected String doInBackground(String... params)
  {
    try {
      URL               url  = new URL(this.url);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      diag("URL", null, this.url);
      try {
        conn.setInstanceFollowRedirects(true);
        if (this.headers.size() > 0) {
          for (Map.Entry<String, String> hdr : this.headers.entrySet()) {
            conn.addRequestProperty(hdr.getKey(), hdr.getValue());
            diag("Header", "<b><i>%s</i></b>: %s", hdr.getKey(), hdr.getValue());
          }
        }
        if (this.params.length() > 0) {
          conn.setDoOutput(true);
          conn.setRequestMethod("POST");
          conn.getOutputStream().write(this.params.getBytes());
          diag("POST", "<xmp>%s</xmp>", this.params);
        }
        conn.connect();
        responseCode = conn.getResponseCode();
        diag("Response code", "%d", responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
          if (destinationFile != null) {
            diag("Payload", null, "Downloading a file");
            InputStream in = conn.getInputStream();
            File tmp = File.createTempFile("at7", "apk", new File(
                Environment.getExternalStorageDirectory(), "Download"));
            FileOutputStream fil = new FileOutputStream(tmp);
            backgroundReadStream(in, fil);
            fil.close();
//            if (!tmp.renameTo(destinationFile))
//              Log.e("HTTP", "File rename failure %s --> %s", tmp.getAbsolutePath(),
//                    destinationFile.getAbsolutePath());
            if (onComplete != null && completeInThread)
              onComplete.complete(this, destinationFile.getAbsolutePath());
            return destinationFile.getAbsolutePath();
          } else if (destinationStream != null) {
            backgroundReadStream(conn.getInputStream(), destinationStream);
            return "success";
          } else {
            BufferedInputStream is   = new BufferedInputStream(conn.getInputStream());
            BufferedReader      br   = new BufferedReader(new InputStreamReader(is));
            String              l;
            StringBuilder       resj = new StringBuilder();
            while ((l = br.readLine()) != null)
              resj.append(l);
            br.close();
            is.close();
            diag("Payload", "<xmp>%s</xmp>", resj.toString());
            if (onComplete != null && completeInThread)
              onComplete.complete(this, resj.toString());
            return resj.toString();
          }
        } else {
          responseMessage = conn.getResponseMessage();
          diag("Payload", "<pre>%s</pre>", responseMessage);
        }
      } finally {
        conn.disconnect();
      }
    } catch (SocketTimeoutException e) {
      serverException = e;
      diag(e);
    } catch (IOException e) {
      serverException = e;
      e.printStackTrace();
      diag(e);
    } catch (Exception e) {
      serverException = e;
      diag(e);
      if (diag == null)
        throw e;  // if we're diagnosing, don't crash & burn
    }
    if (onComplete != null && completeInThread)
      onComplete.complete(this, null);
    return null;
  }

  protected void onPostExecute(String data) //(HttpResponse response)
  {
    if (data != null && data.length() > 0) {
      if (onComplete != null && !completeInThread)
        onComplete.complete(this, data);
    } else if (onFail != null)
      onFail.complete(this, null);
  }

  public interface GetCompleteEvent
  {
    void complete(HTTP http, String s);
  }
}

class StringComposer
{
  private StringBuilder sb;
  private boolean appendLn;

  public StringComposer(boolean appendLn)
  {
    sb = new StringBuilder();
    this.appendLn = appendLn;
  }

  public StringComposer append(String format, Object ...args)
  {
    sb.append(String.format(Locale.GERMAN, format, args));
    if (appendLn) sb.append("\n");
    return this;
  }

  public void trParam(String label, String dataFormat, Object ...args)
  {
    if (dataFormat == null || dataFormat.isEmpty()) dataFormat = "%s";
    String tmp = String.format(Locale.GERMAN, "<tr><td><b>%s</b></td><td>%s</td></tr>", label, dataFormat);
    sb.append(String.format(Locale.GERMAN, tmp, args));
    Log.i("sc", String.format(Locale.GERMAN, tmp, args));
    if (appendLn) sb.append("\n");
  }

  @Override
  public String toString()
  {
    return sb.toString();
  }
}
