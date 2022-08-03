
package Modelo;

import java.util.Objects;

//PASO 1: CREACIÓN DEL MODELO
public class Persona {
    /*
    Se toma en cuenta los wrappers en ves de las variables primitivas por que nos interesa
    que pueda tener valor null, ya que hasta que no lo guardamos en la base de datos no 
    sabemos su valor.
    */
    private Long id = null;
    private String nombre;
    private String apellido;
    private int edad;

    public Persona(String name, String apellido, int edad) {
        this.nombre = name;
        this.apellido = apellido;
        this.edad = edad;
    } 
    
    public Persona() {
        this.nombre = "";
        this.apellido = "";
        this.edad = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return nombre;
    }

    public void setName(String name) {
        this.nombre = name;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Persona{" + "id=" + id + ", name=" + nombre + ", apellido=" + apellido + ", edad=" + edad + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.nombre);
        hash = 53 * hash + Objects.hashCode(this.apellido);
        hash = 53 * hash + this.edad;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Persona other = (Persona) obj;
        if (this.edad != other.edad) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.apellido, other.apellido)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }
    
    
}
