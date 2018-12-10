package io.mosip.authentication.core.spi.notification.service;

import java.util.List;
import java.util.Map;

import io.mosip.authentication.core.dto.indauth.AuthRequestDTO;
import io.mosip.authentication.core.dto.indauth.AuthResponseDTO;
import io.mosip.authentication.core.dto.indauth.IdentityInfoDTO;
import io.mosip.authentication.core.dto.otpgen.OtpRequestDTO;
import io.mosip.authentication.core.exception.IdAuthenticationBusinessException;

public interface NotificationService {
	public void sendAuthNotification(AuthRequestDTO authRequestDTO, String refId, AuthResponseDTO authResponseDTO,
			Map<String, List<IdentityInfoDTO>> idInfo, boolean isAuth) throws IdAuthenticationBusinessException ;
	public void sendOtpNotification(OtpRequestDTO otpRequestDto, String otp, Map<String, Object> idResDTO,
			String email, String mobileNumber);
	
}
