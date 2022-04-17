package servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CustomOutputStream extends ServletOutputStream {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private String content;

    @Override
    public void write(int b) {
        out.write(b);
    }

    @Override
    public void close() throws IOException {
        content = out.toString();
        out.close();
        super.close();
    }

    public String getContentAsString() {
        return this.content;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }
}
