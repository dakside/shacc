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
package dakside.utils;

import org.dakside.utils.FileUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class FileUtilTest {

    public FileUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    private static final String TEST_FILE = "/tmp/test.pdf";
    File source = new File(TEST_FILE);
    File destination = new File(TEST_FILE + ".copy");
    byte[] sourceByte = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    byte[] destinationByte = null;

    @Before
    public void setUp() {
        try {
            if (destination.exists()) {
                destination.delete();
            }
        } catch (Exception ex) {
        }

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of copy method, of class FileUtil.
     */
    @Test
    public void testCopy_InputStream_OutputStream() {
        System.out.println("copy inputstream to outputstream");
        ByteArrayInputStream in = new ByteArrayInputStream(sourceByte);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        assertTrue(FileUtil.copy(in, out));
        assertArrayEquals(sourceByte, out.toByteArray());
    }

    /**
     * Test of copy method, of class FileUtil.
     */
    @Test
    public void testCopy_File_File() {
        System.out.println("copy file");
        assertTrue(FileUtil.copy(source, destination));

        //test null
        source = null;
        destination = null;
        assertFalse(FileUtil.copy(source, destination));

        source = null;
        destination = new File(TEST_FILE + "notthere");
        assertFalse(FileUtil.copy(source, destination));

        source = new File(TEST_FILE);
        destination = null;
        assertFalse(FileUtil.copy(source, destination));

    }
}
