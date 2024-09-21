package com.proyecto.okapii.model;

public class UsuarioFactory {

    public static Usuario crearUsuario(String tipo) {
        switch (tipo) {
            case "Admin":
                return new Admin();
            case "Gerente":
                return new Gerente();
            case "Cliente":
                return new Cliente();
            default:
                return null;
        }
    }
}
