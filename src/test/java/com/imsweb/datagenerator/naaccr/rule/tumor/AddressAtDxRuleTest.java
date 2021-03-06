package com.imsweb.datagenerator.naaccr.rule.tumor;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.imsweb.datagenerator.naaccr.NaaccrDataGeneratorOptions;

public class AddressAtDxRuleTest {

    private AddressAtDxRule _rule = new AddressAtDxRule();

    @Test
    public void testExecute() {

        // if not state is available, address fields should be blank
        Map<String, String> rec = new HashMap<>();
        _rule.execute(rec, null, null);
        Assert.assertNull(rec.get("addressAtDxCity"));
        Assert.assertNull(rec.get("addressAtDxState"));
        Assert.assertNull(rec.get("addressAtDxPostalCode"));
        Assert.assertNull(rec.get("addressAtDxStreetName"));
        Assert.assertNull(rec.get("addressAtDxCounty"));

        // generate 10 random addresses and verify pattern for each
        NaaccrDataGeneratorOptions options = new NaaccrDataGeneratorOptions();
        options.setState("MD");
        for (int i = 0; i < 10; i++) {
            rec.clear();
            _rule.execute(rec, null, options);

            // verify city name pattern
            Assert.assertTrue(rec.get("addressAtDxCity").matches("^[\\w \\.]+$"));
            // verify 2 letter state
            Assert.assertTrue(rec.get("addressAtDxState").matches("^[A-Z]{2}$"));
            // verify 5 or 9 digit zip code
            Assert.assertTrue(rec.get("addressAtDxPostalCode").matches("^\\d{5}$") || rec.get("addressCurrentPostalCode").matches("^\\d{9}$"));
            // verify length and pattern of street name
            Assert.assertTrue(rec.get("addressAtDxStreetName").length() <= 60);
            Assert.assertTrue(rec.get("addressAtDxStreetName").matches("^\\d+ [\\w \\.]+ \\w+\\.?$"));
            // verify 3 digit county code
            Assert.assertTrue(rec.get("addressAtDxCounty").matches("^\\d{3}$"));
        }
    }
}
