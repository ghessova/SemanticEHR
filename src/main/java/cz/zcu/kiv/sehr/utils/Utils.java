package cz.zcu.kiv.sehr.utils;

import javax.ws.rs.core.Response;

/**
 * Created by ghessova on 20.12.16.
 */
public class Utils {

    public static PagingParams processPagingParams(String from, String count) {
        int skip;
        int limit;
        try {
            skip = Integer.parseInt(from);
            limit = Integer.parseInt(count);
        } catch (NumberFormatException e) {
            return null;
        }
        if (skip < 0 || limit < 0) {
            return null;
        }
        else {
            return new PagingParams(skip, limit);
        }
    }

    public static boolean isEmpty(String s) {
        if (s == null) {
            return true;
        }
        else if ("".equals(s)) {
            return true;
        }
        return false;
    }
}
