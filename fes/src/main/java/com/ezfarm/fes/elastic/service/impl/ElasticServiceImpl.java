/**
 *
 */
package com.ezfarm.fes.elastic.service.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.ezfarm.fes.elastic.service.ElasticService;
import com.ezfarm.fes.vo.SearchVO;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.RequestConfigCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.ezfarm.fes.elastic.ElasticResultMap;

import java.time.LocalDate;


/**
 * 공통 엘라스틱 service
 * @author hyunseongkil
 * @since
 * 	0204	init
 */
@EnableAsync
@Service
public class ElasticServiceImpl implements ElasticService {

	@Value("${elastic.id}")
	private String id;

	@Value("${elastic.pw}")
	private String pw;

	@Value("${elastic.hostname}")
	private String hostname;

	@Value("${elastic.port}")
	private String port;

	private String _fesIndex = "/wiselake_daily_data_count";
	private StringBuffer _qry = new StringBuffer();
	private LocalDate _now = null;
	private LocalDate _pastOfDays = null;



	/**
	 *
	 */
	public RestClient restClient = null;
	

	/**
	 * ssl 연결 테스트
	 * @throws IOException
	 */
	@Async
	private void connectSslTest() throws IOException {

		//
		Header[] headers = createHeaders();
		Map<String, String> params =  createDefaultParams();

		//
		String requestJson = "";
		HttpEntity entity = new NStringEntity(requestJson, ContentType.APPLICATION_JSON);

		//
		Response response = restClient.performRequest(METHOD_GET, POSTFIX_SEARCH, params, entity, headers);

		//
		int statusCode = response.getStatusLine().getStatusCode();
		String result = EntityUtils.toString(response.getEntity());

		//
		//LOG.debug("statusCode:{}", statusCode);
		//LOG.debug("result:{}", result);
		
	}


	/**
	 * @return
	 */
	private HttpAsyncResponseConsumerFactory createConsumerFactory() {
		return new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(200 * 1024 * 1024);
	}

	/**
	 * @return
	 */
	private Map<String,String> createDefaultParams(){
		return Collections.singletonMap("pretty", "true");
	}

	/**
	 * @param s
	 * @return
	 */
	private HttpEntity createEntity(String s) {
		return new NStringEntity(s, ContentType.APPLICATION_JSON);
	}

	/**
	 * ssl 인증을 위한 header 생성
	 * @return
	 */
	private Header[] createHeaders() {
		//
		String auth = id + ":" + pw;
		String basicAuth = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());

		//
		Header[] headers = {new BasicHeader("Authorization", basicAuth)};

