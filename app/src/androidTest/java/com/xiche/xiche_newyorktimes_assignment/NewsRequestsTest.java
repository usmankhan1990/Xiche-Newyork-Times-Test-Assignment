package com.xiche.xiche_newyorktimes_assignment;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import helper.Constants;
import helper.HelperMethods;
import network.retrofit.RetrofitCallsInterface;
import network.retrofit.model.ServiceResponseModel;
import retrofit2.Call;
import retrofit2.Response;
import static junit.framework.Assert.assertEquals;

public class NewsRequestsTest {

    private RetrofitCallsInterface rfInterface = null;
    Call<ServiceResponseModel> rmDataCall = null;
    Response<ServiceResponseModel> rmDataResponse = null;
    private HelperMethods helperMethods = HelperMethods.getInstance();
    private Constants constants = Constants.getInstance();

    @Before
    public void setUp() throws Exception {

        rfInterface = helperMethods.getRetrofitInterface(constants.service);
        rmDataCall = rfInterface.getDetails(constants.apikey);
        rmDataResponse = rmDataCall.execute();

    }

    @Test
    public void success200_ResponseCode() throws IOException {

        assertEquals(200, rmDataResponse.code());
        assertEquals(true, rmDataResponse.isSuccessful());
    }

    @Test
    public void successOK_ResponseCode() throws IOException {

        assertEquals("OK", rmDataResponse.message());
    }

    @Test
    public void getArticleSourceText() throws IOException {

        Assert.assertEquals( "The New York Times", rmDataResponse.body().getResults().get(0).getSource() );
    }

    @After
    public void tearDown() {
//        rfInterface = null;
    }

}