# Banco Samuel

![img.png](img.png)


Banco Samuel es una interfaz bancaria escrita en Java.




## Pruebas de aceptación: (Teniendo en cuenta los 100€ ingresados en el apartado 1.)
1. Ingresar 100€ -> Mensaje de confirmación; saldo 100€.
2. Retirar 30€ -> Mensaje; saldo 70€.
3. Retirar 100€ -> Debería aparecer `Saldo insuficiente`. (Ya que no hay saldo.)
4. Cambiar límite diario a 50€ -> Confirmado.
5. Retirar 60€ -> Debería aparecer `Límite diario excedido`.
6. Ver Movimientos -> Lista con fecha, tipo, importe y saldo actualizado.
7. Cancelar -> vuelve al menú o sale si lo haces en el titular.
8. Se añaden apartados de Tarjetas al index donde se reflejan dos tarjetas inventadas para visualizar su internación en el index.
9. Se añade el apartado de Cuenta al index donde se refleja un número de cuenta ficticio dentro del index.


## Notas para la corrección
- Paquetes: `com.minibanco.app`, `com.minibanco.domain`, `com.minibanco.exceptions`.
- Se usan `ArrayList` para historial del banco y `LocalDateTime` para fechas que el usuario elija.
- Excepciones personalizadas implementadas en el código.
- Validaciones de entrada y manejo de Cancelar.


---


## Código fuente

Insertado en las carpetas subyacentes. Añadida además un archivo index.html donde se refleja la página web del proyecto para su uso.

### src/com/bancosamuel/domain/TipoMovimiento.java
```java
package com.bancosamuel.domain;


/** Tipo de movimiento: INGRESO o RETIRO */
public enum TipoMovimiento {
INGRESO,
RETIRO
}
