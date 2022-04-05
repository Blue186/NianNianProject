package com.nian.business.utils;

import java.io.*;
import java.nio.charset.Charset;

public class ReadFileUtil {
    public String readFileToString(InputStream in, Charset charset) throws IOException {
        StringBuilder resultString = new StringBuilder();
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) != -1) {
            resultString.append(new String(buf, 0, len, charset));
        }
        in.close();
        return resultString.toString();
    }
}
