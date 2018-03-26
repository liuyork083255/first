package com.sumscope.cdh.webbond.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.index.hash.HashIndex;
import com.googlecode.cqengine.index.navigable.NavigableIndex;
import com.sumscope.cdh.webbond.utils.QpidReceiver;
import com.sumscope.cdh.webbond.utils.TimeMeasure;
import com.sumscope.cdh.webbond.Config;
import com.sumscope.cdh.webbond.qpid.BondQpidConsumer;
import com.sumscope.cdh.webbond.utils.BondsTool;
import com.sumscope.cdh.webbond.utils.ModuleResources;
import com.sumscope.cdh.webbond.utils.JsonTool;
import com.sumscope.cdh.webbond.utils.DictionaryTool;
import com.sumscope.cdh.webbond.model.Bond;
import com.sumscope.cdh.webbond.request.BondFilter;
import com.sumscope.cdh.webbond.utils.BondFilterTool;
import com.sumscope.cdh.webbond.utils.BondsJsonTool;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Configuration
@PropertySource(value="application.properties")
class RefDataTestConfig {
    @Bean
    Config config(){ return new Config();}
}

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RefDataTestConfig.class)
public class RefDataTest {
    static final Logger logger = LoggerFactory.getLogger("com.sumscope.cdh.webbond");

    @Autowired
    Config config;

