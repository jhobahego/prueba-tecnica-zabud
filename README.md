## OBJETIVO

La prueba consiste en el diseño e implementación de una pequeña aplicación dentro de la cual se deben contemplar los siguientes aspectos:

- Lógica funcional del backend.
- Documentación en comentarios dentro del código.
- Conexión a la base de datos.
- Frontend:
  - Estructura de aplicación.
  - Desarrollo de la funcionalidad.
- Programación orientada a objetos:
  - Uso de clases.
  - Uso de atributos.
  - Manejo de relaciones entre clases.
- API Rest:
  - Exposición del servicio.
  - Persistencia de la información.
  - Operación de almacenamiento de información (post/put).
  - Operación de obtención de información (get).
  - Operación de eliminación de información (delete).

Nota: los aspectos estéticos de la aplicación (css) son tomados en cuenta pero no son determinantes; el 90% de los aspectos a evaluar se centran en la lógica y funcionalidad.

## RESTRICCIONES TÉCNICAS

Para el desarrollo de la aplicación contamos con una serie de restricciones técnicas y de ambiente.

- Restricciones de frontend: se recomienda la implementación con una de las siguientes tecnologías.
  - vuejs
  - angular
  - react
- Restricciones de backend: se recomienda utilizar uno de los siguientes lenguajes.
  - Java
  - C#
  - TypeScript (node)
    - Java: se recomienda el uso de SpringBoot (no es obligatorio).
    - Node: se recomienda el uso de nestJs (no es obligatorio).

Nota: si desea realizar la prueba en un lenguaje diferente a los especificados, notificar por este medio.

## DEFINICIÓN DE LA APLICACIÓN

La tienda ColdIt necesita un sistema de información para realizar el cálculo de las facturas que paga cada cliente. Para esto, nosotros debemos diseñar una aplicación para llevar el control de los productos y realizar el cálculo de las facturas al momento de las ventas. Para lograrlo, vamos a diseñar y desarrollar una aplicación que:

### Funcionalidades

1. Crear producto: se debe poder registrar un producto en la base de datos.
2. Editar producto: se debe poder editar los campos valor y nombre de un producto en la base de datos.
3. Eliminar producto: se debe poder eliminar un registro de producto de la base de datos.
4. Calcular valor de factura: al mandar al backend una petición post /calcular, debe calcular el valor total de la factura realizando el cálculo por ítem y el totalizado de toda la factura.

### Entidades

- Producto
  - código
  - valor
  - nombre
- Item
  - producto
  - cantidad
  - valorTotal (valor del producto x cantidad)
- Factura
  - cliente
  - item[]
  - valorTotal (sumatoria de los valores de cada ítem).

Ejemplo petición de cálculo | Ejemplo respuesta de cálculo
--------------------------- | -----------------------------
(No proporcionado)           | (No proporcionado)

## INSTRUCCIONES DE ENTREGA

Al terminar la prueba, subirla en un repositorio de git y compartirla por este medio.

## DOCUMENTACIÓN

### Spring:
- [Guía Rest Service](https://spring.io/guides/gs/rest-service/)
- [Acceso a datos MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
- [Acceso a datos MongoDB Rest](https://spring.io/guides/gs/accessing-mongodb-data-rest/)
- [Acceso a datos Rest](https://spring.io/guides/gs/accessing-data-rest/)

### NestJS:
- [Documentación NestJS](https://docs.nestjs.com/)
