package services;

import io.netty.util.internal.StringUtil;

/**
 * This service class is used for doing Manipulation on String
 * Created by kartik.raina on 12/27/2016.
 */
public class StringServices {

    /**
     * This method reverses the string that is given to it.
     * @param stringToBeReversed The string that has to be reversed.
     * @return The reversed {@link String}.
     */
    public String reverseString(String stringToBeReversed){

        if(StringUtil.isNullOrEmpty(stringToBeReversed)){
            return "";
        }

        return new StringBuilder(stringToBeReversed).reverse().toString();
    }
}
