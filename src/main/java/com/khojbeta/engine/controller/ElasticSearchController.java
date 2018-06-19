package com.khojbeta.engine.controller;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.khojbeta.engine.model.MovieModel;

@RestController
@RequestMapping("elastic/movies")
public class ElasticSearchController {
	private Client transportClient;

	public ElasticSearchController() throws UnknownHostException {
		Settings settings = Settings.builder()
		        .put("cluster.name", "elasticsearch_vijayjaiswal").build();
		transportClient = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getLoopbackAddress(), 9300));

	}
	private Field[] attributeMapper(){
		return MovieModel.class.getFields();
	}
	@RequestMapping(method = RequestMethod.POST, value = "/addmovie")
	public Object movie(@RequestBody final MovieModel movie) {
		IndexResponse indexResponse=null;
		try {
			XContentBuilder builder = XContentFactory.jsonBuilder();
			builder.startObject();
			{
					
			    builder.field("id", movie.getId());
			    builder.field("year", movie.getYear());
			    builder.field("title", movie.getTitle());
			    builder.field("genre", movie.getGenre());
			}
			builder.endObject();
			indexResponse = transportClient.prepareIndex("movies", "movie",movie.getId().toString()).
					setSource(builder).get();
					;
			
			 return indexResponse.getResult().toString();
		} 
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
//	@RequestMapping(method = RequestMethod.POST, value = "/addmovies")
//	public Object movie(@RequestBody final List<MovieModel> movie) {
//		IndexResponse indexResponse=null;
//		try {
//			XContentBuilder builder = XContentFactory.jsonBuilder();
//			builder.startObject();
//			{
//					
//			    builder.field("id", movie.getId());
//			    builder.field("year", movie.getYear());
//			    builder.field("title", movie.getTitle());
//			    builder.field("genre", movie.getGenre());
//			}
//			builder.endObject();
//			indexResponse = transportClient.prepareBulk().
//					setSource(builder).get();
//					;
//			
//			 return indexResponse.getResult().toString();
//		} 
//		catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		return null;
//	}

	@GetMapping("/movie/{id}")
	public Map<String, Object> movie(@PathVariable final String id) {
		GetResponse getResponse=null;
		try {
			getResponse = transportClient.prepareGet("movies", "movie", id).get();
			
			 return getResponse.getSource();
		} 
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	@GetMapping("/movie/")
	public Map<String, Object> movie() {
		GetResponse getResponse=null;
		try {
			getResponse = transportClient.prepareGet().get();
			
			 return getResponse.getSource();
		} 
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	@GetMapping("/moviematching/{match}")
	public long phraseSearch(@PathVariable String match){
		SearchResponse actionGet = transportClient.prepareSearch("movies").setTypes("movie")
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH).execute()
        .actionGet();
		return actionGet.getHits().totalHits;
	}
}
