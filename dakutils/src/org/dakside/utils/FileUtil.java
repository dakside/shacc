/*
 *  Copyright (C) 2010 Le Tuan Anh <tuananh.ke@gmail.com>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.dakside.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class FileUtil {

    private static final Logger logger = Logger.getLogger(FileUtil.class.getName());
    private static final int BUFFER_SIZE = 65535;
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public static boolean copy(InputStream input, OutputStream output) {
        byte[] buffer = new byte[BUFFER_SIZE];
        int byteCount = 0;
        try {
            //do copy here
            BufferedInputStream is = new BufferedInputStream(input);
            BufferedOutputStream os = new BufferedOutputStream(output);
            do {
                byteCount = is.read(buffer);
                if (byteCount > 0) {
                    os.write(buffer, 0, byteCount);
                }
            } while (byteCount > 0);
            os.flush();
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Cannot copy stream", e);
        }
        return false;
    }

    public static boolean copy(File source, File destination) {
        if (source == null || destination == null || !source.canRead() || destination.exists()) {
            return false;
        }
        try {
            return copy(new FileInputStream(source), new FileOutputStream(destination));
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Cannot copy file", ex);
        }
        return false;
    }

    /**
     * Read content of a file as a string
     *
     * @param filepath
     * @return null if the file's content couldn't be read
     */
    public static String read(String filepath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filepath)), "UTF-8");
        } catch (Exception ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, "Cannot read file content", ex);
            return null;
        }
    }

    /**
     * Get input stream from context.<br/>
     * e.g. context's type is name.space.Foo<br/>
     * In order to get name.space.afile.txt:<br/>
     * context instanceof Foo<br/>
     * name = afile.txt<br/>
     * In order to get name.space.asub.afile.txt:<br/>
     * context instanceof Foo<br/>
     * name = asub/afile.txt<br/>
     *
     * @param context
     * @param name
     * @return
     */
    public static InputStream getStream(Object context, String name) {
        if (context == null) {
            return null;
        } else {
            return context.getClass().getResourceAsStream(name);
        }
    }

    /**
     * Read file's content with default charset (UTF-8)
     *
     * @param context
     * @param filename
     * @return
     */
    public static String read(Object context, String filename) {
        return read(context, filename, UTF8);
    }

    public static String read(Object context, String filename, Charset charset) {
        if (context == null) {
            return null;
        } else {
            try {
                Path p = Paths.get(context.getClass().getResource(filename).toURI());
                return new String(Files.readAllBytes(p), charset);
            } catch (URISyntaxException ex) {
                logger.log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Read all lines in a file with charset UTF-8
     *
     * @param context
     * @param filename
     * @return
     */
    public static List<String> readLines(Object context, String filename) {
        return readLines(context, filename, UTF8);
    }

    public static List<String> readLines(Object context, String filename, Charset charset) {
        if (context == null) {
            return null;
        } else {
            try {
                Path p = Paths.get(context.getClass().getResource(filename).toURI());
                return Files.readAllLines(p, charset);
            } catch (URISyntaxException ex) {
                logger.log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static void delete(Path pathToFile) throws IOException {
        Files.deleteIfExists(pathToFile);
    }

    public static void delete(String pathToFile) throws IOException {
        delete(Paths.get(pathToFile));
    }
}
