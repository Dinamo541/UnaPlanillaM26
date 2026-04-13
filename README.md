<div align="center">
   <img width=100% src=https://capsule-render.vercel.app/api?type=waving&height=100&color=gradient&reversal=true />
</div>

<h1 align="center">
  UNA Planilla 2026 Repositorio 
</h1>

## Autor y direccion academica

Este proyecto fue desarrollado y es impartido academicamente por el Master Carlos Carranza Blanco.

## Descripcion

UNA Planilla 2026 es una aplicacion de escritorio desarrollada en Java con JavaFX, enfocada en la gestion basica de procesos de planilla. El proyecto organiza su logica por capas (controladores, modelos y utilidades) para separar la interfaz grafica del manejo de datos y del flujo de navegacion entre pantallas.

De forma general, el sistema incluye modulos para inicio de sesion, administracion de empleados y manejo de tipos de planilla, permitiendo centralizar operaciones comunes de una planilla en una estructura clara y mantenible.

## Tecnologias utilizadas

- Java
- JavaFX (FXML y CSS)
- Maven

## Enfoque academico del proyecto

El objetivo principal de UNA Planilla 2026 no es construir un producto empresarial final, sino servir como una base de practica para aprender Java de forma aplicada. En lugar de estudiar solo teoria, este proyecto permite ver como se conectan los conceptos del lenguaje dentro de una aplicacion real de escritorio.

El valor academico esta en que cada modulo representa una oportunidad de aprendizaje: interfaz grafica, controladores, estructuras de datos simples, flujo de pantallas y organizacion del codigo.

## Objetivos de aprendizaje

- Comprender la estructura de un proyecto Java con Maven.
- Practicar la separacion por capas (vista, controlador, modelo y utilidades).
- Entender como JavaFX usa archivos FXML para definir interfaces.
- Aprender a controlar eventos de interfaz (botones, formularios, navegacion).
- Fortalecer logica de programacion orientada a objetos con clases y responsabilidades claras.
- Desarrollar criterio para escribir codigo legible y mantenible.

## Conceptos explicados por puntos

### 1. Programacion orientada a objetos (POO)

El proyecto esta dividido en clases con responsabilidades especificas. Por ejemplo, un controlador no deberia encargarse de todo; su papel es coordinar acciones de la vista y apoyarse en otras clases.

Que se aprende aqui:
- Encapsulamiento: cada clase maneja su propio estado y comportamiento.
- Reutilizacion: metodos de utilidad para evitar repetir logica.
- Claridad de diseno: separar lo que hace la interfaz de lo que hace la logica.

### 2. Patron MVC adaptado a JavaFX

Aunque no sea un MVC estricto, la estructura refleja esa idea:
- Vista: archivos FXML y estilos CSS.
- Controlador: clases que responden a eventos de la UI.
- Modelo: objetos de datos como DTOs.

Que se aprende aqui:
- Como conectar la vista con el controlador.
- Como mantener el codigo organizado para que sea mas facil de entender.
- Como evitar mezclar reglas de negocio dentro del archivo visual.

### 3. JavaFX y FXML

JavaFX permite construir interfaces de escritorio modernas. FXML define la pantalla en un archivo declarativo, mientras el controlador gestiona el comportamiento.

Que se aprende aqui:
- Separar diseno visual y logica.
- Asociar componentes visuales con variables y metodos del controlador.
- Manejar transiciones entre pantallas de forma estructurada.

### 4. Flujo de navegacion entre pantallas

El proyecto incluye pantallas como Login, Principal, Empleados y Tipos de Planilla. La navegacion enseña como mover al usuario entre vistas segun acciones o permisos.

Que se aprende aqui:
- Control del ciclo de vida de ventanas y escenas.
- Coordinacion centralizada del flujo de la aplicacion.
- Diseno de experiencias basicas de usuario en escritorio.

### 5. Manejo de datos con DTO

El uso de objetos como EmpleadoDto ayuda a transportar informacion de forma clara entre capas.

Que se aprende aqui:
- Representar entidades del dominio con clases simples.
- Evitar trabajar con datos sueltos o desordenados.
- Preparar la base para validaciones o persistencia futura.

### 6. Utilidades y apoyo tecnico

Las clases utilitarias (contexto, formato, mensajes, respuestas) ayudan a concentrar funciones comunes.

Que se aprende aqui:
- Reducir duplicacion de codigo.
- Centralizar comportamiento repetido.
- Crear una base mas facil de extender en tareas futuras.

## Por que este proyecto es util para aprender Java

Este proyecto combina teoria y practica en un entorno concreto. Permite pasar de ejemplos pequenos a una aplicacion estructurada, lo cual mejora la comprension de:

- Sintaxis y estructura del lenguaje Java.
- Diseno de clases en un caso de uso real.
- Lectura y mantenimiento de codigo existente.
- Trabajo incremental: agregar, corregir y refactorizar funcionalidades.

En resumen, UNA Planilla 2026 funciona como laboratorio academico para practicar fundamentos de Java, JavaFX y organizacion de proyectos, priorizando el aprendizaje progresivo sobre la complejidad de un sistema empresarial.

