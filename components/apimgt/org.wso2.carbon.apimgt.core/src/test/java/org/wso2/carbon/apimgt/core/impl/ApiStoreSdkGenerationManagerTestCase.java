package org.wso2.carbon.apimgt.core.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.apimgt.core.SampleTestObjectCreator;
import org.wso2.carbon.apimgt.core.api.APIStore;
import org.wso2.carbon.apimgt.core.exception.APIManagementException;
import org.wso2.carbon.apimgt.core.exception.ApiStoreSdkGenerationException;
import org.wso2.carbon.apimgt.core.models.API;

import java.io.File;
import java.util.UUID;

@RunWith(PowerMockRunner.class)
@PrepareForTest(APIManagerFactory.class)
public class ApiStoreSdkGenerationManagerTestCase {
    private static Logger log = LoggerFactory.getLogger(ApiStoreSdkGenerationManagerTestCase.class);

    private static final String USER = "admin";
    private static final String LANGUAGE = PetStoreSwaggerTestCase.CORRECT_LANGUAGE;
    private static final int MIN_SDK_SIZE = 0;
    private static final String SWAGGER_PET_STORE = PetStoreSwaggerTestCase.SWAGGER_PET_STORE_CORRECT;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGenerateSdkForApi() throws APIManagementException, ApiStoreSdkGenerationException {
        String apiId = UUID.randomUUID().toString();

        APIManagerFactory instance = Mockito.mock(APIManagerFactory.class);
        PowerMockito.mockStatic(APIManagerFactory.class);
        PowerMockito.when(APIManagerFactory.getInstance()).thenReturn(instance);
        APIStore apiStore = Mockito.mock(APIStoreImpl.class);

        Mockito.when(instance.getAPIConsumer(USER)).thenReturn(apiStore);

        API api = SampleTestObjectCreator.createDefaultAPI().build();
        Mockito.when(apiStore.getAPIbyUUID(apiId)).thenReturn(api);
        Mockito.when(apiStore.getApiSwaggerDefinition(apiId)).thenReturn(SWAGGER_PET_STORE);

        ApiStoreSdkGenerationManager sdkGenerationManager = new ApiStoreSdkGenerationManager();
        String pathToZip = sdkGenerationManager.generateSdkForApi(apiId, LANGUAGE, USER);

        File sdkZipFile = new File(pathToZip);
        Assert.assertTrue(sdkZipFile.exists() && sdkZipFile.length() > MIN_SDK_SIZE);
    }

    private static void printTestMethodName() {
        log.info("------------------ Test method: " + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " ------------------");
    }
}
