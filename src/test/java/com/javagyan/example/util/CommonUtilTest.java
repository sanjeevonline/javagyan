package com.javagyan.example.util;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommonUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testIsEmptyCollectionOfQ() {
        Assert.assertTrue(CommonUtil.isEmptyCollection(null));
        Assert.assertTrue(CommonUtil.isEmptyCollection(new ArrayList<Integer>()));

        final Collection<Integer> coll = new ArrayList<Integer>();
        coll.add(1);
        Assert.assertFalse(CommonUtil.isEmptyCollection(coll));
    }

    @Test
    public final void testIsEmptyString() {
        Assert.assertTrue(CommonUtil.isEmpty(null));
        Assert.assertTrue(CommonUtil.isEmpty(""));
        Assert.assertFalse(CommonUtil.isEmpty(" Not Empty"));
    }

    @Test
    public final void testUnion() {
        final Collection<Integer> coll1 = new ArrayList<Integer>();
        coll1.add(1);
        coll1.add(2);
        coll1.add(3);
        coll1.add(4);
        coll1.add(5);
        coll1.add(6);

        final Collection<Integer> coll2 = new ArrayList<Integer>();
        coll2.add(1);
        coll2.add(2);
        coll2.add(7);
        coll2.add(8);
        coll2.add(9);
        coll2.add(10);

        final Collection<Integer> result = CommonUtil.union(coll1, coll2);
        Assert.assertEquals(result.size(), 10);
    }

    @Test
    public final void testIntersection() {
        final Collection<Integer> coll1 = new ArrayList<Integer>();
        coll1.add(1);
        coll1.add(2);
        coll1.add(3);
        coll1.add(4);
        coll1.add(5);
        coll1.add(6);

        final Collection<Integer> coll2 = new ArrayList<Integer>();
        coll2.add(1);
        coll2.add(2);
        coll2.add(7);
        coll2.add(8);
        coll2.add(9);
        coll2.add(10);

        final Collection<Integer> result = CommonUtil.intersection(coll1, coll2);
        Assert.assertEquals(result.size(), 2);
    }

    @Test
    public final void testGetCardinalityMap() {
        // fail("Not yet implemented"); // TODO
    }

}
