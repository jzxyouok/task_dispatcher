package com.hptpd.taskdispatcherserver.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {
    /**
     * 将文字转为汉语拼音
     * @param chineseLanguage 要转成拼音的中文
     */
//    public String toHanyuPinyin(String ChineseLanguage){
//        char[] cl_chars = ChineseLanguage.trim().toCharArray();
//        String hanyupinyin = "";
//        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
//        // 输出拼音全部小写
//        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        // 不带声调
//        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
//        try {
//            for (int i=0; i<cl_chars.length; i++){
//                if (String.valueOf(cl_chars[i]).matches("[\u4e00-\u9fa5]+")){
//                    // 如果字符是中文,则将中文转为汉语拼音
//                    hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0];
//                } else {
//                    // 如果字符不是中文,则不转换
//                    hanyupinyin += cl_chars[i];
//                }
//            }
//        } catch (BadHanyuPinyinOutputFormatCombination e) {
//            System.out.println("字符不能转成汉语拼音");
//        }
//        return hanyupinyin;
//    }

    public static String getFirstLettersUp(String chineseLanguage){
        return getFirstLetters(chineseLanguage ,HanyuPinyinCaseType.UPPERCASE);
    }

    public static String getFirstLettersLo(String chineseLanguage){
        return getFirstLetters(chineseLanguage ,HanyuPinyinCaseType.LOWERCASE);
    }

    public static String getFirstLetters(String chineseLanguage,HanyuPinyinCaseType caseType) {
        char[] clChars = chineseLanguage.trim().toCharArray();
        StringBuffer hanyupinyinSB = new StringBuffer();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出拼音全部大写
        defaultFormat.setCaseType(caseType);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            for (int i = 0; i < clChars.length; i++) {
                String str = String.valueOf(clChars[i]);
                if (str.matches("[\u4e00-\u9fa5]+")) {
                    // 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                    hanyupinyinSB.append(PinyinHelper.toHanyuPinyinStringArray(clChars[i], defaultFormat)[0].substring(0, 1));
                } else if (str.matches("[0-9]+")) {
                    // 如果字符是数字,取数字
                    hanyupinyinSB.append(clChars[i]);
                } else if (str.matches("[a-zA-Z]+")) {
                    // 如果字符是字母,取字母
                    hanyupinyinSB.append(clChars[i]);
                } else {// 否则不转换
                    //如果是标点符号的话，带着
                    hanyupinyinSB.append(clChars[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            System.out.println("字符不能转成汉语拼音");
        }
        return hanyupinyinSB.toString();
    }

    public static String getPinyinString(String chineseLanguage){
        char[] clChars = chineseLanguage.trim().toCharArray();
        StringBuffer hanyupinyinSB = new StringBuffer();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出拼音全部大写
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            for (int i = 0; i < clChars.length; i++) {
                String str = String.valueOf(clChars[i]);
                if (str.matches("[\u4e00-\u9fa5]+")) {
                    // 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                    hanyupinyinSB.append(PinyinHelper.toHanyuPinyinStringArray(
                            clChars[i], defaultFormat)[0]);
                } else if (str.matches("[0-9]+")) {
                    // 如果字符是数字,取数字
                    hanyupinyinSB.append(clChars[i]);
                } else if (str.matches("[a-zA-Z]+")) {
                    // 如果字符是字母,取字母
                    hanyupinyinSB.append(clChars[i]);
                } else {// 否则不转换
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            System.out.println("字符不能转成汉语拼音");
        }
        return hanyupinyinSB.toString();
    }
    /**
     * 取第一个汉字的第一个字符
     *
     * @return String
     */
    public static String getFirstLetter(String chineseLanguage){
        char[] cl_chars = chineseLanguage.trim().toCharArray();
        StringBuffer hanyupinyinSB = new StringBuffer();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出拼音全部大写
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            String str = String.valueOf(cl_chars[0]);
            if (str.matches("[\u4e00-\u9fa5]+")) {
                // 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                hanyupinyinSB.append(PinyinHelper.toHanyuPinyinStringArray(
                        cl_chars[0], defaultFormat)[0].substring(0, 1));
            } else if (str.matches("[0-9]+")) {
                // 如果字符是数字,取数字
                hanyupinyinSB.append(cl_chars[0]);
            } else if (str.matches("[a-zA-Z]+")) {
                // 如果字符是字母,取字母
                hanyupinyinSB.append(cl_chars[0]);
            } else {// 否则不转换
                hanyupinyinSB.append(cl_chars[0]);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            System.out.println("字符不能转成汉语拼音");
        }
        return hanyupinyinSB.toString().toUpperCase();
    }

    public static void main(String[] args) {
        PinyinUtil hanyuPinyinHelper = new PinyinUtil();
//        System.out.println(hanyuPinyinHelper.toHanyuPinyin("多发的发独守空房阿道夫打发第三方"));
        System.out.println(PinyinUtil.getFirstLetter("多发的发独守空房阿道夫打发第三方"));
        System.out.println(PinyinUtil.getFirstLetters("多发的发独守空房阿道夫打发第三方", HanyuPinyinCaseType.UPPERCASE));
        System.out.println(PinyinUtil.getFirstLettersLo("多发的发独守空房阿道夫打发第三方"));
        System.out.println(PinyinUtil.getFirstLettersUp("多发的发独守空房阿道夫打发第三方"));
        System.out.println(PinyinUtil.getPinyinString("多发的发独守空房阿道夫打发第三方"));
        System.out.println(PinyinUtil.getFirstLetter("###多发的发独守空房阿道夫打发第三方"));
        System.out.println(PinyinUtil.getFirstLetter("__多发的发独守空房阿道夫打发第三方"));
        System.out.println(PinyinUtil.getFirstLetter("@@多发的发独守空房阿道夫打发第三方"));
        System.out.println(PinyinUtil.getFirstLetter("aahf多发的发独守空房阿道夫打发第三方"));
    }
}