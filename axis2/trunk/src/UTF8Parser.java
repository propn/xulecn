import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UTF8Parser {

    public static void main(String[] args) {
        if (args.length < 2) {
            return;
        }
        if (args[1].startsWith(".")) {
            args[1] = args[1].substring(1);
        }
        File file = new File(args[0] + args[1]);
        System.out.println("java代码位置：" + file.getAbsolutePath());
        UTF8Parser.clearUTF8Mark(file);
    }

    private static void clearUTF8Mark(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                clearUTF8Mark(f);
            }
        } else {
            FileInputStream fis = null;
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            OutputStream out = null;
            try {
                fis = new FileInputStream(file);
                is = new BufferedInputStream(fis);
                baos = new ByteArrayOutputStream();
                byte b[] = new byte[3];

                is.read(b);
                // System.out.println(b[0] + ":" + b[1] + ":" + b[2]);

                if (-17 == b[0] && -69 == b[1] && -65 == b[2]) {
                    System.out.println(file.getAbsolutePath());
                    b = new byte[1024];
                    while (true) {
                        int bytes = 0;
                        try {
                            bytes = is.read(b);
                        } catch (IOException e) {
                        }
                        if (bytes == -1) {
                            break;
                        }
                        baos.write(b, 0, bytes);

                        b = baos.toByteArray();
                    }
                    // System.out.println(new String(b, "utf-8"));
                    file.delete();

                    out = new FileOutputStream(file);
                    baos.writeTo(out);
                }
            } catch (Exception e) {
                System.exit(0);
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                    if (baos != null) {
                        baos.close();
                    }
                } catch (Exception e) {
                    System.exit(0);
                }
            }
        }
    }
}
