package com.nian.business.utils;

import java.io.*;
import java.nio.charset.Charset;

public class ReadFileUtil {
    public String readFileToString(File file, Charset charset) throws IOException {
        StringBuilder resultString = new StringBuilder();
        InputStream is = new FileInputStream(file);
        byte[] buf = new byte[1024];
        int len;
        while ((len = is.read(buf)) != -1) {
            resultString.append(new String(buf, 0, len, charset));
        }
        is.close();
        return resultString.toString();
    }
}