    private static BondFilter createFilter() {
        BondFilter filter = new BondFilter();
        filter.setSsSecCode("");
        filter.setQbBondTypes(Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"));
        filter.setBondSubTypes(Arrays.asList("BGB", "SCB", "EGB"));
        filter.setBondResidualMaturities(Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
        filter.setBondResidualMaturityStart("100D");
        filter.setBondResidualMaturityEnd("2.1Y");
        filter.setBondMaturityDateStart("20150101");
        filter.setBondMaturityDateEnd("20161231");
        filter.setPerpetualTypes(Arrays.asList("01", "02"));
        filter.setIssuers(Arrays.asList("A000001", "S000077", "T000068"));
        filter.setIssuerTypes(Arrays.asList("CGE", "LGE", "PVE", "OTE"));
        filter.setMunicipalTypes(Arrays.asList("01", "02", "03"));
        filter.setIssuerRatings(Arrays.asList("01", "02", "03", "04", "05", "06", "07"));
        filter.setBondRatings(Arrays.asList("01", "02", "03", "04", "05", "06", "07"));
        filter.setRatingAugments(Arrays.asList("GUA", "NON", "SEC"));
        filter.setFinancialBondTypes(Arrays.asList("01", "02"));
        filter.setCorporateBondTypes(Arrays.asList("01", "02"));
        filter.setCouponTypes(Arrays.asList("01", "02", "03"));
        filter.setOptionTypes(Arrays.asList("01", "02"));
        filter.setSectors(Arrays.asList("CJY0201", "CMY0101", "DQS0101"));
        filter.setProvinces(Arrays.asList("AHPRN", "BJPRN", "CQPRN"));
        filter.setIssueYears(Arrays.asList("2016", "2015", "2014", "2013", "2012", "2011"));
        filter.setSpecialTypes(Arrays.asList("01", "02", "03", "04", "05"));


        return filter;
    }

    private static BondFilter createFilter2() {
        BondFilter filter = new BondFilter();
        filter.setSsSecCode("");
        filter.setQbBondTypes(Arrays.asList("01"));
        filter.setBondSubTypes(Arrays.asList("BGB"));
        filter.setBondResidualMaturities(Arrays.asList("01"));
        filter.setPerpetualTypes(Arrays.asList("01"));
        filter.setIssuers(Arrays.asList("A000001"));
        filter.setIssuerTypes(Arrays.asList("CGE"));
        filter.setMunicipalTypes(Arrays.asList("01"));
        filter.setIssuerRatings(Arrays.asList("01"));
        filter.setBondRatings(Arrays.asList("01"));
        filter.setRatingAugments(Arrays.asList("GUA"));
        filter.setFinancialBondTypes(Arrays.asList("01", "02"));
        filter.setCorporateBondTypes(Arrays.asList("01", "02"));
        filter.setCouponTypes(Arrays.asList("01", "02"));
        filter.setOptionTypes(Arrays.asList("01"));
        filter.setSectors(Arrays.asList("CJY0201"));
        filter.setProvinces(Arrays.asList("AHPRN"));
        filter.setIssueYears(Arrays.asList("2016", "2015"));
        filter.setSpecialTypes(Arrays.asList("01", "02"));

        return filter;
    }

    private static String oneof(List<String> values, Random rand) {
        return values.get(rand.nextInt(values.size()));
    }

    private static Bond createRandomBond(BondFilter filter, int bondSequence, Random rand) {
        Bond bond = new Bond();
        bond.bondCode = "code" + Integer.toString(bondSequence);
        bond.qbBondType = oneof(filter.getQbBondTypes(), rand);
        bond.bond_subtype = oneof(filter.getBondSubTypes(), rand);
        bond.bondResidualMaturity = Double.valueOf(oneof(filter.getBondResidualMaturities(), rand));
        bond.maturity_date = 20170806;
        //bond.maturity_date = LocalDate.of(rand.nextInt(5) + 2016, rand.nextInt(12) + 1, rand.nextInt(28) + 1).toString();
        bond.perpetualType = oneof(filter.getPerpetualTypes(), rand);
        bond.issuer_code = oneof(filter.getIssuers(), rand);
        bond.issuerType = oneof(filter.getIssuerTypes(), rand);
        bond.municipalType = Arrays.asList(oneof(filter.getMunicipalTypes(), rand));
        bond.issuerRating = oneof(filter.getIssuerRatings(), rand);
        bond.bondRating = oneof(filter.getBondRatings(), rand);
        bond.rating_augment = oneof(filter.getRatingAugments(), rand);
        bond.financialBondType = oneof(filter.getFinancialBondTypes(), rand);
        bond.corporateBondType = oneof(filter.getCorporateBondTypes(), rand);
        bond.couponType = oneof(filter.getCouponTypes(), rand);
        bond.optionType = oneof(filter.getOptionTypes(), rand);
        bond.sector = oneof(filter.getSectors(), rand);
        bond.province = oneof(filter.getProvinces(), rand);
        bond.issue_year = oneof(filter.getIssueYears(), rand);
        bond.specialTypes = Arrays.asList(oneof(filter.getSpecialTypes(), rand));
        return bond;
    }

    private static List<Bond> createRandomBonds(int size, BondFilter filter, Random rand) {
        List<Bond> bonds = new ArrayList<>(size);
        for (int i = 0; i<size; i++)
            bonds.add(createRandomBond(filter, i+1, rand));
        return bonds;
    }

    @Ignore
    @Test
    public void testWriteRandomBondsToFile() throws IOException {
        ObjectMapper mapper = JsonTool.createObjectMapper();
        String ss = mapper.writeValueAsString(createFilter());
        logger.info(ss);
        FileUtils.writeStringToFile(new File("d:\\request.json"), ss, "UTF-8");
        BondFilter filter = mapper.readValue(ss, BondFilter.class);
        List<Bond> bonds = createRandomBonds(50000, filter, new Random());
        mapper.writeValue(new File("d:\\bonds.json"), bonds);
    }

    @Ignore
    @Test
    public void testCreateBondsFromFileAndDoFilter() throws Exception {
        ObjectMapper mapper = JsonTool.createObjectMapper();
        List<Bond> bonds = BondsJsonTool.deserializeBondsFromFile("d:\\bonds.json", mapper);

        BondFilter filter = createFilter2();
        try(TimeMeasure timeMeasure = new TimeMeasure("request bond")) {
            Collection<String> bondIds = BondFilterTool.filterBonds(bonds, filter);
            logger.info(Integer.toString(bondIds.size()));
        }
    }

    @Test
    public void testBondLevel1() throws IOException {
        ObjectMapper mapper = JsonTool.createObjectMapper();
        Assert.assertTrue(DictionaryTool.getLevel1FromLevel2(ModuleResources.getStaticDictionary(mapper),
                "bondSubType", "RRD", "N").equals("M"));
    }

    @Test
    //@Ignore
    public void testGenerateDictionary() throws Exception {
        ObjectMapper mapper = JsonTool.createObjectMapper();
        ObjectNode dictionaryNode = ModuleResources.getStaticDictionary(mapper);

        try(TimeMeasure timeMeasure = new TimeMeasure("Generate dictionary")) {
            Assert.assertTrue(dictionaryNode.with("msg").size() == 16);
            DictionaryTool.generateDictionary(config, mapper, dictionaryNode);
            Assert.assertTrue(dictionaryNode.with("msg").size() == 20);
            Assert.assertTrue(dictionaryNode.with("msg").withArray("issuer").size() > 100);

            // JsonTool.nodeToFile(mapper, dictionaryNode, "D:\\dictionary.json");

            // test SectorLevel1
            Assert.assertTrue(DictionaryTool.getLevel1FromLevel2(dictionaryNode,
                    "sector", "YYS0204", "").equals("YYS0101"));
        }
    }

    @Test
    public void testConvertDaysToYears() {
        double x1 = 0.024;
        System.out.println(String.format("%.2f", x1));
        double x2 = 0.025;
        System.out.println(String.format("%.2f", x2));

        logger.info(BondsTool.convertDaysToYears("notUsed", "6882D"));
        logger.info(BondsTool.residualMaturityLabel("3650"));
    }

    @Test
   // @Ignore
    public void testGenerateBonds() throws Exception {
        ObjectMapper mapper = JsonTool.createObjectMapper();
        ObjectNode dictionaryNode = ModuleResources.getStaticDictionary(mapper);
        DictionaryTool.generateDictionary(config, mapper, dictionaryNode);

        Map<String, Bond> bonds = new ConcurrentHashMap<>();
        try(TimeMeasure timeMeasure = new TimeMeasure("Generate bonds")) {
            BondsTool.generateBonds(config, bonds, dictionaryNode);
            logger.info("Generated bond sizes: {}", bonds.size());
            Assert.assertTrue(bonds.size() > 10000);
        }

        Bond testBond = new Bond();
        testBond.maturity_date = 20170807;
        testBond.bondResidualMaturity = 23.5;
        bonds.put("hahah", testBond);


        BondFilter filter = new BondFilter();

        filter.setBondSubTypes(Arrays.asList("A","B","C","D","CCP"));
        filter.setSpecialTypes(Arrays.asList("01","02","03"));

        //request.setBondResidualMaturities(Arrays.asList("01","02","03"));
        filter.setBondResidualMaturityEnd("1y");
        //request.setBondMaturityDateStart("20180101");
        filter.setBondMaturityDateEnd("20181231");

        try(TimeMeasure timeMeasure = new TimeMeasure("request bond using old way")) {
            Collection<String> bondIds = BondFilterTool.filterBonds(bonds.values(), filter);
            logger.info(Integer.toString(bondIds.size()));
        }

        //do filter1
        IndexedCollection<Bond> indexedBonds = new ConcurrentIndexedCollection<Bond>();
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_SUB_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_SUB_TYPE_LEVEL1));
        indexedBonds.addIndex(NavigableIndex.onAttribute(Bond.BOND_RESIDUAL_MATURITY));
        indexedBonds.addIndex(NavigableIndex.onAttribute(Bond.BOND_RESIDUAL_MATURITY_LABEL));
        indexedBonds.addIndex(NavigableIndex.onAttribute(Bond.BOND_MATURITY_DATE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_PERPETUAL_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_ISSUER_CODE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_ISSUER_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_MUNICIPAL_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_ISSUER_RATING));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_RATING));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_RATING_AUGMENT));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_FINANCIAL_BOND_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_CORPORATE_BOND_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_COUPON_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_OPTION_TYPE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_SECTOR));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_SECTOR_LEVEL1));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_PROVINCE));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_ISSUER_YEAR));
        indexedBonds.addIndex(HashIndex.onAttribute(Bond.BOND_SPECIAL_TYPES));
        for (Map.Entry<String, Bond> entry : bonds.entrySet()) {
            indexedBonds.add(entry.getValue());
        }



        try(TimeMeasure timeMeasure = new TimeMeasure("filer bond using CQEngine")) {
            Set<String> results = BondFilterTool.filterBonds(indexedBonds, filter);
            logger.info(Integer.toString(results.size()));
        }


        // qpid notify and update bond
        ExecutorService qpidBondNotifierExecutor = Executors.newSingleThreadExecutor();
        QpidReceiver qpidReceiver = new QpidReceiver(config,
                new BondQpidConsumer(config, indexedBonds, dictionaryNode));
        qpidBondNotifierExecutor.submit(qpidReceiver);

        // to do an integration test, set a bigger-sleep and change mysql BOND table and see the output.
        Thread.sleep(300*1000);
    }

    @Test
    public void testIndexCollection(){
        IndexedCollection indexedCollection = new ConcurrentIndexedCollection();
        Bond bond1 = new Bond();
        bond1.bond_key = "bond_key1";
        bond1.listed_market = "listed_market1";
        Bond bond2 = new Bond();
        bond2.bond_key = "bond_key1";
        bond2.listed_market = "listed_market1";
        Bond bond3 = new Bond();
        bond3.bond_key = "bond_key3";
        bond3.listed_market = "listed_market3";
        Bond bond4 = new Bond();
        bond4.bond_key = "bond_key1";
        bond4.listed_market = "listed_market4";
        Bond bond5 = new Bond();
        bond5.bond_key = "bond_key5";
        bond5.listed_market = "listed_market1";
        assert indexedCollection.add(bond1);
        assert !indexedCollection.add(bond2);
        assert indexedCollection.add(bond3);
        assert indexedCollection.add(bond4);
        assert indexedCollection.add(bond5);
        assert indexedCollection.size() == 4;
    }

}
