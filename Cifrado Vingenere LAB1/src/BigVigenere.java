import java.util.Scanner;

public class BigVigenere {
    private String clave;
    private char[][] alfabeto;
    private static final Scanner scanner = new Scanner(System.in);

    public BigVigenere(String claveTexto) {
        this.clave = claveTexto;
        generarAlfabeto();
    }

    private void generarAlfabeto() { //O(1)
        String caracteres = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
        alfabeto = new char[64][64];
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                alfabeto[i][j] = caracteres.charAt((i + j) % 64);
            }
        }
    }

    private int getIndex(char c) { //O(1)
        String caracteres = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
        return caracteres.indexOf(c);
    }

    public String encrypt(String mensaje) { //O(n)
        long inicio = System.currentTimeMillis();
        StringBuilder cifrado = new StringBuilder();
        int j = 0;

        for (int i = 0; i < mensaje.length(); i++) {
            char caracterMensaje = mensaje.charAt(i);

            if (caracterMensaje == ' ') {
                cifrado.append(' ');
                continue;
            }

            if (caracterMensaje == '\n') {
                cifrado.append('\n');
                continue;
            }

            char caracterClave = clave.charAt(j % clave.length());
            j++;

            int indiceMensaje = getIndex(caracterMensaje);
            int indiceClave = getIndex(caracterClave);

            if (indiceMensaje == -1 || indiceClave == -1) {
                cifrado.append(caracterMensaje);
            } else {
                cifrado.append(alfabeto[indiceMensaje][indiceClave]);
            }
        }
        long fin = System.currentTimeMillis();
        System.out.println("Tiempo: " + ((fin - inicio)) + " ms");
        return cifrado.toString();
    }

    public String decrypt(String mensajeCifrado) { //O(n)
        long inicio = System.currentTimeMillis();
        StringBuilder descifrado = new StringBuilder();
        int j = 0;

        for (int i = 0; i < mensajeCifrado.length(); i++) {
            char caracterCifrado = mensajeCifrado.charAt(i);

            if (caracterCifrado == ' ') {
                descifrado.append(' ');
                continue;
            }

            if (caracterCifrado == '\n') {
                descifrado.append('\n');
                continue;
            }

            char caracterClave = clave.charAt(j % clave.length());
            j++;

            int indiceClave = getIndex(caracterClave);
            if (indiceClave == -1) {
                descifrado.append(caracterCifrado);
                continue;
            }

            int indiceMensaje = -1;
            for (int k = 0; k < 64; k++) {
                if (alfabeto[k][indiceClave] == caracterCifrado) {
                    indiceMensaje = k;
                    break;
                }
            }

            if (indiceMensaje == -1) {
                descifrado.append(caracterCifrado);
            } else {
                descifrado.append(alfabeto[indiceMensaje][0]);
            }
        }
        long fin = System.currentTimeMillis();
        System.out.println("Tiempo: " + ((fin - inicio)) + " ms");
        return descifrado.toString();
    }

    public char optimalSearch(int posicion) { //O(p) p = posicion
        boolean[][] visitado = new boolean[64][64];
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        int dir = 0;
        int x = 0, y = 0;

        for (int i = 0; i < posicion; i++) {
            visitado[x][y] = true;
            int nuevoX = x + dx[dir];
            int nuevoY = y + dy[dir];
            if (nuevoX >= 0 && nuevoX < 64 && nuevoY >= 0 && nuevoY < 64 && !visitado[nuevoX][nuevoY]) {
                x = nuevoX;
                y = nuevoY;
            } else {
                dir = (dir + 1) % 4;
                x += dx[dir];
                y += dy[dir];
            }
        }
        return alfabeto[x][y];
    }

    public static void main(String[] args) { //O(n + p)
        System.out.print("Ingrese la clave de cifrado: ");
        String claveTexto = scanner.nextLine();
        BigVigenere cifrador = new BigVigenere(claveTexto);

        System.out.println("Ingrese si desea Cifrar o Descifrar un mensaje.");
        System.out.println("Cifrar: C/c");
        System.out.println("Descifrar: D/d");
        String eleccion = scanner.nextLine() ;

        StringBuilder sb = new StringBuilder();
        String linea;

        if(eleccion.equals("C") || eleccion.equals("c")){
            System.out.print("Ingrese un mensaje a cifrar (escribe 'EOF' en una línea nueva para finalizar): ");
            while (true) {
                linea = scanner.nextLine();
                if (linea.equals("EOF")) {
                    break;
                }
                sb.append(linea).append("\n");
            }
            String mensaje = sb.toString();
            String mensajeCifrado = cifrador.encrypt(mensaje);
            System.out.println("Mensaje cifrado: " + mensajeCifrado);
        } else if(eleccion.equals("D") || eleccion.equals("d")){
            System.out.print("Ingrese un mensaje a descifrar (escribe 'EOF' en una línea nueva para finalizar: ");
            while (true) {
                linea = scanner.nextLine();
                if (linea.equals("EOF")) {
                    break;
                }
                sb.append(linea).append("\n");
            }
            String mensaje = sb.toString();
            String mensajeDescifrado = cifrador.decrypt(mensaje);
            System.out.println("Mensaje descifrado: " + mensajeDescifrado);
        }

        System.out.print("Ingrese una posición para búsqueda óptima: ");
        int posicion = scanner.nextInt();
        System.out.println("Carácter encontrado en espiral: " + cifrador.optimalSearch(posicion));

        scanner.close();
    }
}