		return headers;
	}

	/**
	 * @return StringBuffer
	 */
	public StringBuffer allQryStatement(){
		_qry.delete(0, _qry.length());
		_qry.append("    {");
		_qry.append("        \"sort\": [");
		_qry.append("        {");
		_qry.append("            \"agg_dt\": {");
		_qry.append("                \"order\": \"desc\"");
		_qry.append("            }");
		_qry.append("        }");
		_qry.append("    ],");
		_qry.append("    \"size\": 1");
		_qry.append("}");
		return _qry;
	}
	public void dateInit(String[] dateType){
		_now = LocalDate.parse(dateType[2]);
		_pastOfDays = LocalDate.parse(dateType[1]);
	}

	public void transDateForm(String condition, String[] dateType){
		if(condition.equals(dateType[0])) {
			dateInit(dateType);
			return;
		} else {
			_now = LocalDate.now();
			_pastOfDays = (condition.equals("daily")) ? _now.minusDays(7) : 
					(condition.equals("week")) ? _now.minusWeeks(5) : _now.minusMonths(6);
		}
	}

	/**
	 * @return StringBuffer
	 */
	public StringBuffer dayQryStatement(String[] daily){
		transDateForm("daily", daily);
		_qry.delete(0, _qry.length());
		_qry.append(" {");
		_qry.append("   \"query\": {");
		_qry.append("       \"bool\": {");
		_qry.append("          \"must\": [");
		_qry.append("          {");
		_qry.append("             \"range\": {");
		_qry.append("             \"agg_dt\": {");
		_qry.append("                \"gte\": \""+_pastOfDays+"\",");
		_qry.append("                      \"lte\": \""+_now+"\"");
		_qry.append("             }");
		_qry.append("          }");
		_qry.append("          }");
		_qry.append("      ]");
		_qry.append("       }");
		_qry.append("    },");
		_qry.append("       \"sort\": [");
		_qry.append("       {");
		_qry.append("          \"agg_dt\": {");
		_qry.append("          \"order\": \"asc\"");
		_qry.append("       }");
		_qry.append("       }");
		_qry.append("      ],");
		_qry.append("       \"size\": 999");
		_qry.append("    }");
		return _qry;
	}

	/**
	 * @return StringBuffer
	 */
	public StringBuffer weekQryStatement(String[] week){
		transDateForm("week", week);
		_qry.delete(0, _qry.length());
		_qry.append("{");
		_qry.append("   \"query\": {");
		_qry.append("   \"bool\": {");
		_qry.append("      \"must\": [");
		_qry.append("      {");
		_qry.append("      \"range\": {");
		_qry.append("         \"agg_dt\": {");
		_qry.append("            \"gte\": \""+_pastOfDays+"\",");
		_qry.append("            \"lte\": \""+_now+"\"");
		_qry.append("           }");
		_qry.append("      }");
		_qry.append("      }");
		_qry.append("  ]");
		_qry.append("   }");
		_qry.append("},");
		_qry.append("   \"aggs\": {");
		_qry.append("   \"aggWeek\": {");
		_qry.append("      \"date_histogram\": {");
		_qry.append("         \"field\": \"agg_dt\",");
		_qry.append("               \"interval\": \"week\"");
		_qry.append("      },");
		_qry.append("      \"aggs\": {");
		_qry.append("         \"daily_modon_increment\": {");
		_qry.append("            \"max\": {");
		_qry.append("               \"field\": \"daily_modon_increment\"");
		_qry.append("            }");
		_qry.append("         },");
		_qry.append("         \"daily_ekape_increment\": {");
		_qry.append("            \"max\": {");
		_qry.append("               \"field\": \"daily_ekape_increment\"");
		_qry.append("            }");
		_qry.append("         },");
		_qry.append("         \"daily_total_increment\": {");
		_qry.append("            \"max\": {");
		_qry.append("               \"field\": \"daily_total_increment\"");
		_qry.append("            }");
		_qry.append("         },");
		_qry.append("         \"re_daily_modon_increment\": {");
		_qry.append("            \"sum\": {");
		_qry.append("               \"field\": \"re_daily_modon_increment\"");
		_qry.append("            }");
		_qry.append("         },");
		_qry.append("         \"re_daily_ekape_increment\": {");
		_qry.append("            \"sum\": {");
		_qry.append("               \"field\": \"re_daily_ekape_increment\"");
		_qry.append("            }");
		_qry.append("         },");
		_qry.append("         \"re_daily_total_increment\": {");
		_qry.append("            \"sum\": {");
		_qry.append("               \"field\": \"re_daily_total_increment\"");
		_qry.append("            }");
		_qry.append("         }");
		_qry.append("      }");
		_qry.append("   }");
		_qry.append("},");
		_qry.append("   \"size\": 0");
		_qry.append("}");
		return _qry;
	}

	/**
	 * @return StringBuffer
	 */
	public StringBuffer monthQryStatement(String[] month){
		transDateForm("month", month);
		_qry.delete(0, _qry.length());
		_qry.append("{");
		_qry.append("   \"query\": {");
		_qry.append("   \"bool\": {");
		_qry.append("      \"must\": [");
		_qry.append("      {");
		_qry.append("         \"range\": {");
		_qry.append("         \"agg_dt\": {");
		_qry.append("            \"gte\": \""+_pastOfDays+"\",");
		_qry.append("                  \"lte\": \""+_now+"\"");
		_qry.append("         }");
		_qry.append("      }");
		_qry.append("      }");
		_qry.append("  ]");
		_qry.append("   }");
		_qry.append("},");
		_qry.append("   \"aggs\": {");
		_qry.append("   \"aggWeek\": {");
		_qry.append("      \"date_histogram\": {");
		_qry.append("         \"field\": \"agg_dt\",");
		_qry.append("               \"interval\": \"month\"");
		_qry.append("      },");
		_qry.append("      \"aggs\": {");
		_qry.append("         \"daily_modon_increment\": {");
		_qry.append("            \"max\": {");
		_qry.append("               \"field\": \"daily_modon_increment\"");
		_qry.append("            }");
		_qry.append("         },");
		_qry.append("         \"daily_ekape_increment\": {");
		_qry.append("            \"max\": {");
		_qry.append("               \"field\": \"daily_ekape_increment\"");
		_qry.append("            }");
		_qry.append("         },");
		_qry.append("         \"daily_total_increment\": {");
		_qry.append("            \"max\": {");
		_qry.append("               \"field\": \"daily_total_increment\"");
		_qry.append("            }");
		_qry.append("         },");
		_qry.append("         \"re_daily_modon_increment\": {");
		_qry.append("            \"sum\": {");
		_qry.append("               \"field\": \"re_daily_modon_increment\"");
		_qry.append("            }");
		_qry.append("         },");
		_qry.append("         \"re_daily_ekape_increment\": {");
		_qry.append("            \"sum\": {");
		_qry.append("               \"field\": \"re_daily_ekape_increment\"");
		_qry.append("            }");
		_qry.append("         },");
		_qry.append("         \"re_daily_total_increment\": {");
		_qry.append("            \"sum\": {");
		_qry.append("               \"field\": \"re_daily_total_increment\"");
		_qry.append("            }");
		_qry.append("         }");
		_qry.append("      }");
		_qry.append("   }");
		_qry.append("},");
		_qry.append("   \"size\": 0");
		_qry.append("}");
		return _qry;
	}




	/**
	 * RestClient 생성
	 * @return
	 */
	public RestClient createRestClient() {
		//RestClientBuilder restClient = RestClient.builder(new HttpHost(hostname, Integer.parseInt(port) , scheme));
		RestClientBuilder restClient = RestClient.builder(new HttpHost(hostname, Integer.parseInt(port) , "http"));
		
		// 헤더에 elastic 계정 셋팅함
		Header[] defaultHeaders = createHeaders();
		restClient.setDefaultHeaders(defaultHeaders);
		
		restClient.setRequestConfigCallback(new RequestConfigCallback() {
			@Override
			public Builder customizeRequestConfig(Builder requestConfigBuilder) {
				return requestConfigBuilder.setConnectTimeout(60000).setSocketTimeout(1200000).setConnectionRequestTimeout(0);
			}
		});


		//
		return restClient.build();

	}


	@Override
	public ElasticResultMap getCount(String index, Class<?> clazz)
			throws IOException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ElasticResultMap getMapping(String index, Class<?> clazz)
			throws IOException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public ElasticResultMap getSearch(String index, Class<?> clazz) throws IOException, InstantiationException, IllegalAccessException {
		if(null == restClient) {
			return null;
		}

		//
		String jsonString = performRequest(index, "", METHOD_GET, POSTFIX_SEARCH);

		//
		//return new ElasticResultMap(jsonString, clazz);
		return null;
	}


	@PostConstruct
	private void init() throws IOException, InstantiationException, IllegalAccessException {
		this.restClient = createRestClient();
		LOG.debug("restClient:" + restClient);

		//
		connectSslTest();

	}



	@Override
	public void postDoc(String index, String qry)
			throws IOException, InstantiationException, IllegalAccessException {
		String jsonString = performRequest(index, qry, METHOD_POST, POSTFIX_DOC);

	}


	@Override
	public ElasticResultMap postCount(String index, String qry)	throws IOException, InstantiationException, IllegalAccessException {
		String jsonString = performRequest(index, qry, METHOD_POST, POSTFIX_COUNT);
		return new ElasticResultMap(jsonString);
	}


	@Override
	public ElasticResultMap postMapping(String index, String qry)
			throws IOException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ElasticResultMap postSearch(String index, String qry) throws IOException, InstantiationException, IllegalAccessException {
		String jsonString = performRequest(index, qry, METHOD_POST, POSTFIX_SEARCH);
		return new ElasticResultMap(jsonString);
	}

	@Override
	public ElasticResultMap fesSearch() throws IOException, InstantiationException, IllegalAccessException {
		String qry = allQryStatement().toString();
		String jsonString = performRequest(_fesIndex, qry, METHOD_GET, POSTFIX_SEARCH);
		return new ElasticResultMap(jsonString);
	}


	@Override
	public ElasticResultMap fesDailySearch(String[] daily) throws IOException, InstantiationException, IllegalAccessException {
		//일간 누적 모돈수 / 누적 출하두수 / 누적 데이터건수 조회 쿼리
		String qry = dayQryStatement(daily).toString();
		String jsonString = performRequest(_fesIndex, qry, METHOD_GET, POSTFIX_SEARCH);
		return new ElasticResultMap(jsonString);
	}

	@Override
	public ElasticResultMap fesDailySearchAPI(SearchVO searchVO) throws IOException, InstantiationException, IllegalAccessException {
		String[] daily = {searchVO.getGcCondition(), searchVO.getStartCondition(), searchVO.getEndCondition()};
		//일간 누적 모돈수 / 누적 출하두수 / 누적 데이터건수 조회 쿼리
		String qry = dayQryStatement(daily).toString();
		String jsonString = performRequest(_fesIndex, qry, METHOD_GET, POSTFIX_SEARCH);
		return new ElasticResultMap(jsonString);
	}

	@Override
	public ElasticResultMap fesWeekSearchAPI(SearchVO searchVO) throws IOException, InstantiationException, IllegalAccessException {
		String[] week = {searchVO.getGcCondition(), searchVO.getStartCondition(), searchVO.getEndCondition()};
		//일간 누적 모돈수 / 누적 출하두수 / 누적 데이터건수 조회 쿼리
		String qry = weekQryStatement(week).toString();
		String jsonString = performRequest(_fesIndex, qry, METHOD_GET, POSTFIX_SEARCH);
		return new ElasticResultMap(jsonString);
	}

	@Override
	public ElasticResultMap fesMonthSearchAPI(SearchVO searchVO) throws IOException, InstantiationException, IllegalAccessException {
		String[] month = {searchVO.getGcCondition(), searchVO.getStartCondition(), searchVO.getEndCondition()};
		//일간 누적 모돈수 / 누적 출하두수 / 누적 데이터건수 조회 쿼리
		String qry = monthQryStatement(month).toString();
		String jsonString = performRequest(_fesIndex, qry, METHOD_GET, POSTFIX_SEARCH);
		return new ElasticResultMap(jsonString);
	}

	@Override
	public ElasticResultMap fesWeekSearch(String[] week) throws IOException, InstantiationException, IllegalAccessException {
		//주간 누적 모돈수 / 누적 출하두수 / 누적 데이터건수 조회 쿼리
		String qry = weekQryStatement(week).toString();
		String jsonString = performRequest(_fesIndex, qry, METHOD_GET, POSTFIX_SEARCH);
		return new ElasticResultMap(jsonString);
	}

	@Override
	public ElasticResultMap fesMonthSearch(String[] month) throws IOException, InstantiationException, IllegalAccessException {
		String qry = monthQryStatement(month).toString();
		String jsonString = performRequest(_fesIndex, qry, METHOD_GET, POSTFIX_SEARCH);
		return new ElasticResultMap(jsonString);
	}

	/**
	 * @param index
	 * @param qry
	 * @param method
	 * @param postfix
	 * @return
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private String performRequest(String index, String qry, String method, String postfix) throws IOException, InstantiationException, IllegalAccessException {
		if(null == restClient) {
			LOG.info("<<.performRequest - restClient is null");
			return null;
		}


		//
		Map<String,String> params = createDefaultParams();
		HttpEntity entity = createEntity(qry);
		HttpAsyncResponseConsumerFactory consumerFactory = createConsumerFactory();
		Header[] headers = createHeaders();


		//
		Response response = restClient.performRequest(method, index + postfix, params, entity, consumerFactory, headers);
		String jsonString = EntityUtils.toString(response.getEntity());


		return jsonString;
	}

}