package com.battlereport.kernel.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.zip.GZIPInputStream;

/**
 * Created by LiuPeng on 2016/5/20.
 * HttpUrlConnection处理结果封装
 */
public class HttpResponse {
    private HttpURLConnection mHttpConnection;
    private boolean IS_GZIP = false;
    private static final String GZIP = "gzip";

    public HttpResponse(HttpURLConnection mHttpConnection) {
        this.mHttpConnection = mHttpConnection;
        GZIP.equals(mHttpConnection.getContentEncoding());
    }

    public String getContent() throws IOException {
        if (!IS_GZIP) {
            return readDate(mHttpConnection.getInputStream());
        }
        return readFromGzip(mHttpConnection.getInputStream());
    }

    private String readDate(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IOException("InputStream is null");
        }
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();//关闭流释放资源
                if (bufferedReader != null) {
                    bufferedReader.close();//关闭流释放资源
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "";
    }

    private String readFromGzip(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IOException("InputStream is null!");
        }
        GZIPInputStream gzipInputStream = null;
        ByteArrayOutputStream baos = null;
        byte[] result;
        try {
            gzipInputStream = new GZIPInputStream(new BufferedInputStream(inputStream));
            baos = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = -1;
            while ((len = gzipInputStream.read(buff)) != -1) {
                baos.write(buff, 0, len);
            }
            result = baos.toByteArray();
        } finally {
            inputStream.close();
            if (gzipInputStream != null) {
                gzipInputStream.close();
            }
            if (baos != null) {
                baos.close();
            }
        }
        return new String(result, "UTF-8");
    }

    public void closeConnection() {
        if (mHttpConnection != null) {
            mHttpConnection.disconnect();
        }
    }
}
