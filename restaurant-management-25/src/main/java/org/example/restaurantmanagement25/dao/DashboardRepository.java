package org.example.restaurantmanagement25.dao;

import org.example.restaurantmanagement25.model.BestSellingDish;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DashboardRepository {
    private final DataSource dataSource;

    public List<BestSellingDish> findBestSellers(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = """
            SELECT d.id, d.name, SUM(do.quantity) as total_quantity,
                   SUM(do.quantity * i.current_price) as total_revenue
            FROM dish_order do
            JOIN dishes d ON do.dish_id = d.id
            JOIN ingredients i ON d.main_ingredient_id = i.id
            JOIN orders o ON do.order_id = o.id
            WHERE o.status = 'FINISHED'
            AND o.date_time BETWEEN ? AND ?
            GROUP BY d.id, d.name
            ORDER BY total_quantity DESC
            LIMIT 10""";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(startDate));
            stmt.setTimestamp(2, Timestamp.valueOf(endDate));

            ResultSet rs = stmt.executeQuery();
            List<BestSellingDish> results = new ArrayList<>();

            while (rs.next()) {
                results.add(new BestSellingDish(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("total_quantity"),
                        rs.getBigDecimal("total_revenue")
                ));
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch best sellers", e);
        }
    }

    public List<ProcessingStat> findProcessingStats(String dishId, LocalDateTime startDate, LocalDateTime endDate) {
        String sql = """
            WITH status_times AS (
                SELECT do.dish_id,
                       MIN(CASE WHEN dos.status = 'IN_PROGRESS' THEN dos.date_time END) as start_time,
                       MIN(CASE WHEN dos.status = 'FINISHED' THEN dos.date_time END) as end_time
                FROM dish_order_status dos
                JOIN dish_order do ON dos.dish_order_id = do.id
                JOIN orders o ON do.order_id = o.id
                WHERE do.dish_id = ?
                AND o.date_time BETWEEN ? AND ?
                GROUP BY do.dish_id, do.id
            )
            SELECT dish_id, EXTRACT(EPOCH FROM (end_time - start_time)) as processing_seconds
            FROM status_times
            WHERE end_time IS NOT NULL""";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, dishId);
            stmt.setTimestamp(2, Timestamp.valueOf(startDate));
            stmt.setTimestamp(3, Timestamp.valueOf(endDate));

            ResultSet rs = stmt.executeQuery();
            List<ProcessingStat> stats = new ArrayList<>();

            while (rs.next()) {
                stats.add(new ProcessingStat(
                        rs.getString("dish_id"),
                        rs.getLong("processing_seconds")
                ));
            }
            return stats;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch processing stats", e);
        }
    }

    public Optional<String> findDishNameById(String dishId) {
        String sql = "SELECT name FROM dishes WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, dishId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? Optional.of(rs.getString("name")) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch dish name", e);
        }
    }

    @Data
    @AllArgsConstructor
    public static class ProcessingStat {
        private String dishId;
        private long processingSeconds;
    }
}
