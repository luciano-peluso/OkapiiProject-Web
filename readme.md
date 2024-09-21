# Okapii Project

Okapii Project es un sistema de gestión de proyectos con roles de usuarios: Administradores, Gerentes, Clientes, y Desarrolladores. La aplicación web está construida utilizando Spring MVC, Thymeleaf, Spring Data JPA, Lombok, y MySQL como base de datos.

## Características

- Los Administradores pueden gestionar usuarios y asignar desarrolladores a proyectos.
- Los Gerentes pueden administrar clientes y asignarles proyectos.
- Los Clientes pueden ver sus proyectos y gestionar su perfil.
- Los Desarrolladores pueden ser asignados a proyectos, mostrando en qué están trabajando.

## Tecnologías Usadas

- Java 17 (o superior)
- Spring Boot 3.x
- MySQL (Servidor de base de datos)
- Maven (para gestionar las dependencias)

## Estructura del Proyecto

### Controladores (Controller):

- AdminController.java: Controlador para las funciones de administración de usuarios y asignación de desarrolladores.
- ClienteController.java: Controlador para las operaciones relacionadas con los clientes.
- GerenteController.java: Controlador para gestionar proyectos y clientes.
- LoginController.java: Maneja el proceso de autenticación.
- UserController.java: Controlador para la gestión de perfiles y visualización de datos por parte de los usuarios.

### Modelos (Model):

- Admin.java: Clase que representa a los usuarios administradores.
- Cliente.java: Clase que representa a los clientes.
- Desarrollador.java: Clase que representa a los desarrolladores.
- Gerente.java: Clase que representa a los gerentes.
- Proyecto.java: Clase que modela los proyectos.
- Usuario.java: Clase base para los diferentes tipos de usuarios.
- UsuarioFactory.java: Clase para la creación de instancias de los diferentes tipos de usuarios.

### Repositorios (Repository):

- RepositorioUsuarios.java: Acceso a datos para la entidad Usuario.
- RepositorioClientes.java: Acceso a datos para la entidad Cliente.
- RepositorioDesarrolladores.java: Acceso a datos para la entidad Desarrollador.
- RepositorioProyectos.java: Acceso a datos para la entidad Proyecto.
- RepositorioDevsProyectos.java: Relación entre desarrolladores y proyectos.

### Recursos estáticos (Static Resources):

- static/css/: Carpeta que contiene los archivos CSS para los estilos (admin.css, menu.css, style.css, tablas.css).
- static/img/: Carpeta para almacenar las imágenes utilizadas en la aplicación.

### Plantillas HTML (Templates):

- all_users.html: Vista de todos los usuarios para los administradores.
- menu_admin.html, menu_gerente.html: Menús específicos para los roles de Administrador y Gerente.
- ver_proyectos.html, registrar_usuario.html: Vistas para los usuarios ver proyectos y registrar nuevos usuarios, respectivamente.
- ... (y más vistas para otras funcionalidades).

## Colaboradores

- Desarrollador: Luciano Peluso
- Desarrollador: Nahuel Olivera

## Licencia

Este proyecto se encuentra bajo la Licencia MIT.
