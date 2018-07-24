package com.qinjun.autotest.tsplugin;

import org.apache.commons.io.FileUtils;
import org.apache.jmeter.assertions.Assertion;
import org.apache.jmeter.assertions.AssertionResult;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class JSONSchemaAssertion  extends AbstractTestElement
        implements Serializable, Assertion {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(JSONSchemaAssertion.class);

    public AssertionResult getResult(SampleResult response)
    {
        AssertionResult result = new AssertionResult(getName());

        String resultData = response.getResponseDataAsString();
        if (resultData.length() == 0) {
            return result.setResultForNull();
        }
        String jsonFileName = getJsonSchemaFileName();
        log.debug("jsonString: {}, jsonFileName: {}", resultData, jsonFileName);
        if ((jsonFileName == null) || (jsonFileName.length() == 0)) {
            result.setResultForFailure("FileName is required");
        } else {
            setSchemaResult(result, resultData, jsonFileName);
        }
        return result;
    }

    public void setJsonSchemaFileName(String jsonSchemaFileName)
            throws IllegalArgumentException
    {
        setProperty("jsonschema_assertion_filename", jsonSchemaFileName);
    }

    public String getJsonSchemaFileName()
    {
        return getPropertyAsString("jsonschema_assertion_filename");
    }

    private void setSchemaResult(AssertionResult result, String jsonStr, String jsonSchemaFileName)
    {
        try
        {
            JSONObject rawSchema = new JSONObject(new JSONTokener(FileUtils.readFileToString(new File(jsonSchemaFileName))));
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate((new JSONObject(jsonStr)));
        }
        catch (IOException e)
        {
            log.warn("IO error", e);
            result.setResultForFailure(e.getMessage());
        }
    }

    
}
