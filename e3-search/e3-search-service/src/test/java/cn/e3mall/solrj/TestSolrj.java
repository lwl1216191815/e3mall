package cn.e3mall.solrj;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient.Builder;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;


public class TestSolrj {
	@Test
	public void addDocument() throws Exception{
		Builder builder = new Builder("http://localhost:8983/solr/test_core");
		// 1 创建一个SolrServer对象，参数为solr的服务地址
		HttpSolrClient solrClient = builder.build();
		// 2 创建一个文档对象SolrInputDocument对象
		SolrInputDocument document = new SolrInputDocument();
		// 3 向文档中添加域，文档中必须包含一个ID域，所有的域名必须在Schema.xml中定义
		document.addField("id", "test001");
		document.addField("item_title", "测试商品");
		document.addField("item_price", "199");
		// 4 把文档写入索引库
		UpdateResponse response = solrClient.add(document);
		System.out.println(response.getElapsedTime());
		// 5 提交
		solrClient.commit();
	}
	@Test
	public void queryDocument() throws Exception {
		//Builder builder = new Builder("http://localhost:8983/solr");;
		Builder builder = new Builder("http://localhost:8983/solr/test_core");
		// 1 创建一个SolrServer对象，参数为solr的服务地址
		HttpSolrClient solrClient = builder.build();
		// 第二步：创建一个SolrQuery对象。
		SolrQuery query = new SolrQuery();
		// 第三步：向SolrQuery中添加查询条件、过滤条件。。。
		query.setQuery("*:*");
		// 第四步：执行查询。得到一个Response对象。
		QueryResponse response = solrClient.query(query);
		// 第五步：取查询结果。
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println("查询结果的总记录数：" + solrDocumentList.getNumFound());
		// 第六步：遍历结果并打印。
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
		}
	}
	
	@Test
	public void deleteDocById() throws Exception {
		Builder builder = new Builder("http://localhost:8983/solr/test_core");
		SolrClient solrClient = builder.build();
		solrClient.deleteById("test001");
		solrClient.commit();
	}
	@Test
	public void deleteDocByQuery() throws Exception {
		Builder builder = new Builder("http://localhost:8983/solr/test_core");
		SolrClient solrClient = builder.build();
		solrClient.deleteByQuery("item_title:测试商品");
		solrClient.commit();
	}
	@Test
	public void testEnum() throws Exception{
		//System.out.println(SearchItemEnum.ID);
	}
}
