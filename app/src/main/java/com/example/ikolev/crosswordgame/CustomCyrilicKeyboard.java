package com.example.ikolev.crosswordgame;


/**
 * Created by ikolev on 8/11/2015.
 */
public class CustomCyrilicKeyboard {

    public static String getChar(int id) {
        switch (id) {
            case R.id.Cyrilic_a:
                return "\u0430";
            case R.id.Cyrilic_b:
                return "\u0431";
            case R.id.Cyrilic_v:
                return "\u0432";
            case R.id.Cyrilic_g:
                return "\u0433";
            case R.id.Cyrilic_d:
                return "\u0434";
            case R.id.Cyrilic_e:
                return "\u0435";
            case R.id.Cyrilic_j:
                return "\u0436";
            case R.id.Cyrilic_z:
                return "\u0437";
            case R.id.Cyrilic_i:
                return "\u0438";
            case R.id.Cyrilic_i_kratko:
                return "\u0439";
            case R.id.Cyrilic_k:
                return "\u043A";
            case R.id.Cyrilic_l:
                return "\u043B";
            case R.id.Cyrilic_m:
                return "\u043C";
            case R.id.Cyrilic_n:
                return "\u043D";
            case R.id.Cyrilic_o:
                return "\u043E";
            case R.id.Cyrilic_p:
                return "\u043F";
            case R.id.Cyrilic_r:
                return "\u0440";
            case R.id.Cyrilic_s:
                return "\u0441";
            case R.id.Cyrilic_t:
                return "\u0442";
            case R.id.Cyrilic_u:
                return "\u0443";
            case R.id.Cyrilic_f:
                return "\u0444";
            case R.id.Cyrilic_h:
                return "\u0445";
            case R.id.Cyrilic_c:
                return "\u0446";
            case R.id.Cyrilic_ch:
                return "\u0447";
            case R.id.Cyrilic_sh:
                return "\u0448";
            case R.id.Cyrilic_sht:
                return "\u0449";
            case R.id.Cyrilic_er_goliam:
                return "\u044A";
            case R.id.Cyrilic_er_malak:
                return "\u044C";
            case R.id.Cyrilic_iu:
                return "\u044E";
            case R.id.Cyrilic_ya:
                return "\u044F";
        }
        return null;
    }

}