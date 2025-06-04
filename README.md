# Operación Fuego de Quasar

## Resumen del desafío

Crear un programa en Java que retorne la fuente y el contenido de un mensaje de auxilio. Para esto, se cuenta con tres satélites que permiten triangular la posición del emisor. El mensaje puede llegar incompleto o desfasado a cada satélite debido a interferencias, por lo que también debe ser reconstruido correctamente a partir de los fragmentos recibidos.

---

## Requisitos Técnicos

- Java 17
- Maven 3.9.8
- Docker

---

## Cómo ejecutar

```bash
docker build -t quasar-fire .
docker run -p 8080:8080 quasar-fire
```
---

## Repositorio público

Proyecto disponible en GitHub:  
https://github.com/berto2904/QuasarFireOperation

---

## Servicio desplegado

URL pública:  
https://quasarfireoperation.onrender.com

---

## Endpoints disponibles

> En caso de que cualquier endpoint falle (por mensaje irreconstruible o posición indeterminable), el sistema devolverá `HTTP 404 Not Found`.

### POST /topsecret

Recibe información de los tres satélites y retorna la posición y el mensaje reconstruido.

#### Ejemplo de request:

```json
{
  "satellites": [
    {
      "name": "kenobi",
      "distance": 200.0,
      "message": ["este", "", "", "mensaje", ""]
    },
    {
      "name": "skywalker",
      "distance": 115.5,
      "message": ["", "es", "", "", "secreto"]
    },
    {
      "name": "sato",
      "distance": 20.7,
      "message": ["este", "", "un", "", ""]
    }
  ]
}
```

#### Ejemplo de response:

```json
{
  "position": { "x": -100.0, "y": 75.5 },
  "message": "este es un mensaje secreto"
}
```

---

### POST /topsecret_split/{satellite_name}

Recibe los datos de un satélite individualmente.

#### Ejemplo:

`POST /topsecret_split/kenobi`

```json
{
  "distance": 200.0,
  "message": ["este", "", "", "mensaje", ""]
}
```

---

### GET /topsecret_split

Devuelve la posición y el mensaje cuando se hayan recibido datos de los tres satélites. Caso contrario: 404.

#### Ejemplo de response:

```json
{
  "position": { "x": -100.0, "y": 75.5 },
  "message": "este es un mensaje secreto"
}
```

#### Si faltan satélites:

```http
HTTP 404 Not Found
```

---

### DELETE /topsecret_split

Limpia la memoria temporal de los satélites cargados.

#### Ejemplo de response:

```json
{
  "message": "Los satélites almacenados fueron eliminados correctamente."
}
```

---

## Algoritmo de Trilateración

Para calcular la posición `(x, y)` del emisor, se utiliza la intersección de tres círculos definidos por las posiciones conocidas de los satélites y su distancia al emisor:

Dado:

- Kenobi: `(-500, -200)`
- Skywalker: `(100, -100)`
- Sato: `(500, 100)`
- Distancias al emisor: `d₁`, `d₂`, `d₃`

Se parte del sistema de ecuaciones:

```
(x - x₁)² + (y - y₁)² = d₁²
(x - x₂)² + (y - y₂)² = d₂²
(x - x₃)² + (y - y₃)² = d₃²
```

Restando la primera ecuación con la segunda y tercera, se elimina la parte cuadrática y se obtiene un sistema lineal de dos ecuaciones:

```
A·x + B·y = C
D·x + E·y = F
```

Donde:

```
A = 2(x₂ - x₁)
B = 2(y₂ - y₁)
C = d₁² - d₂² - x₁² + x₂² - y₁² + y₂²

D = 2(x₃ - x₁)
E = 2(y₃ - y₁)
F = d₁² - d₃² - x₁² + x₃² - y₁² + y₃²
```

Resolviendo el sistema lineal se obtiene:

```
x = (C·E - F·B) / (A·E - B·D)
y = (A·F - C·D) / (A·E - B·D)
```

> Cabe destacar que para esta resolucion se utilizo la regla de Cramer
Adjunto el repaso de la formula en el siguiente [link](https://openstax.org/books/precálculo-2ed/pages/9-8-resolver-sistemas-con-la-regla-de-cramer)

---

## Reconstrucción del mensaje

Cada satélite recibe un arreglo de palabras con posibles vacíos (`""`).  
El mensaje original se reconstruye tomando la primera palabra no vacía en cada posición.  
Si hay conflicto (palabras distintas en la misma posición), se lanza una excepción.

---



## Autor

Pablo Bertoli  
Email: berto2904@gmail.com
