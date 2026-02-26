# 📱 Kharon Mobile

App bancaria simulada para Android, desarrollada desde cero como proyecto de práctica para la materia **Programación Mobile** — INCADE 2do A.

El proyecto recorrió todo el proceso de desarrollo: desde los primeros bocetos en papel, pasando por el diseño en Figma, el modelado de entidades, hasta su implementación en Android con arquitectura **MVC**.

---

## 🖼️ Vista Previa

<p align="center">
  <img src="https://github.com/user-attachments/assets/e7b81922-60bf-48be-9550-3a5099af952d" width="220" alt="Inicio de sesión"/>
  <img src="https://github.com/user-attachments/assets/e71b5696-2918-4722-95bf-a7f4bda51b18" width="220" alt="Menú principal"/>
  <img src="https://github.com/user-attachments/assets/3959cdaa-2cb6-4db3-b35d-897b8a02bee1" width="220" alt="Confirmar transferencia"/>
</p>

<p align="center">
  <em>Inicio de sesión &nbsp;·&nbsp; Menú principal &nbsp;·&nbsp; Confirmar transferencia</em>
</p>

---

## 🎯 Funcionalidades

- Inicio de sesión con validación de credenciales
- Menú principal con acceso a las funciones bancarias
- Transferencias entre cuentas con pantalla de confirmación
- Navegación entre Activities siguiendo el patrón MVC

---

## 🏗️ Arquitectura MVC

El proyecto está organizado siguiendo el patrón **Modelo - Vista - Controlador**:

```
app/
├── model/          # Entidades y lógica de negocio (Usuario, Cuenta, Transferencia...)
├── view/           # Layouts XML (Activities y Fragments)
└── controller/     # Lógica de navegación y manejo de eventos
```

Esta separación permite mantener el código organizado, facilitar el mantenimiento y escalar el proyecto sin mezclar responsabilidades.

---

## 🗃️ Modelo de Entidades

Las principales entidades del sistema y sus relaciones:

| Entidad | Descripción |
|---|---|
| `Usuario` | Datos del titular de la cuenta |
| `Cuenta` | Cuenta bancaria asociada al usuario |
| `Transferencia` | Movimiento de fondos entre cuentas |
| `Movimiento` | Historial de transacciones |

Las relaciones fueron diseñadas previamente en tablas para visualizar cómo interactúan las entidades antes de comenzar a codear.

---

## 🎨 Proceso de Diseño

El proyecto siguió un proceso de diseño completo antes de escribir una sola línea de código:

1. **Boceto en papel** — Wireframes a mano de cada pantalla y flujo de navegación
2. **Prototipo en Figma** — Diseño de alta fidelidad con paleta de colores, tipografía y componentes
3. **Modelado de entidades** — Tablas de relaciones para definir la estructura de datos
4. **Implementación** — Desarrollo en Android Studio respetando el diseño aprobado

---

## 🚀 Tecnologías

- **Lenguaje**: Java
- **Plataforma**: Android (Android Studio)
- **Arquitectura**: MVC
- **Diseño**: Figma
- **Build**: Gradle

---

## ⚙️ Cómo correr el proyecto

1. Cloná el repositorio:
```bash
git clone https://github.com/Cristaldo-Fiorela/kharon_mobile.git
```
2. Abrí el proyecto en **Android Studio**
3. Esperá a que Gradle sincronice las dependencias
4. Ejecutá en un emulador o dispositivo físico con Android 8.0+

---

## 📝 Licencia

Proyecto académico desarrollado con fines educativos.
