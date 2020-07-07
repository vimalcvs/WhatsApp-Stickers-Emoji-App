package com.vimalcvs.stickers_app.editor.editimage.utils;

import java.util.List;


/**
 * Created by vimalcvs on 27/11/2017.
 */
public class ListUtil {
    public static boolean isEmpty(List list) {
        if (list == null)
            return true;

        return list.size() == 0;
    }

}//end class
