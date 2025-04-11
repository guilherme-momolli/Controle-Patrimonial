//package br.com.guilherme_momolli.controle_patrimonial.configs;
//
//import com.fasterxml.jackson.core.io.CharacterEscapes;
//import com.fasterxml.jackson.core.io.SerializedString;
//
//import java.util.Arrays;
//
//public class UTF8CharacterEscapes extends CharacterEscapes {
//    private final int[] asciiEscapes;
//
//    public UTF8CharacterEscapes() {
//        int[] baseEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
//
//        asciiEscapes = Arrays.copyOf(baseEscapes, 256);
//
//        for (int i = 128; i < 256; i++) {
//            asciiEscapes[i] = CharacterEscapes.ESCAPE_CUSTOM;
//        }
//    }
//
//    @Override
//    public int[] getEscapeCodesForAscii() {
//        return asciiEscapes;
//    }
//
//    @Override
//    public SerializedString getEscapeSequence(int ch) {
//        return new SerializedString(String.valueOf((char) ch));
//    }
//}
