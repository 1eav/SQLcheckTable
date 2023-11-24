package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/new_courses";
        String user = "root";
        String pass = "1eav";

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            insertCourses(connection, "'SEO-специалист'", 5);
            insertStudents(connection, "Тургенев Самсон", 27);
            printStudents(connection);
            printCourses(connection);
            printStudentsMax(connection);
            printCoursesMin(connection);

            System.out.println(" ");
            System.out.println("Курс добавлен!");
            System.out.println("Студент добавлен!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private static void insertCourses(Connection connection, String name, int duration) throws SQLException {
        String insertCoursesQuery = "INSERT INTO courses (name, duration) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertCoursesQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, duration);
            preparedStatement.executeUpdate();
        }
    }
    private static void insertStudents(Connection connection, String name, int age) throws SQLException {
        String insertStudentsQuery = "INSERT INTO students (name, age) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertStudentsQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.executeUpdate();
        }
    }

    private static void printStudents(Connection connection) throws SQLException {
        String selectStudentsQuery = "SELECT * FROM students";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectStudentsQuery)) {
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                        ", Имя: " + resultSet.getString("name") +
                        ", Возраст: " + resultSet.getInt("age"));
            }
        }
    }

    private static void printCourses(Connection connection) throws SQLException {
        String selectCoursesQuery = "SELECT * FROM courses";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectCoursesQuery)) {
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                        ", Название курса: " + resultSet.getString("name") +
                        ", Продолжительность: " + resultSet.getInt("duration"));
            }
        }
    }

    private static void printStudentsMax(Connection connection) throws SQLException {
        String selectStudentsQuery = "SELECT * FROM students WHERE age > 20";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectStudentsQuery)) {
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                        ", Имя: " + resultSet.getString("name") +
                        ", Возраст: " + resultSet.getInt("age") +
                        " - Возраст студента больше 20 лет");
            }
        }
    }

    private static void printCoursesMin(Connection connection) throws SQLException {
        String selectCoursesQuery = "SELECT * FROM courses WHERE duration < 50";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectCoursesQuery)) {
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                        ", Имя: " + resultSet.getString("name") +
                        ", Продолжительность: " + resultSet.getInt("duration") +
                        " - Продолжительность курса меньше 50 часов");
            }
        }
    }
}