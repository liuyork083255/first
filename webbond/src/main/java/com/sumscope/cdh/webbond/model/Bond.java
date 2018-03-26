package com.sumscope.cdh.webbond.model;

import com.googlecode.cqengine.attribute.MultiValueNullableAttribute;
import com.googlecode.cqengine.attribute.SimpleNullableAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Bond
{
    public static final String INVALID_VALUE = "-999";

    public String id;
    public String auction_date_start;
    public String bond_id;
    public String bond_key;
    public String bond_subtype;
    public String bond_type;
    public String compound_frequency;
    public String coupon_frequency;
    public String coupon_rate_current;
    public String coupon_rate_spread;
    public String coupon_type;
    public String delisted_date;
    public String ent_cor;
    public String fixing_ma_days;
    public String frn_index_id;
    public String full_name;
    public String interest_start_date;
    public String is_municipal;
    public String issue_amount;
    public String issue_price;
    public String issue_rate;
    public String issue_start_date;
    public String issue_year;
    public String issuer_code;
    public String issuer_outlook_current;
    public String issuer_rating_current;
    public String issuer_rating_current_npy;
    public String issuer_rating_date;
    public String issuer_rating_institution_code;
    public String listed_date;
    public Integer maturity_date;
    public String maturity_term;
    public String next_coupon_date;
    public String option_date;
    public String option_exercise_date;
    public String option_style;
    public String option_type;
    public String p_issuer_outlook_current;
    public String p_issuer_rating_current;
    public String p_rating_current;
    public String payment_date;
    public String planned_issue_amount;
    public String rating_augment;
    public String rating_current;
    public String rating_current_npy;
    public String rating_date;
    public String rating_institution_code;
    public String redemption_str;
    public String term_structure;
    public String term_unit;

    public String listed_market;
    public String pinyin;
    public String pinyin_full;
    public String short_name;
    public String cashflow_paymentdate;

    public String optionType;
    public String corporateBondType;
    public String qbBondType;
    public String corpBondRating; // corp and bond rating in format "--/--"
    public String couponType;
    public String newListed;
    public String perpetualType;
    public String bondRating;
    public String financialBondType;
    public String bondCode;
    public String interBank;
    public String exchange;
    public String pledge;

    // below items not loaded from reftful-json
    public String issuerType;
    public String issuerRating;
    public List<String> municipalType; // 城投(01),非城投(02),平台债(03)
    public String bondSubTypeLevel1;
    public String sectorLevel1;
    public String crossMarket;
    public String sector;
    public String province;
    public List<String> specialTypes; // newListed(01), crossMarket(02), pledge(03), interBank(04), exchange(05)
    public String bondResidualMaturityFormat;
    public Double bondResidualMaturity;
    public String bondResidualMaturityLabel;

    public String bondKeyListMarket;
    public List<String> frequentlyUsedSector;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bond that = (Bond) o;
        return StringUtils.equals(bond_key, that.bond_key) && StringUtils.equals(listed_market, that.listed_market);
    }

    @Override
    public int hashCode()
    {
        int result = bond_key != null ? bond_key.hashCode() : 0;
        return 31 * result + (listed_market != null ? listed_market.hashCode() : 0);
    }


    public static final SimpleNullableAttribute<Bond, String> BOND_SUB_TYPE = new SimpleNullableAttribute<Bond, String>("bond_subtype")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.bond_subtype;
        }
    };

    public static final SimpleNullableAttribute<Bond, String> BOND_ISSUER_YEAR = new SimpleNullableAttribute<Bond, String>("issue_year")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.issue_year;
        }
    };

    public static final SimpleNullableAttribute<Bond, String> BOND_ISSUER_CODE = new SimpleNullableAttribute<Bond, String>("issuer_code")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.issuer_code;
        }
    };

    public static final SimpleNullableAttribute<Bond, String> BOND_RATING_AUGMENT = new SimpleNullableAttribute<Bond, String>("rating_augment")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.rating_augment;
        }
    };

    public static final SimpleNullableAttribute<Bond, String> BOND_OPTION_TYPE = new SimpleNullableAttribute<Bond, String>("optionType")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.optionType;
        }
    };

    public static final SimpleNullableAttribute<Bond, String> BOND_CORPORATE_BOND_TYPE = new SimpleNullableAttribute<Bond, String>("corporateBondType")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.corporateBondType;
        }
    };

    public static final SimpleNullableAttribute<Bond, String> BOND_TYPE = new SimpleNullableAttribute<Bond, String>("qbBondType")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.qbBondType;
        }
    };

    public static final SimpleNullableAttribute<Bond, String> BOND_COUPON_TYPE = new SimpleNullableAttribute<Bond, String>("couponType")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.couponType;
        }
    };

    public static final SimpleNullableAttribute<Bond, String> BOND_PERPETUAL_TYPE = new SimpleNullableAttribute<Bond, String>("perpetualType")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.perpetualType;
        }
    };

    public static final SimpleNullableAttribute<Bond, String> BOND_RATING = new SimpleNullableAttribute<Bond, String>("bondRating")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.bondRating;
        }
    };

    public static final SimpleNullableAttribute<Bond, String> BOND_FINANCIAL_BOND_TYPE = new SimpleNullableAttribute<Bond, String>("financialBondType")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.financialBondType;
        }
    };

    public static final SimpleNullableAttribute<Bond, String> BOND_ISSUER_TYPE = new SimpleNullableAttribute<Bond, String>("issuerType")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.issuerType;
        }
    };

    public static final SimpleNullableAttribute<Bond, String> BOND_ISSUER_RATING = new SimpleNullableAttribute<Bond, String>("issuerRating")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.issuerRating;
        }
    };

    public static final MultiValueNullableAttribute<Bond, String> BOND_MUNICIPAL_TYPE = new MultiValueNullableAttribute<Bond, String>("municipalType", true) {
        @Override
        public Iterable<String> getNullableValues(Bond o, QueryOptions queryOptions) {
            return o.municipalType;
        }
    };

    public static final MultiValueNullableAttribute<Bond, String> BOND_FREQUENTLYUSEDSECTOR_TYPE = new MultiValueNullableAttribute<Bond, String>("frequentlyUsedSector", true) {
        @Override
        public Iterable<String> getNullableValues(Bond o, QueryOptions queryOptions) {
            return o.frequentlyUsedSector;
        }
    };

    public static final SimpleNullableAttribute<Bond, String> BOND_SUB_TYPE_LEVEL1 = new SimpleNullableAttribute<Bond, String>("bondSubTypeLevel1")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.bondSubTypeLevel1;
        }
    };

    public static final SimpleNullableAttribute<Bond, String> BOND_SECTOR_LEVEL1 = new SimpleNullableAttribute<Bond, String>("sectorLevel1")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.sectorLevel1;
        }
    };

    public static final SimpleNullableAttribute<Bond, String> BOND_SECTOR = new SimpleNullableAttribute<Bond, String>("sector")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.sector;
        }
    };

    public static final SimpleNullableAttribute<Bond, String> BOND_PROVINCE = new SimpleNullableAttribute<Bond, String>("province")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.province;
        }
    };

    public static final MultiValueNullableAttribute<Bond, String> BOND_SPECIAL_TYPES = new MultiValueNullableAttribute<Bond, String>("specialTypes", true)
    {
        @Override
        public Iterable<String> getNullableValues(Bond o, QueryOptions queryOptions)
        {
            return o.specialTypes;
        }
    };

    public static final SimpleNullableAttribute<Bond, Double> BOND_RESIDUAL_MATURITY = new SimpleNullableAttribute<Bond, Double>("bondResidualMaturity")
    {
        @Override
        public Double getValue(Bond o, QueryOptions queryOptions)
        {
            return o.bondResidualMaturity;
        }
    };

    public static final SimpleNullableAttribute<Bond, Integer> BOND_MATURITY_DATE = new SimpleNullableAttribute<Bond, Integer>("maturity_date")
    {
        @Override
        public Integer getValue(Bond o, QueryOptions queryOptions)
        {
            if (o.maturity_date == null)
            {
                return 0;
            }
            else
            {
                return o.maturity_date;
            }
        }
    };

    public static final SimpleNullableAttribute<Bond, String> BOND_RESIDUAL_MATURITY_LABEL = new SimpleNullableAttribute<Bond, String>("bondResidualMaturityLabel")
    {
        @Override
        public String getValue(Bond o, QueryOptions queryOptions)
        {
            return o.bondResidualMaturityLabel;
        }
    };
}