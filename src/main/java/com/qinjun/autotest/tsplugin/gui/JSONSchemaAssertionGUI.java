package com.qinjun.autotest.tsplugin.gui;

import com.qinjun.autotest.tsplugin.JSONSchemaAssertion;
import org.apache.jmeter.assertions.gui.AbstractAssertionGui;
import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class JSONSchemaAssertionGUI extends AbstractAssertionGui
    {
        private static final Logger log = LoggerFactory.getLogger(JSONSchemaAssertionGUI.class);
        private static final long serialVersionUID = 1L;
        private JTextField jsonSchema;

        public JSONSchemaAssertionGUI()
        {
            init();
        }

        public String getLabelResource()
        {
            return "jsonschema_assertion_title";
        }

        public String getStaticLabel()
        {
            return "JSON Schema";
        }

        public TestElement createTestElement()
        {
            log.debug("JSONSchemaAssertionGui.createTestElement() called");
            JSONSchemaAssertion el = new JSONSchemaAssertion();
            modifyTestElement(el);
            return el;
        }

        public void modifyTestElement(TestElement inElement)
        {
            log.debug("JSONSchemaAssertionGui.modifyTestElement() called");
            configureTestElement(inElement);
            ((JSONSchemaAssertion)inElement).setJsonSchemaFileName(this.jsonSchema.getText());
        }

        public void clearGui()
        {
            super.clearGui();

            this.jsonSchema.setText("");
        }

        public void configure(TestElement el)
        {
            super.configure(el);
            JSONSchemaAssertion assertion = (JSONSchemaAssertion)el;
            this.jsonSchema.setText(assertion.getJsonSchemaFileName());
        }

        private void init()
        {
            setLayout(new BorderLayout(0, 10));
            setBorder(makeBorder());

            add(makeTitlePanel(), "North");

            JPanel mainPanel = new JPanel(new BorderLayout());

            VerticalPanel assertionPanel = new VerticalPanel();
            assertionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "JSON Schema"));

            HorizontalPanel jsonSchemaPanel = new HorizontalPanel();

            jsonSchemaPanel.add(new JLabel("Schema File"));

            this.jsonSchema = new JTextField(26);
            jsonSchemaPanel.add(this.jsonSchema);

            assertionPanel.add(jsonSchemaPanel);

            mainPanel.add(assertionPanel, "North");
            add(mainPanel, "Center");
        }
    }
