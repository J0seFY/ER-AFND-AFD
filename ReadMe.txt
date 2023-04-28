Integrantes:
- Diego Arteaga Mendoza
- Christian Diaz Reyes
- José Fuentes Yáñez

Referencias obtenidas desde: https://github.com/maticou/ER-to-AFND-to-AFD

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

El presente proyecto corresponde a un sistema que permite la entrada mediante el teclado de una expresión regular, la cual posteriormente es intervenida para generar un AFND, ese AFND sera interpretado en un archivo de extención "dot", el cual pasara por el software Graphviz, software que permite transformar la información escrita en el archivo "dot", llamado "Automata1" para el AFND, convirtiendola a una imagen ".jpg", para poder ser visualizada
con facilidad.

Posteriormente este automata finito no deterministico es transformado a un AFD, el cual sigue un procedimiento similar, llegando a obtener una imagen para su interpretación.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

El programa cuenta con un total de 8 archivos ".java" dento del folder src, en este el main corresponde al archivo "Test.java", encargado de recibir la ER y ademas de inicializar los archivos "ProcessBuilderDemo1 y ProcessBuilderDemo2" encargados de crear las imagenes.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

La entrada del Test.java solicita que se ingrese la ER, la cual puede llevar los siguientes conectores "*,|,.,(,)".
