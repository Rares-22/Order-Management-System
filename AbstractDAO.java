package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import connection.ConnectionFactory;

public class AbstractDAO<Ty> {
    private final Class<Ty> type;
    /**
     * Uses parameterized class to execute generic methods.
     * @param type - type of the class
     */
    public AbstractDAO(Class<Ty> type) {
        this.type = type;
    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " = ?");
        return sb.toString();
    }
    /**
     * Searches for the entry with the given id in the corresponding database table.
     * @param id - searched id
     */
    public Ty findById(int id) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(type.getDeclaredFields()[0].getName());
        try {
            conn = ConnectionFactory.getConnection();
            st = conn.prepareStatement(query);
            st.setInt(1, id);
            resultSet = st.executeQuery();

            List<Ty> l = createObjects(resultSet);
            for(Ty elem:l) {
                return elem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(st);
            ConnectionFactory.close(conn);
        }
        return null;
    }
    /**
     * Searches for the all elements.
     * @throws SQLException if no entry is found
     */
    public List<Ty> findAll() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName();
        try {
            conn = ConnectionFactory.getConnection();
            st = conn.prepareStatement(query);
            resultSet = st.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(st);
            ConnectionFactory.close(conn);
        }
        return null;
    }
    /**
     * Create a new object.
     * @throws InstantiationException if no entry is found
     */
    private List<Ty> createObjects(ResultSet resultSet) {
        List<Ty> l = new ArrayList<Ty>();
        try {
            while (resultSet.next()) {
                Ty instance = type.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();

                    if (method.getName().equals("setDate")) {
                        method.invoke(instance, value.toString());
                    }
                    else {
                        method.invoke(instance, value);
                    }
                }
                l.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return l;
    }
    /**
     * Create a new query for Insert operation.
     * @param ty
     * @return insert string + values
     * @throws IllegalArgumentException
     */
    public String createInsertQuery(Ty ty) {
        StringBuilder insert = new StringBuilder();
        StringBuilder values = new StringBuilder();
        insert.append("INSERT INTO " + ty.getClass().getSimpleName() + "(");
        values.append("VALUES(");

        for (Field field : ty.getClass().getDeclaredFields()) {
            if( !field.equals(ty.getClass().getDeclaredFields()[0]) ) {
                field.setAccessible(true);
                Object value;
                try {
                    insert.append(field.getName() + ",");
                    value = field.get(ty);
                    if(value instanceof String) {
                        values.append("\"");
                        values.append(value);
                        values.append("\",");
                    }
                    else {
                        values.append(value + ",");
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        insert.delete(insert.length()-1,insert.length());
        values.delete(values.length()-1, values.length());
        insert.append(") ");
        values.append(")");
        return insert.toString() + values.toString();
    }
    /**
     * Inserts element into corresponding table.
     * @param ty - element to be inserted
     * @return - id of the inserted element
     */
    public int insert(Ty ty) {
        Connection dbConn = ConnectionFactory.getConnection();
        PreparedStatement insertSt = null;
        String insertStatementString = createInsertQuery(ty);
        int insertedID = -1;
        try {
            insertSt = dbConn.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            try {
                insertSt.executeUpdate();
            }
            catch(MySQLSyntaxErrorException ex) {
                ex.printStackTrace();
            }
            ResultSet rs = insertSt.getGeneratedKeys();
            if (rs.next()) {
                insertedID = rs.getInt(1);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(insertSt);
            ConnectionFactory.close(dbConn);
        }
        return insertedID;
    }
    /**
     * Creates a query for Update operation.
     * @param ty - element to be updated
     */
    public String createUpdateQuery(Ty ty) {
        StringBuilder update = new StringBuilder();
        update.append("UPDATE " + ty.getClass().getSimpleName() + " SET ");

        for (Field field : ty.getClass().getDeclaredFields()) {
            if( !field.getName().contains("id") ) {
                field.setAccessible(true);
                Object value;
                try {
                    update.append(field.getName() + "=");
                    value = field.get(ty);
                    if(value instanceof String) {
                        update.append("\"");
                        update.append(value);
                        update.append("\",");
                    }
                    else {
                        update.append(value + ",");
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        update.delete(update.length()-1,update.length());
        Field f = type.getDeclaredFields()[0];
        f.setAccessible(true);
        int id = -1;
        try {
            id = (int)f.get(ty);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        update.append(" WHERE " + type.getDeclaredFields()[0].getName() + "=" + id);
        return update.toString();
    }
    /**
     * Updates a given element into the corresponding table.
     * @param ty - element to be updated
     * @return - id of the updated element
     */
    public int update(Ty ty) {
        Connection dbConn = ConnectionFactory.getConnection();
        PreparedStatement updateSt = null;
        String updateStatementString = createUpdateQuery(ty);
        int updatedId = -1;

        try {
            updateSt = dbConn.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
            updateSt.executeUpdate();

            ResultSet rs = updateSt.getGeneratedKeys();
            if (rs.next()) {
                updatedId = rs.getInt(1);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(updateSt);
            ConnectionFactory.close(dbConn);
        }
        return updatedId;
    }
    /**
     * Detele entries.
     * @return - ok, if successfully deleted
     */
    public int deleteEntries() {
        Connection dbConn = ConnectionFactory.getConnection();
        PreparedStatement deleteSt = null;
        String deleteStatementString = "DELETE FROM " + type.getSimpleName();
        int ok = -1;

        try {
            deleteSt = dbConn.prepareStatement(deleteStatementString);
            deleteSt.executeUpdate();
            ok = 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionFactory.close(deleteSt);
            ConnectionFactory.close(dbConn);
        }
        return ok;
    }
}