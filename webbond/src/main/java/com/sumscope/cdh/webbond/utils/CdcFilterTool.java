package com.sumscope.cdh.webbond.utils;

import com.sumscope.cdh.webbond.generated.WebbondBbo;
import com.sumscope.cdh.webbond.generated.WebbondTrade;
import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by chengzhang.wang on 2017/3/31.
 */
public class CdcFilterTool
{
    public Collection<WebbondBbo> updateWebBbo(boolean isAuthed, Collection<WebbondBbo> webbondBbos)
    {
        if (!isAuthed)
        {
            List<WebbondBbo> list = new ArrayList<>();
            webbondBbos.forEach((bbo) ->
            {
                WebbondBbo clonedBbo = SerializationUtils.clone(bbo);
                clonedBbo.setCdcValuation("--");
                clonedBbo.setCdcValuationValue(-999D);
                clonedBbo.setCdcSubOfr("--");
                clonedBbo.setCdcSubOfrValue(-999D);
                clonedBbo.setBidSubCdc("--");
                clonedBbo.setBidSubCdcValue(-999D);
                list.add(clonedBbo);
            });
            return list;
        }
        return webbondBbos;
    }

    public Collection<WebbondTrade> updateWebTrade(boolean isAuthed, Collection<WebbondTrade> webbondTrades)
    {
        if (!isAuthed)
        {
            List<WebbondTrade> list = new ArrayList<>();
            webbondTrades.forEach((trade) ->
            {
                WebbondTrade clonedTrade = SerializationUtils.clone(trade);
                clonedTrade.setCdcValuation("--");
                clonedTrade.setCdcValuationValue(-999D);
                list.add(clonedTrade);
            });
            return list;
        }
        return webbondTrades;
    }
}
