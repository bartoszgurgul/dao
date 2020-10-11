package pl.javastart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pl.javastart.model.User;
import pl.javastart.util.ConnectionProvider;
import pl.javastart.util.DbOperationException;

public class MysqlUserDao implements UserDao {
	
	private final String CREATE = "INSERT INTO user(pesel, firstName, lastName) VALUES(?,?,?);";
	private final String READE = "SELECT pesel, firstName, lastName FROM user WHERE pesel = ?;";
	private final String UPDATE = "UPDATE user SET pesel = ?, firstName = ?, lastName = ? WHERE pesel = ?;";
	private final String DELETE = "DELETE FROM user WHERE pesel = ?;";
	@Override
	public void create(User user) {
		try (Connection connection = ConnectionProvider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CREATE)){
			preparedStatement.setString(1, user.getPesel());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DbOperationException(e);
		}
		
	}
	
	
	@Override
	public User read(String pesel) {
		User outUser = null;
		
		try(Connection connection = ConnectionProvider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(READE)){
			preparedStatement.setString(1, pesel);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				outUser = new User();
				
				outUser.setPesel(resultSet.getString("pesel"));
				outUser.setFirstName(resultSet.getString("firstName"));
				outUser.setLastName(resultSet.getString("lastName"));
				
			}
		} catch (SQLException e) {
			throw new DbOperationException(e);
		}
		return outUser;
	}
	
	
	@Override
	public void update(User user) {
		try( Connection connection = ConnectionProvider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
			preparedStatement.setString(1, user.getPesel());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setString(4, user.getPesel());
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DbOperationException(e);
		}
		
	}

	@Override
	public void delete(User user) {
		try(Connection connection = ConnectionProvider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE)){
			preparedStatement.setString(1, user.getPesel());
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DbOperationException(e);
		}
		
	}
	
	

}
