package telasi.recutil.tests;
///*
// *   Copyright (C) 2007 by JSC Telasi
// *   http://www.telasi.ge
// *
// *   This program is free software; you can redistribute it and/or modify
// *   it under the terms of the GNU General Public License as published by
// *   the Free Software Foundation; either version 2 of the License, or
// *   (at your option) any later version.
// *
// *   This program is distributed in the hope that it will be useful,
// *   but WITHOUT ANY WARRANTY; without even the implied warranty of
// *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *   GNU General Public License for more details.
// *
// *   You should have received a copy of the GNU General Public License
// *   along with this program; if not, write to the
// *   Free Software Foundation, Inc.,
// *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
// */
//package ge.telasi.recut.calc.tests;
//
//import ge.telasi.recut.calc07.Utilities;
//import ge.telasi.recut.core.stimpl.Charge;
//import ge.telasi.recut.core.stimpl.ChargeElement;
//import ge.telasi.recut.core.stimpl.RecalcCutItem;
//import ge.telasi.recut.core.stimpl.RecalcInstCp;
//import ge.telasi.recut.core.stimpl.RecalcTariffItem;
//import ge.telasi.recut.core.types.Date;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import junit.framework.Assert;
//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestResult;
//import junit.framework.TestSuite;
//
///**
// * Tests for Utilities class.
// * 
// * @author dimakura
// */
//public class UtilityTesting extends TestCase {
//
//    /**
//     * Precision with which calculations are tested.
//     */
//    public static final float PRECISION = 0.00001f;
//
//    // test result, for use when handling exceptions
//    private TestResult result;
//
//    /**
//     * Create test suite with methods of this class.
//     * 
//     * @return test suite
//     */
//    public static Test suite() {
//    TestSuite suite = new TestSuite();
//    suite.addTest(new UtilityTesting("testRoundNumbers"));
//    suite.addTest(new UtilityTesting("testPrevMonthDaysCount"));
//    suite.addTest(new UtilityTesting("testExpansionWithDefaultTariffHistory"));
//    suite.addTest(new UtilityTesting("testActualChargeIntervalsExtraction"));
//    //suite.addTest(new UtilityTesting("testExpansionWithCuts"));
//    suite.addTest(new UtilityTesting("testInstCpKwhCalculation"));
//    suite.addTest(new UtilityTesting("testStatndardInstCpCalculationProcedure"));
//    suite.addTest(new UtilityTesting("testInstCpContinuousProcedure"));
//    suite.addTest(new UtilityTesting("testReadingDerivation"));
//    suite.addTest(new UtilityTesting("testKwhDerivation"));
//    suite.addTest(new UtilityTesting("testStepLookupInChargeInterval"));
//    suite.addTest(new UtilityTesting("testSplittingOnVATChange"));
//    return suite;
//    }
//
//    /**
//     * Create instance of this class.
//     * 
//     * @param name
//     *            name of the method to be tested
//     */
//    public UtilityTesting(String name) {
//    super(name);
//    }
//
//    /**
//     * Setting up the environment.
//     */
//    public void setUp() {
//    // nothing to do?
//    }
//
//    /**
//     * Run test as in TestCase, simply save result variable.
//     */
//    public void run(TestResult result) {
//    this.result = result;
//    super.run(result);
//    }
//
//    /**
//     * Test the number round facility.
//     */
//    public void testRoundNumbers() {
//    float f1 = 5.66666f;
//    float f2 = 5.66333f;
//    Assert.assertEquals(5.67f, Utilities.round(f1), PRECISION);
//    Assert.assertEquals(5.66f, Utilities.round(f2), PRECISION);
//    Assert.assertEquals(5.667f, Utilities.round(f1, 3), PRECISION);
//    Assert.assertEquals(5.663f, Utilities.round(f2, 3), PRECISION);
//    Assert.assertEquals(6.0f, Utilities.round(f1, 0), PRECISION);
//    Assert.assertEquals(6.0f, Utilities.round(f2, 0), PRECISION);
//    }
//
//    /**
//     * PrevMonthDaysCount testing.
//     */
//    public void testPrevMonthDaysCount() {
//    Date d1  = new Date(2007, 1,  15);
//    Date d2  = new Date(2007, 2,  3);
//    Date d3  = new Date(2007, 3,  15);
//    Date d4  = new Date(2007, 4,  6);
//    Date d5  = new Date(2007, 5,  15);
//    Date d6  = new Date(2007, 6,  22);
//    Date d7  = new Date(2007, 7,  2);
//    Date d8  = new Date(2007, 8,  1);
//    Date d9  = new Date(2007, 9,  10);
//    Date d10 = new Date(2007, 10, 30);
//    Date d11 = new Date(2007, 11, 5);
//    Date d12 = new Date(2007, 12, 1);
//    Assert.assertEquals(31, Utilities.getPrevMonthDaysCount(d1));
//    Assert.assertEquals(31, Utilities.getPrevMonthDaysCount(d2));
//    Assert.assertEquals(28, Utilities.getPrevMonthDaysCount(d3));
//    Assert.assertEquals(31, Utilities.getPrevMonthDaysCount(d4));
//    Assert.assertEquals(30, Utilities.getPrevMonthDaysCount(d5));
//    Assert.assertEquals(31, Utilities.getPrevMonthDaysCount(d6));
//    Assert.assertEquals(30, Utilities.getPrevMonthDaysCount(d7));
//    Assert.assertEquals(31, Utilities.getPrevMonthDaysCount(d8));
//    Assert.assertEquals(31, Utilities.getPrevMonthDaysCount(d9));
//    Assert.assertEquals(30, Utilities.getPrevMonthDaysCount(d10));
//    Assert.assertEquals(31, Utilities.getPrevMonthDaysCount(d11));
//    Assert.assertEquals(30, Utilities.getPrevMonthDaysCount(d12));
//    }
//    
//    /**
//     * Test expansion using Telasi default tariff history.
//     */
//    public void testExpansionWithDefaultTariffHistory() {
//    /*default tariff history*/
//    List<RecalcTariffItem> tariffHistory = TestUtils.generateDefaultTariffHistory();
//
//    List<ChargeElement> charges=null;
//    float kwh, gel;
//    /*
//     * Case #1. 100Kwh (01/06/2006-01/07/2006)
//     */
//    charges = Utilities.expand(100, new Date(2006,6,1), new Date(2006,7,1), tariffHistory);
//    kwh = gel = 0f;
//    for (ChargeElement e : charges) {
//        kwh += e.getKwh();
//        gel += e.getGel();
//    }
//    Assert.assertEquals(100f, kwh, PRECISION);
//    Assert.assertEquals(13.48f, gel, PRECISION);
//    /*
//     * Case #2. 100Kwh (01/05/2006-01/07/2006)
//     */
//    charges = Utilities.expand(100, new Date(2006,5,1), new Date(2006,7,1), tariffHistory);
//    kwh = gel = 0f;
//    int index = 0;
//    for (ChargeElement e : charges) {
//        kwh += e.getKwh();
//        gel += e.getGel();
//        switch (index) {
//        case 0:
//            Assert.assertEquals(new Date(2006, 5, 1), e.getStartDate());
//            Assert.assertEquals(new Date(2006, 5, 31), e.getEndDate());
//            break;
//        case 1:
//            Assert.assertEquals(new Date(2006, 5, 31), e.getStartDate());
//            Assert.assertEquals(new Date(2006, 7, 1), e.getEndDate());
//            break;
//        }
//        index++;
//    }
//    Assert.assertEquals(100f, kwh, PRECISION);
//    Assert.assertEquals(12.74082f, gel, PRECISION);
//    /*
//     * Case #3. 100Kwh (01/01/2000-01/09/2000)
//     */
//    charges = Utilities.expand(100, new Date(2000,1,1), new Date(2000,9,1), tariffHistory);
//    kwh = gel = 0f;
//    index = 0;
//    for (ChargeElement e : charges) {
//        kwh += e.getKwh();
//        gel += e.getGel();
//        switch (index) {
//        case 0:
//            Assert.assertEquals(new Date(2000, 1, 1), e.getStartDate());
//            Assert.assertEquals(new Date(2000, 6, 11), e.getEndDate());
//            break;
//        case 1:
//            Assert.assertEquals(new Date(2000, 6, 11), e.getStartDate());
//            Assert.assertEquals(new Date(2000, 9, 1), e.getEndDate());
//            break;
//        }
//        index++;
//    }
//    Assert.assertEquals(100.0f, kwh, PRECISION);
//    Assert.assertEquals(3.02459f, gel, PRECISION);
//    }
//    
//    /**
//     * Actual charge interval extraction.
//     */
//    public void testActualChargeIntervalsExtraction() {
//    /*create cut intervals*/
//    List<RecalcCutItem> cuts = TestUtils.createIntervals(
//            new Date(2000, 1, 1), new Date(2000, 2, 1),
//            new Date(2000, 9, 1), new Date(2000, 9, 15));
//    Assert.assertEquals(2, cuts.size());
//    Assert.assertEquals(new Date(2000, 1, 1),  cuts.get(0).getStartDate());
//    Assert.assertEquals(new Date(2000, 2, 1),  cuts.get(0).getEndDate());
//    Assert.assertEquals(new Date(2000, 9, 1),  cuts.get(1).getStartDate());
//    Assert.assertEquals(new Date(2000, 9, 15), cuts.get(1).getEndDate());
//    /*get charge intervals and test them*/
//    Date[] charges = Utilities.extractActualChargeIntervals(
//            new Date(2000, 1, 1), new Date(2000, 12, 31), cuts, null);
//    Assert.assertEquals(4, charges.length);
//    Assert.assertEquals(new Date(2000, 2, 1), charges[0]);
//    Assert.assertEquals(new Date(2000, 9, 1), charges[1]);
//    Assert.assertEquals(new Date(2000, 9, 15), charges[2]);
//    Assert.assertEquals(new Date(2000, 12, 31), charges[3]);
//    }
//    
////    /**
////     * Test expansion with cuts.
////     */
////    public void testExpansionWithCuts() {
////    List<ChargeElement> charges=null;
////    /* Default tariff history */
////    List<RecalcTariffItem> tariffHistory = TestUtils.generateDefaultTariffHistory();
////    /* Create cut intervals */
////    List<RecalcCutItem> cuts = TestUtils.createIntervals(new Date(2000, 9, 1), null);
////    /* Make expansion */
////    charges = Utilities.expand(100f, new Date(2000, 8, 1),  new Date(2000, 9, 10),
////            tariffHistory, cuts);
////    Assert.assertEquals(1, charges.size());
////    Assert.assertEquals(9f, charges.get(0).getGel());
////    Assert.assertEquals(100f, charges.get(0).getKwh());
////    Assert.assertEquals(new Date(2000, 8, 1), charges.get(0).getStartDate());
////    Assert.assertEquals(new Date(2000, 9, 1), charges.get(0).getEndDate());
////    }
// 
//    /**
//     * Tests kWh calculation procedure for installed capacity
//     * history record.
//     */
//    public void testInstCpKwhCalculation() {
//    /*interval dates*/
//    Date d1 = new Date(2007, 7, 1);
//    Date d2 = new Date(2007, 7, 31);
//    Date d3 = new Date(2007, 7, 30);
//    Date d4 = new Date(2007, 8, 15);
//    Date d5 = new Date(2007, 7, 29);
//    Date d6 = new Date(2007, 7, 28);
//    Date d7 = new Date(2007, 7, 27);
//    Date d8 = new Date(2007, 8, 1);
//    Date d9 = new Date(2007, 8, 2);
//    /*normalize on 30 days*/
//    RecalcInstCp inst1 = new RecalcInstCp();
//    inst1.setAmount(100.f);
//    inst1.setOption(RecalcInstCp.NORMALIZE_ON_30_DAYS);
//    Assert.assertEquals(100.f, Utilities.instcpKwhCharge(d1, d2, inst1, null), PRECISION);
//    Assert.assertEquals(97f, Utilities.instcpKwhCharge(d1, d3, inst1, null), PRECISION);
//    Assert.assertEquals(0.f, Utilities.instcpKwhCharge(d1, d1, inst1, null), PRECISION);
//    Assert.assertEquals(150.f, Utilities.instcpKwhCharge(d1, d4, inst1, null), PRECISION);
//    /*normalize on prev. month days*/
//    RecalcInstCp inst2 = new RecalcInstCp();
//    inst2.setAmount(100.f);
//    inst2.setOption(RecalcInstCp.NORMALIZE_ON_PREV_MONTH);
//    Assert.assertEquals(100.f, Utilities.instcpKwhCharge(d1, d2, inst2, null), PRECISION);
//    Assert.assertEquals(97f, Utilities.instcpKwhCharge(d1, d3, inst2, null), PRECISION);
//    Assert.assertEquals(0.f, Utilities.instcpKwhCharge(d1, d1, inst2, null), PRECISION);
//    Assert.assertEquals(145f, Utilities.instcpKwhCharge(d1, d4, inst2, null), PRECISION);
//    /*normalize on prev. month days with rounding on 3 days*/
//    RecalcInstCp inst3 = new RecalcInstCp();
//    inst3.setAmount(100.f);
//    inst3.setOption(RecalcInstCp.NORM_ON_PRV_MONTH_RND_ON_3_DAYS);
//    Assert.assertEquals(100.f, Utilities.instcpKwhCharge(d1, d2, inst3, null), PRECISION);
//    Assert.assertEquals(100.f, Utilities.instcpKwhCharge(d1, d3, inst3, null), PRECISION);
//    Assert.assertEquals(100.f, Utilities.instcpKwhCharge(d1, d5, inst3, null), PRECISION);
//    Assert.assertEquals(100.f, Utilities.instcpKwhCharge(d1, d6, inst3, null), PRECISION);
//    Assert.assertEquals(87f, Utilities.instcpKwhCharge(d1, d7, inst3, null), PRECISION);
//    Assert.assertEquals(100.0f, Utilities.instcpKwhCharge(d1, d8, inst3, null), PRECISION);
//    Assert.assertEquals(103f, Utilities.instcpKwhCharge(d1, d9, inst3, null), PRECISION);
//    /*day charge*/
//    RecalcInstCp inst4 = new RecalcInstCp();
//    inst4.setAmount(10.f);
//    inst4.setOption(RecalcInstCp.AVERAGE_DAY_CHARGE);
//    Assert.assertEquals(300.f, Utilities.instcpKwhCharge(d1, d2, inst4, null), PRECISION);
//    Assert.assertEquals(290.f, Utilities.instcpKwhCharge(d1, d3, inst4, null), PRECISION);
//    }
//
//    /**
//     * Test standard inst. capacity calculation procedure.
//     */
//    public void testStatndardInstCpCalculationProcedure() {
//    /* Create List */
//    RecalcInstCp inst1 = new RecalcInstCp();
//    inst1.setAmount(120);
//    inst1.setOption(RecalcInstCp.NORMALIZE_ON_PREV_MONTH);
//    inst1.setStartDate(new Date(2006, 1, 1));
//    inst1.setEndDate(new Date(2006, 1, 31));
//    RecalcInstCp inst2 = new RecalcInstCp();
//    inst2.setAmount(150);
//    inst2.setOption(RecalcInstCp.NORMALIZE_ON_PREV_MONTH);
//    inst2.setStartDate(new Date(2006, 2, 1));
//    inst2.setEndDate(new Date(2006, 2, 28));
//    RecalcInstCp inst3 = new RecalcInstCp();
//    inst3.setAmount(140);
//    inst3.setOption(RecalcInstCp.NORMALIZE_ON_PREV_MONTH);
//    inst3.setStartDate(new Date(2006, 3, 1));
//    inst3.setEndDate(new Date(2006, 3, 31));
//    RecalcInstCp inst4 = new RecalcInstCp();
//    inst4.setAmount(145);
//    inst4.setOption(RecalcInstCp.NORMALIZE_ON_PREV_MONTH);
//    inst4.setStartDate(new Date(2006, 4, 1));
//    inst4.setEndDate(new Date(2006, 4, 30));
//    RecalcInstCp inst5 = new RecalcInstCp();
//    inst5.setAmount(130);
//    inst5.setOption(RecalcInstCp.NORMALIZE_ON_PREV_MONTH);
//    inst5.setStartDate(new Date(2006, 5, 1));
//    inst5.setEndDate(new Date(2006, 5, 31));
//    List<RecalcInstCp> instcps = new ArrayList<RecalcInstCp>();
//    instcps.add(inst1);
//    instcps.add(inst2);
//    instcps.add(inst3);
//    instcps.add(inst4);
//    instcps.add(inst5);
//    /* Create charges */
//    List<RecalcCutItem> cuts = new ArrayList<RecalcCutItem>();
//    RecalcCutItem cut1 = new RecalcCutItem();
//    cut1.setStartDate(new Date(2006, 2, 15));
//    cut1.setEndDate(new Date(2006, 3, 15));
//    cuts.add(cut1);
//    /* Calculate charge -- WITHOUT cut, on 60 days */
//    Date d1 = new Date(2006, 1, 1);
//    Date d2 = new Date(2006, 5, 1);
//    Charge c = new Charge();
//    Utilities.calculateInstCapacityUsingStandardProcedure(c, d1, d2, instcps, null, false);
//    Assert.assertEquals(1, c.expansion().size());
//    Assert.assertEquals(new Date(2006, 3, 2), c.expansion().get(0).getStartDate());// 60 days
//    Assert.assertEquals(d2, c.expansion().get(0).getEndDate());
//    Assert.assertEquals(260.0f, c.expansion().get(0).getKwh());
//    Assert.assertEquals(0.0f, c.expansion().get(0).getGel());
//    /* Calculate charge -- WITHOUT cut, on exactly given period days */
//    Utilities.calculateInstCapacityUsingStandardProcedure(c, d1, d2, instcps, null, true);
//    Assert.assertEquals(1, c.expansion().size());
//    Assert.assertEquals(d1, c.expansion().get(0).getStartDate());
//    Assert.assertEquals(d2, c.expansion().get(0).getEndDate());
//    Assert.assertEquals(520.0f, c.expansion().get(0).getKwh());
//    Assert.assertEquals(0.0f, c.expansion().get(0).getGel());
//    /* Calculate charge -- WITH cut, on 60 days */
//    Utilities.calculateInstCapacityUsingStandardProcedure(c, d1, d2, instcps, cuts, false);
//    Assert.assertEquals(1, c.expansion().size());
//    Assert.assertEquals(new Date(2006, 3, 15), c.expansion().get(0).getStartDate());
//    Assert.assertEquals(d2, c.expansion().get(0).getEndDate());
//    Assert.assertEquals(204f, c.expansion().get(0).getKwh());
//    Assert.assertEquals(0.0f, c.expansion().get(0).getGel());
//    /* Calculate charge -- WITHOUT cut, on exactly given period days */
//    Utilities.calculateInstCapacityUsingStandardProcedure(c, d1, d2, instcps, cuts, true);
//    Assert.assertEquals(2, c.expansion().size());
//    Assert.assertEquals(d1, c.expansion().get(0).getStartDate());
//    Assert.assertEquals(new Date(2006, 2, 15), c.expansion().get(0).getEndDate());
//    Assert.assertEquals(new Date(2006, 3, 15), c.expansion().get(1).getStartDate());
//    Assert.assertEquals(d2, c.expansion().get(1).getEndDate());
//    Assert.assertEquals(195.0f, c.expansion().get(0).getKwh());
//    Assert.assertEquals(0.0f, c.expansion().get(0).getGel());
//    Assert.assertEquals(204f, c.expansion().get(1).getKwh());
//    Assert.assertEquals(0.0f, c.expansion().get(1).getGel());
//    }
//    
//    /**
//     * Test continuous inst. capacity calculation proceudre.
//     */
//    public void testInstCpContinuousProcedure() {
//    /* Create List */
//    RecalcInstCp inst1 = new RecalcInstCp();
//    inst1.setAmount(120);
//    inst1.setOption(RecalcInstCp.NORMALIZE_ON_PREV_MONTH);
//    inst1.setStartDate(new Date(2006, 1, 1));
//    inst1.setEndDate(new Date(2006, 1, 31));
//    RecalcInstCp inst2 = new RecalcInstCp();
//    inst2.setAmount(150);
//    inst2.setOption(RecalcInstCp.NORMALIZE_ON_PREV_MONTH);
//    inst2.setStartDate(new Date(2006, 2, 1));
//    inst2.setEndDate(new Date(2006, 2, 28));
//    RecalcInstCp inst3 = new RecalcInstCp();
//    inst3.setAmount(140);
//    inst3.setOption(RecalcInstCp.NORMALIZE_ON_PREV_MONTH);
//    inst3.setStartDate(new Date(2006, 3, 1));
//    inst3.setEndDate(new Date(2006, 3, 31));
//    RecalcInstCp inst4 = new RecalcInstCp();
//    inst4.setAmount(145);
//    inst4.setOption(RecalcInstCp.NORMALIZE_ON_PREV_MONTH);
//    inst4.setStartDate(new Date(2006, 4, 1));
//    inst4.setEndDate(new Date(2006, 4, 30));
//    RecalcInstCp inst5 = new RecalcInstCp();
//    inst5.setAmount(130);
//    inst5.setOption(RecalcInstCp.NORMALIZE_ON_PREV_MONTH);
//    inst5.setStartDate(new Date(2006, 5, 1));
//    inst5.setEndDate(new Date(2006, 5, 31));
//    List<RecalcInstCp> instcps = new ArrayList<RecalcInstCp>();
//    instcps.add(inst1);
//    instcps.add(inst2);
//    instcps.add(inst3);
//    instcps.add(inst4);
//    instcps.add(inst5);
//    /* Create charges */
//    List<RecalcCutItem> cuts = new ArrayList<RecalcCutItem>();
//    RecalcCutItem cut1 = new RecalcCutItem();
//    cut1.setStartDate(new Date(2006, 2, 15));
//    cut1.setEndDate(new Date(2006, 3, 15));
//    cuts.add(cut1);
//    /* Calculate charge -- WITHOUT cut, on 60 days */
//    Date d1 = new Date(2006, 1, 1);
//    Date d2 = new Date(2006, 3, 10);
//    Charge c = new Charge();
//    Utilities.calculateInstCapacityUsingContinuousProcedure(c, d1, d2, instcps, null);
//    Assert.assertEquals(3, c.expansion().size());
//    Assert.assertEquals(120.0f, c.expansion().get(0).getKwh(), PRECISION);
//    Assert.assertEquals(150.0f, c.expansion().get(1).getKwh(), PRECISION);
//    Assert.assertEquals(45.0f, c.expansion().get(2).getKwh(), PRECISION);
//    Assert.assertEquals(new Date(2006, 1, 1), c.expansion().get(0).getStartDate());
//    Assert.assertEquals(new Date(2006, 2, 1), c.expansion().get(0).getEndDate());
//    Assert.assertEquals(new Date(2006, 2, 1), c.expansion().get(1).getStartDate());
//    Assert.assertEquals(new Date(2006, 3, 1), c.expansion().get(1).getEndDate());
//    Assert.assertEquals(new Date(2006, 3, 1), c.expansion().get(2).getStartDate());
//    Assert.assertEquals(new Date(2006, 3, 10), c.expansion().get(2).getEndDate());
//    Assert.assertEquals(0.f, c.expansion().get(0).getGel(), PRECISION);
//    }
//    
//    /**
//     * Testing reading derivation.
//     */
//    public void testReadingDerivation() {
//    Date d1 = new Date(2006, 1, 1);
//    Date d2 = new Date(2006, 1, 31);
//    float r;
//    r = Utilities.deriveReading(100f, 200f, d1, d2, new Date(2006, 1, 16), 5, true);
//    Assert.assertEquals(150f, r, PRECISION);
//    r = Utilities.deriveReading(9000f, 1002f, d1, d2, new Date(2006, 1, 16), 4, true);
//    Assert.assertEquals(1f, r, PRECISION);
//    r = Utilities.deriveReading(9000f, 1002f, d1, d2, new Date(2006, 1, 16), 4, false);
//    Assert.assertEquals(10001f, r, PRECISION);
//    }
//    
//    /**
//     * Testing kWh derivation.
//     */
//    public void testKwhDerivation() {
//    float kwh;
//    kwh = Utilities.deriveKwh(200, 100, 1, 4, 0);
//    Assert.assertEquals(100f, kwh, PRECISION);
//    kwh = Utilities.deriveKwh(100, 9900, 1, 4, 0);
//    Assert.assertEquals(200f, kwh, PRECISION);
//    }
//    
//    /**
//     * Tests step-tariff lookup in charge intervals.
//     */
//    public void testStepLookupInChargeInterval() {
//    /*default tariff history*/
//    List<RecalcTariffItem> tariffs = TestUtils.generateDefaultTariffHistory();
//    Date d1 = new Date(2005, 1, 1);
//    Date d2 = new Date(2006, 1, 1);
//    Date d3 = new Date(2007, 1, 1);
//
//    Charge c = new Charge();
//
//    ChargeElement el1 = new ChargeElement();
//    el1.setStartDate(d1);
//    el1.setEndDate(d2);
//    c.addElement(el1);
//    Assert.assertFalse(Utilities.isStepTariffInChargePeriod(c, tariffs));
//
//    ChargeElement el2 = new ChargeElement();
//    el2.setStartDate(d2);
//    el2.setEndDate(d3);
//    c.addElement(el2);
//    Assert.assertTrue(Utilities.isStepTariffInChargePeriod(c, tariffs));
//    c.clear();
//    c.addElement(el2);
//    Assert.assertTrue(Utilities.isStepTariffInChargePeriod(c, tariffs));
//    }
// 
//    /**
//     * Tests splitting on VAT change period.
//     */
//    public void testSplittingOnVATChange() {
//    Charge c = new Charge();
//    ChargeElement el1 = Utilities.createChargeElement(new Date(2005, 6, 15),
//            new Date(2005, 7, 17), 100, 50, -1, false, false);
//    c.addElement(el1);
//    Utilities.splitOnVATChange(c, TestUtils.generateDefaultTariffHistory());
//    Assert.assertEquals(2, c.expansion().size());
//    Assert.assertEquals(50f, c.expansion().get(0).getKwh(), PRECISION);
//    Assert.assertEquals(50f, c.expansion().get(1).getKwh(), PRECISION);
//    Assert.assertEquals(25.210085f, c.expansion().get(0).getGel(), PRECISION);
//    Assert.assertEquals(24.789915f, c.expansion().get(1).getGel(), PRECISION);
//    }
//    
//}
