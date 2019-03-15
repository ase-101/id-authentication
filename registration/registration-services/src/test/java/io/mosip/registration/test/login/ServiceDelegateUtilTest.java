package io.mosip.registration.test.login;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import io.mosip.registration.constants.RegistrationConstants;
import io.mosip.registration.context.ApplicationContext;
import io.mosip.registration.dto.LoginUserDTO;
import io.mosip.registration.dto.OtpGeneratorRequestDTO;
import io.mosip.registration.dto.ResponseDTO;
import io.mosip.registration.exception.RegBaseCheckedException;
import io.mosip.registration.util.restclient.RequestHTTPDTO;
import io.mosip.registration.util.restclient.RestClientUtil;
import io.mosip.registration.util.restclient.ServiceDelegateUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ApplicationContext.class })
public class ServiceDelegateUtilTest {
	@Mock
	private RestClientUtil restClientUtil;

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@InjectMocks
	private ServiceDelegateUtil delegateUtil;

	@Mock
	private Environment environment;
	
	@Mock
	private RequestHTTPDTO requestHTTPDTO;
	
	@Before
	public void initialize() throws IOException, URISyntaxException {

		ReflectionTestUtils.setField(delegateUtil, "urlPath", "https://integ.mosip.io/authmanager/v1.0/authorize/validateToken");
		ReflectionTestUtils.setField(delegateUtil, "clientId", "clientId");
		ReflectionTestUtils.setField(delegateUtil, "secretKey", "secretKey");

		LoginUserDTO loginDto = new LoginUserDTO();
		loginDto.setUserId("super_admin");
		loginDto.setPassword("super_admin");

		PowerMockito.mockStatic(ApplicationContext.class);
		Map<String, Object> globalParams = new HashMap<>();
		globalParams.put("userDTO", loginDto);
		PowerMockito.when(ApplicationContext.map().get("userDTO")).thenReturn(globalParams);
		PowerMockito.when(ApplicationContext.map()).thenReturn(globalParams);
	}

	/*
	 * @Test public void getURITest() {
	 * 
	 * Map<String, String> requestParamMap = new HashMap<String, String>();
	 * requestParamMap.put(RegistrationConstants.USERNAME_KEY, "yashReddy");
	 * requestParamMap.put(RegistrationConstants.OTP_GENERATED, "099887");
	 * Assert.assertEquals(delegateUtil.getUri(requestParamMap,
	 * "http://localhost:8080/otpmanager/otps").toString(),
	 * "http://localhost:8080/otpmanager/otps?otp=099887&key=yashReddy"); }
	 */

	@Test
	public void getRequestTest() throws Exception {

		ResponseDTO response = new ResponseDTO();
		when(environment.getProperty("otp_validator.service.httpmethod")).thenReturn("GET");
		when(environment.getProperty("otp_validator.service.url")).thenReturn("http://localhost:8080/otpmanager/otps");
		when(environment.getProperty("otp_validator.service.responseType"))
				.thenReturn("io.mosip.registration.dto.OtpValidatorResponseDTO");
		when(environment.getProperty("otp_validator.service.headers")).thenReturn("Content-Type:APPLICATION/JSON");
		when(environment.getProperty("otp_validator.service.authrequired")).thenReturn("false");
		when(environment.getProperty("otp_validator.service.authheader")).thenReturn("Authorization:BASIC");
        Map<String,Object> responseMap=new HashMap<>();
		when(restClientUtil.invoke(Mockito.any())).thenReturn(responseMap);
		Map<String, String> requestParamMap = new HashMap<String, String>();
		requestParamMap.put(RegistrationConstants.USERNAME_KEY, "yashReddy");
		requestParamMap.put(RegistrationConstants.OTP, "099886");
		HttpHeaders header=new HttpHeaders();
		header.add("authorization", "Mosip-TokeneyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdXBlcl9hZG1pbiIsIm1vYmlsZSI6Ijc1ODU2NzUzNjQiLCJtYWlsIjoic3VwZXJfYWRtaW5AbW9zaXAuaW8iLCJyb2xlIjoiU1VQRVJBRE1JTiIsImlhdCI6MTU0ODkxODQ5NywiZXhwIjoxNTQ4OTI0NDk3fQ.illxy8uqsiCVfi7bkZQWMbBOCR1ly3XjuwLMDH12GJNvg2prdWWl4_Fv52Flar32qFXZY6Bir144hCrVrUi-VQ");
		responseMap.put("responseHeader", header);
		responseMap.put("responseBody", response);
		Mockito.when(restClientUtil.invoke((Mockito.anyObject()))).thenReturn(responseMap);
		assertNotNull(delegateUtil.get("otp_validator", requestParamMap, false,"System"));
	}

