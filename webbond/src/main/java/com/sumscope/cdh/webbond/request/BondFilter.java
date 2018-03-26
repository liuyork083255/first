package com.sumscope.cdh.webbond.request;

import com.sumscope.cdh.webbond.WebBondException;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.List;

public class BondFilter extends PageableRequest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(BondFilter.class);

    private String ssSecCode;
    private List<String> qbBondTypes;
    private List<String> bondSubTypes;
    private List<String> bondResidualMaturities;
    private String bondResidualMaturityStart;
    private String bondResidualMaturityEnd;
    private String bondMaturityDateStart;
    private String bondMaturityDateEnd;
    private List<String> perpetualTypes;
    private List<String> issuers;
    private List<String> issuerTypes;
    private List<String> municipalTypes;
    private List<String> issuerRatings;
    private List<String> bondRatings;
    private List<String> ratingAugments;
    private List<String> financialBondTypes;
    private List<String> corporateBondTypes;
    private List<String> couponTypes;
    private List<String> optionTypes;
    private List<String> sectors;
    private List<String> provinces;
    private List<String> issueYears;
    private List<String> specialTypes;
    
    private List<String> frequentlyUsedSector;

    public List<String> getFrequentlyUsedSector() {
		return frequentlyUsedSector;
	}

	public void setFrequentlyUsedSector(List<String> frequentlyUsedSector) {
		this.frequentlyUsedSector = frequentlyUsedSector;
	}

	public void check() throws Exception
    {
        super.check();
        try
        {
            if (null != getBondMaturityDateStart())
                DateUtils.parseDate(getBondMaturityDateStart(), "yyyyMMdd");
            if (null != getBondMaturityDateEnd())
                DateUtils.parseDate(getBondMaturityDateEnd(), "yyyyMMdd");
        }
        catch (ParseException e)
        {
            LOGGER.error("parse date failed", e);
            throw WebBondException.PredefinedExceptions.InvalidDateFormat;
        }
    }

    @Override
    public void trimToEmpty()
    {

    }

    public String getSsSecCode()
    {
        return ssSecCode;
    }

    public void setSsSecCode(String ssSecCode)
    {
        this.ssSecCode = ssSecCode;
    }

    public List<String> getQbBondTypes()
    {
        return qbBondTypes;
    }

    public void setQbBondTypes(List<String> qbBondTypes)
    {
        this.qbBondTypes = qbBondTypes;
    }

    public List<String> getBondSubTypes()
    {
        return bondSubTypes;
    }

    public void setBondSubTypes(List<String> bondSubTypes)
    {
        this.bondSubTypes = bondSubTypes;
    }

    public List<String> getBondResidualMaturities()
    {
        return bondResidualMaturities;
    }

    public void setBondResidualMaturities(List<String> bondResidualMaturities)
    {
        this.bondResidualMaturities = bondResidualMaturities;
    }

    public String getBondResidualMaturityStart()
    {
        return bondResidualMaturityStart;
    }

    public void setBondResidualMaturityStart(String bondResidualMaturityStart)
    {
        this.bondResidualMaturityStart = bondResidualMaturityStart;
    }

    public String getBondResidualMaturityEnd()
    {
        return bondResidualMaturityEnd;
    }

    public void setBondResidualMaturityEnd(String bondResidualMaturityEnd)
    {
        this.bondResidualMaturityEnd = bondResidualMaturityEnd;
    }

    public String getBondMaturityDateStart()
    {
        return bondMaturityDateStart;
    }

    public void setBondMaturityDateStart(String bondMaturityDateStart)
    {
        this.bondMaturityDateStart = bondMaturityDateStart;
    }

    public String getBondMaturityDateEnd()
    {
        return bondMaturityDateEnd;
    }

    public void setBondMaturityDateEnd(String bondMaturityDateEnd)
    {
        this.bondMaturityDateEnd = bondMaturityDateEnd;
    }

    public List<String> getPerpetualTypes()
    {
        return perpetualTypes;
    }

    public void setPerpetualTypes(List<String> perpetualTypes)
    {
        this.perpetualTypes = perpetualTypes;
    }

    public List<String> getIssuers()
    {
        return issuers;
    }

    public void setIssuers(List<String> issuers)
    {
        this.issuers = issuers;
    }

    public List<String> getIssuerTypes()
    {
        return issuerTypes;
    }

    public void setIssuerTypes(List<String> issuerTypes)
    {
        this.issuerTypes = issuerTypes;
    }

    public List<String> getMunicipalTypes()
    {
        return municipalTypes;
    }

    public void setMunicipalTypes(List<String> municipalTypes)
    {
        this.municipalTypes = municipalTypes;
    }

    public List<String> getIssuerRatings()
    {
        return issuerRatings;
    }

    public void setIssuerRatings(List<String> issuerRatings)
    {
        this.issuerRatings = issuerRatings;
    }

    public List<String> getBondRatings()
    {
        return bondRatings;
    }

    public void setBondRatings(List<String> bondRatings)
    {
        this.bondRatings = bondRatings;
    }

    public List<String> getRatingAugments()
    {
        return ratingAugments;
    }

    public void setRatingAugments(List<String> ratingAugments)
    {
        this.ratingAugments = ratingAugments;
    }

    public List<String> getFinancialBondTypes()
    {
        return financialBondTypes;
    }

    public void setFinancialBondTypes(List<String> financialBondTypes)
    {
        this.financialBondTypes = financialBondTypes;
    }

    public List<String> getCorporateBondTypes()
    {
        return corporateBondTypes;
    }

    public void setCorporateBondTypes(List<String> corporateBondTypes)
    {
        this.corporateBondTypes = corporateBondTypes;
    }

    public List<String> getCouponTypes()
    {
        return couponTypes;
    }

    public void setCouponTypes(List<String> couponTypes)
    {
        this.couponTypes = couponTypes;
    }

    public List<String> getOptionTypes()
    {
        return optionTypes;
    }

    public void setOptionTypes(List<String> optionTypes)
    {
        this.optionTypes = optionTypes;
    }

    public List<String> getSectors()
    {
        return sectors;
    }

    public void setSectors(List<String> sectors)
    {
        this.sectors = sectors;
    }

    public List<String> getProvinces()
    {
        return provinces;
    }

    public void setProvinces(List<String> provinces)
    {
        this.provinces = provinces;
    }

    public List<String> getIssueYears()
    {
        return issueYears;
    }

    public void setIssueYears(List<String> issueYears)
    {
        this.issueYears = issueYears;
    }

    public List<String> getSpecialTypes()
    {
        return specialTypes;
    }

    public void setSpecialTypes(List<String> specialTypes)
    {
        this.specialTypes = specialTypes;
    }
}
