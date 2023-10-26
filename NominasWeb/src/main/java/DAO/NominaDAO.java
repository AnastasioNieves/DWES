package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NominaDAO {
    private Connection connection;

    public NominaDAO(Connection connection) {
        this.connection = connection;
    }

    public double obtenerSalarioPorCategoria(int categoria, int anyos) {
        double salarioBase = obtenerSalarioBasePorCategoria(categoria);
        double salarioTotal = salarioBase + (anyos * 5000.0);
        return salarioTotal;
    }

    private double obtenerSalarioBasePorCategoria(int categoria) {
        double salarioBase = 0.0;
        String sql = "SELECT salario FROM categorias WHERE categoria = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoria);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                salarioBase = resultSet.getDouble("salario_base");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salarioBase;
    }

  
}
