package com.openquartz.easyfile.common.util;

import com.openquartz.easyfile.common.exception.Asserts;
import com.openquartz.easyfile.common.exception.CommonErrorCode;
import java.util.Collection;
import java.util.Map;
/**
 * param utils
 *
 * @author svnee
 **/
public final class ParamUtils {

    private ParamUtils() {
    }

    public static void checkNotNull(Object obj) {
        Asserts.notNull(obj, CommonErrorCode.PARAM_ILLEGAL_ERROR);
    }

    public static void checkNotEmpty(String str) {
        Asserts.isTrue(StringUtils.isNotBlank(str), CommonErrorCode.PARAM_ILLEGAL_ERROR);
    }

    public static void checkNotEmpty(Collection<?> collection) {
        Asserts.notEmpty(collection, CommonErrorCode.PARAM_ILLEGAL_ERROR);
    }

    public static void checkNotEmpty(Map<?, ?> map) {
        Asserts.notEmpty(map, CommonErrorCode.PARAM_ILLEGAL_ERROR);
    }

    public static void checkArgument(boolean b) {
        Asserts.isTrue(b, CommonErrorCode.PARAM_ILLEGAL_ERROR);
    }

}
