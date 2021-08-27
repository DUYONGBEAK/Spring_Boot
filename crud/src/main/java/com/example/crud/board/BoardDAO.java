package com.example.crud.board;

import java.util.ArrayList;
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

public class BoardDAO {
	@Autowired
	private MongoTemplate mt;

	public BoardDAO() {
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
	
	public Boolean insert(BoardDTO dto) {
		try {
			mt.insert(dto);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public Boolean update(BoardDTO dto) {
		try {
			Query query = new Query(new Criteria("_id").is(dto.getId())); // 만약 덧붙이고 싶으면 and("column").is(column)
			Update update = new Update();
			update.set("title", dto.getTitle());
			update.set("content", dto.getContent());
			update.set("userId", dto.getUserId());
			update.set("date", dto.getDate());

			mt.updateFirst(query, update, BoardDTO.class);
			System.out.println(update);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Boolean delete(String id) {
		try {
			Query query = new Query(new Criteria("_id").is(id));
			mt.remove(query, BoardDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<BoardDTO> getDTO() {
		List<BoardDTO> arr;
		try {
			
			 arr = mt.findAll(BoardDTO.class); 

//			Query query = new Query(new Criteria("_id").ne("*"));
//			System.out.println(query.toString()); arr = mt.find(query, BoardDTO.class);
//			System.out.println(arr.size());
			 			
		} catch (Exception e) {
			e.printStackTrace();
			arr = new ArrayList<>();
			arr.add(new BoardDTO("err"));
		} 

		if (arr.size() == 0) {
			arr = new ArrayList<>();
			arr.add(new BoardDTO("err"));
		}
		return arr;
	}
	
	public BoardDTO getDTO(String id) {
		// 이렇게 말고 update처럼 쿼리문으로 하는 것도 가능.
		BoardDTO arr = null;
		try {
			arr = mt.findById(id, BoardDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (arr.equals(null))
			arr = new BoardDTO("err");
		return arr;
	}
	
	
}
