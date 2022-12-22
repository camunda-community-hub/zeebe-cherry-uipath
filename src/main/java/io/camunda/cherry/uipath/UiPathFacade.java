package io.camunda.cherry.uipath;


import io.camunda.cherry.definition.AbstractConnector;
import io.camunda.cherry.definition.BpmnError;
import io.camunda.cherry.definition.RunnerParameter;
import io.camunda.connector.api.error.ConnectorException;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.zeebe.spring.client.exception.ZeebeBpmnError;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import io.camunda.connector.UiPathConnectorFunction;
import io.camunda.connector.UiPathConnectorRequest;
import io.camunda.connector.UiPathConnectorResult;
import java.util.Arrays;
import java.util.Collections;

@Component
public class UiPathFacade extends AbstractConnector {

    private static final String WORKERLOGO = "data:image/svg+xml,%3C?xml version='1.0' encoding='UTF-8' standalone='no'?%3E%3Csvg   xmlns:dc='http://purl.org/dc/elements/1.1/'   xmlns:cc='http://creativecommons.org/ns%23'   xmlns:rdf='http://www.w3.org/1999/02/22-rdf-syntax-ns%23'   xmlns:svg='http://www.w3.org/2000/svg'   xmlns='http://www.w3.org/2000/svg'   version='1.0'   id='katman_1'   x='0px'   y='0px'   viewBox='0 0 18 18'   xml:space='preserve'   width='18'   height='18'%3E%3Cmetadata   id='metadata873'%3E%3Crdf:RDF%3E%3Ccc:Work       rdf:about=''%3E%3Cdc:format%3Eimage/svg+xml%3C/dc:format%3E%3Cdc:type         rdf:resource='http://purl.org/dc/dcmitype/StillImage' /%3E%3Cdc:title%3E%3C/dc:title%3E%3C/cc:Work%3E%3C/rdf:RDF%3E%3C/metadata%3E%3Cdefs   id='defs871' /%3E%3Cstyle   type='text/css'   id='style860'%3E .st0%7Bfill:%230085CA;%7D%3C/style%3E%3Cg   id='g866'   transform='matrix(0.00968627,0,0,0.00968627,-6.6764499,-2.5113737)'%3E %3Cpath   class='st0'   d='M 2530,2058.7 V 313.3 c 0,-28.2 -22.2,-56.5 -55.6,-56.5 H 761.6 c -33.4,0 -55.6,22.6 -55.6,56.5 v 1745.3 c 0,28.3 22.3,56.5 55.6,56.5 h 1712.8 c 33.3,0 55.6,-28.2 55.6,-56.4 z m -72.3,-17 H 778.3 V 330.3 h 1679.4 z'   id='path862' /%3E %3Cpath   class='st0'   d='m 1401.1,1765 c 250.2,0 411.5,-141.3 411.5,-508.4 V 635.3 h -183.5 v 638.2 c 0,242.9 -94.5,322 -228,322 -133.5,0 -216.9,-79.1 -216.9,-322 V 640.9 H 989.6 v 621.4 c 0,361.5 161.3,502.7 411.5,502.7 z m 756.3,-977.2 c 66.8,0 116.8,-45.2 116.8,-107.3 0,-67.8 -50,-113 -116.8,-113 -66.8,0 -116.8,45.2 -116.8,113 0,62.1 50.1,107.3 116.8,107.3 z m 94.5,146.8 H 2062.8 V 1765 h 189.1 z'   id='path864' /%3E%3C/g%3E%3C/svg%3E";

    public UiPathFacade() {
        super(UiPathConnectorFunction.TYPE_CONNECTOR,
                // List of Input parameters
                Arrays.asList(
                        RunnerParameter.getInstance(UiPathConnectorRequest.PACKAGE_NAME, "Package name", String.class,
                                RunnerParameter.Level.REQUIRED, "UiPath package to use"),
                        RunnerParameter.getInstance(UiPathConnectorRequest.ROBOT_INPUT, "Robot input",
                                JSONObject.class, RunnerParameter.Level.REQUIRED, "JSON input to bot"),
                        RunnerParameter.getInstance(UiPathConnectorRequest.ROBOT_OUTPUT,"JSON output from bot", String.class,
                                RunnerParameter.Level.REQUIRED, "Response from bot, if any"),
                        RunnerParameter.getInstance(UiPathConnectorRequest.CLIENT_ID, "Client ID",
                                String.class, RunnerParameter.Level.REQUIRED, "Client ID"),
                        RunnerParameter.getInstance(UiPathConnectorRequest.CLIENT_KEY, "Client Key",
                                String.class, RunnerParameter.Level.REQUIRED, "Client Key"),
                        RunnerParameter.getInstance(UiPathConnectorRequest.ORGANIZATION_NAME, "Organization Name",
                                String.class, RunnerParameter.Level.REQUIRED, "Organization Name"),
                        RunnerParameter.getInstance(UiPathConnectorRequest.ORGANIZATION_ID, "Organization ID",
                                String.class, RunnerParameter.Level.REQUIRED, "Organization ID"),
                        RunnerParameter.getInstance(UiPathConnectorRequest.TENANT, "Tenant", String.class,
                                RunnerParameter.Level.REQUIRED, "Tenant"),
                        RunnerParameter.getInstance(UiPathConnectorRequest.POLLING_INTERVAL, "Polling interval", Integer.class,
                                RunnerParameter.Level.REQUIRED, "How often to poll for bot completion")

                ),
                // The connector Input class
                UiPathConnectorRequest.class,

                // List of Output parameters
                Collections.singletonList(
                        RunnerParameter.getInstance(UiPathConnectorResult.OUTPUT_VARIABLE,"Output", Object.class,
                                RunnerParameter.Level.REQUIRED, "FileVariable converted (a File Variable Reference)")),
                // The connector Output class
                UiPathConnectorResult.class,

                // List of BPMNErrors TODO
                Arrays.asList(null));
    }

    @Override
    public String getName() {
        return "Ui Path";
    }

    @Override
    public String getDescription() {
        return "Start UiPath bots";
    }

    @Override
    public String getLogo() {
        return WORKERLOGO;
    }

    @Override
    public UiPathConnectorResult execute(OutboundConnectorContext context) throws Exception {
        UiPathConnectorFunction uiPathConnectorFunction = new UiPathConnectorFunction();
        try {
            return uiPathConnectorFunction.execute(context);
        } catch (ConnectorException e) {
            throw new ZeebeBpmnError(e.getErrorCode(), e.getMessage());
        }
    }

}

