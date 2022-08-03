
package dao;

import Exception.DAOException;
import java.util.List;

//PASO 3: GENERALIZACIÓN DE LOS DAO'S
public interface DAO<T,K> {
    
    void insertar(T p) throws DAOException;

    void modificar(T p) throws DAOException;

    void eliminar(T p) throws DAOException;

    List<T> obtenerPersonas() throws DAOException;

    T obtenerPorId(K id) throws DAOException;
    
}
