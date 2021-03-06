package org.code4everything.boot.constant;

/**
 * 整数常量类
 *
 * @author pantao
 * @since 2018/11/2
 **/
public class IntegerConsts {

    public static final int ONE_THOUSAND_AND_TWENTY_FOUR = 1024;

    public static final int EIGHT = 8;

    public static final int FIVE = 5;

    public static final int ZERO = 0;

    public static final int ONE = 1;

    public static final int TWO = 2;

    public static final int THREE = 3;

    public static final int FOUR = 4;

    public static final int SIX = 6;

    public static final int SEVEN = 7;

    public static final int NINE = 9;

    public static final int TEN = 10;

    private IntegerConsts() {}

    public class FileSize {

        public static final long KB = ONE_THOUSAND_AND_TWENTY_FOUR;

        public static final long MB = KB * ONE_THOUSAND_AND_TWENTY_FOUR;

        public static final long GB = MB * ONE_THOUSAND_AND_TWENTY_FOUR;

        public static final long TB = GB * ONE_THOUSAND_AND_TWENTY_FOUR;

        private FileSize() {}
    }
}
