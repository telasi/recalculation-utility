/*
 *   Copyright (C) 2007 by JSC Telasi
 *   http://www.telasi.ge
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the
 *   Free Software Foundation, Inc.,
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package telasi.recutil.tests;

import java.util.ArrayList;
import java.util.List;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.FlatTariff;
import telasi.recutil.beans.RecalcCutItem;
import telasi.recutil.beans.RecalcTariffItem;
import telasi.recutil.beans.StepTariff2006;
import telasi.recutil.beans.StepTariff2006.TariffStep;

/**
 * Test utilities. 
 * 
 * @author dimakura
 */
public class TestUtils {

    /**
     * Generates step tariff as it was introduced from 01/06/2007.
     * @return step tariff
     */
    public static StepTariff2006 generateStepTariff() {
    /*create steps*/
    TariffStep st1 = new TariffStep();
    st1.setInterval(100);
    st1.setTariff(0.1348f);
    TariffStep st2 = new TariffStep();
    st2.setInterval(200);
    st2.setTariff(0.16f);
    TariffStep st3 = new TariffStep();
    st3.setInterval(-1);
    st3.setTariff(0.17698f);
    /*create steps list*/
    List steps = new ArrayList();
    steps.add(st1); steps.add(st2); steps.add(st3);
    /*create step tariff*/
    StepTariff2006 t = new StepTariff2006(steps);
    t.setName("Step Tariff");
    t.setId(100);
    return t;
    }

    /**
     * Generates default tariff history.
     * @return tariff history list
     */
    public static List generateDefaultTariffHistory() {
    /*12/06/2000 - 03/09/2000: 0.09*/
    RecalcTariffItem t1 = new RecalcTariffItem();
    t1.setStartDate(new Date(2000, 6, 12));
    t1.setEndDate(new Date(2000, 9, 3));
    t1.setTariff(new FlatTariff(0.09f));
    /*04/09/2000 - 14/11/2001: 0.098*/
    RecalcTariffItem t2 = new RecalcTariffItem();
    t2.setStartDate(new Date(2000, 9, 4));
    t2.setEndDate(new Date(2001, 11, 14));
    t2.setTariff(new FlatTariff(0.098f));
    /*15/11/2001 - 31/10/2002: 0.124*/
    RecalcTariffItem t3 = new RecalcTariffItem();
    t3.setStartDate(new Date(2001, 11, 15));
    t3.setEndDate(new Date(2002, 10, 31));
    t3.setTariff(new FlatTariff(0.124f));
    /*01/11/2002 - 31/12/2002: 0.137*/
    RecalcTariffItem t4 = new RecalcTariffItem();
    t4.setStartDate(new Date(2002, 11, 1));
    t4.setEndDate(new Date(2002, 12, 31));
    t4.setTariff(new FlatTariff(0.137f));
    /*01/01/2003 - 30/06/2005: 0.1218*/
    RecalcTariffItem t5 = new RecalcTariffItem();
    t5.setStartDate(new Date(2003, 1, 1));
    t5.setEndDate(new Date(2005, 6, 30));
    t5.setTariff(new FlatTariff(0.1218f));
    /*01/07/2005 - 31/05/2006: 0.11977*/
    RecalcTariffItem t6 = new RecalcTariffItem();
    t6.setStartDate(new Date(2005, 7, 1));
    t6.setEndDate(new Date(2006, 5, 31));
    t6.setTariff(new FlatTariff(0.11977f));
    /*01/06/2006 - +int: step2006*/
    RecalcTariffItem t7 = new RecalcTariffItem();
    t7.setStartDate(new Date(2006, 6, 1));
    t7.setEndDate(null);
    t7.setTariff(generateStepTariff());
    /*create list*/
    List list = new ArrayList();
    list.add(t1); list.add(t2); list.add(t3);
    list.add(t4); list.add(t5); list.add(t6);
    list.add(t7);
    return list;
    }
    
    /**
     * Creates cut intervals from given dates array.
     * @param dates cut intervals array
     * @return list of cut intervals
     */
    public static List createIntervals(Date[] dates) {
    List cuts = new ArrayList();
    for (int i = 0; i < dates.length/2; i++) {
        RecalcCutItem item = new RecalcCutItem();
        item.setStartDate(dates[2*i]);
        item.setEndDate(dates[2*i + 1]);
        cuts.add(item);
    }
    return cuts;
    }
    
}
