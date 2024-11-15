
# Proyecto Android - Aplicación Kotlin Compose

Este proyecto fue desarrollado por **David Merchan** para la empresa **Apply Digital Latam**. Es una aplicación Android que muestra un listado de articulos de manera online y offline, utiliza **Kotlin** como lenguaje principal y **Jetpack Compose** con **Material 3** para el diseño de la interfaz de usuario. Se siguen los principios de diseño **SOLID**, **KISS** y **DRY** para garantizar un desarrollo eficiente, fácil mantenimiento y entendimiento del código.

## Características del Proyecto

1. **Pruebas**:
    - Se implementan pruebas unitarias utilizando la biblioteca **MockK**.
    - Se integra **Crashlytics** para capturar y rastrear errores en caso de fallos durante la ejecución de la aplicación.

2. **Arquitectura**:
    - El proyecto sigue el modelo de **Clean Architecture**, dividiendo el código en módulos por capas:
        - **Presentation**: Contiene la lógica de la interfaz de usuario.
        - **Domain**: Maneja la lógica de negocio y casos de uso.
        - **Data**: Administra el acceso a fuentes de datos remotas y locales.
        - **Database**: Responsable del manejo de la información local.
        - **Network**: Encargado de las llamadas a la API.
        - **Core**: Contiene componentes transversales utilizados en toda la aplicación.
        - **DI**: Centraliza la configuración para la inyección de dependencias.
    - Se implementan los patrones de diseño **MVVM** (Model-View-ViewModel) para el flujo de datos y **MVI** (Model-View-Intent) para la gestión de estados y eventos en la UI.

3. **Inyección de Dependencias**:
    - Se utiliza **Hilt** como herramienta para la inyección de dependencias, debido a su robustez y capacidad de proporcionar feedback de errores en tiempo de compilación.

4. **Manejo de Datos**:
    - Para las consultas a la API, se implementa **Retrofit**.
    - Los datos obtenidos de la API se almacenan en una base de datos local utilizando **Room**, permitiendo que los datos sean accesibles incluso en ausencia de conexión a internet.

## Detalles Técnicos

### Principios y Buenas Prácticas
- **SOLID**: Para garantizar un diseño modular, escalable y mantenible.
- **KISS**: Manteniendo el código simple y fácil de entender.
- **DRY**: Evitando la duplicación de lógica y asegurando la reutilización del código.

### Herramientas y Librerías
- **Kotlin**: Lenguaje principal.
- **Jetpack Compose**: Para construir una UI moderna y reactiva.
- **Material 3**: Para aplicar el diseño visual.
- **MockK**: Para pruebas unitarias.
- **Crashlytics**: Para el monitoreo de errores y fallos.
- **Retrofit**: Para llamadas a la API.
- **Room**: Para la gestión de datos locales.
- **Detekt**: Para el analisis de codigo estatico.
- **Github Actions**: Se implementa un sencillo CI con Github actions para corroborar la estabilidad del codigo cada que se sube un cambio al repositorio.

## Imágenes

<p align="center">
  <img src="media/1.png" alt="Imagen 1" width="150"/>
  <img src="media/2.png" alt="Imagen 1" width="150"/>
  <img src="media/3.png" alt="Imagen 1" width="150"/>
  <img src="media/4.png" alt="Imagen 1" width="150"/>
  <img src="media/5.png" alt="Imagen 1" width="150"/>
  <img src="media/video.gif" alt="Imagen 1" width="150"/>
</p>
