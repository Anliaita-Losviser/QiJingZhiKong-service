package com.ruoyi.manage.service.impl;

import com.ruoyi.manage.domain.vo.SensorHourlyData;
import com.ruoyi.manage.service.ISensorDataService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SensorDataServiceImpl implements ISensorDataService {
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Override
    public List<SensorHourlyData> getSensorDataList(LocalDateTime startTime, LocalDateTime endTime, String roomNumber) {

//        AggregationExpression hourExpression = DateOperators.dateOf("timeStamp")
//                .toString("%Y-%m-%dT%H:00:00");
//
//        Aggregation aggregation = Aggregation.newAggregation(
//                match(Criteria.where("timeStamp").gte(startTime).lte(endTime)),
//                addFields()
//                        .addFieldWithValue("tempNum", ConvertOperators.valueOf("Temperature").convertToDouble())
//                        .addFieldWithValue("humidityNum", ConvertOperators.valueOf("Humidity").convertToDouble())
//                        .build(),
//                group(hourExpression.toString())
//                        .avg("tempNum").as("averageTemperature")
//                        .avg("humidityNum").as("averageHumidity"),
//                sort(Sort.Direction.ASC, "_id"),
//                project()
//                        .and("_id").as("hour")
//                        .andInclude("averageTemperature", "averageHumidity")
//                        .andExclude("_id")
//        );
//
//        AggregationResults<SensorHourlyData> results = mongoTemplate.aggregate(aggregation,
//                roomNumber,SensorHourlyData.class);
        
        // 定义聚合管道（直接使用 MongoDB 原生语法）
        List<Document> pipeline = Arrays.asList(
                new Document("$match", new Document("timeStamp",
                        new Document("$gte", startTime)
                                .append("$lte", endTime))),
                new Document("$addFields", new Document()
                        .append("tempNum", new Document("$toDouble", "$Temperature"))
                        .append("humidityNum", new Document("$toDouble", "$Humidity"))),
                new Document("$group", new Document()
                        .append("_id", new Document("$dateTrunc",
                                new Document("date", "$timeStamp")
                                        .append("unit", "hour")))
                        .append("avgTemperature", new Document("$avg", "$tempNum"))
                        .append("avgHumidity", new Document("$avg", "$humidityNum"))),
                new Document("$sort", new Document("_id", 1)),
                new Document("$project", new Document()
                        .append("_id", 0)
                        .append("hour", "$_id")
                        .append("avgTemperature", 1)
                        .append("avgHumidity", 1))
        );
        //System.out.println(pipeline);
        // 执行聚合查询
        ArrayList<Document> results = mongoTemplate.getCollection(roomNumber)
                .aggregate(pipeline)
                .into(new ArrayList<>());
        
//        System.out.println("总数：");
//        System.out.println(results.size());
//        System.out.println("查询结果：");
//        System.out.println(results);
        ArrayList<SensorHourlyData> sensorHourlyDataList = new ArrayList<>();
        for (Document doc : results) {
            SensorHourlyData sensorHourlyData = new SensorHourlyData();
            sensorHourlyData.setHour(doc.getDate("hour").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            sensorHourlyData.setAverageTemperature(doc.getDouble("avgTemperature"));
            sensorHourlyData.setAverageHumidity(doc.getDouble("avgHumidity"));
            sensorHourlyDataList.add(sensorHourlyData);
        }
//        System.out.println("转换结果：");
//        System.out.println(sensorHourlyDataList);
        
        return sensorHourlyDataList;
    }
}
