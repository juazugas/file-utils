package com.defimak47.file.utils;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.equalTo;

/**
 * Clase para testear la clase de utilidades de conversión de escala de bytes.
 */
public class FileSizeUtilsTest {

    /**
     * @see FileSizeUtils#getShift(String)
     */
    @Test
    public void testGetShift () {
        String[] scaleToTest = { "B", "k", "M", "GB", "Tera", "PetaBytes", "E", "Z", "Y" };
        Long[] shift = new Long[scaleToTest.length];
        _given: {

        }

        _then: {
            for (int i = 0; i < scaleToTest.length; i++) {
                shift[i] = FileSizeUtils.getShift(scaleToTest[i]);
            }
        }

        _expect: {
            for (int index = 0; index < shift.length; index++) {
                assertThat(shift[index], equalTo(index*1L));
            }
            assertThat(shift, arrayContaining(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L));
        }
    }

    /**
     * @see FileSizeUtils#getShift(String)
     */
    @Test
    public void testGetShiftWhenNullOrEmptyScaleReturns0 () {
        String nullScale = null, emptyScale = null;
        long nullShift = -1L, emptyShift = -1L;
        _given: {
            nullScale = null;
            emptyScale = "";
        }

        _then: {
            nullShift = FileSizeUtils.getShift(nullScale);
            emptyShift = FileSizeUtils.getShift(emptyScale);
        }

        _expect: {
            assertThat(nullShift, equalTo(0L));
            assertThat(emptyShift, equalTo(0L));
        }
    }


    /**
     * @see FileSizeUtils#scaleSize(long, String, Class)
     */
    @Test
    public void testScaleSizeAsInteger () {
        String scale = null;
        long size = -1L;
        int scaledSize = -1;
        _given: {
            scale = "M";
            size = 123456789L;
        }
        _then: {
            scaledSize = FileSizeUtils.scaleSize(size, scale, Integer.class);
        }
        _expect: {
            assertThat(scaledSize, equalTo((int) (size / 1024 / 1024)));
        }
    }

    /**
     * @see FileSizeUtils#scaleSize(long, String, Class)
     */
    @Test
    public void testScaleSizeAsLong () {
        String scale = null;
        long size = -1L;
        long scaledSize = -1L;
        _given: {
            scale = "M";
            size = 123456789L;
        }
        _then: {
            scaledSize = FileSizeUtils.scaleSize(size, scale, Long.class);
        }
        _expect: {
            assertThat(scaledSize, equalTo((long)(size/1024/1024)));
        }
    }

    /**
     * @see FileSizeUtils#scaleSize(long, String, Class)
     */
    @Test
    public void testScaleSizeAsDouble () {
        String scale = null;
        long size = -1L;
        double scaledSize = -1.0;
        _given: {
            scale = "M";
            size = 123456789L;
        }
        _then: {
            scaledSize = FileSizeUtils.scaleSize(size, scale, Double.class);
        }
        _expect: {
            assertThat(scaledSize, equalTo(((double)size/1024/1024)));
        }
    }

    /**
     * @see FileSizeUtils#scaleSize(long, String, Class)
     */
    @Test
    public void testScaleSizeWhenInvalidSizeReturnsMinusOne () {
        String scale = null;
        long size = 0L;
        int invalidSize = -1;
        _given: {
            scale = "M";
            size = -12345L;
        }
        _then: {
            invalidSize = FileSizeUtils.scaleSize(size, scale, Integer.class);
        }
        _expect: {
            assertThat(invalidSize, equalTo(-1));
        }
    }

    /**
     * @see FileSizeUtils#scaleSize(long, String, Class)
     */
    @Test
    public void testScaleSizeToInt () {
        String scale = null;
        long size = -1L;
        int scaledSize = -1;
        _given: {
            scale = "M";
            size = 123456789L;
        }
        _then: {
            scaledSize = FileSizeUtils.scaleSizeToInt(size, scale);
        }
        _expect: {
            assertThat(scaledSize, equalTo((int)(size/1024/1024)));
        }
    }

    /**
     * @see FileSizeUtils#scaleSizeToDouble(long, String)
     */
    @Test
    public void testScaleSizeToDouble () {
        String scale = null;
        long size = -1L;
        double scaledSize = -1.0;
        _given: {
            scale = "M";
            size = 123456789L;
        }
        _then: {
            scaledSize = FileSizeUtils.scaleSizeToDouble(size, scale);
        }
        _expect: {
            assertThat(scaledSize, equalTo(((double)size/1024/1024)));
        }
    }

}
