package com.example.crud.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class UserDAO {
	@Autowired
	private MongoTemplate mt;

	public UserDAO() {
		ConnectionString con = new ConnectionString("mongodb://localhost:27017/sample");
		MongoClientSettings mcs = MongoClientSettings.builder().applyConnectionString(con).build();
		MongoClient mc = MongoClients.create(mcs);
		mt = new MongoTemplate(mc, "sample");

		MongoDatabaseFactory mdf = new SimpleMongoClientDatabaseFactory(con);
		// mt = new MongoTemplate(mdf);
		MongoMappingContext mmc;
		/*
		 * DbRefResolver dbRefResolver = new DefaultDbRefResolver(mdf);
		 * MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver,
		 * mmc); converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		 * 
		 * mt = new MongoTemplate(mdf, converter);
		 */
	}

	public Boolean checkId(String id) {
		List<UserDTO> arr;
		Query query = new Query(new Criteria("_id").is(id));
		arr = mt.find(query, UserDTO.class);
		try {
			if (arr.size() != 0) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return false;
	}

	public List<UserDTO> tryLogin(String id, String pwd) {
		List<UserDTO> arr;
		Query query = new Query(new Criteria().andOperator(Criteria.where("_id").is(id).and("pwd").is(pwd)));
		// Query query = new Query(new Criteria("_id").is(id));
		arr = mt.find(query, UserDTO.class);
		System.out.println(id);
		System.out.println(pwd);
		System.out.println(arr);
		return arr;
	}

	public Boolean insert(UserDTO dto) {
		try {
			mt.insert(dto);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public Boolean update(UserDTO dto) {
		try {
			Query query = new Query(new Criteria("_id").is(dto.getId())); // 만약 덧붙이고 싶으면 and("column").is(column)
			Update update = new Update();
			update.set("pwd", dto.getPwd());
			update.set("name", dto.getName());
			update.set("age", dto.getAge());
			update.set("sex", dto.getSex());
			
			System.out.println(dto.getName());
			System.out.println(dto.getAge());
			
			mt.updateFirst(query, update, UserDTO.class);
			System.out.println(query);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Boolean delete(String id) {
		try {
			Query query = new Query(new Criteria("_id").is(id));
			System.out.println(query);
			mt.remove(query, UserDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public UserDTO getDTO(String id) {
		// 이렇게 말고 update처럼 쿼리문으로 하는 것도 가능.
		UserDTO arr = null;
		try {
			arr = mt.findById(id, UserDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (arr.equals(null))
			arr = new UserDTO("err", 0);
		return arr;
	}

}
