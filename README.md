# WarriorsBinker

Proyecto de despliegue un Bot "guerrero" en una batalla, a partir del *Framework* desarrollado por Gabriel Barrera, para la materia Inteligencia Artificial de la Universidad de Palermo. Segundo cuatrimestre 2018

## Desarrollo

A raíz de las clases **Warrior** y **WarriorManager** proporcionadas por el docente de la currícula, se desarrollaron las clases **Fury** y **Spiderman**, las cuales heredan la lógica de las mismas e implementan sus métodos.

### Fury

Es la clase que tiene la responsabilidad de mandar a la arena de combate a los guerreros. Implementa el método *getNextWarrior()* que envía a Spiderman a la batalla, estableciendo los valores de sus atributos: vida, velocidad, fuerza, rango de ataque y defensa.  

### Spiderman 

Implementa los métodos *playTurn()* *wasAttacked()* y *enemyKilled()* entre otras, que personalizan el comportamiento del guerrero.

### SaltoSpider

Es una clase que extiende a **Move**, con lo cual este *Framework* te provee la posibilidad de generar movimientos personalizados para cada héroe. 
