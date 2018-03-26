package com.sumscope.cdh.realtime.transfer;

import com.lmax.disruptor.dsl.Disruptor;
import com.sumscope.cdh.realtime.transfer.model.handler.BondType;
import com.sumscope.cdh.realtime.transfer.model.handler.SourceEventModel;
import com.sumscope.cdh.realtime.transfer.model.handler.TargetEventModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@Configuration
@EnableAutoConfiguration
@ComponentScan("com.sumscope.cdh.realtime.transfer")
@PropertySource(value = "application-dev.properties")
class DataStandardApplicationTestsConfig {
}

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = DataStandardApplicationTestsConfig.class) // to specify properties file
public class DataStandardApplicationTests {
	
	@Autowired
	@Qualifier("SourceDisruptor")
	private Disruptor<SourceEventModel> sourceDisruptor;

	@Autowired
	@Qualifier("TargetDisruptor")
	private Disruptor<TargetEventModel> targetDisruptor;

	private String bbo_1 = "{\"AckMsgHead\":{\"exectime\":0.0,\"total\":1,\"retcode\":0,\"idx\":0},\"AckMsgBody\":{\"BusinessCode\":\"BOND\",\"IMQ_OUTCOME\":[{\"fbar\":\"1\",\"vol\":\"1000\",\"pri\":\"4.80\",\"fbad\":\"false\",\"gc\":\"101558056\",\"id\":\"6196f91d93de4c7ea8ba55d281c9773d\",\"cn\":\"\\u56fd\\u5229\",\"ps\":\"1\",\"ver\":7109,\"lm\":\"CIB\",\"reb\":\"0\",\"ct\":1507516774,\"fr\":\"0\",\"fv\":\"false\",\"bklm\":\"D0001152015CORLMN01CIB\",\"yd\":\"4.80\",\"sym\":\"-1\",\"bk\":\"D0001152015CORLMN01\",\"np\":\"98.1655\",\"ds\":\"0\",\"qt\":\"3\",\"cid\":\"1\",\"pdesc\":\"1000(*)\",\"mt\":1507516774,\"sts\":\"1\",\"gsn\":\"15\\u5927\\u8fde\\u5efa\\u6295MTN001\"},{\"fbar\":\"1\",\"vol\":\"2000\",\"pri\":\"5.25\",\"fbad\":\"false\",\"gc\":\"101580014\",\"id\":\"45e771d30dd34f7aa5c80001ea96dd75\",\"cn\":\"\\u56fd\\u5229\",\"ps\":\"1\",\"ver\":7110,\"lm\":\"CIB\",\"reb\":\"0\",\"ct\":1507516774,\"fr\":\"0\",\"fv\":\"false\",\"bklm\":\"J0001532015CORLMN02CIB\",\"yd\":\"5.25\",\"sym\":\"-1\",\"bk\":\"J0001532015CORLMN02\",\"np\":\"99.2529\",\"ds\":\"0\",\"qt\":\"3\",\"cid\":\"1\",\"pdesc\":\"2000(*)\",\"mt\":1507516774,\"sts\":\"1\",\"gsn\":\"15\\u666f\\u56fd\\u8d44MTN002\"},{\"fbar\":\"1\",\"vol\":\"1000\",\"pri\":\"4.75\",\"fbad\":\"false\",\"gc\":\"101561019\",\"id\":\"382face332f244aaa88fa879430921db\",\"cn\":\"\\u56fd\\u5229\",\"ps\":\"1\",\"ver\":7111,\"lm\":\"CIB\",\"reb\":\"0\",\"ct\":1507516774,\"fr\":\"0\",\"fv\":\"false\",\"bklm\":\"J0004292015CORLMN01CIB\",\"yd\":\"4.75\",\"sym\":\"-1\",\"bk\":\"J0004292015CORLMN01\",\"np\":\"100.8851\",\"ds\":\"0\",\"qt\":\"3\",\"cid\":\"1\",\"pdesc\":\"1000(*)\",\"mt\":1507516774,\"sts\":\"1\",\"gsn\":\"15\\u91d1\\u534e\\u57ce\\u5efaMTN001\"},{\"fbar\":\"1\",\"vol\":\"2000\",\"pri\":\"5.00\",\"fbad\":\"false\",\"gc\":\"101754055\",\"id\":\"b578391f8e104c03a5fff35f86364ea2\",\"cn\":\"\\u56fd\\u5229\",\"ps\":\"1\",\"ver\":7112,\"lm\":\"CIB\",\"reb\":\"0\",\"ct\":1507516774,\"fr\":\"0\",\"fv\":\"false\",\"bklm\":\"L0001602017CORLMN03CIB\",\"yd\":\"5.00\",\"sym\":\"-1\",\"bk\":\"L0001602017CORLMN03\",\"np\":\"101.1205\",\"ds\":\"0\",\"qt\":\"3\",\"cid\":\"1\",\"pdesc\":\"2000(*)\",\"mt\":1507516774,\"sts\":\"1\",\"gsn\":\"17\\u7eff\\u57ce\\u623f\\u4ea7MTN003\"},{\"fbar\":\"1\",\"vol\":\"3000\",\"pri\":\"4.40\",\"fbad\":\"false\",\"gc\":\"1282399\",\"id\":\"74ec8abf949646919cf5bf635d419cc2\",\"cn\":\"\\u56fd\\u5229\",\"ps\":\"1\",\"ver\":7113,\"lm\":\"CIB\",\"reb\":\"0\",\"ct\":1507516774,\"fr\":\"0\",\"fv\":\"false\",\"bklm\":\"N0000442012CORLMN02CIB\",\"yd\":\"4.399\",\"sym\":\"-1\",\"bk\":\"N0000442012CORLMN02\",\"np\":\"100.0149\",\"ds\":\"0\",\"qt\":\"3\",\"cid\":\"1\",\"pdesc\":\"3000(*)\",\"mt\":1507516774,\"sts\":\"1\",\"gsn\":\"12\\u5357\\u65b0\\u5de5MTN2\"},{\"fbar\":\"1\",\"vol\":\"1000\",\"pri\":\"4.70\",\"fbad\":\"false\",\"gc\":\"101456043\",\"id\":\"f562215ce3db43d09f002a5f357bcbf4\",\"cn\":\"\\u56fd\\u5229\",\"ps\":\"1\",\"ver\":7114,\"lm\":\"CIB\",\"reb\":\"0\",\"ct\":1507516774,\"fr\":\"0\",\"fv\":\"false\",\"bklm\":\"N0000652014CORLMN01CIB\",\"yd\":\"4.70\",\"sym\":\"-1\",\"bk\":\"N0000652014CORLMN01\",\"np\":\"102.7519\",\"ds\":\"0\",\"qt\":\"3\",\"cid\":\"1\",\"pdesc\":\"1000(*)\",\"mt\":1507516774,\"sts\":\"1\",\"gsn\":\"14\\u5b81\\u4ea4\\u6295MTN001\"},{\"fbar\":\"1\",\"vol\":\"2000\",\"pri\":\"5.45\",\"fbad\":\"false\",\"gc\":\"101663013\",\"id\":\"d6736273574e49689d3be9b57b860e5d\",\"cn\":\"\\u56fd\\u5229\",\"ps\":\"1\",\"ver\":7115,\"lm\":\"CIB\",\"reb\":\"0\",\"ct\":1507516774,\"fr\":\"0\",\"fv\":\"false\",\"bklm\":\"N0000982016CORLMN01CIB\",\"yd\":\"5.45\",\"sym\":\"-1\",\"bk\":\"N0000982016CORLMN01\",\"np\":\"96.5794\",\"ds\":\"0\",\"qt\":\"3\",\"cid\":\"1\",\"pdesc\":\"2000(*)\",\"mt\":1507516774,\"sts\":\"1\",\"gsn\":\"16\\u5175\\u56e2\\u4e8c\\u5e08MTN001\"},{\"fbar\":\"1\",\"vol\":\"3000\",\"pri\":\"5.00\",\"fbad\":\"false\",\"gc\":\"101558012\",\"id\":\"ec2c8ffc5c3542139db053a9c89a8c20\",\"cn\":\"\\u56fd\\u5229\",\"ps\":\"1\",\"ver\":7116,\"lm\":\"CIB\",\"reb\":\"0\",\"ct\":1507516774,\"fr\":\"0\",\"fv\":\"false\",\"bklm\":\"S0001732015CORLMN02CIB\",\"yd\":\"5.00\",\"sym\":\"-1\",\"bk\":\"S0001732015CORLMN02\",\"np\":\"100.0533\",\"ds\":\"0\",\"qt\":\"3\",\"cid\":\"1\",\"pdesc\":\"3000(*)\",\"mt\":1507516774,\"sts\":\"1\",\"gsn\":\"15\\u534e\\u5f3aMTN002\"},{\"fbar\":\"1\",\"vol\":\"3000\",\"pri\":\"5.00\",\"fbad\":\"false\",\"gc\":\"101575020\",\"id\":\"79003970f3a74142bd6a9cffc86b707c\",\"cn\":\"\\u56fd\\u5229\",\"ps\":\"1\",\"ver\":7117,\"lm\":\"CIB\",\"reb\":\"0\",\"ct\":1507516774,\"fr\":\"0\",\"fv\":\"false\",\"bklm\":\"Y0000392015CORLMN01CIB\",\"yd\":\"5.00\",\"sym\":\"-1\",\"bk\":\"Y0000392015CORLMN01\",\"np\":\"98.3897\",\"ds\":\"0\",\"qt\":\"3\",\"cid\":\"1\",\"pdesc\":\"3000(*)\",\"mt\":1507516774,\"sts\":\"1\",\"gsn\":\"15\\u5b9c\\u6625\\u57ce\\u6295MTN001\"},{\"fbar\":\"1\",\"vol\":\"5000\",\"pri\":\"5.05\",\"fbad\":\"false\",\"gc\":\"101472008\",\"id\":\"942cf5db596847789b3995077ea0da3a\",\"cn\":\"\\u56fd\\u5229\",\"ps\":\"1\",\"ver\":7118,\"lm\":\"CIB\",\"reb\":\"0\",\"ct\":1507516774,\"fr\":\"0\",\"fv\":\"false\",\"bklm\":\"Y0001192014CORLMN01CIB\",\"yd\":\"5.05\",\"sym\":\"-1\",\"bk\":\"Y0001192014CORLMN01\",\"np\":\"103.0659\",\"ds\":\"0\",\"qt\":\"3\",\"cid\":\"1\",\"pdesc\":\"5000(*)\",\"mt\":1507516774,\"sts\":\"1\",\"gsn\":\"14\\u96c5\\u5b89\\u53d1\\u5c55MTN001\"},{\"fbar\":\"1\",\"vol\":\"2000\",\"pri\":\"4.45\",\"fbad\":\"false\",\"gc\":\"1382161\",\"id\":\"df560624998c4ecab70e0c5447d37625\",\"cn\":\"\\u56fd\\u5229\",\"ps\":\"1\",\"ver\":7119,\"lm\":\"CIB\",\"reb\":\"0\",\"ct\":1507516774,\"fr\":\"0\",\"fv\":\"false\",\"bklm\":\"Z0003862013CORLMN01CIB\",\"yd\":\"4.4501\",\"sym\":\"-1\",\"bk\":\"Z0003862013CORLMN01\",\"np\":\"100.2553\",\"ds\":\"0\",\"qt\":\"3\",\"cid\":\"1\",\"pdesc\":\"2000(*)\",\"mt\":1507516774,\"sts\":\"1\",\"gsn\":\"13\\u6e1d\\u6c34\\u6295MTN1\"},{\"fbar\":\"1\",\"vol\":\"2000\",\"pri\":\"4.70\",\"fbad\":\"false\",\"gc\":\"101458029\",\"id\":\"78279c91422a4788afd33fad121f4c25\",\"cn\":\"\\u56fd\\u5229\",\"ps\":\"1\",\"ver\":7120,\"lm\":\"CIB\",\"reb\":\"0\",\"ct\":1507516774,\"fr\":\"0\",\"fv\":\"false\",\"bklm\":\"Z0009052014CORLMN01CIB\",\"yd\":\"4.70\",\"sym\":\"-1\",\"bk\":\"Z0009052014CORLMN01\",\"np\":\"102.216\",\"ds\":\"0\",\"qt\":\"3\",\"cid\":\"1\",\"pdesc\":\"2000(*)\",\"mt\":1507516774,\"sts\":\"1\",\"gsn\":\"14\\u73e0\\u6d77\\u534e\\u53d1MTN001\"}]},\"seq\":336460}";
	private String bbo_2 = "{\"AckMsgHead\":{\"exectime\":0.0,\"total\":1,\"retcode\":0,\"idx\":0},\"AckMsgBody\":{\"BusinessCode\":\"BOND\",\"IMQ_OUTCOME\":[{\"MS_OPERATE\":\"0\",\"MS_GOODS_CODE\":\"136061\",\"MS_BOND_KEY_LISTED_MARKET\":\"D0000402015FINSEB01SSE\",\"MS_BestVersion\":7095,\"MS_TYPE\":\"1\",\"MS_BOND_KEY\":\"D0000402015FINSEB01\",\"MS_BODY\":{\"MS_ofrFlagRelation\":\"0\",\"MS_ofrPriceDescription\":\"5000(*)+4054(*)+3000(*)+5000(*)\",\"MS_ofrOfferId\":\"bfae84ea50424da6841f35d4b5199a13\",\"MS_ofrVolume\":\"5000+4054+3000+5000\",\"MS_ofrExercise\":\"1\",\"MS_ofrFlagBargain\":\"1\",\"MS_ofrNetPrice\":\"97.982\",\"MS_ofrPrice\":\"4.60\",\"MS_ofrRebate\":\"0\",\"MS_ofrYield\":\"4.60\",\"MS_goodsCode\":\"136061\",\"MS_createTime\":1507516711},\"MS_COMPANY_ID\":\"1\",\"MS_CREATE_TIME\":1507516711,\"MS_Version\":32363,\"MS_MODIFY_TIME\":1507516711,\"MS_LISTED_MARKET\":\"SSE\"},{\"MS_OPERATE\":\"0\",\"MS_GOODS_CODE\":\"1480522\",\"MS_BOND_KEY_LISTED_MARKET\":\"J0003692014CORLEB01CIB\",\"MS_BestVersion\":7096,\"MS_TYPE\":\"1\",\"MS_BOND_KEY\":\"J0003692014CORLEB01\",\"MS_BODY\":{\"MS_ofrFlagRelation\":\"0\",\"MS_ofrPriceDescription\":\"4000(*)\",\"MS_ofrOfferId\":\"1998a80c436f4216bcf8718ce1142241\",\"MS_ofrVolume\":\"4000\",\"MS_ofrExercise\":\"1\",\"MS_ofrFlagBargain\":\"1\",\"MS_ofrNetPrice\":\"82.8908\",\"MS_ofrPrice\":\"5.65\",\"MS_ofrRebate\":\"0\",\"MS_ofrYield\":\"5.65\",\"MS_goodsCode\":\"1480522\",\"MS_createTime\":1507516711},\"MS_COMPANY_ID\":\"1\",\"MS_CREATE_TIME\":1507516711,\"MS_Version\":32364,\"MS_MODIFY_TIME\":1507516711,\"MS_LISTED_MARKET\":\"CIB\"},{\"MS_OPERATE\":\"0\",\"MS_GOODS_CODE\":\"127415\",\"MS_BOND_KEY_LISTED_MARKET\":\"S0008362016CORLEB01SSE\",\"MS_BestVersion\":7097,\"MS_TYPE\":\"1\",\"MS_BOND_KEY\":\"S0008362016CORLEB01\",\"MS_BODY\":{\"MS_ofrFlagRelation\":\"0\",\"MS_ofrPriceDescription\":\"5000(*)\",\"MS_ofrOfferId\":\"267bc98021de4aa896c42fc764f0cb9b\",\"MS_ofrVolume\":\"5000\",\"MS_ofrExercise\":\"1\",\"MS_ofrFlagBargain\":\"1\",\"MS_ofrNetPrice\":\"96.0628\",\"MS_ofrPrice\":\"4.95\",\"MS_ofrRebate\":\"0\",\"MS_ofrYield\":\"4.95\",\"MS_goodsCode\":\"127415\",\"MS_createTime\":1507516711},\"MS_COMPANY_ID\":\"1\",\"MS_CREATE_TIME\":1507516711,\"MS_Version\":32365,\"MS_MODIFY_TIME\":1507516711,\"MS_LISTED_MARKET\":\"SSE\"}]},\"seq\":336223}";
	private String trade = "{\"AckMsgHead\":{\"exectime\":0.0,\"total\":1,\"retcode\":0,\"idx\":0},\"AckMsgBody\":{\"BusinessCode\":\"BOND\",\"IMQ_OUTCOME\":[{\"MS_OPERATE\":\"2\",\"MS_GOODS_CODE\":\"018005\",\"MS_BOND_KEY_LISTED_MARKET\":\"G0001242017FINPBB01SHSSE\",\"MS_TYPE\":\"2\",\"MS_BOND_KEY\":\"G0001242017FINPBB01SH\",\"MS_BODY\":{\"MS_id\":\"2480118dc0864070800fcaebef6a2103\",\"MS_price\":\"4.2962\",\"MS_fullPrice\":\"101.1762\",\"MS_netPrice\":\"99.250\",\"MS_pre_close_price\":\"99.350\",\"MS_goodsShortName\":\"\\u56fd\\u5f001701\",\"MS_dealStatus\":\"3\",\"MS_volume\":\"1007.1\",\"MS_goodsCode\":\"018005\",\"MS_createTime\":1507516802,\"MS_yield\":\"4.2962\"},\"MS_COMPANY_ID\":\"e\",\"MS_CREATE_TIME\":1507516802,\"MS_Version\":3079,\"MS_MODIFY_TIME\":1507516802,\"MS_LISTED_MARKET\":\"SSE\"},{\"MS_OPERATE\":\"2\",\"MS_GOODS_CODE\":\"132002\",\"MS_BOND_KEY_LISTED_MARKET\":\"T0000712015CORSCV01SSE\",\"MS_TYPE\":\"2\",\"MS_BOND_KEY\":\"T0000712015CORSCV01\",\"MS_BODY\":{\"MS_id\":\"96dbd9ffc87049bd82c69d5dbafd7c0f\",\"MS_dealStatus\":\"3\",\"MS_netPrice\":\"103.130\",\"MS_pre_close_price\":\"103.600\",\"MS_goodsShortName\":\"15\\u5929\\u96c6EB\",\"MS_volume\":\"0.2\",\"MS_goodsCode\":\"132002\",\"MS_createTime\":1507516833},\"MS_COMPANY_ID\":\"e\",\"MS_CREATE_TIME\":1507516833,\"MS_Version\":3080,\"MS_MODIFY_TIME\":1507516833,\"MS_LISTED_MARKET\":\"SSE\"}]},\"seq\":336721}";

	@Before
	public void init(){
	}

	@Test
	public void insertBBO_1(){
		sourceDisruptor.publishEvent((event, seq, arg) -> {event.setMessage(bbo_1);event.setBondType(BondType.SINGLEBBO);}, bbo_1);
	}

	@Test
	public void insertBBO_2(){
		sourceDisruptor.publishEvent((event, seq, arg) -> {event.setMessage(bbo_2);event.setBondType(BondType.BBO);}, bbo_2);
	}

	@Test
	public void insertTrade(){
		sourceDisruptor.publishEvent((event, seq, arg) -> {event.setMessage(trade);event.setBondType(BondType.TRADE);}, trade);
	}
}
