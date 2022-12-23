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

    private static final String WORKERLOGO = "data:image/svg+xml,%3C?xml version='1.0' encoding='UTF-8' standalone='no'?%3E%3Csvg   xmlns:dc='http://purl.org/dc/elements/1.1/'   xmlns:cc='http://creativecommons.org/ns%23'   xmlns:rdf='http://www.w3.org/1999/02/22-rdf-syntax-ns%23'   xmlns:svg='http://www.w3.org/2000/svg'   xmlns='http://www.w3.org/2000/svg'   version='1.0'   width='18'   height='18'   viewBox='0 0 18 18'   preserveAspectRatio='xMidYMid meet'   id='svg1455'%3E  %3Cmetadata     id='metadata1461'%3E    %3Crdf:RDF%3E      %3Ccc:Work         rdf:about=''%3E        %3Cdc:format%3Eimage/svg+xml%3C/dc:format%3E        %3Cdc:type           rdf:resource='http://purl.org/dc/dcmitype/StillImage' /%3E        %3Cdc:title%3E%3C/dc:title%3E      %3C/cc:Work%3E    %3C/rdf:RDF%3E  %3C/metadata%3E  %3Cdefs     id='defs1459' /%3E  %3Cstyle     type='text/css'     id='style1443'%3E .st0%7Bfill:%230085CA;%7D%3C/style%3E  %3Cg     transform='matrix(0.009,0,0,-0.009,0,18)'     fill='%23000000'     stroke='none'     id='g1453'%3E    %3Cpath       class='st0'       d='M 0,1000 V 0 H 1000 2000 V 1000 2000 H 1000 0 Z m 1790,0 V 210 H 1000 210 v 790 790 h 790 790 z'       id='path1445' /%3E    %3Cpath       class='st0'       d='m 1424,1601 c -31,-13 -61,-55 -68,-95 -20,-105 99,-182 192,-126 68,42 79,129 23,192 -22,25 -36,31 -77,34 -27,2 -59,0 -70,-5 z'       id='path1447' /%3E    %3Cpath       class='st0'       d='m 390,1133 c 0,-368 4,-402 50,-497 31,-66 93,-124 168,-159 64,-31 73,-32 187,-32 101,1 129,4 177,24 109,43 200,151 227,272 6,25 11,194 11,392 v 347 h -99 -100 l -3,-342 C 1005,834 1003,790 987,755 951,674 897,638 811,637 708,636 651,671 613,755 c -15,35 -18,82 -21,383 l -4,342 h -99 -99 z'       id='path1449' /%3E    %3Cpath       class='st0'       d='M 1380,855 V 450 h 100 100 v 405 405 h -100 -100 z'       id='path1451' /%3E  %3C/g%3E%3C/svg%3E";

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
                Arrays.asList(BpmnError.getInstance("Error", "Error")));
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