	@Test
	public void postRequestTest() throws URISyntaxException, HttpClientErrorException, RegBaseCheckedException,
			HttpServerErrorException, ResourceAccessException, SocketTimeoutException {

		ResponseDTO response = new ResponseDTO();
		when(environment.getProperty("otp_generator.service.httpmethod")).thenReturn("POST");
		when(environment.getProperty("otp_generator.service.url")).thenReturn("http://localhost:8080/otpmanager/otps");
		when(environment.getProperty("otp_generator.service.requestType"))
				.thenReturn("io.mosip.registration.dto.OtpGeneratorResponseDTO");
		when(environment.getProperty("otp_generator.service.headers")).thenReturn("Content-Type:APPLICATION/JSON");
		when(environment.getProperty("otp_generator.service.authrequired")).thenReturn("false");
		when(environment.getProperty("otp_generator.service.authheader")).thenReturn("Authorization:BASIC");
		Map<String,Object> responseMap=new HashMap<>();
		HttpHeaders header=new HttpHeaders();
		header.add("authorization", "Mosip-TokeneyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdXBlcl9hZG1pbiIsIm1vYmlsZSI6Ijc1ODU2NzUzNjQiLCJtYWlsIjoic3VwZXJfYWRtaW5AbW9zaXAuaW8iLCJyb2xlIjoiU1VQRVJBRE1JTiIsImlhdCI6MTU0ODkxODQ5NywiZXhwIjoxNTQ4OTI0NDk3fQ.illxy8uqsiCVfi7bkZQWMbBOCR1ly3XjuwLMDH12GJNvg2prdWWl4_Fv52Flar32qFXZY6Bir144hCrVrUi-VQ");
		responseMap.put("responseHeader", header);
		responseMap.put("responseBody", response);		
		when(restClientUtil.invoke(Mockito.any())).thenReturn(responseMap);
		OtpGeneratorRequestDTO generatorRequestDto = new OtpGeneratorRequestDTO();
		generatorRequestDto.setKey("yashReddy");
		assertNotNull(delegateUtil.post("otp_generator", generatorRequestDto,"System"));
	}
	
	@Test
	public void getRequestTestTrue() throws Exception {

		ResponseDTO response = new ResponseDTO();
		when(environment.getProperty("otp_validator.service.httpmethod")).thenReturn("GET");
		when(environment.getProperty("otp_validator.service.url")).thenReturn("http://localhost:8080/otpmanager/otps");
		when(environment.getProperty("otp_validator.service.responseType"))
				.thenReturn("io.mosip.registration.dto.OtpValidatorResponseDTO");
		when(environment.getProperty("otp_validator.service.headers")).thenReturn("Content-Type:APPLICATION/JSON");
		when(environment.getProperty("otp_validator.service.authrequired")).thenReturn("true");
		when(environment.getProperty("otp_validator.service.authheader")).thenReturn("Authorization:BASIC");
		Map<String,Object> responseMap=new HashMap<>();
		Map<String, String> requestParamMap = new HashMap<String, String>();
		requestParamMap.put(RegistrationConstants.USERNAME_KEY, "yashReddy");
		requestParamMap.put(RegistrationConstants.OTP, "099886");
		HttpHeaders header=new HttpHeaders();
		header.add("authorization", "Mosip-TokeneyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdXBlcl9hZG1pbiIsIm1vYmlsZSI6Ijc1ODU2NzUzNjQiLCJtYWlsIjoic3VwZXJfYWRtaW5AbW9zaXAuaW8iLCJyb2xlIjoiU1VQRVJBRE1JTiIsImlhdCI6MTU0ODkxODQ5NywiZXhwIjoxNTQ4OTI0NDk3fQ.illxy8uqsiCVfi7bkZQWMbBOCR1ly3XjuwLMDH12GJNvg2prdWWl4_Fv52Flar32qFXZY6Bir144hCrVrUi-VQ");
		responseMap.put("responseHeader", header);
		responseMap.put("responseBody", response);
		Mockito.when(restClientUtil.invoke((Mockito.anyObject()))).thenReturn(responseMap);
		assertNotNull(delegateUtil.get("otp_validator", requestParamMap, false,"System"));
	}
	
@Test
	public void postRequest() throws URISyntaxException, HttpClientErrorException, RegBaseCheckedException,
			HttpServerErrorException, ResourceAccessException, SocketTimeoutException {

		ResponseDTO response = new ResponseDTO();
		when(environment.getProperty("otp_generator.service.httpmethod")).thenReturn("POST");
		when(environment.getProperty("otp_generator.service.url")).thenReturn("http://localhost:8080/otpmanager/otps");
		when(environment.getProperty("otp_generator.service.requestType"))
				.thenReturn("io.mosip.registration.dto.OtpGeneratorResponseDTO");
		when(environment.getProperty("otp_generator.service.headers")).thenReturn("Content-Type:APPLICATION/JSON");
		when(environment.getProperty("otp_generator.service.authrequired")).thenReturn("true");
		when(environment.getProperty("otp_generator.service.authheader")).thenReturn("Authorization:oauth");
		Map<String,Object> responseMap=new HashMap<>();
		HttpHeaders header=new HttpHeaders();
		header.add("authorization", "Mosip-TokeneyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdXBlcl9hZG1pbiIsIm1vYmlsZSI6Ijc1ODU2NzUzNjQiLCJtYWlsIjoic3VwZXJfYWRtaW5AbW9zaXAuaW8iLCJyb2xlIjoiU1VQRVJBRE1JTiIsImlhdCI6MTU0ODkxODQ5NywiZXhwIjoxNTQ4OTI0NDk3fQ.illxy8uqsiCVfi7bkZQWMbBOCR1ly3XjuwLMDH12GJNvg2prdWWl4_Fv52Flar32qFXZY6Bir144hCrVrUi-VQ");
		responseMap.put("responseHeader", header);
		responseMap.put("responseBody", response);		
		when(restClientUtil.invoke(Mockito.any())).thenReturn(responseMap);
		OtpGeneratorRequestDTO generatorRequestDto = new OtpGeneratorRequestDTO();
		generatorRequestDto.setKey("yashReddy");
		assertNotNull(delegateUtil.post("otp_generator", generatorRequestDto,"System"));
	}
	
	

}
