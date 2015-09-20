package com.defimak47.file.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Clase de utilidades para el tamaño de ficheros.
 *
 */
public class FileSizeUtils {


    /**
     * Evitar instancias de la clase estática.
     */
    private FileSizeUtils () {
        /* no-op constructor */
    }

    /**
     * Friendly method to scale conversion to Integer.
     *
     * @param size
     * @param scale
     * @return
     */
    public static int scaleSizeToInt (long size, String scale) {
        return scaleSize(size, scale, Integer.class);
    }

    /**
     * Cadena de escalas de Bytes.
     * Bytes, Kilos, Megas, Gigas, Teras, Petas, Exa, Zettas, Yottas, ...
     */
    public static final String BYTE_SCALE = "BKMGTPEZY";

    /**
     * Obtiene el divisor del tamaño para escalar el tamaño de bytes.
     *
     * @param scale
     * @return
     */
    public static long getShift (String scale) {
        return (StringUtils.isEmpty(scale)) ? 0 : StringUtils.indexOfIgnoreCase(BYTE_SCALE, StringUtils.substring(scale,0,1))*10;
    }

    /**
     * Convierte el tamaño en bytes introducido al tipo y la escala solicitados.
     * Si el tamaño es menor que 0 devuelve -1, o nulo si no puede realizar la conversión
     * al objeto de retorno.
     *
     * @param size
     *              long con tamaño en bytes.
     * @param scale
     *              letra de escala (null o vacío, devuelve bytes).
     * @param clazz
     *              clase del objeto de retorno.
     * @return
     */
    public static <T extends Number> T scaleSize (long size, String scale, Class<T> clazz) {
        long flsize = invalidSize(size) ? -1 : size;
        long lsize  = flsize >> getShift(scale);
        // comprobamos que no resulte negativo.
        if (lsize < 0) {
            lsize = -1;
        }

        if (Integer.class.isAssignableFrom(clazz)) {
            return (T) new Integer(new Long(lsize).intValue());
        } else if (Long.class.isAssignableFrom(clazz)) {
            return (T) new Long(lsize);
        } else if (Double.class.isAssignableFrom(clazz)) {
            return (T) new Double(new Long(lsize).doubleValue());
        } else if (BigDecimal.class.isAssignableFrom(clazz)) {
            return (T) BigDecimal.valueOf(lsize);
        } else {
            return null;
        }
    }

    /**
     * Comprueba que el tamaño introducido sea válido
     *
     * @param size
     *          tamaño a comprobar la validad.
     * @return
     */
    private static boolean invalidSize(long size) {
        return size < 0;
    }

}
