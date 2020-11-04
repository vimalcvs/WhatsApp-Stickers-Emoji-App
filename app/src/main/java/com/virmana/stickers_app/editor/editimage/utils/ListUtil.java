package com.virmana.stickers_app.editor.editimage.utils;

import java.util.List;

/**
 * Created by Vimal-CVS on 04/11/2020.
 */
public class ListUtil {
    public static boolean isEmpty(List list) {
        if (list == null)
            return true;

        return list.size() == 0;
    }

}//end class
