package padroesestruturais.composite;

import java.util.Locale;

/** Utilitário de formatação monetária (independente do idioma do sistema). */
final class Formato {

    private Formato() {}

    static String moeda(double valor) {
        return String.format(Locale.US, "R$ %.2f", valor);
    }

    static String recuo(int nivel) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nivel; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }
}
