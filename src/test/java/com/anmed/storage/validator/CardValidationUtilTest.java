package com.anmed.storage.validator;

import org.junit.Assert;
import org.junit.Test;


public class CardValidationUtilTest {


    @Test
    public void validatedByLuhnAlgorithmPositive() throws Exception {
        Assert.assertTrue(CardValidationUtil.validatedByLuhnAlgorithm("4111111111111111".toCharArray()));
    }

    @Test
    public void validatedByLuhnAlgorithmNegative() throws Exception {
        Assert.assertFalse(CardValidationUtil.validatedByLuhnAlgorithm("1234567890123456".toCharArray()));
    }

}