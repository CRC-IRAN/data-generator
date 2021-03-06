package com.imsweb.datagenerator.naaccr.rule.tumor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.imsweb.datagenerator.naaccr.NaaccrDataGeneratorOptions;
import com.imsweb.datagenerator.naaccr.rule.AddressRule;
import com.imsweb.datagenerator.random.RandomUtils;

public class AddressAtDxRule extends AddressRule {

    // unique identifier for this rule
    public static final String ID = "address-dx";

    /**
     * Constructor.
     */
    public AddressAtDxRule() {
        super(ID, "Address at DX", "addressAtDxState", "addressAtDxCity", "addressAtDxPostalCode", "addressAtDxCountry", "addressAtDxStreetName",
                "addressAtDxSupplementl", "addressAtDxCounty");
    }

    @Override
    public void execute(Map<String, String> record, List<Map<String, String>> otherRecords, NaaccrDataGeneratorOptions options) {
        // if this is not the first tumor, there is an 80% chance to keep previous address at DX
        if (otherRecords != null && !otherRecords.isEmpty() && RandomUtils.nextInt(10) < 8) {
            Map<String, String> otherRecord = otherRecords.get(otherRecords.size() - 1);
            for (String field : Arrays.asList(_fieldState, _fieldCity, _fieldCountry, _fieldPostalCode, _fieldStreetName, _fieldSupplementl, _fieldCounty))
                record.put(field, otherRecord.get(field));
        }
        else
            super.execute(record, otherRecords, options);
    }
}
