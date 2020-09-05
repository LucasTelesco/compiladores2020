# compiladores2020
Trabajo de compiladores 2020 - Grupo 10
Temas particulares
 Análisis Léxico

3. Enteros largos: Constantes enteras con valores entre
–231 y 231 – 1. Estas constantes llevarán el sufijo “_l”.
Se debe incorporar a la lista de palabras reservadas la palabra
LONGINT.

5. Flotantes: Números reales con signo y parte
exponencial. El exponente comienza con la letra f
(minúscula) y llevará signo. La parte exponencial puede
estar ausente.
Ejemplos válidos: 1. .6 -1.2 3.f–5 2.f+34
2.5f-1 15. 0.
Considerar el rango
1.17549435f-38 < x < 3.40282347f+38 
-3.40282347f+38 < x < -1.17549435f-38  0.0
Se debe incorporar a la lista de palabras reservadas la
palabra FLOAT.

13. Incorporar a la lista de palabras reservadas las
palabras LOOP y UNTIL.

18. Comentarios de 1 línea: Comentarios que comiencen
con “%%” y terminen con el fin de línea.

21. Cadenas multilínea: Cadenas de caracteres que
comiencen y terminen con “ “” . Estas cadenas pueden
ocupar más de una línea, y en dicho caso, al final de cada
línea, excepto la última, debe aparecer un guión “ - ”. (En la
Tabla de símbolos se guardará la cadena sin el guión, y sin el
salto de línea.
