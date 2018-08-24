# Biometria

Proyecto realizado para la asignatura de "Biometría y Seguridad de Sistemas" del Grado de Ingeniería Informática en Ingeniería de Software cursado en la Universidad de Extremadura.

El objetivo de la aplicación es realizar un preprocesado a la imagen de la huella dactilar para posteriormente identificar los rasgos que la caracterizan y diferencian, estas son las minucias. Con los datos obtenidos de la aplicación, se podría hacer una búsqueda en una base de datos de huellas dactilares para proceder a su identificación.

Los tratamientos previos que realiza incluyen:
- Conversión a escala de grises
- Ecualizados
- Eliminación de ruido binario
- Filtrado de convolución
- Adelgazado mediante el algoritmo de Zhang-Suen

Las operaciones posteriores a estos tratamientos son:
- Deteción de minucias
- Cálculo de los ángulos de las minucias

![Vista general de la aplicación](https://github.com/jfbermejo/Biometria/blob/master/vistaAplicacion.jpg)
