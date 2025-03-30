import java.io.*;
import java.util.*;

public class BigVigenere() {
    private int[] clave;
    private char[][] alfabeto;
    private Scanner scanner = new Scanner(System.in);

    public BigVigenere() {
        System.out.println("Ingrese la clave numericas: ");
        String claveNumerica = scanner.nextLine();
        inicializarClave(claveNumerica);
        generarAlfabeto();
    }

    public BigVigenere(String claveNumerica) {
        inicializarClave(claveNumerica);
        generarAlfabeto();
    }

    private void inicializarClave(String claveNumerica) {
        clave = new int[claveNumerica.length()];
        for(int i = 0; i < claveNumerica.length(); i++){
            clave[i] = Character.getNumericValue(claveNumerica.charAt(i));
        }
    }

    private void generarAlfabeto() {
        String caracteres = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        alfabeto = new char[64][64];
        int indice = 0;
        for (int i = 0; i < 64; i++){
            for (int j = 0; j < 64; j++){
                alfabeto[i][j] = caracteres.charAt((i + j) % 64);

            }
        }
    }

    public String encrypt(String mensaje) {
        StringBuilder cifrado = new StringBuilder();
        for (int i = 0; i < mensaje.length(); i++){
            char caracter = mensaje.charAt(i);
            int indiceMensaje = new String(alfabeto[0]).indexOf(caracter);
            int indiceClave = clave[i % clave.length];
            cifrado.append(alfabeto[indiceMensaje][indiceClave]);
        }
        return cifrado.toString();
    }

    public String decrypt(String mensajeCifrado) {
        StringBuilder descifrado = new StringBuilder();
        for (int i = 0; i < mensajeCifrado.length(); i++){
            char caracter = mensajeCifrado.charAt(i);
            int indiceClave = clave[i % clave.length];
            int indiceMensaje = -1;
            for (int j = 0; j < 64; j++) {
                if (alfabeto[j][indiceClave] == caracter) {
                    indiceMensaje = j;
                    break;
                }
            }
            descifrado.append(alfabeto[indiceMensaje][0]);
        }
        return descifrado.toString();
    }

    public void reEncrypt() {

        System.out.println("Ingrese el mensaje encriptado: ");
        String mensajeCifrado = scanner.nextLine();
        String mensajeDescifrado = decrypt(mensajeCifrado);
        System.out.print("Ingrese la nueva clave numerica: ");
        String nuevaClave = scanner.nextLine();
        inicializarClave(nuevaClave);
        String nuevoMensajeCifrado = encrypt(mensajeDescifrado);
        System.out.println("Nuevo mensaje cifrado: " + nuevoMensajeCifrado);
    }

    public char search(int posicion) {
        int fila = posicion / 64;
        int columna = posicion % 64;
        return alfabeto[fila][columna];
    }

    public char optimalSearch(int posicion) {
        return search(posicion);
    }
}