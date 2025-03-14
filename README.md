# 🚀 Aplicación Web Java - Gestión de Usuarios y Sesiones 

[![Java](https://img.shields.io/badge/Java-17-%23ED8B00?logo=openjdk)](https://www.java.com/)
[![Apache Tomcat](https://img.shields.io/badge/Apache_Tomcat-9.0-%23F8DC75?logo=apache-tomcat)](https://tomcat.apache.org/)
[![JUnit](https://img.shields.io/badge/JUnit-5-%2325A162?logo=junit5)](https://junit.org/)

Aplicación web completa desarrollada con arquitectura MVC que implementa funcionalidades avanzadas de gestión de usuarios y control de sesiones seguras.

## 🌟 Características Principales

### 🛠️ Backend
- **Arquitectura MVC** con Servlets como controladores
- **Patrones de diseño**:
  - ✅ DAO (Data Access Object) para persistencia de datos
  - ✅ Factory Method para creación de objetos complejos
- **Middlewares**:
  - 🔐 Sistema de autenticación con gestión de sesiones
  - 🛡️ Autorización por roles de usuario
  - ⏳ Tiempos de sesión configurables

### 🧪 Testing
- Pruebas unitarias con **JUnit 5**
- Mocks de dependencias con **Mockito**
- Cobertura de pruebas > 80%

### 🎨 Frontend
- **Diseño responsive** con Bootstrap 5
- Interactividad con **jQuery**
- Validación de formularios en tiempo real
- UI moderna con:
  - 🖥️ Layouts adaptables
  - 🎭 Transiciones CSS3
  - 📱 Compatibilidad móvil

## 🛠️ Tecnologías Utilizadas

<p align="center">
  <img src="https://skillicons.dev/icons?i=java,tomcat,mysql,html,css,bootstrap,jquery,github" />
</p>

## ⚙️ Configuración

1. Requisitos:
   ```bash
   Java 17+
   Apache Tomcat 9
   MySQL 8


## 📊 Estructura del proyecto
```bash
src/
├── main/
│ ├── java/
│ │ ├── controllers/ # Servlets
│ │ ├── dao/ # Patrón DAO
│ │ ├── factories/ # Factorías de objetos
│ │ └── filters/ # Middlewares
│ ├── resources/ # Archivos de configuración
│ └── webapp/
│ ├── WEB-INF/ # Configuración de la aplicación
│ └── assets/ # CSS, JS e imágenes
├── test/ # Pruebas unitarias
