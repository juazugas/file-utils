package com.defimak47.file.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Clase de utilidades para el tamaño de ficheros.
 *
 */
public final class FileSizeUtils {


    /**
     * Cadena de escalas de Bytes.
     * Bytes, Kilos, Megas, Gigas, Teras, Petas, Exa, Zettas, Yottas, ...
     */
    public static final String BYTE_SCALE = "BKMGTPEZY";

    /**
     * Cantidad de saltos entre escalas.
     */
    private static final long BYTES_SCALE_NUMBER = 1024;

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
     * Friendly method to scale conversion to Integer.
     *
     * @param size
     * @param scale
     * @return
     */
    public static double scaleSizeToDouble (long size, String scale) {
        return scaleSize(size, scale, Double.class);
    }

    /**
     * Obtiene el divisor del tamaño para escalar el tamaño de bytes.
     *
     * @param scale
     * @return
     */
    public static long getShift (String scale) {
        return (StringUtils.isEmpty(scale)) ? 0 : StringUtils.indexOfIgnoreCase(BYTE_SCALE, StringUtils.substring(scale,0,1));
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
        T result = null;
        double flsize = invalidSize(size) ? -1.0 : size;
        double dsize  = flsize / Math.pow(BYTES_SCALE_NUMBER,getShift(scale));
        // comprobamos que no resulte negativo.
        if (dsize < 0) {
            dsize = -1;
        }

        if (Integer.class.isAssignableFrom(clazz)) {
            result = (T) Integer.valueOf(Double.valueOf(dsize).intValue());
        } else if (Long.class.isAssignableFrom(clazz)) {
            result = (T) Long.valueOf(Double.valueOf(dsize).longValue());
        } else if (Double.class.isAssignableFrom(clazz)) {
            result = (T) Double.valueOf(dsize);
        } else if (BigDecimal.class.isAssignableFrom(clazz)) {
            result = (T) BigDecimal.valueOf(dsize);
        }
        return result;
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

