package cn.colorfuline.elderlylauncher.https.download;

/**
 * Created by CQDXP on 2016/8/16.
 */
public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
